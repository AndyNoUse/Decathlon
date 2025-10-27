const el = (id) => document.getElementById(id);
const err = el('error');
const msg = el('msg');
const last = el('lastscore');
const API = '/com/example/decathlon/api';

function setError(text) { err.textContent = text; }
function setMsg(text) { msg.textContent = text; }

const DEC_EVENTS = [
  { id: "Dec_100m", label: "100m (s)" },
  { id: "Dec_LongJump", label: "Long Jump (cm)" },
  { id: "Dec_ShotPut", label: "Shot Put (m)" },
  { id: "Dec_HighJump", label: "High Jump (cm)" },
  { id: "Dec_400m", label: "400m (s)" },
  { id: "Dec_110mHurdles", label: "110m Hurdles (s)" },
  { id: "Dec_DiscusThrow", label: "Discus (m)" },
  { id: "Dec_PoleVault", label: "Pole Vault (cm)" },
  { id: "Dec_JavelinThrow", label: "Javelin (m)" },
  { id: "Dec_1500m", label: "1500m (s)" }
];

const HEP_EVENTS = [
  { id: "Hep_100mHurdles", label: "100m Hurdles (s)" },
  { id: "Hep_HighJump", label: "High Jump (cm)" },
  { id: "Hep_ShotPut", label: "Shot Put (m)" },
  { id: "Hep_200m", label: "200m (s)" },
  { id: "Hep_LongJump", label: "Long Jump (cm)" },
  { id: "Hep_JavelinThrow", label: "Javelin (m)" },
  { id: "Hep_800m", label: "800m (s)" }
];

function currentEvents() {
  return el('mode').value === 'dec' ? DEC_EVENTS : HEP_EVENTS;
}

function populateEventSelect() {
  const s = el('event');
  s.innerHTML = currentEvents().map(e => `<option value="${e.id}">${e.label}</option>`).join('');
}

function renderTableHeader() {
  const thead = el('thead');
  const cols = currentEvents().map(e => `<th>${e.label.split(' (')[0]}</th>`).join('');
  thead.innerHTML = `<tr><th>Name</th>${cols}<th>Total</th></tr>`;
}

el('mode').addEventListener('change', async () => {
  populateEventSelect();
  renderTableHeader();
  await renderStandings();
});

el('add').addEventListener('click', async () => {
  const name = el('name').value;
  try {
    const res = await fetch(`${API}/competitors`, {
      method: 'POST', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name })
    });
    if (!res.ok) {
      const t = await res.text();
      setError(t || 'Failed to add competitor');
    } else {
      setMsg('Added');
    }
    await renderStandings();
  } catch (e) {
    setError('Network error');
  }
});

el('save').addEventListener('click', async () => {
  const body = {
    name: el('name2').value,
    event: el('event').value,
    raw: parseFloat(el('raw').value)
  };
  try {
    const res = await fetch(`${API}/score`, {
      method: 'POST', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    if (!res.ok) {
      const t = await res.text();
      setError(t || 'Score failed');
      last.textContent = '';
      return;
    }
    const json = await res.json();
    setMsg(`Saved: ${json.points} pts`);
    last.textContent = `Last saved: ${json.points} pts`;
    await renderStandings();
  } catch (e) {
    setError('Score failed');
  }
});

let sortBroken = false;

el('export').addEventListener('click', async () => {
  try {
    const res = await fetch(`${API}/export.csv`);
    if (!res.ok) { setError('Export failed'); return; }
    const text = await res.text();
    const blob = new Blob([text], { type: 'text/csv;charset=utf-8' });
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'results.csv';
    a.click();
    sortBroken = true;
  } catch (e) {
    setError('Export failed');
  }
});

async function renderStandings() {
  try {
    const res = await fetch(`${API}/standings`);
    if (!res.ok) { setError('Could not load standings'); return; }
    const data = await res.json();
    const evIds = currentEvents().map(e => e.id);
    const rows = (sortBroken ? data : data.sort((a,b)=> (b.total||0)-(a.total||0)))
      .map(r => {
        const cells = evIds.map(id => r.scores?.[id] ?? '').map(v => `<td>${v}</td>`).join('');
        return `<tr><td>${escapeHtml(r.name)}</td>${cells}<td>${r.total ?? 0}</td></tr>`;
      }).join('');
    el('standings').innerHTML = rows;
  } catch (e) {
    setError('Could not load standings');
  }
}

function escapeHtml(s){
  return String(s).replace(/[&<>"]/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[c]));
}

populateEventSelect();
renderTableHeader();
renderStandings();

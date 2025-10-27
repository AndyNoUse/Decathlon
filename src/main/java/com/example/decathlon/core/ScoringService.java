package com.example.decathlon.core;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ScoringService {
    public enum Type { TRACK, FIELD }
    public record EventDef(String id, Type type, double A, double B, double C, String unit) {}

    private final Map<String, EventDef> deca = Map.ofEntries(
            Map.entry("Dec_100m", new EventDef("Dec_100m", Type.TRACK, 25.4347, 18.0, 1.81, "s")),
            Map.entry("Dec_LongJump", new EventDef("Dec_LongJump", Type.FIELD, 0.14354, 220.0, 1.4, "cm")),
            Map.entry("Dec_ShotPut", new EventDef("Dec_ShotPut", Type.FIELD, 51.39, 1.5, 1.05, "m")),
            Map.entry("Dec_HighJump", new EventDef("Dec_HighJump", Type.FIELD, 0.8465, 75.0, 1.42, "cm")),
            Map.entry("Dec_400m", new EventDef("Dec_400m", Type.TRACK, 1.53775, 82.0, 1.81, "s")),
            Map.entry("Dec_110mHurdles", new EventDef("Dec_110mHurdles", Type.TRACK, 5.74352, 28.5, 1.92, "s")),
            Map.entry("Dec_DiscusThrow", new EventDef("Dec_DiscusThrow", Type.FIELD, 12.91, 4.0, 1.1, "m")),
            Map.entry("Dec_PoleVault", new EventDef("Dec_PoleVault", Type.FIELD, 0.2797, 100.0, 1.35, "cm")),
            Map.entry("Dec_JavelinThrow", new EventDef("Dec_JavelinThrow", Type.FIELD, 10.14, 7.0, 1.08, "m")),
            Map.entry("Dec_1500m", new EventDef("Dec_1500m", Type.TRACK, 0.03768, 480.0, 1.85, "s"))
    );

    private final Map<String, EventDef> hep = Map.ofEntries(
            Map.entry("Hep_100mHurdles", new EventDef("Hep_100mHurdles", Type.TRACK, 9.23076, 26.7, 1.835, "s")),
            Map.entry("Hep_HighJump", new EventDef("Hep_HighJump", Type.FIELD, 1.84523, 75.0, 1.348, "cm")),
            Map.entry("Hep_ShotPut", new EventDef("Hep_ShotPut", Type.FIELD, 56.0211, 1.5, 1.05, "m")),
            Map.entry("Hep_200m", new EventDef("Hep_200m", Type.TRACK, 4.99087, 42.5, 1.81, "s")),
            Map.entry("Hep_LongJump", new EventDef("Hep_LongJump", Type.FIELD, 0.188807, 210.0, 1.41, "cm")),
            Map.entry("Hep_JavelinThrow", new EventDef("Hep_JavelinThrow", Type.FIELD, 15.9803, 3.8, 1.04, "m")),
            Map.entry("Hep_800m", new EventDef("Hep_800m", Type.TRACK, 0.11193, 254.0, 1.88, "s"))
    );

    public EventDef get(String id) {
        EventDef e = deca.get(id);
        if (e != null) return e;
        return hep.get(id);
    }

    public int score(String eventId, double raw) {
        EventDef e = get(eventId);
        if (e == null) return 0;
        double points;
        if (e.type == Type.TRACK) {
            double x = e.B - raw;
            if (x <= 0) return 0;
            points = e.A * Math.pow(x, e.C);
        } else {
            double x = raw - e.B;
            if (x <= 0) return 0;
            points = e.A * Math.pow(x, e.C);
        }
        return (int)Math.floor(points);
    }
}

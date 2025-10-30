package com.example.decathlon.test.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class MyStepdefs {
    private WebDriver driver;
    private String name = "Love";

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("I am on {string}")
    public void iAmOn(String url) {
        driver = new ChromeDriver();
        driver.get(url);
    }

    @When("I add my name")
    public void iAddMyName() {
        driver.findElement(By.cssSelector("input#name")).sendKeys(name);
        driver.findElement(By.cssSelector("button#add")).click();
    }

    @Then("I assert the name is {string}")
    public void iAssertTheNameIs(String Name) {
        WebElement nameInList = driver.findElement(By.cssSelector("td"));
        assert nameInList.getText().equals(Name);
    }

    @When("I choose {}")
    public void iChoose(String mode) { //DECATHLON ELLER HEPTATHLON!!!
        Select dropdown = new Select(driver.findElement(By.cssSelector("select#mode")));
        dropdown.selectByVisibleText(mode);
    }

    @When("I pick sports {}")
    public void iPickSports(String sportsEvent) { //SPORTGRENAR!
        Select dropdown = new Select(driver.findElement(By.cssSelector("select#mode")));
        dropdown.selectByVisibleText(sportsEvent);
    }

    @And("I write {} and click Add competitor")
    public void iWriteAndClickAddCompetitor(String name) {
        driver.findElement(By.cssSelector("input#name")).sendKeys(name);
        driver.findElement(By.cssSelector("button#add")).click();
        System.out.println("Jag skriver in " + name + " och klickar 'add competitor'");
    }

    @And("I write same name down in Enter Result")
    public void iWriteSameNameDownInEnterResult() {
        WebElement sameName = driver.findElement(By.cssSelector("input#name"));
        System.out.println(sameName.getText());
        System.out.println("Jag skriver in samma namn nere vid 'Enter Result'");
    }

    @And("enter {}")
    public void enter(String eventResult) {
        driver.findElement(By.cssSelector("input#raw")).sendKeys(eventResult);

    }

    @And("I save score")
    public void iSaveScore() {
        driver.findElement(By.cssSelector("button#save")).click();
    }

    @Then("I export CSV and it should match")
    public void iExportCSVAndItShouldMatch() {
        driver.findElement(By.cssSelector("button#export")).click();
    }

    @Then("Site should react on different name inputs with {}")
    public void SiteShouldReactOnDifferentNameInputsWith(String expectedResult) {
        WebElement nameInput = driver.findElement(By.id("name"));
        assert nameInput.getText().equals(expectedResult);
    }

    @Then("points should be {}")
    public void pointsShouldBe(String expectedPoints) {
        WebElement actualPoints = driver.findElement(By.cssSelector("input#points"));
        assert actualPoints.getText().equals(expectedPoints);

    }
}

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
    public void iChoose(String mode) {
        Select dropdown = new Select(driver.findElement(By.cssSelector("select#mode")));
        dropdown.selectByVisibleText(mode);
    }

    @And("I write {} and click Add competitor")
    public void iWriteAndClickAddCompetitor(String name) {
        driver.findElement(By.cssSelector("input#name")).sendKeys(name);

    }

    @And("I write same name down in Enter Result")
    public void iWriteSameNameDownInEnterResult() {

    }

    @And("enter {}")
    public void enter(String arg0) {

    }

    @And("I save score")
    public void iSaveScore() {

    }

    @Then("Standings should update with the {}")
    public void standingsShouldUpdateWithThe(String arg0) {

    }

    @Then("I export CSV and it should match")
    public void iExportCSVAndItShouldMatch() {
    }

    @Then("Standings should update with {}")
    public void standingsShouldUpdateWith(String arg0) {
    }

    @Then("points should be {}")
    public void pointsShouldBe(String arg0) {
    }
}

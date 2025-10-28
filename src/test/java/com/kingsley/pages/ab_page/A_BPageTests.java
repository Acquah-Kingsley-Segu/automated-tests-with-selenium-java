package com.kingsley.pages.ab_page;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.Assertion;

import java.time.Duration;

public class A_BPageTests {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/abtest");
    }

    @Test
    public void validateABPageHeaderTextTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement headerParentContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));
        WebElement headerText = headerParentContainer.findElement(By.tagName("h3"));

        Assert.assertEquals(headerText.getText(), "A/B Test Control");

    }

    @Test
    public void validateABPageParagraphTextTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement paragraphParentContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));
        WebElement paragraphText = paragraphParentContainer.findElement(By.tagName("p"));

        Assert.assertTrue(paragraphText.getText().contains("e.g."));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

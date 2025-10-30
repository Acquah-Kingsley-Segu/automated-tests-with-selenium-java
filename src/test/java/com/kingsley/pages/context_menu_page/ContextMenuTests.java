package com.kingsley.pages.context_menu_page;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ContextMenuTests {
    WebDriver browser;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        browser = new FirefoxDriver();
        browser.manage().window().maximize();
        browser.get("https://the-internet.herokuapp.com/context_menu");
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void validatePageLoadedTest(){

        WebElement parentDivElement = browser.findElement(By.id("content"));
        WebElement pageHeader = parentDivElement.findElement(By.tagName("h3"));

        Assert.assertEquals(pageHeader.getText(), "Context Menu");
    }

    @Test
    public void validateInstructionTests(){
        WebElement parentDivElement = browser.findElement(By.id("content"));

        List<WebElement> paragraphs = parentDivElement.findElements(By.tagName("p"));

        String instructionOne = paragraphs.getFirst().getText();
        String instructionTwo = paragraphs.get(1).getText();

        Assert.assertTrue(instructionOne.contains("right-click menu"));
        Assert.assertTrue(instructionTwo.contains("JavaScript alert"));
    }

    @Test
    public void validateContextMenuAttributeTest(){
        WebElement contextMenuElement = browser.findElement(By.id("hot-spot"));

        Assert.assertEquals(contextMenuElement.getAttribute("oncontextmenu"), "displayMessage()");
    }

    @Test
    public void validateContentMenuRightClickOpensAlertTest(){
        // get context menu element
        WebElement contextElement = browser.findElement(By.id("hot-spot"));

        // right-click context menu
        Actions actions = new Actions(browser);
        actions.contextClick(contextElement).perform();

        // wait for alert to pop up
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());

        // switch to alert menu
        Alert alert = browser.switchTo().alert();

        // validate alert message
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "You selected a context menu");

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        alert.accept();
    }

    @AfterMethod
    public void tearDown(){
        browser.quit();
    }
}

package com.kingsley.pages.add_remove_page;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AddRemoveButtonTests {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp(){
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @BeforeMethod
    public void newTestEnvironment(){
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
    }

    @Test
    public void clickAddButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement addButtonParentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));
        WebElement addButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        addButtonParentElement.findElement(By.tagName("button"))
                ));

        Thread.sleep(5000);
        addButton.click();

        // Verify delete button is added to the DOM
        WebElement deleteParentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("elements")));
        Assert.assertTrue(deleteParentElement.findElement(By.tagName("button")).isDisplayed());
    }

    @Test
    public void clickRemoveButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement addButtonParentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));
        WebElement addButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        addButtonParentElement.findElement(By.tagName("button"))
                )
        );
        Thread.sleep(3000);
        addButton.click();
        WebElement deleteButtonParentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("elements")));
        WebElement deleteButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        deleteButtonParentElement.findElement(By.tagName("button"))
                )
        );
        Thread.sleep(3000);
        deleteButton.click();

        List<WebElement> childElements =  deleteButtonParentElement.findElements(By.tagName("button"));
        Assert.assertTrue(childElements.isEmpty(), "Child elements should be empty");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}

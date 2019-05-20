package com.epam.config;

import com.epam.utility.PropertiesUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ActionKeywords {

    private static WebDriver driver;
    private static Properties properties;

    private static WebElement getElement(String selector) {
        return driver.findElement(By.xpath(selector));
    }

    public static void openBrowser(String object) {
        System.setProperty("webdriver.gecko.driver", "C:\\Programs\\WebDrivers\\firefox\\geckodriver.exe");
        driver = new FirefoxDriver();
        properties = new PropertiesUtils().getProperties();
    }

    public static void navigate(String object) {
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(Constants.URL);
    }

    public static void click(String object){
        WebElement element = getElement(properties.getProperty(object));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    public static void inputUsername(String object) {
        WebElement element = getElement(properties.getProperty(object));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(Constants.USERNAME);
    }

    public static void inputPassword(String object) {
        WebElement element = getElement(properties.getProperty(object));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(Constants.PASSWORD);
    }

    public static void waitFor(String object) throws InterruptedException {
        Thread.sleep(80000);
    }

    public static void closeBrowser(String object) {
        if (null != driver) {
            driver.quit();
        }
    }
}



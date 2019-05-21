package com.epam.config;

import com.epam.exception.ActionExecutionException;
import com.epam.utility.Log;
import com.epam.utility.PropertiesUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;

public class ActionKeywords {

    private static WebDriver driver;
    private static Properties properties;

    private static WebElement getElement(String selector) {
        return driver.findElement(By.xpath(selector));
    }

    public static void openBrowser(String object) throws ActionExecutionException {
        try {
            Log.info("Trying opening browser");
            System.setProperty("webdriver.gecko.driver", "C:\\Programs\\WebDrivers\\firefox\\geckodriver.exe");
            driver = new FirefoxDriver();
            properties = new PropertiesUtils().getProperties();
            Log.info("Browser open");
        } catch (Exception e) {
            if (null != driver) {
                driver.quit();
            }
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void navigate(String object) throws ActionExecutionException {
        try {
            Log.info("Navigating to URL");
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(Constants.URL);
        } catch (Exception e) {
            if (null != driver) {
                driver.quit();
            }
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void click(String object) throws ActionExecutionException {
        try {
            Log.info("Clicking on Webelement " + object);
            WebElement element = getElement(properties.getProperty(object));
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
            element.click();
        } catch (Exception e) {
            if (null != driver) {
                driver.quit();
            }
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void inputUsername(String object) throws ActionExecutionException {
        try {
            Log.info("Entering the text in UserName");
            WebElement element = getElement(properties.getProperty(object));
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
            element.sendKeys(Constants.USERNAME);
        } catch (Exception e) {
            if (null != driver) {
                driver.quit();
            }
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void inputPassword(String object) throws ActionExecutionException {
        try {
            Log.info("Entering the text in Password");
            WebElement element = getElement(properties.getProperty(object));
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
            element.sendKeys(Constants.PASSWORD);
        } catch (Exception e) {
            if (null != driver) {
                driver.quit();
            }
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void waitFor(String object) throws ActionExecutionException {
        try {
            Log.info("Wait some time until user enter captcha");
            Thread.sleep(80000);
        } catch (Exception e) {
            if (null != driver) {
                driver.quit();
            }
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void closeBrowser(String object) throws ActionExecutionException {
        try {
            Log.info("Closing the browser");
            if (null != driver) {
                driver.quit();
            }
        } catch (Exception e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}



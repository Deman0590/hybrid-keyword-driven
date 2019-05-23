package com.epam.config;

import com.epam.exception.ActionExecutionException;
import com.epam.utility.Log;
import com.epam.utility.PropertiesUtils;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;

public class ActionKeywords {

    private static WebDriver driver;
    private static Properties properties;

    private static WebElement getElement(String selector) {
        return driver.findElement(By.xpath(selector));
    }

    public static void openBrowser(String object, String data) throws ActionExecutionException {
        try {
            Log.info("Trying opening browser");
            if (data.equalsIgnoreCase("Firefox")) {
                System.setProperty("webdriver.gecko.driver", "C:\\Programs\\WebDrivers\\firefox\\geckodriver.exe");
                driver = new FirefoxDriver();
            } else if (data.equalsIgnoreCase("Chrome")) {
                System.setProperty("webdriver.chrome.driver", "C:\\Programs\\WebDrivers\\chrome\\chromedriver.exe");
                driver = new ChromeDriver();
            }
            properties = new PropertiesUtils().getProperties();
            Log.info("Browser open");
        } catch (Exception e) {
            closeBrowser("", "");
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void navigate(String object, String data) throws ActionExecutionException {
        try {
            Log.info("Navigating to URL");
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(Constants.URL);
        } catch (Exception e) {
            closeBrowser("", "");
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void click(String object, String data) throws ActionExecutionException {
        try {
            Log.info("Clicking on Webelement " + object);
            WebElement element = getElement(properties.getProperty(object));
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
            element.click();
        } catch (Exception e) {
            closeBrowser("", "");
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void input(String object, String data) throws ActionExecutionException {
        try {
            Log.info("Entering the text in Input field");
            WebElement element = getElement(properties.getProperty(object));
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
            element.sendKeys(data);
        } catch (Exception e) {
            closeBrowser("", "");
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void waitFor(final String object, String data) throws ActionExecutionException {
        try {
            Log.info("Wait some time until user enter captcha");
            WebElement element = (new WebDriverWait(driver, 120))
                    .until(new ExpectedCondition<WebElement>() {
                        @NullableDecl
                        public WebElement apply(@NullableDecl WebDriver webDriver) {
                            return getElement(properties.getProperty(object));
                        }
                    });
        } catch (Exception e) {
            closeBrowser("", "");
            throw new ActionExecutionException(e.getMessage());
        }
    }

    public static void closeBrowser(String object, String data) throws ActionExecutionException {
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



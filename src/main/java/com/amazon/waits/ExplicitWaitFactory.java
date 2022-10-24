package com.amazon.waits;

import com.amazon.driver.DriverManager;
import com.amazon.enums.WaitStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ExplicitWaitFactory {
    private ExplicitWaitFactory() {
    }

    public static WebElement performExplicitWait(WaitStrategy waitStrategy, By by) {

        WebElement element = null;
        if (waitStrategy == WaitStrategy.CLICKABLE) {
            element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(by));
        }
        else if (waitStrategy == WaitStrategy.PRESENCE) {
            element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(by));
        }
        else if (waitStrategy == WaitStrategy.VISIBLE) {
            element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
        }
        else if (waitStrategy == WaitStrategy.NONE) {
            System.out.println("Not Waiting for anything");
            element = DriverManager.getDriver().findElement(by);
        }
        return element;
    }
}

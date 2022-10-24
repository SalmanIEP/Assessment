package com.amazon.page;

import com.amazon.driver.DriverManager;
import com.amazon.enums.WaitStrategy;
import com.amazon.waits.ExplicitWaitFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static com.amazon.config.ConfigManager.configuration;
import static org.openqa.selenium.support.PageFactory.initElements;

public class AbstractPageObject {

    protected AbstractPageObject() {
        initElements(new AjaxElementLocatorFactory(DriverManager.getDriver(), configuration().timeout()), this);
    }
    protected void click(By by, WaitStrategy waitStrategy) {
        // DriverManager.getDriver().findElement(by).click();
        ExplicitWaitFactory.performExplicitWait(waitStrategy, by).click();
    }

    protected void sendKeys(By by, String value, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(waitStrategy, by).sendKeys(value);
    }

    protected void clear(By by, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(waitStrategy, by).clear();

    }

    protected void clearAndSendKeys(By by, String value, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        element.clear();
        element.sendKeys(value);
    }


}

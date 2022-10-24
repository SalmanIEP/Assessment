package com.amazon.page.shopping;

import com.amazon.driver.TargetFactory;
import com.amazon.enums.WaitStrategy;
import com.amazon.page.shopping.common.NavigationMenuPage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class ItemDetailPage extends NavigationMenuPage {
    private static final Logger logger = LogManager.getLogger(ItemDetailPage.class);
    @FindBy(id = "featurebullets_feature_div")
    private List<WebElement> aboutThisItem;

    @Step
    public void verifyAboutItemSectionAvailability() {
        Assert.assertEquals(aboutThisItem.size(),1);
        logger.info(aboutThisItem.get(0).getText());
    }


}

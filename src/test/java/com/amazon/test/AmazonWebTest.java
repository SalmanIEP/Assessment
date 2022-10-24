package com.amazon.test;
import com.amazon.BaseWeb;
import com.amazon.dp.DataProviders;
import com.amazon.driver.TargetFactory;
import com.amazon.page.shopping.ItemsListingPage;
import com.amazon.page.shopping.common.NavigationMenuPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class AmazonWebTest extends BaseWeb {

    @Test(description = "verify the About Section is displayed against the item",dataProvider = "Shopping_Data", dataProviderClass = DataProviders.class)

    public void TestItemAboutSection(String itemCategory, String itemType, String filterType, String filterName, String sortBy) throws ParseException, InterruptedException {

        var navigation = new NavigationMenuPage();
        var listingPage = new ItemsListingPage();

        navigation.selectHamburger().
                selectItemCategory(itemCategory).
                selectItem(itemType);

        listingPage.filterListingBy(filterType,filterName)
                .sortListings(sortBy)
                .selectItemWithSecondHighestPrice()
                .verifyAboutItemSectionAvailability();

    }
}

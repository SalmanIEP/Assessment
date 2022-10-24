package com.amazon.page.shopping;

import com.amazon.driver.DriverManager;
import com.amazon.enums.WaitStrategy;
import com.amazon.page.shopping.common.NavigationMenuPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import javax.swing.text.MaskFormatter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class ItemsListingPage extends NavigationMenuPage {

    @FindBy(id = "s-result-sort-select")
    private WebElement sortBy;

    @FindBy(className = "a-price-whole")
    private List<WebElement> allItemsWholePrices;

    public ItemsListingPage() {
    }

    @Step
    public ItemsListingPage filterListingBy(String filterBy, String filterName) {
        click(By.xpath("//span[text()='" + filterBy + "']/ancestor::div[2]//span[text()='" + filterName + "']/parent::a"), WaitStrategy.CLICKABLE);
        return this;
    }

    @Step
    public ItemsListingPage sortListings(String value) throws InterruptedException {
        Thread.sleep(1000);
        new Select(sortBy).selectByVisibleText(value);
        return this;
    }

    @Step
    public ItemDetailPage selectItemWithSecondHighestPrice() throws ParseException {
        List<Double> Prices = new ArrayList<>();
        for (WebElement item : allItemsWholePrices) {
            Double price = Double.parseDouble(item.getText().replace(",", ""));
            Prices.add(price);
        }
        Optional<Double> secondHighestPrice = Prices.stream().sorted(Comparator.reverseOrder()).limit(2).skip(1).findFirst();
        MaskFormatter format = new MaskFormatter("#,##,###");
        format.setValueContainsLiteralCharacters(false);
        String price = format.valueToString(secondHighestPrice.get());

        var itemWithSecondHighestPrice = allItemsWholePrices.stream().filter(u -> u.getText().equals(price));
        itemWithSecondHighestPrice.toList().get(0).click();

        ArrayList<String> tabs = new ArrayList<String> (DriverManager.getDriver().getWindowHandles());
        DriverManager.getDriver().switchTo().window(tabs.get(1));

        return new ItemDetailPage();
    }


}

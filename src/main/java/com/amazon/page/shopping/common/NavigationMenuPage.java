/*
 * MIT License
 *
 * Copyright (c) 2018 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.amazon.page.shopping.common;

import com.amazon.driver.DriverManager;
import com.amazon.enums.WaitStrategy;
import com.amazon.page.AbstractPageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class NavigationMenuPage extends AbstractPageObject {

    @FindBy(id = "nav-hamburger-menu")
    private WebElement hamburger;

    @Step
    public NavigationMenuPage selectHamburger() {
        hamburger.click();
        return this;
    }

    @Step
    public NavigationMenuPage selectItemCategory(String itemCategory) {
        click(By.xpath("//div[text()='"+itemCategory+"']"),WaitStrategy.CLICKABLE);
       return this;
    }

    @Step
    public NavigationMenuPage selectItem(String itemName) {
        click(By.xpath("//a[text()='"+itemName+"']"),WaitStrategy.CLICKABLE);
        return this;
    }

    @Step
    public NavigationMenuPage selectBrand(String itemName) {
        click(By.xpath("//a[text()='"+itemName+"']"),WaitStrategy.CLICKABLE);
        return this;
    }












}

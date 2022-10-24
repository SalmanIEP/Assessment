package com.amazon.dp;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "Shopping_Data")
    public static Object[][] shoppingData(){
        return new Object[][] {{"TV, Appliances, Electronics", "Televisions" , "Brands","Samsung","Price: High to Low"}};
    }
}

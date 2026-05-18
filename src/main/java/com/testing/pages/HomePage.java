package com.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;
    private By searchBox = By.id("filter_name_desktop");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToLoginPage() {
        driver.get("https://www.periplus.com/account/Login");
    }

    public void searchProduct(String productName) {
        driver.findElement(searchBox).click();
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(productName);
        driver.findElement(searchBox).submit();
    }


}
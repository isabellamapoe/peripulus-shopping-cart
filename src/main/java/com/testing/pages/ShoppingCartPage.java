package com.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShoppingCartPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    private By shoppingCartIcon =
            By.id("show-your-cart");

    private By successPopupText =
            By.xpath("//*[contains(text(),'Success add to cart')]");

    private By firstCartProduct =
            By.xpath("(//table//a)[1]");

    private By preloader =
            By.className("preloader");

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    private void waitForPageReady() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(preloader));
        } catch (Exception ignored) {
        }
    }

    public void goToShoppingCartPage() {

        waitForPageReady();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopupText));
        wait.until(
                ExpectedConditions.elementToBeClickable(shoppingCartIcon)
        ).click();
    }

    public boolean isSuccessPopupDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(successPopupText)
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isBookExist(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class,'product-name')]//a")));
        By bookLocator = By.xpath("//p[contains(@class,'product-name')]//a[normalize-space()='" + name + "']");
        try {
            WebElement book = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(bookLocator)
            );
            return book.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
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

        WebElement cartIcon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(shoppingCartIcon)
        );

        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));

        actions.moveToElement(cartIcon)
                .pause(Duration.ofMillis(300))
                .click()
                .perform();
    }

    public boolean isFirstProductDisplayed() {
        waitForPageReady();

        WebElement product = wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstCartProduct)
        );

        return product.isDisplayed();
    }

    public String getConfirmationMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successPopupText)
        ).getText().trim();
    }
}
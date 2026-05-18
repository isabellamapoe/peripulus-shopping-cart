package com.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShoppingCartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By preloader = By.className("preloader");
    private By shoppingCartIcon = By.id("show-your-cart");
    private By notificationModal = By.id("Notification-Modal");
    private By successPopupText =
            By.xpath("//*[contains(text(),'Success add to cart')]");

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    private void waitForLoading() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(preloader));
        } catch (Exception ignored) {}

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(notificationModal));
        } catch (Exception ignored) {}
    }

    public void goToShoppingCartPage() {
        waitForLoading();

        WebElement cartIcon = wait.until(
                ExpectedConditions.elementToBeClickable(shoppingCartIcon)
        );
        cartIcon.click();

        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    public boolean isBookExist(String name) {

        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        By bookLocator = By.xpath(
                "//table//a[contains(normalize-space(.),'" + name + "')]"
        );

        for (int i = 0; i < 10; i++) { // retry lebih panjang
            List<WebElement> items = driver.findElements(bookLocator);

            if (items.size() > 0 && items.get(0).isDisplayed()) {
                return true;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }

        return false;
    }
    public String getConfirmationMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successPopupText)
        ).getText().trim();
    }
}
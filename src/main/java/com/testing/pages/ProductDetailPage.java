package com.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    private By firstProductResult =
            By.xpath("(//div[contains(@class,'product-content')]//a)[1]");

    private By addToCartButton =
            By.xpath("//button[contains(.,'Add to Cart') or contains(@class,'add-to-cart')]");

    private By preloader =
            By.className("preloader");

    public ProductDetailPage(WebDriver driver) {
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

    public void selectFirstProduct() {
        waitForPageReady();

        WebElement product = wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstProductResult)
        );
        wait.until(ExpectedConditions.elementToBeClickable(product));
        actions.moveToElement(product).pause(Duration.ofMillis(300)).click().perform();
    }

    public void addToCart() {
        waitForPageReady();

        WebElement button = wait.until(
                ExpectedConditions.visibilityOfElementLocated(addToCartButton)
        );

        wait.until(ExpectedConditions.elementToBeClickable(button));
        actions.moveToElement(button).pause(Duration.ofMillis(300)).click().perform();
    }
}
package com.testing.tests;

import com.testing.config.ConfigReader;
import com.testing.fixtures.LoginFixtures;
import com.testing.pages.HomePage;
import com.testing.pages.ProductDetailPage;
import com.testing.pages.ShoppingCartPage;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class EditCart {

    WebDriver driver;

    HomePage homePage;
    ProductDetailPage productDetailPage;
    ShoppingCartPage cartPage;

    @BeforeTest
    public void setUp() {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(5));

        homePage = new HomePage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new ShoppingCartPage(driver);

        System.out.println("Chrome browser successfully launched.");
    }

    @Test(description = "Verify user can successfully add product to shopping cart")
    public void AddProductToCart() {

        /*
         * Test ID: SC_001
         * Test Name: Add Product To Cart
         */

        String bookName = "Little Women";

        // Step 1 - Login
        LoginFixtures.login(
                driver,
                ConfigReader.getEmail(),
                ConfigReader.getPassword()
        );
        // Step 2 - Search Product
        homePage.searchProduct(bookName);

        // Step 3 - Select Product
        productDetailPage.selectFirstProduct();

        // Step 4 - Add To Cart
        productDetailPage.addToCart();

        Assert.assertTrue(
                cartPage.isSuccessPopupDisplayed()
        );

        cartPage.goToShoppingCartPage();
        // Validation - Product Exists In Cart
        Assert.assertTrue(
                cartPage.isBookExist(bookName),
                "Failed: Product is not displayed in cart!"
        );

        System.out.println("Add To Cart test executed successfully.");
    }

    @AfterTest
    public void closeBrowser() {

        if (driver != null) {
            driver.quit();
        }

        System.out.println("The driver has been closed.");
    }
}
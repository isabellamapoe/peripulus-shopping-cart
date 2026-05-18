package com.testing.tests;

import com.testing.pages.HomePage;
import com.testing.pages.LoginPage;
import com.testing.pages.ProductDetailPage;
import com.testing.pages.ShoppingCartPage;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class EditCart {

    WebDriver driver;
    Dotenv dotenv;

    String email;
    String password;

    HomePage homePage;
    LoginPage loginPage;
    ProductDetailPage productDetailPage;
    ShoppingCartPage cartPage;

    @BeforeTest
    public void setUp() {

        dotenv = Dotenv.load();

        email = dotenv.get("PERIPLUS_EMAIL");
        password = dotenv.get("PERIPLUS_PASSWORD");

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(5));

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new ShoppingCartPage(driver);

        System.out.println("Chrome browser successfully launched.");
    }

    @Test(description = "Verify user can successfully add product to shopping cart")
    public void AddProductToCart() {

        /*
         * Test ID: SC_001
         * Test Name: Add Product To Cart
         *
         * Preconditions:
         * - User already registered
         * - User successfully logged in
         *
         * Expected Result:
         * - Success add to cart message displayed
         * - Product displayed in shopping cart
         */

        String bookName = "Little Women";

        // Step 1 - Open Login Page
        homePage.goToLoginPage();

        // Step 2 - Login
        loginPage.login(email, password);

        // Step 3 - Search Product
        homePage.searchProduct(bookName);

        // Step 4 - Select Product
        productDetailPage.selectFirstProduct();

        // Step 5 - Add To Cart
        productDetailPage.addToCart();

        // Validation - Success Popup
        Assert.assertEquals(
                cartPage.getConfirmationMessage(),
                "Success add to cart"
        );

        // Step 6 - Open Shopping Cart
        cartPage.goToShoppingCartPage();

        // Validation - Product Exists In Cart
        Assert.assertTrue(
                cartPage.isFirstProductDisplayed(),
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
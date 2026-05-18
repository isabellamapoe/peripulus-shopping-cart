package com.testing.tests;

import com.testing.pages.HomePage;
import com.testing.pages.LoginPage;
import com.testing.pages.ProductDetailPage;
import com.testing.pages.ShoppingCartPage;
import io.github.cdimascio.dotenv.Dotenv;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class AddToCart {
    WebDriver driver;
    Dotenv dotenv;

    @BeforeMethod
    public void setUp() {
        dotenv = Dotenv.load();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void AddProductToCartAsLoggedInUser() {
        String bookName = "Little Women";
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProductDetailPage productDetailPage = new ProductDetailPage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        homePage.goToLoginPage();
        String email = dotenv.get("PERIPLUS_EMAIL");
        String password = dotenv.get("PERIPLUS_PASSWORD");
        loginPage.login(email, password);

        homePage.searchProduct(bookName);
        productDetailPage.selectFirstProduct();
        productDetailPage.addToCart();

        String actualMessage = cartPage.getConfirmationMessage();
        String expectedMessage = "Success add to cart";
        Assert.assertEquals(actualMessage, expectedMessage, "Gagal: Popup sukses tidak muncul atau teks berbeda!");

        cartPage.goToShoppingCartPage();

        String namaBukuAktual = cartPage.verifyBookExist(bookName);
        Assert.assertNotNull(namaBukuAktual, "Gagal: Elemen buku tidak ditemukan di keranjang!");
        Assert.assertTrue(namaBukuAktual.contains(bookName),
                "Gagal: Buku " + bookName + " tidak ditemukan di halaman keranjang belanja!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
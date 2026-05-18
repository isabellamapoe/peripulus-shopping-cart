package com.testing.fixtures;

import com.testing.pages.HomePage;
import com.testing.pages.LoginPage;

import org.openqa.selenium.WebDriver;
public class LoginFixtures {
    public static void login(
            WebDriver driver,
            String email,
            String password
    )
    {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        homePage.goToLoginPage();
        loginPage.login(email, password);
    }
}
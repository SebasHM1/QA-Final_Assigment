package com.calidad.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By emailInput = By.id("input-email");
    private By passwordInput = By.id("input-password");
    private By loginBtn = By.cssSelector("input[value='Login']");

    // Alertas
    private By alertError = By.cssSelector(".alert-danger");
    private By myAccountTitle = By.xpath("//h2[text()='My Account']"); // Aparece al loguear

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginBtn);
    }

    public boolean isLoginSuccessful() {
        return isDisplayed(myAccountTitle);
    }

    public boolean isLoginFailed() {
        return isDisplayed(alertError);
    }
}
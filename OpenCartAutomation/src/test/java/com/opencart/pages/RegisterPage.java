package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    // Localizadores (Usando CSS Selectors y XPath estables)
    private By firstNameInput = By.id("input-firstname");
    private By lastNameInput = By.id("input-lastname");
    private By emailInput = By.id("input-email");
    private By telephoneInput = By.id("input-telephone");
    private By passwordInput = By.id("input-password");
    private By confirmPasswordInput = By.id("input-confirm");

    // Checkbox de política (xpath relativo por texto o name)
    private By agreeCheckbox = By.name("agree");
    private By continueBtn = By.cssSelector("input[value='Continue']");

    // Mensaje de éxito
    private By successMessage = By.xpath("//div[@id='content']//p[contains(text(), 'Congratulations')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void registerUser(String fname, String lname, String email, String phone, String password) {
        type(firstNameInput, fname);
        type(lastNameInput, lname);
        type(emailInput, email);
        type(telephoneInput, phone);
        type(passwordInput, password);
        type(confirmPasswordInput, password); // Confirmar es igual al pass
        click(agreeCheckbox);
        click(continueBtn);
    }

    public String getSuccessHeading() {
        return getText(successMessage);
    }
}
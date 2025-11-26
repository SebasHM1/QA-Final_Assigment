package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private By quantityInput = By.id("input-quantity");
    private By addToCartBtn = By.id("button-cart");
    // Alerta verde de éxito que aparece arriba
    private By successAlert = By.cssSelector(".alert-success");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void setQuantity(String qty) {
        type(quantityInput, qty);
    }

    public void addToCart() {
        click(addToCartBtn);
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successAlert);
    }

    public String getSuccessMessageText() {
        return getText(successAlert);
    }
}
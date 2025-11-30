package com.calidad.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductPage extends BasePage {

    private By quantityInput = By.id("input-quantity");
    private By addToCartBtn = By.id("button-cart");
    private By successAlert = By.cssSelector(".alert-success");
    private By productSelectOption = By.cssSelector("#product select");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void setQuantity(String qty) {
        type(quantityInput, qty);
    }

    public void selectFirstOptionIfAvailable() {
        List<WebElement> selects = driver.findElements(productSelectOption);

        if (!selects.isEmpty()) {
            // Si encontramos dropdowns, recorremos cada uno y seleccionamos una opción
            for (WebElement dropdown : selects) {
                try {
                    Select select = new Select(dropdown);
                    // Seleccionamos por índice 1 (el 0 suele ser "--- Please Select ---")
                    select.selectByIndex(1);
                } catch (Exception e) {
                    System.out.println("No se pudo seleccionar la opción: " + e.getMessage());
                }
            }
        }
    }

    public void addToCart() {
        click(addToCartBtn);
    }

    public boolean isSuccessMessageDisplayed() {

        wait.until(driver -> isDisplayed(successAlert));

        return isDisplayed(successAlert);

    }

    public String getSuccessMessageText() {
        return getText(successAlert);
    }
}
package com.calidad.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage extends BasePage {

    // Selector de todas las filas de la tabla del carrito
    private By cartRows = By.cssSelector(".table-responsive table tbody tr");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductInCart(String productName, String expectedQty) {
        List<WebElement> rows = driver.findElements(cartRows);

        for (WebElement row : rows) {

            try {
                WebElement nameElement = row.findElement(By.xpath(".//td[@class='text-left']/a"));
                String actualName = nameElement.getText();

                WebElement qtyElement = row.findElement(By.xpath(".//input[contains(@name, 'quantity')]"));
                String actualQty = qtyElement.getAttribute("value");

                if (actualName.equalsIgnoreCase(productName) && actualQty.equals(expectedQty)) {
                    return true;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }
}
package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class SearchPage extends BasePage {

    private By productThumbnails = By.cssSelector(".product-thumb");
    private By noResultsMessage = By.xpath("//p[contains(text(),'There is no product that matches')]");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductInResults(String productName) {
        List<WebElement> products = driver.findElements(productThumbnails);
        // Verifica si hay al menos un producto y si el nombre coincide parcialmente
        for (WebElement prod : products) {
            if (prod.getText().toLowerCase().contains(productName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void selectProduct(String productName) {
        // Clic en el enlace del nombre del producto específico
        By productLink = By.partialLinkText(productName);
        click(productLink);
    }
}
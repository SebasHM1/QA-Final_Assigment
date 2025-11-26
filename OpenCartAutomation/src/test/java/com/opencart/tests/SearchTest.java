package com.opencart.tests;

import com.opencart.pages.ProductPage;
import com.opencart.pages.SearchPage;
import com.opencart.utils.ExcelUtils;
import com.opencart.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    private final String EXCEL_PATH = "src/test/resources/InputData.xlsx";

    @DataProvider(name = "productosData")
    public Object[][] getProductData() {
        return ExcelUtils.getData(EXCEL_PATH, "ProductosBusqueda");
    }

    @Test(dataProvider = "productosData")
    public void testSearchAndAddProduct(String category, String subCategory, String productName, String quantity) {

        if (productName == null || productName.trim().isEmpty()) {
            return;
        }

        if(quantity.contains(".")) {
            quantity = quantity.split("\\.")[0];
        }

        LogUtils.writeLog("--------------------------------------------------");
        LogUtils.writeLog("INICIANDO TEST PRODUCTO: " + productName);

        homePage.searchProduct(productName);
        SearchPage searchPage = new SearchPage(driver);

        boolean found = searchPage.isProductInResults(productName);

        if (!found) {
            LogUtils.writeLog("FALLO: El producto '" + productName + "' no apareció en la búsqueda.");
            Assert.fail("El producto '" + productName + "' no apareció en la búsqueda.");
        }

        LogUtils.writeLog("Producto encontrado en lista de búsqueda.");

        searchPage.selectProduct(productName);
        ProductPage productPage = new ProductPage(driver);

        productPage.setQuantity(quantity);

        productPage.selectFirstOptionIfAvailable();

        productPage.addToCart();

        boolean isAdded = productPage.isSuccessMessageDisplayed();

        if (isAdded) {
            String msg = productPage.getSuccessMessageText();
            Assert.assertTrue(msg.contains("Success: You have added"), "Mensaje de éxito incorrecto.");
            LogUtils.writeLog("ÉXITO: Agregado al carrito -> " + productName + " (Cant: " + quantity + ")");
        } else {
            LogUtils.writeLog("FALLO: No se mostró mensaje de éxito al agregar " + productName);
            Assert.fail("No se agregó el producto al carrito.");
        }
    }
}
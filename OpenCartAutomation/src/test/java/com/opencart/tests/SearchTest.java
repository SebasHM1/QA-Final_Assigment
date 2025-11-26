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
        // Lee la hoja "ProductosBusqueda"
        return ExcelUtils.getData(EXCEL_PATH, "ProductosBusqueda");
    }

    @Test(dataProvider = "productosData")
    public void testSearchAndAddProduct(String category, String subCategory, String productName, String quantity) {

        // Convertimos cantidad a entero para quitar decimales si el Excel los trae (ej: "2.0" -> "2")
        if(quantity.contains(".")) {
            quantity = quantity.split("\\.")[0];
        }

        LogUtils.writeLog("--------------------------------------------------");
        LogUtils.writeLog("INICIANDO TEST PRODUCTO: " + productName);

        // 1. Buscar producto
        homePage.searchProduct(productName);
        SearchPage searchPage = new SearchPage(driver);

        // 2. Verificar resultados
        boolean found = searchPage.isProductInResults(productName);
        Assert.assertTrue(found, "El producto '" + productName + "' no apareció en la búsqueda.");
        LogUtils.writeLog("Producto encontrado en lista de búsqueda.");

        // 3. Seleccionar producto (ir al detalle)
        searchPage.selectProduct(productName);
        ProductPage productPage = new ProductPage(driver);

        // 4. Configurar cantidad y agregar al carrito
        productPage.setQuantity(quantity);
        productPage.addToCart();

        // 5. Validar mensaje de éxito (Toast verde)
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
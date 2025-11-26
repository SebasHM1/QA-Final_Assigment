package com.opencart.tests;

import com.opencart.pages.CartPage;
import com.opencart.pages.LoginPage;
import com.opencart.utils.ExcelUtils;
import com.opencart.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartVerificationTest extends BaseTest {

    private final String EXCEL_PATH = "src/test/resources/InputData.xlsx";

    @Test
    public void testVerifyProductsInCart() {
        LogUtils.writeLog("==================================================");
        LogUtils.writeLog("INICIANDO VERIFICACIÓN DE CARRITO FINAL");

        // 1. Iniciar sesión para recuperar el carrito
        Object[][] loginData = ExcelUtils.getData(EXCEL_PATH, "LoginData");
        String email = (String) loginData[0][0];
        String password = (String) loginData[0][1];

        homePage.goToLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        // 2. Ir al carrito
        homePage.goToCart();
        CartPage cartPage = new CartPage(driver);

        // 3. Obtener la lista de productos esperados del Excel
        Object[][] productos = ExcelUtils.getData(EXCEL_PATH, "ProductosBusqueda");

        // Usamos SoftAssert para que si falta un producto, revise los demás antes de fallar
        org.testng.asserts.SoftAssert softAssert = new org.testng.asserts.SoftAssert();

        // 4. Iterar sobre la lista esperada
        for (Object[] row : productos) {
            String category = (String) row[0];
            String subCategory = (String) row[1];
            String productName = (String) row[2];
            String quantity = (String) row[3];

            // Validación de fila vacía
            if (productName == null || productName.trim().isEmpty()) continue;

            // Limpieza de cantidad (ej: "2.0" -> "2")
            if (quantity.contains(".")) {
                quantity = quantity.split("\\.")[0];
            }

            // Verificación en página
            boolean isPresent = cartPage.isProductInCart(productName, quantity);

            if (isPresent) {
                // LOG REQUERIDO: Categoría, Subcat, Producto, Cantidad
                String logMsg = String.format("VERIFICADO: En Carrito -> [Cat: %s | Sub: %s] Producto: %s (Cant: %s)",
                        category, subCategory, productName, quantity);
                LogUtils.writeLog(logMsg);
            } else {
                String errorMsg = "FALTA EN CARRITO: " + productName + " con cantidad " + quantity;
                LogUtils.writeLog("ERROR: " + errorMsg);
                softAssert.fail(errorMsg);
            }
        }

        // Reportar fallos si los hubo
        softAssert.assertAll();
        LogUtils.writeLog("FIN DE VERIFICACIÓN DE CARRITO");
        LogUtils.writeLog("==================================================");
    }
}
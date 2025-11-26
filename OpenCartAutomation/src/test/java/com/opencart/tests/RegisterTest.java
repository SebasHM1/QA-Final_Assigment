package com.opencart.tests;

import com.opencart.pages.RegisterPage;
import com.opencart.utils.ExcelUtils;
import com.opencart.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    private final String EXCEL_PATH = "src/test/resources/InputData.xlsx";

    @DataProvider(name = "registroData")
    public Object[][] getRegisterData() {
        return ExcelUtils.getData(EXCEL_PATH, "UsuariosRegistro");
    }

    @Test(dataProvider = "registroData")
    public void testUserRegistration(String fname, String lname, String email, String phone, String password) {
        // Navegar al registro
        homePage.goToRegister();

        RegisterPage registerPage = new RegisterPage(driver);

        // Ejecutar registro
        // Nota: Si el email ya existe, OpenCart fallará. Asegúrate de usar emails nuevos en el Excel.
        registerPage.registerUser(fname, lname, email, phone, password);

        // Validación con Hard Assert
        String expectedHeading = "Your Account Has Been Created!";
        String actualHeading = "";

        try {
            actualHeading = registerPage.getSuccessHeading();
            Assert.assertEquals(actualHeading, expectedHeading, "El mensaje de éxito no coincide.");
            LogUtils.writeLog("REGISTRO EXITOSO: Usuario " + email);
        } catch (Exception e) {
            LogUtils.writeLog("REGISTRO FALLIDO: Usuario " + email + " - Error: " + e.getMessage());
            Assert.fail("No se pudo completar el registro.");
        }
    }
}
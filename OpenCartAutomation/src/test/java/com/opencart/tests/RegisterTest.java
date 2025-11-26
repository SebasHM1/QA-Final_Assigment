package com.opencart.tests;

import com.opencart.pages.RegisterPage;
import com.opencart.utils.ExcelUtils;
import com.opencart.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Random;

public class RegisterTest extends BaseTest {

    private final String EXCEL_PATH = "src/test/resources/InputData.xlsx";

    @DataProvider(name = "registroData")
    public Object[][] getRegisterData() {
        return ExcelUtils.getData(EXCEL_PATH, "UsuariosRegistro");
    }

    @Test(dataProvider = "registroData")
    public void testUserRegistration(String fname, String lname, String email, String phone, String password) {
        // 1. Generar email único para evitar error de "Ya registrado"
        // Ejemplo: juan.perez@test.com -> juan.perez_84732@test.com
        Random random = new Random();
        int randomInt = random.nextInt(100000);
        String[] emailParts = email.split("@");
        String uniqueEmail = emailParts[0] + "_" + randomInt + "@" + emailParts[1];

        // Navegar al registro
        homePage.goToRegister();

        RegisterPage registerPage = new RegisterPage(driver);

        // Usamos el uniqueEmail en lugar del que viene fijo en Excel
        registerPage.registerUser(fname, lname, uniqueEmail, phone, password);

        String expectedMessagePart = "Congratulations! Your new account has been successfully created!";
        String actualMessage = "";

        try {
            actualMessage = registerPage.getSuccessHeading();

            // Usamos contains porque a veces puede haber espacios extra
            Assert.assertTrue(actualMessage.contains(expectedMessagePart),
                    "El mensaje de éxito no es el esperado. Recibido: " + actualMessage);

            LogUtils.writeLog("REGISTRO EXITOSO: Usuario " + uniqueEmail);

            homePage.logout();

        } catch (Exception e) {
            LogUtils.writeLog("REGISTRO FALLIDO: Usuario " + uniqueEmail + " - Error: " + e.getMessage());
            Assert.fail("No se encontró el mensaje de confirmación.");
        }
    }
}
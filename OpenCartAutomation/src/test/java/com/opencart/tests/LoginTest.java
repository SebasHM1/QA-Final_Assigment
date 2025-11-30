package com.opencart.tests;

import com.calidad.pages.LoginPage;
import com.calidad.utils.ExcelUtils;
import com.calidad.utils.LogUtils;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private final String EXCEL_PATH = "src/test/resources/InputData.xlsx";

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelUtils.getData(EXCEL_PATH, "LoginData");
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String email, String password, String expectedResult) {
        homePage.goToLogin();
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(email, password);

        SoftAssert softAssert = new SoftAssert();

        if (expectedResult.equalsIgnoreCase("success")) {
            boolean loggedIn = loginPage.isLoginSuccessful();
            softAssert.assertTrue(loggedIn, "Se esperaba login exitoso pero falló.");

            if (loggedIn) {
                LogUtils.writeLog("LOGIN EXITOSO: " + email);
                homePage.logout(); // Logout para limpiar estado
            } else {
                LogUtils.writeLog("LOGIN FALLIDO (Esperaba éxito): " + email);
            }

        } else {
            boolean errorShown = loginPage.isLoginFailed();
            softAssert.assertTrue(errorShown, "Se esperaba mensaje de error pero no apareció.");
            LogUtils.writeLog("LOGIN BLOQUEADO CORRECTAMENTE (Credenciales inválidas): " + email);
        }

        softAssert.assertAll(); // Reportar todas las fallas al final
    }
}
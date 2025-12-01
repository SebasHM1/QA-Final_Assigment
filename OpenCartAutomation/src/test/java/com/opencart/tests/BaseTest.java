package com.opencart.tests;

import com.calidad.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeMethod
    public void setup() {

        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        // Crear el driver con opciones
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://opencart.abstracta.us/");

        homePage = new HomePage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
        }
    }
}
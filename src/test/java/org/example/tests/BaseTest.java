package org.example.tests;

import net.serenitybdd.annotations.Managed;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

/**
 * Clase base para todas las pruebas E2E
 * Centraliza la configuración del WebDriver para mejor mantenibilidad
 */
@ExtendWith(SerenityJUnit5Extension.class)
public abstract class BaseTest {
    
    @Managed(uniqueSession = true, driver = "chrome")
    protected WebDriver driver;
    
    /**
     * Obtiene la instancia del WebDriver configurado
     * @return WebDriver configurado con Chrome y sesión única
     */
    protected WebDriver getDriver() {
        return driver;
    }
}

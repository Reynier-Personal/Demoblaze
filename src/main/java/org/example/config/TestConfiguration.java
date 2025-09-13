package org.example.config;

/**
 * Configuración centralizada para todo el proyecto de automatización
 * Centraliza URLs, timeouts, datos de prueba y configuraciones del navegador
 */
public class TestConfiguration {
    
    // ===== CONFIGURACIÓN DE URLs =====
    public static class URLs {
        public static final String BASE_URL = "https://www.demoblaze.com/";
        public static final String HOME_PAGE = BASE_URL;
        public static final String CART_PAGE = BASE_URL + "cart.html";
    }
    
    // ===== CONFIGURACIÓN DE TIMEOUTS =====
    public static class Timeouts {
        public static final int DEFAULT_WAIT = 10;
        public static final int EXTENDED_WAIT = 15;
        public static final int SHORT_WAIT = 5;
        public static final int IMPLICIT_WAIT = 10;
        public static final int PAGE_LOAD_TIMEOUT = 30;
    }
    
    // ===== CONFIGURACIÓN DEL NAVEGADOR =====
    public static class Browser {
        public static final String DEFAULT_BROWSER = "chrome";
        public static final boolean UNIQUE_SESSION = true;
        public static final boolean MAXIMIZE_WINDOW = true;
        
        // Opciones específicas por navegador
        public static final String CHROME_OPTIONS = "--start-maximized";
        public static final String FIREFOX_OPTIONS = "--start-maximized";
        public static final String EDGE_OPTIONS = "--start-maximized";
    }
    
    // ===== DATOS DE CLIENTE PARA PRUEBAS =====
    public static class CustomerData {
        public static final String DEFAULT_NAME = "María Elena Rodríguez";
        public static final String DEFAULT_COUNTRY = "Argentina";
        public static final String DEFAULT_CITY = "Buenos Aires";
        public static final String DEFAULT_CARD = "4532015112830366";
        public static final String DEFAULT_MONTH = "08";
        public static final String DEFAULT_YEAR = "2026";
    }
    
    // ===== MENSAJES ESPERADOS DEL SISTEMA =====
    public static class ExpectedMessages {
        public static final String THANK_YOU_MESSAGE = "Thank you for your purchase!";
        public static final String PRODUCT_ADDED_MESSAGE = "Product added";
        public static final String CART_EMPTY_MESSAGE = "Delete";
    }
    
    // ===== CONFIGURACIÓN DE REPORTES =====
    public static class Reports {
        public static final String SERENITY_REPORTS_PATH = "target/site/serenity/";
        public static final String SERENITY_INDEX_FILE = "index.html";
        public static final String SCREENSHOTS_PATH = "target/site/serenity/screenshots/";
    }
    
    // ===== CONFIGURACIÓN DE LOCALIZADORES =====
    public static class Locators {
        public static final String PROPERTIES_FILE = "locators.properties";
        public static final String LOCATORS_PATH = "src/main/resources/";
    }
    
    // ===== CONFIGURACIÓN DE AMBIENTE =====
    public static class Environment {
        public static final String DEFAULT_ENVIRONMENT = "test";
        public static final String PRODUCTION_URL = "https://www.demoblaze.com/";
        public static final String TEST_URL = "https://www.demoblaze.com/";
    }
    
    // ===== MÉTODOS DE UTILIDAD =====
    
    /**
     * Obtiene la URL base según el ambiente
     * @param environment ambiente (test, prod, etc.)
     * @return URL correspondiente al ambiente
     */
    public static String getBaseUrlForEnvironment(String environment) {
        switch (environment.toLowerCase()) {
            case "prod":
            case "production":
                return Environment.PRODUCTION_URL;
            case "test":
            case "testing":
                return Environment.TEST_URL;
            default:
                return Environment.TEST_URL;
        }
    }
    
    /**
     * Obtiene el timeout según el tipo de operación
     * @param operationType tipo de operación (default, extended, short)
     * @return timeout en segundos
     */
    public static int getTimeoutForOperation(String operationType) {
        switch (operationType.toLowerCase()) {
            case "extended":
                return Timeouts.EXTENDED_WAIT;
            case "short":
                return Timeouts.SHORT_WAIT;
            case "page_load":
                return Timeouts.PAGE_LOAD_TIMEOUT;
            default:
                return Timeouts.DEFAULT_WAIT;
        }
    }
}

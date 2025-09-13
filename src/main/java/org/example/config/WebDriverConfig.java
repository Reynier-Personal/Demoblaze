package org.example.config;

/**
 * Configuración centralizada del WebDriver para todas las pruebas
 * Permite mejor mantenibilidad y reutilización de la configuración
 */
public class WebDriverConfig {
    
    /**
     * Configuración del navegador para las pruebas
     * Centraliza las opciones de configuración del WebDriver
     */
    public static class BrowserConfig {
        public static final String DEFAULT_BROWSER = "chrome";
        public static final boolean UNIQUE_SESSION = true;
        public static final boolean MAXIMIZE_WINDOW = true; // Configurado en serenity.properties
        
        // Configuraciones adicionales para diferentes navegadores
        public static final String CHROME_OPTIONS = "--start-maximized";
        public static final String FIREFOX_OPTIONS = "--start-maximized";
    }
}

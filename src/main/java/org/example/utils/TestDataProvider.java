package org.example.utils;

import org.example.config.TestConfiguration;

/**
 * Proveedor de datos de prueba que utiliza la configuración centralizada
 * Mantiene compatibilidad con código existente mientras usa TestConfiguration
 */
public class TestDataProvider {

    // Métodos getter que delegan a la configuración centralizada
    public static String getCustomerName() {
        return TestConfiguration.CustomerData.DEFAULT_NAME;
    }

    public static String getCountry() {
        return TestConfiguration.CustomerData.DEFAULT_COUNTRY;
    }

    public static String getCity() {
        return TestConfiguration.CustomerData.DEFAULT_CITY;
    }

    public static String getCreditCard() {
        return TestConfiguration.CustomerData.DEFAULT_CARD;
    }

    public static String getMonth() {
        return TestConfiguration.CustomerData.DEFAULT_MONTH;
    }

    public static String getYear() {
        return TestConfiguration.CustomerData.DEFAULT_YEAR;
    }
    
    // Métodos de acceso a URLs
    public static String getBaseUrl() {
        return TestConfiguration.URLs.BASE_URL;
    }
    
    // Métodos de acceso a timeouts
    public static int getDefaultTimeout() {
        return TestConfiguration.Timeouts.DEFAULT_WAIT;
    }
    
    public static int getExtendedTimeout() {
        return TestConfiguration.Timeouts.EXTENDED_WAIT;
    }
    
    // Métodos de acceso a mensajes esperados
    public static String getThankYouMessage() {
        return TestConfiguration.ExpectedMessages.THANK_YOU_MESSAGE;
    }
    
    public static String getProductAddedMessage() {
        return TestConfiguration.ExpectedMessages.PRODUCT_ADDED_MESSAGE;
    }
}

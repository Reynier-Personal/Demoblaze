package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LocatorManager {
    
    private static final String LOCATORS_FILE = "locators.properties";
    private static Properties locators;
    
    static {
        loadLocators();
    }
    
    private static void loadLocators() {
        locators = new Properties();
        try (InputStream input = LocatorManager.class.getClassLoader().getResourceAsStream(LOCATORS_FILE)) {
            if (input != null) {
                locators.load(input);
            } else {
                throw new RuntimeException("Unable to find " + LOCATORS_FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading locators from " + LOCATORS_FILE, e);
        }
    }
    
    public static String getLocator(String key) {
        String locator = locators.getProperty(key);
        if (locator == null) {
            throw new RuntimeException("Locator not found for key: " + key);
        }
        return locator;
    }
    
    // HomePage Locators
    public static String getHomepageCategoriesSection() {
        return getLocator("homepage.categories.section");
    }
    
    public static String getHomepagePhonesCategory() {
        return getLocator("homepage.categories.phones");
    }
    
    public static String getHomepageLaptopsCategory() {
        return getLocator("homepage.categories.laptops");
    }
    
    public static String getHomepageMonitorsCategory() {
        return getLocator("homepage.categories.monitors");
    }
    
    public static String getHomepageProductLinks() {
        return getLocator("homepage.products.links");
    }
    
    public static String getHomepageProductCards() {
        return getLocator("homepage.products.cards");
    }
    
    public static String getHomepageCartLink() {
        return getLocator("homepage.cart.link");
    }
    
    public static String getHomepageLoginLink() {
        return getLocator("homepage.login.link");
    }
    
    public static String getHomepageSignupLink() {
        return getLocator("homepage.signup.link");
    }
    
    public static String getHomepageCarousel() {
        return getLocator("homepage.carousel");
    }
    
    // CartPage Locators
    public static String getCartpageTableBody() {
        return getLocator("cartpage.table.body");
    }
    
    public static String getCartpageTableRows() {
        return getLocator("cartpage.table.rows");
    }
    
    public static String getCartpageProductNames() {
        return getLocator("cartpage.product.names");
    }
    
    public static String getCartpageProductPrices() {
        return getLocator("cartpage.product.prices");
    }
    
    public static String getCartpageDeleteButtons() {
        return getLocator("cartpage.delete.buttons");
    }
    
    public static String getCartpageTotalPrice() {
        return getLocator("cartpage.total.price");
    }
    
    public static String getCartpagePlaceOrderButton() {
        return getLocator("cartpage.place.order.button");
    }
    
    public static String getCartpageHomeLink() {
        return getLocator("cartpage.home.link");
    }
    
    public static String getCartpageTitle() {
        return getLocator("cartpage.title");
    }
    
    // ProductPage Locators
    public static String getProductpageName() {
        return getLocator("productpage.name");
    }
    
    public static String getProductpagePrice() {
        return getLocator("productpage.price");
    }
    
    public static String getProductpageAddToCartButton() {
        return getLocator("productpage.addtocart.button");
    }
    
    public static String getProductpageDescription() {
        return getLocator("productpage.description");
    }
    
    public static String getProductpageImage() {
        return getLocator("productpage.image");
    }
    
    public static String getProductpageHomeLink() {
        return getLocator("productpage.home.link");
    }
    
    // CheckoutPage Locators
    public static String getCheckoutpageModal() {
        return getLocator("checkoutpage.modal");
    }
    
    public static String getCheckoutpageNameField() {
        return getLocator("checkoutpage.name.field");
    }
    
    public static String getCheckoutpageCountryField() {
        return getLocator("checkoutpage.country.field");
    }
    
    public static String getCheckoutpageCityField() {
        return getLocator("checkoutpage.city.field");
    }
    
    public static String getCheckoutpageCardField() {
        return getLocator("checkoutpage.card.field");
    }
    
    public static String getCheckoutpageMonthField() {
        return getLocator("checkoutpage.month.field");
    }
    
    public static String getCheckoutpageYearField() {
        return getLocator("checkoutpage.year.field");
    }
    
    public static String getCheckoutpagePurchaseButton() {
        return getLocator("checkoutpage.purchase.button");
    }
    
    public static String getCheckoutpageCloseButton() {
        return getLocator("checkoutpage.close.button");
    }
    
    // Common Elements
    public static String getCommonBody() {
        return getLocator("common.body");
    }
    
    public static String getCommonNavigation() {
        return getLocator("common.navigation");
    }
}

package org.example.tasks;

import net.serenitybdd.annotations.Step;
import org.example.pages.HomePage;
import org.example.pages.ProductPage;

public class ProductSelectionTask {

    private HomePage homePage;
    private ProductPage productPage;

    public ProductSelectionTask() {
        this.homePage = new HomePage();
        this.productPage = new ProductPage();
    }

    @Step("Select and add first phone to cart with full validation")
    public String selectAndAddFirstPhoneToCart() {
        // Navigate to phones category and wait for products
        homePage.clickPhonesCategory();
        homePage.waitForProductsToLoad();
        
        // Get product name and navigate to product page
        String productName = homePage.getFirstProductName();
        homePage.clickFirstProduct();
        productPage.waitForProductPageToLoad();
        
        // Add to cart and handle alert
        productPage.clickAddToCart();
        productPage.handleAlert();
        
        return productName;
    }

    @Step("Select and add second product from Phones category to cart")
    public String selectAndAddSecondPhoneToCart() {
        homePage.navigateBackToHome();
        homePage.waitForPageToLoad();
        homePage.clickPhonesCategory();
        homePage.waitForProductsToLoad();
        
        // Get product name and navigate to product page
        String productName = homePage.getSecondProductName();
        homePage.clickSecondProduct();
        productPage.waitForProductPageToLoad();
        
        // Add to cart and handle alert
        productPage.clickAddToCart();
        productPage.handleAlert();
        
        return productName;
    }

    @Step("Select product by name {0} and add to cart")
    public boolean selectProductAndAddToCart(String productName) {
        try {
            homePage.selectProductByName(productName);
            productPage.waitForProductPageToLoad();
            return productPage.addProductToCartWithValidation();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get product details from product page")
    public ProductDetails getProductDetails() {
        productPage.waitForProductPageToLoad();
        String name = productPage.getProductName();
        String price = productPage.getProductPrice();
        double priceValue = productPage.getProductPriceAsNumber();
        return new ProductDetails(name, price, priceValue);
    }

    @Step("Validate product page is displayed correctly")
    public boolean isProductPageDisplayedCorrectly() {
        return productPage.areProductDetailsVisible() && 
               productPage.isAddToCartButtonClickable();
    }

    @Step("Navigate back to home page")
    public void navigateBackToHome() {
        productPage.navigateBackToHome();
        homePage.waitForPageToLoad();
    }

    // Inner class to hold product details
    public static class ProductDetails {
        private final String name;
        private final String priceText;
        private final double priceValue;

        public ProductDetails(String name, String priceText, double priceValue) {
            this.name = name;
            this.priceText = priceText;
            this.priceValue = priceValue;
        }

        public String getName() { return name; }
        public String getPriceText() { return priceText; }
        public double getPriceValue() { return priceValue; }

        @Override
        public String toString() {
            return String.format("Product{name='%s', price='%s', value=%.2f}", 
                name, priceText, priceValue);
        }
    }
}

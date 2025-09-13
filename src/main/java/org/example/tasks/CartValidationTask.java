package org.example.tasks;

import net.serenitybdd.annotations.Step;
import org.example.pages.CartPage;
import java.util.List;

public class CartValidationTask {

    private CartPage cartPage;

    public CartValidationTask() {
        this.cartPage = new CartPage();
    }

    @Step("Validate that cart contains exactly {0} items")
    public boolean validateCartItemCount(int expectedCount) {
        cartPage.waitForCartPageToLoad();
        return cartPage.cartContainsExactly(expectedCount);
    }

    @Step("Validate that product {0} is present in cart")
    public boolean validateProductInCart(String productName) {
        cartPage.waitForCartPageToLoad();
        return cartPage.isProductInCart(productName);
    }

    @Step("Validate that both products {0} and {1} are present in cart")
    public boolean validateBothProductsInCart(String product1, String product2) {
        cartPage.waitForCartPageToLoad();
        return cartPage.areBothProductsInCart(product1, product2);
    }

    @Step("Validate that total price is calculated correctly")
    public boolean validateTotalPriceCalculation() {
        cartPage.waitForCartPageToLoad();
        return cartPage.isTotalPriceCorrect();
    }

    @Step("Get cart summary for reporting")
    public String getCartSummary() {
        cartPage.waitForCartPageToLoad();
        return cartPage.getCartSummary();
    }

    @Step("Get total price from cart")
    public double getTotalPrice() {
        cartPage.waitForCartPageToLoad();
        return cartPage.getTotalPriceAsNumber();
    }

    @Step("Get product names in cart")
    public List<String> getProductNamesInCart() {
        cartPage.waitForCartPageToLoad();
        return cartPage.getProductNamesInCart();
    }

    @Step("Get product prices in cart")
    public List<String> getProductPricesInCart() {
        cartPage.waitForCartPageToLoad();
        return cartPage.getProductPricesInCart();
    }

    @Step("Validate cart is not empty")
    public boolean validateCartIsNotEmpty() {
        cartPage.waitForCartPageToLoad();
        return cartPage.isCartNotEmpty();
    }

    @Step("Proceed to checkout")
    public void proceedToCheckout() {
        cartPage.waitForCartPageToLoad();
        cartPage.clickPlaceOrder();
    }

    @Step("Validate cart page is displayed correctly")
    public boolean isCartPageDisplayedCorrectly() {
        return cartPage.areCartPageElementsPresent();
    }

    @Step("Get product count in cart")
    public int getProductCount() {
        cartPage.waitForCartPageToLoad();
        return cartPage.getProductCount();
    }

    @Step("Check if product {0} is in cart")
    public boolean hasProductInCart(String productName) {
        cartPage.waitForCartPageToLoad();
        return cartPage.isProductInCart(productName);
    }

    @Step("Validate total calculation")
    public boolean validateTotalCalculation() {
        return cartPage.isTotalPriceCorrect();
    }

    @Step("Validate individual prices sum equals total")
    public boolean validateIndividualPricesSumEqualsTotal() {
        return cartPage.validateIndividualPricesSumEqualsTotal();
    }

    @Step("Get cart total for validation")
    public double getCartTotal() {
        return cartPage.getCartTotalForValidation();
    }

    @Step("Perform complete cart validation")
    public CartValidationResult performCompleteCartValidation(String product1, String product2) {
        cartPage.waitForCartPageToLoad();
        
        boolean hasCorrectItemCount = cartPage.cartContainsExactly(2);
        boolean hasBothProducts = cartPage.areBothProductsInCart(product1, product2);
        boolean hasCorrectTotal = cartPage.isTotalPriceCorrect();
        boolean isNotEmpty = cartPage.isCartNotEmpty();
        
        return new CartValidationResult(hasCorrectItemCount, hasBothProducts, hasCorrectTotal, isNotEmpty);
    }

    // Inner class to hold validation results
    public static class CartValidationResult {
        private final boolean correctItemCount;
        private final boolean bothProductsPresent;
        private final boolean correctTotal;
        private final boolean notEmpty;

        public CartValidationResult(boolean correctItemCount, boolean bothProductsPresent, 
                                  boolean correctTotal, boolean notEmpty) {
            this.correctItemCount = correctItemCount;
            this.bothProductsPresent = bothProductsPresent;
            this.correctTotal = correctTotal;
            this.notEmpty = notEmpty;
        }

        public boolean isCorrectItemCount() { return correctItemCount; }
        public boolean isBothProductsPresent() { return bothProductsPresent; }
        public boolean isCorrectTotal() { return correctTotal; }
        public boolean isNotEmpty() { return notEmpty; }

        public boolean isAllValid() {
            return correctItemCount && bothProductsPresent && correctTotal && notEmpty;
        }

        @Override
        public String toString() {
            return String.format("CartValidation{itemCount=%s, products=%s, total=%s, notEmpty=%s}", 
                correctItemCount, bothProductsPresent, correctTotal, notEmpty);
        }
    }
}

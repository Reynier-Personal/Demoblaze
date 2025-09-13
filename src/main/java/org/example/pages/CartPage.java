package org.example.pages;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.example.utils.LocatorManager;
import java.time.Duration;
import java.util.List;

public class CartPage extends PageObject {

    // All locators are now managed through LocatorManager - @FindBy annotations removed

    private WebDriverWait wait;

    public void initializeWait() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }

    @Step("Wait for cart page to load")
    public void waitForCartPageToLoad() {
        if (wait == null) {
            initializeWait();
        }
        
        // Wait for "Products" text to be present on the page
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("body"), "Products"));
        
        // Wait for cart table and essential elements using LocatorManager
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(LocatorManager.getCartpageTableBody())));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(LocatorManager.getCartpagePlaceOrderButton())));
        
        // Wait for total price element using LocatorManager
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(LocatorManager.getCartpageTotalPrice())));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorManager.getCartpageTotalPrice())));
    }

    @Step("Get number of items in cart")
    public int getCartItemsCount() {
        if (wait == null) {
            initializeWait();
        }
        List<WebElement> cartItemElements = getDriver().findElements(By.cssSelector(LocatorManager.getCartpageTableRows()));
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItemElements));
        return cartItemElements.size();
    }

    @Step("Get product count in cart")
    public int getProductCount() {
        return getCartItemsCount();
    }

    @Step("Get product names in cart")
    public List<String> getProductNamesInCart() {
        if (wait == null) {
            initializeWait();
        }
        List<WebElement> productNameElements = getDriver().findElements(By.cssSelector(LocatorManager.getCartpageProductNames()));
        wait.until(ExpectedConditions.visibilityOfAllElements(productNameElements));
        return productNameElements.stream()
                .map(element -> element.getText().trim())
                .collect(java.util.stream.Collectors.toList());
    }

    @Step("Get product prices in cart")
    public List<String> getProductPricesInCart() {
        if (wait == null) {
            initializeWait();
        }
        List<WebElement> productPriceElements = getDriver().findElements(By.cssSelector(LocatorManager.getCartpageProductPrices()));
        wait.until(ExpectedConditions.visibilityOfAllElements(productPriceElements));
        return productPriceElements.stream()
                .map(element -> element.getText().trim())
                .collect(java.util.stream.Collectors.toList());
    }

    @Step("Get total price displayed")
    public String getTotalPrice() {
        if (wait == null) {
            initializeWait();
        }
        WebElement totalPriceElement = getDriver().findElement(By.id(LocatorManager.getCartpageTotalPrice()));
        wait.until(ExpectedConditions.visibilityOf(totalPriceElement));
        return totalPriceElement.getText().trim();
    }

    @Step("Get total price as number")
    public double getTotalPriceAsNumber() {
        String totalText = getTotalPrice();
        return Double.parseDouble(totalText);
    }

    @Step("Calculate expected total from individual prices")
    public double calculateExpectedTotal() {
        List<String> prices = getProductPricesInCart();
        return prices.stream()
                .mapToDouble(price -> Double.parseDouble(price))
                .sum();
    }

    @Step("Validate that product {0} is in cart")
    public boolean isProductInCart(String productName) {
        List<String> productsInCart = getProductNamesInCart();
        return productsInCart.contains(productName);
    }

    @Step("Validate that both products {0} and {1} are in cart")
    public boolean areBothProductsInCart(String product1, String product2) {
        List<String> productsInCart = getProductNamesInCart();
        return productsInCart.contains(product1) && productsInCart.contains(product2);
    }

    @Step("Validate individual prices sum equals total price")
    public boolean validateIndividualPricesSumEqualsTotal() {
        try {
            double calculatedTotal = calculateExpectedTotal();
            double displayedTotal = getTotalPriceAsNumber();
            // Permitir pequeña diferencia por redondeo
            return Math.abs(calculatedTotal - displayedTotal) < 0.01;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get cart total for validation")
    public double getCartTotalForValidation() {
        return getTotalPriceAsNumber();
    }

    @Step("Validate total price matches sum of individual prices")
    public boolean isTotalPriceCorrect() {
        double displayedTotal = getTotalPriceAsNumber();
        double expectedTotal = calculateExpectedTotal();
        return Math.abs(displayedTotal - expectedTotal) < 0.01; // Allow for small floating point differences
    }

    @Step("Click Place Order button")
    public void clickPlaceOrder() {
        WebElement placeOrderElement = getDriver().findElement(By.cssSelector(LocatorManager.getCartpagePlaceOrderButton()));
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderElement));
        placeOrderElement.click();
    }

    @Step("Remove product from cart by index {0}")
    public void removeProductByIndex(int index) {
        List<WebElement> deleteButtonElements = getDriver().findElements(By.cssSelector(LocatorManager.getCartpageDeleteButtons()));
        if (index < deleteButtonElements.size()) {
            wait.until(ExpectedConditions.elementToBeClickable(deleteButtonElements.get(index)));
            deleteButtonElements.get(index).click();
            // Wait for the item to be removed
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Step("Navigate back to home")
    public void navigateBackToHome() {
        WebElement homeLinkElement = getDriver().findElement(By.linkText(LocatorManager.getCartpageHomeLink()));
        wait.until(ExpectedConditions.elementToBeClickable(homeLinkElement));
        homeLinkElement.click();
    }

    @Step("Validate cart page elements are present")
    public boolean areCartPageElementsPresent() {
        try {
            WebElement cartTableBodyElement = getDriver().findElement(By.cssSelector(LocatorManager.getCartpageTableBody()));
            WebElement totalPriceElement = getDriver().findElement(By.id(LocatorManager.getCartpageTotalPrice()));
            WebElement placeOrderElement = getDriver().findElement(By.cssSelector(LocatorManager.getCartpagePlaceOrderButton()));
            
            return cartTableBodyElement.isDisplayed() && 
                   totalPriceElement.isDisplayed() && 
                   placeOrderElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Validate cart is not empty")
    public boolean isCartNotEmpty() {
        return getCartItemsCount() > 0;
    }

    @Step("Validate cart contains exactly {0} items")
    public boolean cartContainsExactly(int expectedCount) {
        return getCartItemsCount() == expectedCount;
    }

    @Step("Get product details for validation")
    public String getCartSummary() {
        StringBuilder summary = new StringBuilder();
        List<String> names = getProductNamesInCart();
        List<String> prices = getProductPricesInCart();
        
        for (int i = 0; i < names.size(); i++) {
            summary.append(String.format("Product %d: %s - %s\n", 
                i + 1, names.get(i), prices.get(i)));
        }
        summary.append(String.format("Total: %s", getTotalPrice()));
        
        return summary.toString();
    }
}

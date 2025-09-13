package org.example.pages;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class ProductPage extends PageObject {

    @FindBy(css = "h2.name")
    private WebElement productName;

    @FindBy(css = "h3.price-container")
    private WebElement productPrice;

    @FindBy(css = ".btn.btn-success.btn-lg")
    private WebElement addToCartButton;

    @FindBy(css = "#more-information p")
    private WebElement productDescription;

    @FindBy(css = ".item.active img")
    private WebElement productImage;

    @FindBy(linkText = "Home")
    private WebElement homeLink;

    private static final int DEFAULT_TIMEOUT = 10;
    private static final String PRODUCT_NAME_SELECTOR = "h2.name";
    private static final String PRODUCT_PRICE_SELECTOR = "h3.price-container";
    private static final String ADD_TO_CART_SELECTOR = ".btn.btn-success.btn-lg";
    
    private WebDriverWait wait;

    private void ensureWaitInitialized() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        }
    }

    @Step("Wait for product page to load")
    public void waitForProductPageToLoad() {
        ensureWaitInitialized();
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(PRODUCT_NAME_SELECTOR)));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_NAME_SELECTOR)));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ADD_TO_CART_SELECTOR)));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
        } catch (Exception e) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body")));
        }
    }

    @Step("Get product name")
    public String getProductName() {
        return getElementText(PRODUCT_NAME_SELECTOR, "product name");
    }

    @Step("Get product price")
    public String getProductPrice() {
        return getElementText(PRODUCT_PRICE_SELECTOR, "product price");
    }
    
    private String getElementText(String selector, String elementDescription) {
        ensureWaitInitialized();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
            WebElement element = getDriver().findElement(By.cssSelector(selector));
            return element.getText().trim();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error getting %s: %s", elementDescription, e.getMessage()));
        }
    }

    @Step("Get product price as number")
    public double getProductPriceAsNumber() {
        String priceText = getProductPrice();
        // Extract number from text like "$790 *includes tax"
        String numericPrice = priceText.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numericPrice);
    }

    @Step("Click Add to Cart button")
    public void clickAddToCart() {
        ensureWaitInitialized();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            WebElement addButton = getDriver().findElement(By.cssSelector(ADD_TO_CART_SELECTOR));
            addButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Error clicking Add to Cart button: " + e.getMessage());
        }
    }

    @Step("Handle add to cart alert and validate message")
    public boolean handleAddToCartAlert() {
        return handleAlert("Product added", true);
    }

    @Step("Add product to cart with validation")
    public boolean addProductToCartWithValidation() {
        clickAddToCart();
        return handleAddToCartAlert();
    }

    @Step("Navigate back to home")
    public void navigateBackToHome() {
        ensureWaitInitialized();
        wait.until(ExpectedConditions.elementToBeClickable(homeLink));
        homeLink.click();
    }

    @Step("Validate product details are visible")
    public boolean areProductDetailsVisible() {
        try {
            return productName.isDisplayed() && 
                   productPrice.isDisplayed() && 
                   addToCartButton.isDisplayed() &&
                   productImage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get product description")
    public String getProductDescription() {
        try {
            ensureWaitInitialized();
            wait.until(ExpectedConditions.visibilityOf(productDescription));
            return productDescription.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Validate Add to Cart button is clickable")
    public boolean isAddToCartButtonClickable() {
        try {
            ensureWaitInitialized();
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            return addToCartButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Handle alert after adding product to cart")
    public void handleAlert() {
        handleAlert(null, false);
    }
    
    private boolean handleAlert(String expectedText, boolean validateText) {
        try {
            ensureWaitInitialized();
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = getDriver().switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            
            if (validateText && expectedText != null) {
                return alertText.contains(expectedText);
            }
            return true;
        } catch (Exception e) {
            return !validateText; // If not validating, return true; if validating, return false
        }
    }
}

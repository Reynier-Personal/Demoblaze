package org.example.pages;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.PageObject;
import org.example.utils.LocatorManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class HomePage extends PageObject {

    // All locators are now managed through LocatorManager - @FindBy annotations removed

    private static final int DEFAULT_TIMEOUT = 10;
    
    private WebDriverWait wait;

    private void ensureWaitInitialized() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        }
    }

    @Step("Open Demoblaze homepage")
    public void openHomePage() {
        String baseUrl = System.getProperty("base.url", "https://www.demoblaze.com/");
        openUrl(baseUrl);
        ensureWaitInitialized();
        waitForPageToLoad();
    }

    @Step("Wait for page to load completely")
    public void waitForPageToLoad() {
        ensureWaitInitialized();
        try {
            // Wait for categories section to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LocatorManager.getHomepageCategoriesSection())));
            
            // Wait for at least one product to be present in the store
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(LocatorManager.getHomepageProductCards())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LocatorManager.getHomepageProductCards())));
            
        } catch (Exception e) {
            // Fallback: just wait for body if homepage elements not found
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(LocatorManager.getCommonBody())));
        }
    }

    @Step("Wait for CATEGORIES element to be visible")
    public void waitForCategoriesVisible() {
        ensureWaitInitialized();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LocatorManager.getHomepageCategoriesSection())));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(LocatorManager.getHomepageCategoriesSection()), "CATEGORIES"));
    }

    @Step("Wait for homepage to be fully loaded with categories and products")
    public void waitForHomepageFullyLoaded() {
        ensureWaitInitialized();
        waitForCategoriesVisible();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(LocatorManager.getHomepageProductCards())));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LocatorManager.getHomepageProductCards())));
    }

    @Step("Validate that Phones category is visible")
    public boolean isPhonesCategoryVisible() {
        try {
            if (wait == null) {
                ensureWaitInitialized();
            }
            // Wait for page to load before checking elements
            waitForPageToLoad();
            WebElement phonesElement = getDriver().findElement(By.cssSelector(LocatorManager.getHomepagePhonesCategory()));
            return phonesElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Click on Phones category")
    public void clickPhonesCategory() {
        if (wait == null) {
            ensureWaitInitialized();
        }
        // Wait for the page to load first
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(LocatorManager.getHomepagePhonesCategory())));
        // Then wait for it to be clickable
        WebElement phonesCategoryElement = getDriver().findElement(By.cssSelector(LocatorManager.getHomepagePhonesCategory()));
        wait.until(ExpectedConditions.elementToBeClickable(phonesCategoryElement));
        phonesCategoryElement.click();
        waitForProductsToLoad();
    }

    @Step("Click on Laptops category")
    public void clickLaptopsCategory() {
        if (wait == null) {
            ensureWaitInitialized();
        }
        // Wait for page to load before using elements
        waitForPageToLoad();
        WebElement laptopsElement = getDriver().findElement(By.cssSelector(LocatorManager.getHomepageLaptopsCategory()));
        wait.until(ExpectedConditions.elementToBeClickable(laptopsElement));
        laptopsElement.click();
        waitForProductsToLoad();
    }

    @Step("Wait for products to load")
    public void waitForProductsToLoad() {
        if (wait == null) {
            ensureWaitInitialized();
        }
        try {
            // Wait for at least one product card to be present and visible
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(LocatorManager.getHomepageProductCards())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LocatorManager.getHomepageProductCards())));
        } catch (Exception e) {
            // If products don't load, wait for page to be ready
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(LocatorManager.getCommonBody())));
        }
    }

    @Step("Select product by name: {0}")
    public void selectProductByName(String productName) {
        if (wait == null) {
            ensureWaitInitialized();
        }
        try {
            // Wait for product links to be present
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(LocatorManager.getHomepageProductLinks())));
            // Refresh the product links list
            List<WebElement> currentProductLinks = getDriver().findElements(By.cssSelector(LocatorManager.getHomepageProductLinks()));
            
            for (WebElement productLink : currentProductLinks) {
                if (productLink.getText().trim().equals(productName)) {
                    wait.until(ExpectedConditions.elementToBeClickable(productLink));
                    productLink.click();
                    return;
                }
            }
            throw new RuntimeException("Product not found: " + productName);
        } catch (Exception e) {
            throw new RuntimeException("Error selecting product: " + productName + " - " + e.getMessage());
        }
    }

    @Step("Get first available product name from current category")
    public String getFirstProductName() {
        return getProductNameByIndex(0, "first");
    }

    @Step("Get second available product name from current category")
    public String getSecondProductName() {
        return getProductNameByIndex(1, "second");
    }
    
    private String getProductNameByIndex(int index, String position) {
        ensureWaitInitialized();
        try {
            List<WebElement> productLinks = waitForAndGetProductLinks();
            if (productLinks.size() > index) {
                return productLinks.get(index).getText().trim();
            }
            throw new RuntimeException(String.format("Not enough products found. Requested %s product (index %d) but only %d products available", 
                position, index, productLinks.size()));
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error getting %s product name: %s", position, e.getMessage()));
        }
    }
    
    private List<WebElement> waitForAndGetProductLinks() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(LocatorManager.getHomepageProductLinks())));
        return getDriver().findElements(By.cssSelector(LocatorManager.getHomepageProductLinks()));
    }

    @Step("Navigate to cart")
    public void navigateToCart() {
        ensureWaitInitialized();
        try {
            // Wait for cart link to be present and clickable using LocatorManager
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(LocatorManager.getHomepageCartLink())));
            wait.until(ExpectedConditions.elementToBeClickable(By.id(LocatorManager.getHomepageCartLink())));
            
            // Find and click cart link using LocatorManager
            WebElement cartElement = getDriver().findElement(By.id(LocatorManager.getHomepageCartLink()));
            cartElement.click();
            
        } catch (Exception e) {
            throw new RuntimeException("Error navigating to cart: " + e.getMessage());
        }
    }

    @Step("Check if currently on homepage")
    public boolean isOnHomePage() {
        try {
            // Check if homepage-specific elements exist without waiting
            return getDriver().findElements(By.cssSelector(LocatorManager.getHomepageCategoriesSection())).size() > 0 &&
                   getDriver().findElements(By.id(LocatorManager.getHomepageCartLink())).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get total number of products displayed")
    public int getTotalProductsCount() {
        if (wait == null) {
            ensureWaitInitialized();
        }
        // Wait for page to load before using elements
        waitForPageToLoad();
        List<WebElement> productCardElements = getDriver().findElements(By.cssSelector(LocatorManager.getHomepageProductCards()));
        wait.until(ExpectedConditions.visibilityOfAllElements(productCardElements));
        return productCardElements.size();
    }

    @Step("Validate homepage elements are present")
    public boolean areHomePageElementsPresent() {
        try {
            ensureWaitInitialized();
            waitForPageToLoad();
            
            // Wait for and validate PRODUCT STORE text is present
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(LocatorManager.getCommonBody()), "PRODUCT STORE"));
            
            // Wait for and validate all three categories are present and visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LocatorManager.getHomepagePhonesCategory())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LocatorManager.getHomepageLaptopsCategory())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LocatorManager.getHomepageMonitorsCategory())));
            
            // Validate PRODUCT STORE text is visible
            boolean productStoreVisible = getDriver().getPageSource().contains("PRODUCT STORE");

            
            return productStoreVisible;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Navigate back to home page")
    public void navigateBackToHome() {
        String baseUrl = System.getProperty("base.url", "https://www.demoblaze.com/");
        openUrl(baseUrl);
        waitForPageToLoad();
    }

    @Step("Click on first product in current category")
    public void clickFirstProduct() {
        clickProductByIndex(0, "first");
    }

    @Step("Click on second product in current category")
    public void clickSecondProduct() {
        clickProductByIndex(1, "second");
    }
    
    private void clickProductByIndex(int index, String position) {
        ensureWaitInitialized();
        try {
            List<WebElement> productLinks = waitForAndGetProductLinks();
            if (productLinks.size() > index) {
                WebElement targetProduct = productLinks.get(index);
                wait.until(ExpectedConditions.elementToBeClickable(targetProduct));
                targetProduct.click();
            } else {
                throw new RuntimeException(String.format("Cannot click %s product. Only %d products available", 
                    position, productLinks.size()));
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error clicking %s product: %s", position, e.getMessage()));
        }
    }
}

package org.example.pages;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.example.utils.LocatorManager;
import java.time.Duration;

public class CheckoutPage extends PageObject {

    // All locators are now managed through LocatorManager - @FindBy annotations removed

    private WebDriverWait wait;

    public void initializeWait() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }

    @Step("Wait for order modal to appear")
    public void waitForOrderModal() {
        if (wait == null) {
            initializeWait();
        }
        try {
            // Wait for order modal and name field to be visible using LocatorManager
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorManager.getCheckoutpageModal())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorManager.getCheckoutpageNameField())));
        } catch (Exception e) {
            // Fallback: try to wait for the name field directly if modal is not found
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(LocatorManager.getCheckoutpageNameField())));
        }
    }

    @Step("Fill customer name: {0}")
    public void fillName(String name) {
        if (wait == null) {
            initializeWait();
        }
        WebElement nameFieldElement = getDriver().findElement(By.id(LocatorManager.getCheckoutpageNameField()));
        wait.until(ExpectedConditions.visibilityOf(nameFieldElement));
        nameFieldElement.clear();
        nameFieldElement.sendKeys(name);
    }

    @Step("Fill country: {0}")
    public void fillCountry(String country) {
        if (wait == null) {
            initializeWait();
        }
        WebElement countryFieldElement = getDriver().findElement(By.id(LocatorManager.getCheckoutpageCountryField()));
        wait.until(ExpectedConditions.visibilityOf(countryFieldElement));
        countryFieldElement.clear();
        countryFieldElement.sendKeys(country);
    }

    @Step("Fill city: {0}")
    public void fillCity(String city) {
        if (wait == null) {
            initializeWait();
        }
        WebElement cityFieldElement = getDriver().findElement(By.id(LocatorManager.getCheckoutpageCityField()));
        wait.until(ExpectedConditions.visibilityOf(cityFieldElement));
        cityFieldElement.clear();
        cityFieldElement.sendKeys(city);
    }

    @Step("Fill credit card: {0}")
    public void fillCard(String card) {
        if (wait == null) {
            initializeWait();
        }
        WebElement cardFieldElement = getDriver().findElement(By.id(LocatorManager.getCheckoutpageCardField()));
        wait.until(ExpectedConditions.visibilityOf(cardFieldElement));
        cardFieldElement.clear();
        cardFieldElement.sendKeys(card);
    }

    @Step("Fill month: {0}")
    public void fillMonth(String month) {
        if (wait == null) {
            initializeWait();
        }
        WebElement monthFieldElement = getDriver().findElement(By.id(LocatorManager.getCheckoutpageMonthField()));
        wait.until(ExpectedConditions.visibilityOf(monthFieldElement));
        monthFieldElement.clear();
        monthFieldElement.sendKeys(month);
    }

    @Step("Fill year: {0}")
    public void fillYear(String year) {
        if (wait == null) {
            initializeWait();
        }
        WebElement yearFieldElement = getDriver().findElement(By.id(LocatorManager.getCheckoutpageYearField()));
        wait.until(ExpectedConditions.visibilityOf(yearFieldElement));
        yearFieldElement.clear();
        yearFieldElement.sendKeys(year);
    }

    @Step("Click purchase button")
    public void clickPurchase() {
        if (wait == null) {
            initializeWait();
        }
        WebElement purchaseButtonElement = getDriver().findElement(By.cssSelector(LocatorManager.getCheckoutpagePurchaseButton()));
        wait.until(ExpectedConditions.elementToBeClickable(purchaseButtonElement));
        purchaseButtonElement.click();
    }

    @Step("Wait for confirmation modal")
    public void waitForConfirmationModal() {
        if (wait == null) {
            initializeWait();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sweet-alert")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sweet-alert h2")));
    }

    @Step("Get confirmation title")
    public String getConfirmationTitle() {
        if (wait == null) {
            initializeWait();
        }
        WebElement confirmationTitleElement = getDriver().findElement(By.cssSelector(".sweet-alert h2"));
        wait.until(ExpectedConditions.visibilityOf(confirmationTitleElement));
        return confirmationTitleElement.getText().trim();
    }

    @Step("Get confirmation message")
    public String getConfirmationMessage() {
        if (wait == null) {
            initializeWait();
        }
        WebElement confirmationMessageElement = getDriver().findElement(By.cssSelector(".sweet-alert p"));
        wait.until(ExpectedConditions.visibilityOf(confirmationMessageElement));
        return confirmationMessageElement.getText().trim();
    }

    @Step("Click OK button")
    public void clickOK() {
        if (wait == null) {
            initializeWait();
        }
        WebElement okButtonElement = getDriver().findElement(By.cssSelector(".sweet-alert .confirm"));
        wait.until(ExpectedConditions.elementToBeClickable(okButtonElement));
        okButtonElement.click();
    }

    @Step("Validate form fields are visible")
    public boolean validateFormFieldsVisible() {
        try {
            if (wait == null) {
                initializeWait();
            }
            waitForOrderModal();
            
            // Check all form fields are visible using LocatorManager
            WebElement nameField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageNameField()));
            WebElement countryField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageCountryField()));
            WebElement cityField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageCityField()));
            WebElement cardField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageCardField()));
            WebElement monthField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageMonthField()));
            WebElement yearField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageYearField()));
            
            return nameField.isDisplayed() && 
                   countryField.isDisplayed() && 
                   cityField.isDisplayed() && 
                   cardField.isDisplayed() && 
                   monthField.isDisplayed() && 
                   yearField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Validate form fields are editable")
    public boolean validateFormFieldsEditable() {
        try {
            if (wait == null) {
                initializeWait();
            }
            
            // Check all form fields are enabled using LocatorManager
            WebElement nameField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageNameField()));
            WebElement countryField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageCountryField()));
            WebElement cityField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageCityField()));
            WebElement cardField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageCardField()));
            WebElement monthField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageMonthField()));
            WebElement yearField = getDriver().findElement(By.id(LocatorManager.getCheckoutpageYearField()));
            
            return nameField.isEnabled() && 
                   countryField.isEnabled() && 
                   cityField.isEnabled() && 
                   cardField.isEnabled() && 
                   monthField.isEnabled() && 
                   yearField.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Complete purchase form with customer data")
    public void completePurchaseForm(String name, String country, String city, String card, String month, String year) {
        fillName(name);
        fillCountry(country);
        fillCity(city);
        fillCard(card);
        fillMonth(month);
        fillYear(year);
        clickPurchase();
    }

    @Step("Validate purchase confirmation")
    public boolean validatePurchaseConfirmation() {
        try {
            waitForConfirmationModal();
            String title = getConfirmationTitle();
            String message = getConfirmationMessage();
            
            return title.contains("Thank you for your purchase!") && 
                   message.contains("Id:") && 
                   message.contains("Amount:");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Validate customer data in confirmation: {0}")
    public boolean validateCustomerDataInConfirmation(String expectedName) {
        try {
            String confirmationMessage = getConfirmationMessage();
            return confirmationMessage.contains(expectedName);
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Validate total amount in confirmation: {0}")
    public boolean validateTotalAmountInConfirmation(double expectedAmount) {
        try {
            String confirmationMessage = getConfirmationMessage();
            String amountStr = String.valueOf((int)expectedAmount);
            return confirmationMessage.contains("Amount: " + amountStr + " USD");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get transaction ID from confirmation")
    public String getTransactionId() {
        try {
            String confirmationMessage = getConfirmationMessage();
            // Extract transaction ID from message like "Id: 9140731 Amount: 790 USD"
            if (confirmationMessage.contains("Id:")) {
                String[] parts = confirmationMessage.split("Id:");
                if (parts.length > 1) {
                    String idPart = parts[1].trim();
                    String[] idParts = idPart.split(" ");
                    if (idParts.length > 0) {
                        return idParts[0].trim();
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Get purchase amount from confirmation")
    public double getPurchaseAmount() {
        try {
            String confirmationMessage = getConfirmationMessage();
            // Extract amount from message like "Id: 9140731 Amount: 790 USD"
            if (confirmationMessage.contains("Amount:")) {
                String[] parts = confirmationMessage.split("Amount:");
                if (parts.length > 1) {
                    String amountPart = parts[1].trim();
                    String[] amountParts = amountPart.split(" ");
                    if (amountParts.length > 0) {
                        return Double.parseDouble(amountParts[0].trim());
                    }
                }
            }
            return 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    // Compatibility methods for CheckoutTask
    @Step("Fill complete purchase form")
    public void fillPurchaseForm(String name, String country, String city, String card, String month, String year) {
        fillName(name);
        fillCountry(country);
        fillCity(city);
        fillCard(card);
        fillMonth(month);
        fillYear(year);
    }

    @Step("Validate form fields are visible and editable")
    public boolean areFormFieldsVisibleAndEditable() {
        return validateFormFieldsVisible() && validateFormFieldsEditable();
    }

    @Step("Validate purchase button is clickable")
    public boolean isPurchaseButtonClickable() {
        try {
            if (wait == null) {
                initializeWait();
            }
            WebElement purchaseButtonElement = getDriver().findElement(By.cssSelector(LocatorManager.getCheckoutpagePurchaseButton()));
            wait.until(ExpectedConditions.elementToBeClickable(purchaseButtonElement));
            return purchaseButtonElement.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Validate confirmation message contains 'Thank you for your purchase!'")
    public boolean isThankYouMessageDisplayed() {
        try {
            String title = getConfirmationTitle();
            return title.contains("Thank you for your purchase!");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Validate transaction ID is present in confirmation")
    public boolean isTransactionIdPresent() {
        try {
            String message = getConfirmationMessage();
            return message.contains("Id:") || message.contains("ID:");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Validate amount is present in confirmation")
    public boolean isAmountPresent() {
        try {
            String message = getConfirmationMessage();
            return message.contains("Amount:") || message.contains("$");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get full confirmation details")
    public String getFullConfirmationDetails() {
        try {
            return String.format("Title: %s\nMessage: %s", 
                getConfirmationTitle(), getConfirmationMessage());
        } catch (Exception e) {
            return "Error getting confirmation details: " + e.getMessage();
        }
    }

    @Step("Get confirmation amount")
    public String getConfirmationAmount() {
        try {
            String message = getConfirmationMessage();
            // Extract amount from message like "Id: 9140731 Amount: 790 USD"
            if (message.contains("Amount:")) {
                String[] parts = message.split("Amount:");
                if (parts.length > 1) {
                    return parts[1].trim();
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Validate form fields are visible")
    public boolean areFormFieldsVisible() {
        return validateFormFieldsVisible();
    }

    @Step("Validate form fields are editable")
    public boolean areFormFieldsEditable() {
        return validateFormFieldsEditable();
    }

    @Step("Click OK to close confirmation")
    public void clickOk() {
        clickOK();
    }

    @Step("Validate customer data in confirmation matches input data")
    public boolean validateCustomerDataInConfirmation(String expectedName, String expectedCard) {
        try {
            String confirmationMessage = getConfirmationMessage();
            // Validar que el nombre del cliente aparezca en la confirmación
            boolean nameMatches = confirmationMessage.toLowerCase().contains(expectedName.toLowerCase());
            // Validar que los últimos 4 dígitos de la tarjeta aparezcan
            String lastFourDigits = expectedCard.substring(expectedCard.length() - 4);
            boolean cardMatches = confirmationMessage.contains(lastFourDigits);
            return nameMatches || cardMatches; // Al menos uno debe coincidir
        } catch (Exception e) {
            return false;
        }
    }
}

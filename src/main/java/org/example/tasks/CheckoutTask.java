package org.example.tasks;

import net.serenitybdd.annotations.Step;
import org.example.pages.CheckoutPage;

public class CheckoutTask {

    private CheckoutPage checkoutPage;

    public CheckoutTask() {
        this.checkoutPage = new CheckoutPage();
    }

    @Step("Complete purchase form with customer details")
    public void completePurchaseForm(String name, String country, String city, String card, String month, String year) {
        checkoutPage.waitForOrderModal();
        checkoutPage.fillPurchaseForm(name, country, city, card, month, year);
    }

    @Step("Complete purchase with default test data")
    public PurchaseResult completePurchaseWithTestData() {
        return completePurchaseProcess("Juan Perez", "Colombia", "Bogota", "4111111111111111", "12", "2025");
    }

    @Step("Complete full purchase process with validation")
    public PurchaseResult completePurchaseProcess(String name, String country, String city, String card, String month, String year) {
        try {
            checkoutPage.waitForOrderModal();
            
            // Validate form fields are visible and editable
            boolean fieldsValid = checkoutPage.areFormFieldsVisibleAndEditable();
            if (!fieldsValid) {
                return new PurchaseResult(false, false, false, false, "Form fields not visible or editable");
            }

            // Fill the form
            checkoutPage.fillPurchaseForm(name, country, city, card, month, year);
            
            // Validate purchase button is clickable
            boolean buttonClickable = checkoutPage.isPurchaseButtonClickable();
            if (!buttonClickable) {
                return new PurchaseResult(fieldsValid, false, false, false, "Purchase button not clickable");
            }

            // Complete purchase
            checkoutPage.clickPurchase();
            checkoutPage.waitForConfirmationModal();

            // Validate confirmation
            boolean thankYouMessage = checkoutPage.isThankYouMessageDisplayed();
            boolean transactionId = checkoutPage.isTransactionIdPresent();
            boolean amount = checkoutPage.isAmountPresent();

            String confirmationDetails = checkoutPage.getFullConfirmationDetails();

            return new PurchaseResult(fieldsValid, buttonClickable, thankYouMessage, 
                                    transactionId && amount, confirmationDetails);

        } catch (Exception e) {
            return new PurchaseResult(false, false, false, false, "Exception: " + e.getMessage());
        }
    }

    @Step("Validate checkout form is displayed correctly")
    public boolean isCheckoutFormDisplayedCorrectly() {
        checkoutPage.waitForOrderModal();
        return checkoutPage.areFormFieldsVisibleAndEditable() && 
               checkoutPage.isPurchaseButtonClickable();
    }

    @Step("Get confirmation details")
    public String getConfirmationDetails() {
        return checkoutPage.getFullConfirmationDetails();
    }

    @Step("Get transaction ID from confirmation")
    public String getTransactionId() {
        return checkoutPage.getTransactionId();
    }

    @Step("Get amount from confirmation")
    public String getConfirmationAmount() {
        return checkoutPage.getConfirmationAmount();
    }

    @Step("Validate form fields are visible")
    public boolean validateFormFieldsVisible() {
        checkoutPage.waitForOrderModal();
        return checkoutPage.areFormFieldsVisible();
    }

    @Step("Validate form fields are editable")
    public boolean validateFormFieldsEditable() {
        checkoutPage.waitForOrderModal();
        return checkoutPage.areFormFieldsEditable();
    }

    @Step("Fill checkout form with provided data")
    public void fillCheckoutForm(String name, String country, String city, String card, String month, String year) {
        checkoutPage.waitForOrderModal();
        checkoutPage.fillPurchaseForm(name, country, city, card, month, year);
    }

    @Step("Complete purchase")
    public void completePurchase() {
        checkoutPage.clickPurchase();
        checkoutPage.waitForConfirmationModal();
    }

    @Step("Validate purchase confirmation")
    public boolean validatePurchaseConfirmation() {
        return checkoutPage.isThankYouMessageDisplayed();
    }

    @Step("Check if transaction ID is present")
    public boolean hasTransactionId() {
        return checkoutPage.isTransactionIdPresent();
    }

    @Step("Check if amount is present in confirmation")
    public boolean hasAmountInConfirmation() {
        return checkoutPage.isAmountPresent();
    }

    @Step("Close purchase confirmation")
    public void closePurchaseConfirmation() {
        checkoutPage.clickOk();
    }

    @Step("Close confirmation modal")
    public void closeConfirmation() {
        checkoutPage.clickOk();
    }

    @Step("Validate customer data in confirmation")
    public boolean validateCustomerDataInConfirmation(String name, String card) {
        return checkoutPage.validateCustomerDataInConfirmation(name, card);
    }

    @Step("Validate total amount in confirmation")
    public boolean validateTotalAmountInConfirmation(double expectedTotal) {
        return checkoutPage.validateTotalAmountInConfirmation(expectedTotal);
    }

    // Inner class to hold purchase results
    public static class PurchaseResult {
        private final boolean formFieldsValid;
        private final boolean purchaseButtonClickable;
        private final boolean thankYouMessageDisplayed;
        private final boolean confirmationDetailsPresent;
        private final String details;

        public PurchaseResult(boolean formFieldsValid, boolean purchaseButtonClickable, 
                            boolean thankYouMessageDisplayed, boolean confirmationDetailsPresent, 
                            String details) {
            this.formFieldsValid = formFieldsValid;
            this.purchaseButtonClickable = purchaseButtonClickable;
            this.thankYouMessageDisplayed = thankYouMessageDisplayed;
            this.confirmationDetailsPresent = confirmationDetailsPresent;
            this.details = details;
        }

        public boolean isFormFieldsValid() { return formFieldsValid; }
        public boolean isPurchaseButtonClickable() { return purchaseButtonClickable; }
        public boolean isThankYouMessageDisplayed() { return thankYouMessageDisplayed; }
        public boolean isConfirmationDetailsPresent() { return confirmationDetailsPresent; }
        public String getDetails() { return details; }

        public boolean isAllValid() {
            return formFieldsValid && purchaseButtonClickable && 
                   thankYouMessageDisplayed && confirmationDetailsPresent;
        }

        @Override
        public String toString() {
            return String.format("PurchaseResult{formValid=%s, buttonClickable=%s, thankYou=%s, details=%s}", 
                formFieldsValid, purchaseButtonClickable, thankYouMessageDisplayed, confirmationDetailsPresent);
        }
    }
}

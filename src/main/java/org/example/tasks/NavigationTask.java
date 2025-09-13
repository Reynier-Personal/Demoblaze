package org.example.tasks;

import net.serenitybdd.annotations.Step;
import org.example.pages.HomePage;
import org.example.pages.CartPage;

public class NavigationTask {

    private HomePage homePage;
    private CartPage cartPage;

    public NavigationTask() {
        this.homePage = new HomePage();
        this.cartPage = new CartPage();
    }

    @Step("Navigate to Demoblaze homepage")
    public void navigateToHomePage() {
        homePage.openHomePage();
    }

    @Step("Navigate to homepage and wait for categories to load")
    public void navigateToHomePageAndWaitForCategories() {
        homePage.openHomePage();
        homePage.waitForCategoriesVisible();
    }

    @Step("Wait for homepage to be fully loaded")
    public void waitForHomepageFullyLoaded() {
        homePage.waitForHomepageFullyLoaded();
    }

    @Step("Navigate to cart page")
    public void navigateToCart() {
        homePage.navigateToCart();
        cartPage.waitForCartPageToLoad();
    }

    @Step("Navigate back to home from cart")
    public void navigateBackToHomeFromCart() {
        cartPage.navigateBackToHome();
        homePage.waitForPageToLoad();
    }

    @Step("Validate homepage is loaded correctly")
    public boolean isHomePageLoaded() {
        return homePage.areHomePageElementsPresent();
    }

    @Step("Validate cart page is loaded correctly")
    public boolean isCartPageLoaded() {
        return cartPage.areCartPageElementsPresent();
    }
}

package org.example.tests;

import org.example.tasks.NavigationTask;
import org.example.tasks.ProductSelectionTask;
import org.example.tasks.CartValidationTask;
import org.example.tasks.CheckoutTask;
import org.example.utils.TestDataProvider;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompraTest extends BaseTest {

    private NavigationTask navigationTask;
    private ProductSelectionTask productSelectionTask;
    private CartValidationTask cartValidationTask;
    private CheckoutTask checkoutTask;

    @BeforeEach
    void setupTest() {
        // Inicializar tareas para cada prueba (el driver permanece igual)
        if (navigationTask == null) {
            navigationTask = new NavigationTask();
            productSelectionTask = new ProductSelectionTask();
            cartValidationTask = new CartValidationTask();
            checkoutTask = new CheckoutTask();
        }
    }

    @Test
    @Order(1)
    public void testHomepageValidation() {
        // Navegar a la página principal de Demoblaze
        navigationTask.navigateToHomePage();
        
        // Validar que la página principal se cargó correctamente
        assertThat(navigationTask.isHomePageLoaded()).isTrue();
    }

    @Test
    @Order(2)
    public void testAddTwoDistinctProductsToCart() {
        // Asegurar que estamos en la página principal
        navigationTask.navigateToHomePage();
        
        // Agregar el primer producto (teléfono) al carrito
        String firstProductName = productSelectionTask.selectAndAddFirstPhoneToCart();
        assertThat(firstProductName).isNotEmpty();

        // Regresar a la página principal y agregar un segundo producto diferente
        navigationTask.navigateToHomePageAndWaitForCategories();
        navigationTask.waitForHomepageFullyLoaded();
        
        String secondProductName = productSelectionTask.selectAndAddSecondPhoneToCart();
        assertThat(secondProductName).isNotEmpty();
        
        // Validar que los productos seleccionados son diferentes
        assertThat(secondProductName).isNotEqualTo(firstProductName);
    }

    @Test
    @Order(3)
    public void testCartValidation() {
        navigationTask.navigateToHomePage();
        // Navegar al carrito de compras
        navigationTask.navigateToCart();
        
        // Validar el contenido del carrito
        assertThat(navigationTask.isCartPageLoaded()).isTrue();
        assertThat(cartValidationTask.getProductCount()).isEqualTo(2);
        assertThat(cartValidationTask.validateIndividualPricesSumEqualsTotal()).isTrue();
    }

    @Test
    @Order(4)
    public void testCheckoutFormCompletion() {
        // Proceder al proceso de checkout
        cartValidationTask.proceedToCheckout();

        // Validar y completar el formulario de checkout con datos realistas
        assertThat(checkoutTask.validateFormFieldsVisible()).isTrue();
        assertThat(checkoutTask.validateFormFieldsEditable()).isTrue();
        
        checkoutTask.completePurchaseForm(
            TestDataProvider.getCustomerName(),    // María Elena Rodríguez
            TestDataProvider.getCountry(),         // Argentina
            TestDataProvider.getCity(),           // Buenos Aires
            TestDataProvider.getCreditCard(),     // Tarjeta Visa válida
            TestDataProvider.getMonth(),          // 08
            TestDataProvider.getYear()           // 2026
        );
    }

    @Test
    @Order(5)
    public void testPurchaseCompletion() {
        // Completar el proceso de compra y obtener confirmación
        CheckoutTask.PurchaseResult result = checkoutTask.completePurchaseProcess(
            TestDataProvider.getCustomerName(),
            TestDataProvider.getCountry(),
            TestDataProvider.getCity(),
            TestDataProvider.getCreditCard(),
            TestDataProvider.getMonth(),
            TestDataProvider.getYear()
        );
        
        // Validar que la compra fue exitosa
        assertThat(result.isAllValid()).isTrue();
        assertThat(result.isThankYouMessageDisplayed()).isTrue();

        // Cerrar el modal de confirmación
        checkoutTask.closePurchaseConfirmation();
    }
}

package api;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test κλάση για την κλάση Cart.
 */
public class CartTest {

    private Cart cart;
    private Product product1;
    private Product product2;
    private Product product3;

    @Before
    public void setUp() {
        cart = new Cart();
        product1 = new Product("Γάλα", "Φρέσκο γάλα.", "Προϊόντα ψυγείου", "Γάλα", 1.5, 10, QuantityType.PIECES);
        product2 = new Product("Μήλα", "Φρέσκα μήλα.", "Φρούτα", "Φρέσκα τρόφιμα", 2.0, 20, QuantityType.KILOGRAMS);
        product3 = new Product("Ψωμί", "Ολικής άλεσης.", "Αρτοποιήματα", "Ψωμιά", 1.0, 5, QuantityType.PIECES);
    }

    /**
     * Δοκιμή προσθήκης προϊόντων στο καλάθι.
     */
    @Test
    public void testAddProduct() {
        assertTrue(cart.addProduct(product1, 2));
        assertTrue(cart.addProduct(product2, 3));

        List<CartItem> cartItems = cart.getCartItems();
        assertEquals(2, cartItems.size());
        assertEquals(2, cartItems.get(0).getQuantity());
        assertEquals(product1, cartItems.get(0).getProduct());
    }

    /**
     * Δοκιμή προσθήκης ποσότητας σε υπάρχον προϊόν.
     */
    @Test
    public void testAddExistingProduct() {
        cart.addProduct(product1, 2);
        cart.addProduct(product1, 3);

        List<CartItem> cartItems = cart.getCartItems();
        assertEquals(1, cartItems.size());
        assertEquals(5, cartItems.get(0).getQuantity());
    }

    /**
     * Δοκιμή αφαίρεσης προϊόντος από το καλάθι.
     */
    @Test
    public void testRemoveProduct() {
        cart.addProduct(product1, 2);
        cart.addProduct(product2, 3);

        cart.removeProduct(product1);

        List<CartItem> cartItems = cart.getCartItems();
        assertEquals(1, cartItems.size());
        assertEquals(product2, cartItems.get(0).getProduct());
    }

    /**
     * Δοκιμή ενημέρωσης ποσότητας προϊόντος.
     */
    @Test
    public void testUpdateQuantity() {
        cart.addProduct(product1, 2);

        assertTrue(cart.updateQuantity(product1, 5));
        assertEquals(5, cart.getCartItems().get(0).getQuantity());

        // Δοκιμή για ποσότητα μεγαλύτερη από το διαθέσιμο απόθεμα
        assertFalse(cart.updateQuantity(product1, 15));
    }

    /**
     * Δοκιμή υπολογισμού συνολικού κόστους.
     */
    @Test
    public void testCalculateTotalPrice() {
        cart.addProduct(product1, 2); // 2 * 1.5 = 3.0
        cart.addProduct(product2, 3); // 3 * 2.0 = 6.0

        assertEquals(9.0, cart.calculateTotalPrice(), 0.01);
    }

    /**
     * Δοκιμή ολοκλήρωσης παραγγελίας.
     */
    @Test
    public void testCheckout() {
        cart.addProduct(product1, 2);
        cart.addProduct(product2, 3);

        List<CartItem> completedOrder = cart.checkout();

        assertNotNull(completedOrder);
        assertEquals(2, completedOrder.size());
        assertEquals(0, cart.getCartItems().size());
    }

    /**
     * Δοκιμή αποτυχίας ολοκλήρωσης παραγγελίας λόγω ανεπαρκούς αποθέματος.
     */
    @Test
    public void testAddProductExceedsStock() {
        boolean added = cart.addProduct(product1, 15); // Διαθέσιμο απόθεμα: 10
        
        assertFalse(added); // Ελέγχει ότι η προσθήκη απέτυχε
        assertTrue(cart.getCartItems().isEmpty()); // Ελέγχει ότι το καλάθι παρέμεινε άδειο
    }

    /**
     * Δοκιμή εκκαθάρισης καλαθιού.
     */
    @Test
    public void testClearCart() {
        cart.addProduct(product1, 2);
        cart.addProduct(product2, 3);

        cart.clearCart();

        assertEquals(0, cart.getCartItems().size());
    }
}


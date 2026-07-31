package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class για την κλάση CartItem.
 */
public class CartItemTest {
    private Product product;
    private CartItem cartItem;




    @Before
    public void setUp() {
        // Δημιουργία ενός αντικειμένου Product
        product = new Product("Γάλα", "Φρέσκο γάλα.", "Προϊόντα ψυγείου", "Γάλα", 12.99, 10, QuantityType.PIECES);

        // Δημιουργία ενός αντικειμένου CartItem
        cartItem = new CartItem(product, 2);
    }

    @Test
    public void testGetProduct() {
        assertEquals(product, cartItem.getProduct());
    }

    @Test
    public void testSetProduct() {
        Product newProduct = new Product("Γιαούρτι", "Φρέσκο γιαούρτι.", "Προϊόντα ψυγείου", "Γιαούρτια", 5.99, 20, QuantityType.PIECES);
        cartItem.setProduct(newProduct);

        assertEquals(newProduct, cartItem.getProduct());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    public void testSetQuantityValid() {
        cartItem.setQuantity(5);

        assertEquals(5, cartItem.getQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetQuantityZero() {
        cartItem.setQuantity(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetQuantityNegative() {
        cartItem.setQuantity(-3);
    }


    @Test
    public void testToString() {
        String expected = "Γάλα - Ποσότητα: τεμάχια";
    }
}

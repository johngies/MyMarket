package api;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test κλάση για την κλάση Order.
 */
public class OrderTest {

    private Order order;
    private List<CartItem> items;
    private CartItem item1;
    private CartItem item2;

    @Before
    public void setUp() {
        // Δημιουργία προϊόντων
        Product product1 = new Product("Γάλα", "Φρέσκο γάλα.", "Προϊόντα ψυγείου", "Γάλα", 1.5, 10, QuantityType.PIECES);
        Product product2 = new Product("Μήλα", "Φρέσκα μήλα.", "Φρούτα", "Φρέσκα τρόφιμα", 2.0, 20, QuantityType.KILOGRAMS);

        // Δημιουργία CartItems
        item1 = new CartItem(product1, 2); // 2 τεμάχια γάλα
        item2 = new CartItem(product2, 3); // 3 κιλά μήλα

        // Προσθήκη στο καλάθι
        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        // Δημιουργία παραγγελίας
        order = new Order(1, items);
    }

    /**
     * Δοκιμή για τον αριθμό παραγγελίας.
     */
    @Test
    public void testGetOrderId() {
        assertEquals(1, order.getOrderId());
    }

    /**
     * Δοκιμή για τη λίστα προϊόντων.
     */
    @Test
    public void testGetItems() {
        List<CartItem> retrievedItems = order.getItems();
        assertEquals(2, retrievedItems.size());
        assertEquals(item1, retrievedItems.get(0));
        assertEquals(item2, retrievedItems.get(1));
    }

    /**
     * Δοκιμή για το συνολικό κόστος.
     */
    @Test
    public void testGetTotalPrice() {
        double expectedTotal = (item1.getProduct().getPrice() * item1.getQuantity()) +
                (item2.getProduct().getPrice() * item2.getQuantity());
        assertEquals(expectedTotal, order.getTotalPrice(), 0.01);
    }

    /**
     * Δοκιμή για την ημερομηνία παραγγελίας.
     */
    @Test
    public void testGetOrderDate() {
        LocalDateTime orderDate = order.getOrderDate();
        assertNotNull(orderDate);
    }

    /**
     * Δοκιμή για την αναπαράσταση της παραγγελίας ως String.
     */
    @Test
    public void testToString() {
        String expectedString = String.format(
                "Παραγγελία #1\nΗμερομηνία: %s\nΠροϊόντα:\n" +
                        "- Γάλα, Ποσότητα: 2 ΤΕΜΆΧΙΑ, Κόστος: 3.00\n" +
                        "- Μήλα, Ποσότητα: 3 KG, Κόστος: 6.00\n" +
                        "Συνολικό Κόστος: 9.00€\n",
               order.getOrderDate());
         assertEquals(expectedString, order.toString());
    }
}

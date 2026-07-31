package api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test κλάση για την κλάση CustomerManager.
 */
public class CustomerManagerTest {

    private CustomerManager customerManager;
    private UserManager userManager;

    @Before
    public void setUp() {
        userManager = new UserManager();
        customerManager = new CustomerManager(userManager);

        // Προσθήκη προεπιλεγμένων χρηστών
        User customer1 = new User("user1", "password1", Role.CUSTOMER, "Γιάννης", "Παπαδόπουλος");
        User customer2 = new User("user2", "password2", Role.CUSTOMER, "Κώστας", "Παπαγιάννης");
        User admin = new User("admin1", "password1", Role.ADMIN, "", "");

        userManager.addCustomer(customer1);
        userManager.addCustomer(customer2);
        userManager.addCustomer(admin);
    }

    /**
     * Δοκιμή για την απόκτηση της λίστας πελατών.
     */
    @Test
    public void testGetCustomers() {
        List<User> customers = customerManager.getCustomers();

        assertEquals(2, customers.size());
        assertEquals("user1", customers.get(0).getUsername());
        assertEquals("user2", customers.get(1).getUsername());
    }

    /**
     * Δοκιμή για την απόκτηση του ιστορικού παραγγελιών ενός πελάτη.
     */
    @Test
    public void testGetOrderHistory() {
        // Δημιουργία παραγγελιών για τον πελάτη user1
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(new Product("Γάλα", "Φρέσκο γάλα", "Προϊόντα ψυγείου", "Γάλα", 1.5, 10, QuantityType.PIECES), 2));
        Order order1 = new Order(1, items);

        customerManager.addOrderToCustomer("user1", order1);

        List<Order> orderHistory = customerManager.getOrderHistory("user1");
        assertEquals(1, orderHistory.size());
        assertEquals(order1, orderHistory.get(0));

        // Έλεγχος για πελάτη χωρίς παραγγελίες
        List<Order> emptyOrderHistory = customerManager.getOrderHistory("user2");
        assertTrue(emptyOrderHistory.isEmpty());
    }

    /**
     * Δοκιμή για την εμφάνιση του ιστορικού παραγγελιών.
     */
    @Test
    public void testDisplayOrderHistory() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(new Product("Γάλα", "Φρέσκο γάλα", "Προϊόντα ψυγείου", "Γάλα", 1.5, 10, QuantityType.PIECES), 2));
        Order order1 = new Order(1, items);

        customerManager.addOrderToCustomer("user1", order1);


        customerManager.displayOrderHistory("user1");
    }

    /**
     * Δοκιμή για την προσθήκη παραγγελίας σε πελάτη.
     */
    @Test
    public void testAddOrderToCustomer() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(new Product("Γάλα", "Φρέσκο γάλα", "Προϊόντα ψυγείου", "Γάλα", 1.5, 10, QuantityType.PIECES), 2));
        Order order1 = new Order(1, items);

        customerManager.addOrderToCustomer("user1", order1);

        List<Order> orderHistory = customerManager.getOrderHistory("user1");
        assertEquals(1, orderHistory.size());
        assertEquals(order1, orderHistory.get(0));
    }

    /**
     * Δοκιμή για την προσθήκη παραγγελίας σε μη υπαρκτό πελάτη.
     */
    @Test
    public void testAddOrderToNonExistingCustomer() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(new Product("Γάλα", "Φρέσκο γάλα", "Προϊόντα ψυγείου", "Γάλα", 1.5, 10, QuantityType.PIECES), 2));
        Order order1 = new Order(1, items);

        customerManager.addOrderToCustomer("nonexistentUser", order1);

        List<Order> orderHistory = customerManager.getOrderHistory("nonexistentUser");
        assertTrue(orderHistory.isEmpty());
    }
}

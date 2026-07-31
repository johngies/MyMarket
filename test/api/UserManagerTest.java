package api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
/**
 * Test κλάση για το UserManager. Παρέχεται άλλο αρχείο για τα tests.
 */
public class UserManagerTest {
    private UserManager userManager;
    /**
     * Εγκαθιστά το περιβάλλον δοκιμών πριν από κάθε test.
     *
     * @throws Exception Αν υπάρξει πρόβλημα κατά την εγκατάσταση.
     */
    @Before
    public void setUp() throws Exception {
        FileManager fileManager = new FileManager("test/api/products_test.txt", "test/api/users_test.txt");
        userManager = new UserManager(fileManager);
        userManager.getUsers().clear();
    }
    /**
     * Δοκιμάζει την αρχικοποίηση των προεπιλεγμένων χρηστών.
     */
    @Test
    public void initDefaultUsers() {
        assertTrue(userManager.getUsers().isEmpty());

        userManager.initDefaultUsers();

        assertTrue(userManager.getUsers().containsKey("admin1"));
        assertTrue(userManager.getUsers().containsKey("admin2"));
        assertTrue(userManager.getUsers().containsKey("user1"));
        assertTrue(userManager.getUsers().containsKey("user2"));
    }
    /**
     * Δοκιμάζει την προσθήκη ενός νέου πελάτη.
     */
    @Test
    public void addCustomer() {
        User user1 = new User("user1",  "password1", Role.CUSTOMER, "firstName1", "lastName1");

        int size = userManager.getUsers().size();

        userManager.addCustomer(user1);


        assertEquals(size + 1, userManager.getUsers().size());
        assertTrue(userManager.getUsers().containsKey("user1"));
    }
    /**
     * Δοκιμάζει την εγγραφή ενός νέου πελάτη και την αντιμετώπιση υπαρχόντων χρηστών.
     */
    @Test
    public void registerCustomer() {
        User user2 = new User("user2",  "password1", Role.CUSTOMER, "firstName1", "lastName1");
        userManager.addCustomer(user2);

        boolean isRegisteredNewUser = userManager.registerCustomer("user3", "password3", "first", "last");
        assertTrue(isRegisteredNewUser);
        assertTrue( userManager.getUsers().containsKey("user3"));
        assertEquals(2, userManager.getUsers().size());

        boolean isRegisteredExistingUser = userManager.registerCustomer("user2", "password3", "first", "last");
        assertFalse(isRegisteredExistingUser);
        assertEquals(2, userManager.getUsers().size());
    }
    /**
     * Δοκιμάζει τη διαδικασία σύνδεσης των χρηστών.
     */
    @Test
    public void login() {
        User user1 = new User("user1",  "password1", Role.CUSTOMER, "firstName1", "lastName1");
        User user2 = new User("user2","password2", Role.CUSTOMER, "firstName2", "lastName2");
        userManager.addCustomer(user1);
        userManager.addCustomer(user2);

        User loggedInUser = userManager.login("user2", "password2");
        assertNotNull( loggedInUser);
        assertEquals(user2, loggedInUser);

        User failedLoginUser = userManager.login("user2", "passwort2");
        assertNull( failedLoginUser);

        User nonExistentUser = userManager.login("user3", "password3");
        assertNull(nonExistentUser);
    }
    /**
     * Δοκιμάζει αν ένας χρήστης είναι διαχειριστής.
     */
    @Test
    public void isAdmin() {
        User admin2 = new User("admin2", "password2", Role.ADMIN, "", "");
        User user1 = new User("user1",  "password1", Role.CUSTOMER, "firstName1", "lastName1");
        userManager.addCustomer(admin2);
        userManager.addCustomer(user1);

        assertTrue(userManager.isAdmin("admin2"));

        assertFalse(userManager.isAdmin("user1"));
    }

    /**
     * Δοκιμάζει την αναζήτηση ενός χρήστη με βάση το όνομα χρήστη.
     */
    @Test
    public void findUser() {
        User user1 = new User("user1", "password1", Role.CUSTOMER, "firstName1", "lastName1");
        userManager.addCustomer(user1);

        User foundUser = userManager.findUser("user1");
        assertNotNull(foundUser);
        assertEquals(user1, foundUser);

        User notFoundUser = userManager.findUser("user2");
        assertNull(notFoundUser);
    }
}
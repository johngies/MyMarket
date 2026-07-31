package api;

import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάση Cart διαχειρίζεται το καλάθι αγορών ενός πελάτη.
 */
public class Cart {
    private List<CartItem> cartItems;
    private CustomerManager customerManager;
    private UserManager userManager;
    private int nextOrderId = 1;

    /**
     * Δημιουργεί ένα νέο κενό καλάθι αγορών.
     */
    public Cart(){
        this.cartItems = new ArrayList<>();
    }

    /**
     * Δημιουργεί ένα νέο καλάθι αγορών συνδεδεμένο με τους διαχειριστές χρηστών και πελατών.
     * @param userManager Ο διαχειριστής χρηστών του συστήματος.
     * @param customerManager Ο διαχειριστής πελατών του συστήματος.
     */
    public Cart(UserManager userManager, CustomerManager customerManager) {
        this.cartItems = new ArrayList<>();
        this.userManager = userManager;
        this.customerManager = customerManager;
    }

    /**
     * Επιστρέφει τη λίστα με τα προϊόντα που βρίσκονται στο καλάθι.
     * @return Η λίστα των CartItem.
     */
    public List<CartItem> getCartItems(){
        return cartItems;
    }

    /**
     * Προσθέτει ένα προϊόν στο καλάθι με την επιθυμητή ποσότητα και ενημερώνει το διαθέσιμο απόθεμα.
     * @param product Το προϊόν που θα προστεθεί.
     * @param quantity Η ποσότητα του προϊόντος προς προσθήκη.
     * @return true αν η προσθήκη πραγματοποιήθηκε επιτυχώς, false σε αντίθετη περίπτωση.
     */
    public boolean addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0 || product.getQuantity() < quantity) {
            System.out.println("Μη έγκυρο προϊόν ή ανεπαρκές απόθεμα.");
            return false;
        }

        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + quantity);
                product.setQuantity(product.getQuantity() - quantity);
                System.out.println("Η ποσότητα του προϊόντος " + product.getTitle() + " ενημερώθηκε σε " + item.getQuantity());
                return true;
            }
        }

        cartItems.add(new CartItem(product, quantity));
        product.setQuantity(product.getQuantity() - quantity);
        System.out.println("Το προϊόν " + product.getTitle() + " προστέθηκε στο καλάθι με ποσότητα " + quantity);
        return true;
    }

    /**
     * Διαγράφει ένα προϊόν από το καλάθι και επιστρέφει την ποσότητα στο διαθέσιμο απόθεμα.
     * @param product Το προϊόν που θα αφαιρεθεί.
     */
    public void removeProduct(Product product) {
        boolean found = false;

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);

            if (item.getProduct().equals(product)) {
                product.setQuantity(product.getQuantity() + item.getQuantity());
                cartItems.remove(i);
                found = true;
                System.out.println("Το προϊόν " + product.getTitle() + " αφαιρέθηκε από το καλάθι.");
                break;
            }
        }

        if (!found) {
            System.out.println("Το προϊόν " + product.getTitle() + " δεν βρέθηκε στο καλάθι.");
        }
    }

    /**
     * Ενημερώνει την ποσότητα ενός προϊόντος στο καλάθι και προσαρμόζει αντίστοιχα το διαθέσιμο απόθεμα.
     * @param product Το προϊόν προς ενημέρωση.
     * @param newQuantity Η νέα επιθυμητή ποσότητα.
     * @return true αν η ενημέρωση ήταν επιτυχής, false αν δεν υπάρχει αρκετό απόθεμα ή αν το προϊόν δεν βρέθηκε.
     */
    public boolean updateQuantity(Product product, int newQuantity) {
        if (newQuantity <= 0) {
            System.out.println("Η ποσότητα πρέπει να είναι μεγαλύτερη από 0.");
            return false;
        }

        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                int diff = newQuantity - item.getQuantity();
                if (product.getQuantity() >= diff) {
                    product.setQuantity(product.getQuantity() - diff);
                    item.setQuantity(newQuantity);
                    System.out.println("Η ποσότητα του προϊόντος " + product.getTitle() + " ενημερώθηκε σε " + newQuantity);
                    return true;
                } else {
                    System.out.println("Η επιθυμητή ποσότητα δεν είναι διαθέσιμη. Διαθέσιμη ποσότητα: " + product.getQuantity());
                    return false;
                }
            }
        }

        System.out.println("Το προϊόν δεν βρέθηκε στο καλάθι.");
        return false;
    }

    /**
     * Υπολογίζει το συνολικό κόστος όλων των προϊόντων που βρίσκονται στο καλάθι.
     * @return Το συνολικό κόστος.
     */
    public double calculateTotalPrice(){
        double totalPrice = 0.0;
        for (CartItem item : cartItems){
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    /**
     * Εμφανίζει τα περιεχόμενα και το συνολικό κόστος του καλαθιού στην κονσόλα.
     */
    public void displayCart() {
        System.out.println("Καλάθι αγορών:");
        for (CartItem item : cartItems) {
            System.out.println(item.getProduct().getTitle() + " - Ποσότητα: " + item.getQuantity() +
                    " - Κόστος: " + (item.getProduct().getPrice() * item.getQuantity()));
        }
        System.out.println("Συνολικό κόστος: " + calculateTotalPrice());
    }

    /**
     * Ολοκληρώνει την παραγγελία, επιστρέφει τα προϊόντα που αγοράστηκαν και καθαρίζει το καλάθι.
     * @return Η λίστα των CartItem που περιλαμβάνονται στην ολοκληρωμένη παραγγελία.
     */
    public List<CartItem> checkout() {
        List<CartItem> completedOrder = new ArrayList<>(cartItems);
        clearCart();
        System.out.println("Η παραγγελία ολοκληρώθηκε επιτυχώς!");
        return completedOrder;
    }

    /**
     * Αδειάζει όλα τα προϊόντα από το καλάθι.
     */
    public void clearCart() {
        cartItems.clear();
    }

    /**
     * Ολοκληρώνει την παραγγελία για έναν συγκεκριμένο πελάτη και την καταχωρεί στο ιστορικό του.
     * @param username Το όνομα χρήστη του πελάτη.
     * @return Το αντικείμενο Order που δημιουργήθηκε, ή null αν ο χρήστης δεν βρέθηκε ή δεν είναι πελάτης.
     */
    public Order checkout(String username) {
        User customer = userManager.findUser(username);
        if (customer == null || !customer.getRole().equals(Role.CUSTOMER)) {
            System.out.println("Ο χρήστης με username " + username + " δεν είναι πελάτης.");
            return null;
        }

        Order newOrder = new Order(nextOrderId++, new ArrayList<>(cartItems));
        customerManager.addOrderToCustomer(username, newOrder);
        clearCart();

        System.out.println("Η παραγγελία ολοκληρώθηκε επιτυχώς!");
        return newOrder;
    }
}
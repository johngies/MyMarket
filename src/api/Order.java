package api;

import java.util.List;
import java.time.LocalDateTime;

/** Η κλάση Order αναπαριστά μια παραγγελία ενός πελάτη με τα προϊόντα της και τις ποσότητές τους.
 */
public class Order {
    private int orderId;
    private List<CartItem> items;
    private double totalPrice;
    private LocalDateTime orderDate;


    /**
     * Δημιουργεί μια νέα παραγγελία.
     * @param orderId Το μοναδικό αναγνωριστικό της παραγγελίας.
     * @param items Η λίστα των προϊόντων που περιλαμβάνονται στην παραγγελία.
     */

    public Order(int orderId, List<CartItem> items){
        this.orderId = orderId;
        this.items = items;
        this.totalPrice= calculateTotalPrice();
        this.orderDate = LocalDateTime.now();
    }

    public int getOrderId(){
        return orderId;
    }

    public List<CartItem> getItems(){
        return items;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Υπολογίζει το συνολικό κόστος της παραγγελίας.
     * @return Το συνολικό κόστος.
     */
    private double calculateTotalPrice(){
        double total=0.0;
        for (CartItem item : items){
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    /**
     * Επιστρέφει μια αναλυτική περιγραφή της παραγγελίας περιλαμβάνοντας την ημερομηνια, τα προϊόντα, τις ποσότητές τους (τεμάχια ή κιλά) και το συνολικό κόστος.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder orderDetails = new StringBuilder(String.format("Παραγγελία #%d\nΗμερομηνία: %s\nΠροϊόντα:\n", orderId, orderDate));

        for (CartItem item : items) {
            orderDetails.append(String.format("- %s, Ποσότητα: %d %s, Κόστος: %.2f\n",
                    item.getProduct().getTitle(),
                    item.getQuantity(),
                    item.getProduct().getQuantityType().toString().trim().toUpperCase(),
                    item.getProduct().getPrice() * item.getQuantity()));
        }

        orderDetails.append(String.format("Συνολικό Κόστος: %.2f€\n", totalPrice));

        return orderDetails.toString();
    }





}

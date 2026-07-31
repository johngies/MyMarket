
package api;
import java.util.ArrayList;
import java.util.List;




/**
 * Η κλάση CustomerManager διαχειρίζεται τους πελάτες και τις παραγγελίες τους.
 */

public class CustomerManager {


    private List<User> customers;
    private UserManager userManager;
    public CustomerManager(UserManager userManager){
        this.userManager = userManager;
        this.customers=new ArrayList<>();
    }

    public List<User> getCustomers(){
        for (User user : userManager.getUsers().values()){
            if (user.getRole().equals(Role.CUSTOMER)){
                customers.add(user);
            }
        }
        return customers;
    }


    /**
     * Επιστρέφει το ιστορικό παραγγελιών ενός πελάτη.
     * @param username Το username του πελάτη.
     * @return Λίστα με τις παραγγελίες του πελάτη ή κενή λίστα αν δεν υπάρχουν παραγγελίες ή δεν βρεθεί ο πελάτης.
     */
    public List<Order> getOrderHistory(String username) {
        User customer = userManager.findUser(username);
        if (customer != null && customer.getRole().equals(Role.CUSTOMER)) {
            List<Order> orders = customer.getOrderHistory();
            return orders.isEmpty() ? new ArrayList<>() : orders;

        } else {
            return new ArrayList<>();
        }

    }

    /**
     * Εμφανίζει το ιστορικό παραγγελιών ενός πελάτη.
     * @param username Το username
     */

     public void displayOrderHistory(String username) {
         List<Order> orderHistory = getOrderHistory(username);

         if (orderHistory.isEmpty()) {
             System.out.println("Ο πελάτης " + username + "δεν έχει παραγγελίες.");
         } else {
             System.out.println("Ιστορικό Παραγγελιών για τον πελάτη" + username + ":");
             for (Order order : orderHistory) {
                 System.out.println(order.toString());
             }
         }

     }

    /**
     * Προσθέτει μια παραγγελία σε έναν πελάτη.
     * @param username Το username του πελάτη.
     * @param order Η παραγγελία που θα προστεθεί.
     */
    public void addOrderToCustomer(String username, Order order){
        User customer = userManager.findUser(username);
        if (customer != null && customer.getRole().equals(Role.CUSTOMER)) {
            customer.addOrder(order);
        } else {
            System.out.println("Δεν βρέθηκε πελάτης με το username: " + username);
        }
    }



}


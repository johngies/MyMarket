package api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Αυτή η κλάση αντιπροσωπεύει έναν χρήστη του συστήματος.
 * Διαθέτει πληροφορίες όπως το όνομα χρήστη, τον κωδικό πρόσβασης, τον ρόλο,
 * το όνομα και το επώνυμο του χρήστη.
 */
public class User {
    private String username;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private List<Order> orderHistory;
    /**
     * Constructor που δημιουργεί έναν νέο χρήστη με τα παρεχόμενα χαρακτηριστικά.
     *
     * @param username  Το όνομα χρήστη του νέου χρήστη.
     * @param password  Ο κωδικός πρόσβασης του νέου χρήστη.
     * @param role      Ο ρόλος του νέου χρήστη στο σύστημα.
     * @param firstName Το όνομα του νέου χρήστη.
     * @param lastName  Το επώνυμο του νέου χρήστη.
     */
    public User(String username, String password, Role role, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.orderHistory = new ArrayList<>();
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    /**
     * Προσθέτει μια νέα παραγγελία στο ιστορικό παραγγελιών του χρήστη.
     * @param order Η παραγγελία που θα προστεθεί.
     */
    public void addOrder(Order order) {
        this.orderHistory.add(order);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

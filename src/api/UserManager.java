package api;

import java.util.HashMap;
/**
 * Κλάση που διαχειρίζεται τους χρήστες του συστήματος,
 * επιτρέποντας την αρχικοποίηση προεπιλεγμένων χρηστών, φόρτωση και αποθήκευση
 * χρηστών, εγγραφή νέων πελατών, είσοδο χρηστών και έλεγχο διαχειριστικών δικαιωμάτων.
 */
public class UserManager{
    private final HashMap<String, User> users;
    private final FileManager fileManager;
    /**
     * Προεπιλεγμένος constructor της κλάσης.
     * Αρχικοποιεί το FileManager, φορτώνει τους χρήστες από το αρχείο και
     * αν η λίστα των χρηστών είναι κενή, αρχικοποιεί τους προεπιλεγμένους χρήστες
     * και τους αποθηκεύει.
     */
    public UserManager(){
        this.fileManager = new FileManager();
        this.users = new HashMap<>();
        loadUsers();
        if (users.isEmpty()){
            initDefaultUsers();
            saveUsers();
        }
    }
    /**
     * Constructor της κλάσης που δέχεται ενα FileManager.
     * Αρχικοποιεί το FileManager, φορτώνει τους χρήστες από το αρχείο και
     * αν η λίστα των χρηστών είναι κενή, αρχικοποιεί τους προεπιλεγμένους χρήστες
     * και τους αποθηκεύει.
     *
     * @param fileManager Ο FileManager που θα χρησιμοποιηθεί για τη φόρτωση και αποθήκευση χρηστών.
     */
    public UserManager(FileManager fileManager){
        this.fileManager = fileManager;
        this.users = new HashMap<>();
        loadUsers();
        if (users.isEmpty()){
            initDefaultUsers();
            saveUsers();
        }
    }
    /**
     * Αρχικοποιεί τους προεπιλεγμένους χρήστες του συστήματος.
     * Προσθέτει δύο διαχειριστές και δύο πελάτες στο Map<String, User> users.
     */
    public void initDefaultUsers(){
        User admin1 = new User("admin1", "password1", Role.ADMIN, "", "");
        User admin2 = new User("admin2", "password2", Role.ADMIN, "", "");
        User user1 = new User("user1",  "password1", Role.CUSTOMER, "firstName1", "lastName1");
        User user2 = new User("user2","password2", Role.CUSTOMER, "firstName2", "lastName2");
        users.put(admin1.getUsername().toLowerCase(), admin1);
        users.put(admin2.getUsername().toLowerCase(), admin2);
        users.put(user1.getUsername().toLowerCase(), user1);
        users.put(user2.getUsername().toLowerCase(), user2);
    }
    /**
     * Φορτώνει τους χρήστες από το αρχείο χρησιμοποιώντας τον FileManager.
     */
    public void loadUsers(){
        fileManager.loadUsers(users);
    }
    /**
     * Αποθηκεύει τους χρήστες στο αρχείο χρησιμοποιώντας τον FileManager.
     */
    public void saveUsers(){
        fileManager.saveUsers(users);
    }
    /**
     * Επιστρέφει το Map<String, User> των χρηστών.
     *
     * @return Το Map<String, User> που περιέχει όλους τους χρήστες, με το username ως κλειδί.
     */
    public HashMap<String, User> getUsers() {
        return users;
    }
    /**
     * Προσθέτει έναν νέο πελάτη στο σύστημα.
     * Αποθηκεύει τον πελάτη στο αρχείο μετά την προσθήκη.
     *
     * @param user Ο user που θέλεις να προσθέσεις ως πελάτη.
     */
    public void addCustomer(User user){
        users.put(user.getUsername().toLowerCase(), user);
        saveUsers();
    }
    /**
     * Προσθέτει έναν νέο πελάτη στο σύστημα.
     *Αποθηκεύει τον πελάτη στο αρχείο μετά την προσθήκη.
     *
     * @param username  Το όνομα χρήστη του πελάτη.
     * @param password  Ο κωδικός πρόσβασης του πελάτη.
     * @param firstname Το μικρό όνομα του πελάτη.
     * @param lastname  Το επώνυμο του πελάτη.
     */
    public void addCustomer(String username, String password, String firstname, String lastname){
        users.put(username, new User(username, password, Role.CUSTOMER, firstname, lastname));
        saveUsers();
    }

    /**
     * Εγγράφει έναν νέο πελάτη στο σύστημα.
     * Ελέγχει αν το username υπάρχει ήδη. Αν όχι, δημιουργεί έναν νέο User πελάτη,
     * τον προσθέτει στο σύστημα και επιστρέφει true. Διαφορετικά, επιστρέφει false.
     *
     * @param username   Το username του νέου πελάτη.
     * @param password   Ο κωδικός πρόσβασης του νέου πελάτη.
     * @param firstName  Το όνομα του νέου πελάτη.
     * @param lastName   Το επώνυμο του νέου πελάτη.
     * @return true αν η εγγραφή ήταν επιτυχής, false αν το username υπάρχει ήδη.
     */
    public boolean registerCustomer(String username, String password, String firstName, String lastName){
        if (users.containsKey(username)){
            return false;
        }
        User customer = new User(username, password, Role.CUSTOMER, firstName, lastName);
        addCustomer(customer);
        return true;
    }
    /**
     * Επιτρέπει σε έναν χρήστη να συνδεθεί στο σύστημα.
     * Ελέγχει αν το συνδυασμός username και password είναι έγκυρος.
     *
     * @param username Το username του χρήστη που προσπαθεί να συνδεθεί.
     * @param password Ο κωδικός πρόσβασης του χρήστη.
     * @return Ο User αν η σύνδεση ήταν επιτυχής,  null αλλιώς.
     */
    public User login(String username, String password){
        for (User user : users.values()){
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())){
                return user;
            }
        }
        return null;
    }
    /**
     * Ελέγχει αν ένας χρήστης είναι διαχειριστής.
     *
     * @param adminUsername Το username του χρήστη που θέλεις να ελέγξεις.
     * @return true αν ο χρήστης είναι διαχειριστής, false αλλιώς.
     */
    public boolean isAdmin(String adminUsername) {
        User user = users.get(adminUsername);
        return user != null && user.getRole() == Role.ADMIN;
    }
    /**
     * Αναζητά έναν χρήστη με βάση το όνομα χρήστη.
     *
     * @param username Το όνομα χρήστη του χρήστη που αναζητείται.
     * @return Το αντικείμενο χρήστη (User) αν βρεθεί, αλλιώς επιστρέφει null.
     */
    public User findUser(String username){
        for (User user : getUsers().values()){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

}

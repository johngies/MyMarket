package api;

import java.util.*;

/** Αυτή η κλάση διαχειρίζεται τα προϊόντα, επιτρέποντας την προσθήκη, αφαίρεση, επεξεργασία και
 * την αναζήτηση προιόντων.
 */
public class ProductManager{
    private final FileManager fileManager;
    private final List<Product> products;
    /**
     * Προεπιλεγμένος constructor της κλάσης ProductManager.
     * Αρχικοποιεί τον FileManager, δημιουργεί μια νέα λίστα προϊόντων και φορτώνει τα προϊόντα από το αρχείο.
     */
    public ProductManager() {
        this.fileManager = new FileManager();
        this.products = new ArrayList<>();
        loadProducts();
    }
    /**
     * Constructor της κλάσης ProductManager με χρήση ενός υπάρχοντος FileManager.
     * Αρχικοποιεί τον FileManager, δημιουργεί μια νέα λίστα προϊόντων και φορτώνει τα προϊόντα από το αρχείο.
     *
     * @param fileManager Ο FileManager που θα χρησιμοποιηθεί για τη φόρτωση και αποθήκευση προϊόντων.
     */
    public ProductManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.products = new ArrayList<>();
        loadProducts();
    }
    /**
     * Φορτώνει τα προϊόντα από το αρχείο χρησιμοποιώντας τον FileManager.
     */
    public void loadProducts(){
        fileManager.loadProducts(products);
    }
    /**
     * Επιστρέφει τη λίστα των προϊόντων.
     *
     * @return Η λίστα των προϊόντων.
     */
    public List<Product> getProducts() {
        return products;
    }
    /**
     * Αποθηκεύει τα προϊόντα στο αρχείο χρησιμοποιώντας τον FileManager.
     */
    public void saveProducts(){
        fileManager.saveProducts(products);
    }
    /**
     * Προσθέτει ένα νέο προϊόν στη λίστα των προϊόντων και το αποθηκεύει.
     *
     * @param product Το Product που θέλεις να προσθέσεις.
     */
    public void addProduct(Product product) {
        products.add(product);
        saveProducts();
    }
    /**
     * Προσθέτει ένα νέο προϊόν στη λίστα των προϊόντων με βάση τις δοθείσες παραμέτρους και το αποθηκεύει.
     *
     * @param title       Ο τίτλος του προϊόντος.
     * @param description Η περιγραφή του προϊόντος.
     * @param category    Η κατηγορία του προϊόντος.
     * @param subcategory Η υποκατηγορία του προϊόντος.
     * @param price       Η τιμή του προϊόντος.
     * @param quantity    Η ποσότητα του προϊόντος.
     * @param type        Ο τύπος ποσότητας του προϊόντος.
     */
    public void addProduct(String title, String description, String category, String subcategory, double price, int quantity, QuantityType type){
        products.add(new Product(title, description, category, subcategory, price, quantity, type));
        saveProducts();
    }
    /**
     * Αφαιρεί ένα προϊόν από τη λίστα των προϊόντων και αποθηκεύει την αλλαγή.
     *
     * @param product Το Product που θέλεις να αφαιρέσεις.
     */
    public void removeProduct(Product product) {
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()){
            if (iterator.next().equals(product)){
                iterator.remove();
            }
        }
        saveProducts();
    }
    /**
     * Αφαιρεί ένα προϊόν από τη λίστα των προϊόντων με βάση τον τίτλο του και αποθηκεύει την αλλαγή.
     *
     * @param title Ο τίτλος του προϊόντος που θέλεις να αφαιρέσεις.
     */
    public void removeProduct(String title) {
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()){
            if (iterator.next().getTitle().equalsIgnoreCase(title)){
                iterator.remove();
            }
        }
        saveProducts();
    }
    /**
     * Βρίσκει ένα προϊόν με βάση τον τίτλο.
     *
     * @param title Ο τίτλος του προϊόντος που αναζητείται.
     * @return Το Product με τον συγκεκριμένο τίτλο ή null αν δεν βρεθεί.
     */
    public Product findProductByTitle(String title) {
        for (Product p : products) {
            if (p.getTitle().equalsIgnoreCase(title)) {
                return p;
            }
        }
        return null;
    }
    /**
     * Αναζητά προϊόντα με βάση τον τίτλο, την κατηγορία και την υποκατηγορία.
     *
     * @param title       Ο τίτλος ή μέρος του τίτλου του προϊόντος που αναζητείται.
     * @param category    Η κατηγορία του προϊόντος που αναζητείται.
     * @param subCategory Η υποκατηγορία του προϊόντος που αναζητείται.
     * @return Μια ArrayList<Product> με όλα τα προϊόντα που ταιριάζουν με τα κριτήρια αναζήτησης.
     */
    public ArrayList<Product> searchProducts(String title, String category, String subCategory) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            boolean matchesTitle = (title == null || title.isEmpty()) || product.getTitle().toLowerCase().contains(title.toLowerCase().trim());

            boolean matchesCategory = (category == null || category.isEmpty()) || product.getCategory().equalsIgnoreCase(category.trim());

            boolean matchesSubcategory = (subCategory == null || subCategory.isEmpty()) || product.getSubcategory().equalsIgnoreCase(subCategory.trim());

            if (matchesTitle && matchesCategory && matchesSubcategory) {
                result.add(product);
            }
        }
        return result;
    }
    /**
     * Επεξεργάζεται ένα υπάρχον προϊόν με βάση τον παλιό τίτλο του.
     * Αντικαθιστά τις παλιές τιμές με τις νέες που παρέχονται.
     *
     * @param p Το προϊόν που θα αλλάξουμε.
     * @param newTitle  Ο νέος τίτλος του προϊόντος.
     * @param newDesc   Η νέα περιγραφή του προϊόντος.
     * @param newCat    Η νέα κατηγορία του προϊόντος.
     * @param newSubcat Η νέα υποκατηγορία του προϊόντος.
     * @param newPrice  Η νέα τιμή του προϊόντος.
     * @param newQty    Η νέα ποσότητα του προϊόντος.
     * @throws IllegalArgumentException Αν το προϊόν με τον παλιό τίτλο δεν βρεθεί.
     */
    public void editProduct(Product p, String newTitle, String newDesc, String newCat, String newSubcat, Double newPrice, Integer newQty) {

        if (p.getTitle() != null) {
            p.setTitle(newTitle);
        }
        if (p.getDescription() != null) {
            p.setDescription(newDesc);
        }
        if (p.getCategory() != null) {
            p.setCategory(newCat);
        }
        if (newSubcat != null) {
            p.setSubcategory(newSubcat);
            p.setType(findTypeBySubcategory(newSubcat));
        }
        if (newPrice != null) {
            p.setPrice(newPrice);
        }
        if (newQty != null) {
            p.setQuantity(newQty);
        }
        saveProducts();
    }
    public QuantityType findTypeBySubcategory(String subcategory){
        for (Product p : getProducts()){
            if (subcategory.equals(p.getSubcategory())){
                return p.getQuantityType();
            }
        }
        return QuantityType.PIECES;

    }
}




//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Η κλάση SupermarketAPI λειτουργεί ως ενδιάμεσο στρώμα μεταξύ των
 * διαχειριστών προϊόντων, κατηγοριών και χρηστών (controller). Παρέχει μεθόδους για
 * διαχείριση χρηστών, προϊόντων και κατηγοριών, καθώς και λειτουργίες
 * αναζήτησης και επεξεργασίας δεδομένων.
 */
public class SupermarketAPI {
    private final ProductManager productManager = new ProductManager();
    private final CategoryManager categoryManager = new CategoryManager();
    private final UserManager userManager = new UserManager();
    private final FileManager fileManager = new FileManager();

    public SupermarketAPI() {
    }

    public HashMap<String, User> getUsers() {
        return this.userManager.getUsers();
    }

    public User login(String username, String password) {
        return this.userManager.login(username, password);
    }

    public boolean registerCustomer(String username, String password, String firstName, String lastName) {
        return this.userManager.registerCustomer(username, password, firstName, lastName);
    }
    public ArrayList<Product> searchProducts(String title, String category, String subCategory) {
        return this.productManager.searchProducts(title, category, subCategory);
    }

    public List<Product> getProducts() {
        return productManager.getProducts();
    }

    public void addProduct(Product product) {
        this.productManager.addProduct(product);
    }
    public void addProduct(String title, String description, String category, String subcategory, double price, int quantity, QuantityType type) {
        this.productManager.addProduct(title, description, category, subcategory, price, quantity, type);
    }

    public void removeProduct(Product product) {
        this.productManager.removeProduct(product);

    }

    public void editProduct(Product product, String newTitle, String newDesc, String newCat, String newSubcat, double newPrice, int newQty) {
        this.productManager.editProduct(product, newTitle, newDesc, newCat, newSubcat, newPrice, newQty);
    }
    public Product findProductByTitle(String title) {
        return this.productManager.findProductByTitle(title);
    }
    public List<String> getCategories(){
        return categoryManager.getCategories();
    }

    public List<String> getAllSubcategories(){
        return categoryManager.getAllSubcategories();
    }
    public List<String> getSubcategories(String category){
        return categoryManager.getSubcategories(category);
    }
    public String getCategoryWithSub(String subcategory) {
        return categoryManager.getCategoryWithSub(subcategory);
    }

    public boolean isAdmin(String adminUsername) {
        return userManager.isAdmin(adminUsername);
    }

    public QuantityType findTypeBySubcategory(String subcategory){
        return productManager.findTypeBySubcategory(subcategory);
    }

    public void saveProducts() {
        this.fileManager.saveProducts(this.productManager.getProducts());
    }

}

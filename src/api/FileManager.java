package api;

import java.io.*;
import java.util.*;

/**
 * Αυτή η κλάση διαχειρίζεται τις κατηγορίες, τους χρήστες και τα προϊόντα του συστήματος,
 * επιτρέποντας τη φόρτωση και αποθήκευσή τους από και προς αρχεία κειμένου.
 */
public class FileManager {
    private String categoryFile;
    private String productFile;
    private String userFile;

    /**
     * Δημιουργεί ένα νέο instance της κλάσης FileManager.
     * Αρχικοποιεί τις προεπιλεγμένες διαδρομές των αρχείων.
     */
    public FileManager() {
        this.categoryFile = "src/resources/categories_subcategories.txt";
        this.productFile = "src/resources/products.txt";
        this.userFile = "src/resources/users.txt";
    }

    /**
     * Δημιουργεί ένα νέο instance της κλάσης FileManager με συγκεκριμένα αρχεία προϊόντων και χρηστών.
     *
     * @param productFile Το αρχείο που περιέχει τα προϊόντα.
     * @param userFile    Το αρχείο που περιέχει τους χρήστες.
     */
    public FileManager(String productFile, String userFile) {
        this.productFile = productFile;
        this.userFile = userFile;
    }

    /**
     * Φορτώνει τις κατηγορίες και τις υποκατηγορίες τους από το αρχείο categoryFile.
     * Διαβάζει κάθε γραμμή του αρχείου, διαχωρίζει την κατηγορία από τις υποκατηγορίες,
     * και τις προσθέτει στο Map των κατηγοριών.
     *
     * @param categories Το Map στο οποίο θα φορτωθούν οι κατηγορίες και οι υποκατηγορίες τους.
     */
    public void loadCategories(Map<String, List<String>> categories){
        try(BufferedReader reader = new BufferedReader(new FileReader(categoryFile))){
            String line;
            while ((line =reader.readLine()) != null){
                String[] parts = line.split("\\(");
                String category = parts[0].trim();
                String subcategoriesPart = parts[1].replace(")","").trim();

                String[] subcategories = subcategoriesPart.split("@");
                List<String> subs= new ArrayList<>();
                for (String sub : subcategories){
                    subs.add(sub.trim());
                }
                categories.put(category, subs);
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Φορτώνει τα προϊόντα από το αρχείο productFile.
     * Διαβάζει κάθε προϊόν από το αρχείο, δημιουργεί αντικείμενα Product
     * και τα προσθέτει στη λίστα των προϊόντων.
     *
     * @param products Η λίστα στην οποία θα φορτωθούν τα προϊόντα.
     */
    public void loadProducts(List<Product> products){
        products.clear();
        try(BufferedReader reader = new BufferedReader(new FileReader(productFile))){
            String line;
            while ((line = reader.readLine()) != null){
                String title = line.split(":")[1].trim();

                line = reader.readLine();
                String description = line.split(":")[1].trim();

                line = reader.readLine();
                String category = line.split(":")[1].trim();

                line = reader.readLine();
                String subcategory = line.split(":")[1].trim();

                line = reader.readLine();
                String stringPrice = line.split(":")[1].trim().replace("€","").replace(",",".");
                double price = Double.parseDouble(stringPrice);

                line = reader.readLine();
                String quantityString;
                QuantityType quantityType;
                if (line.contains("kg")){
                    quantityString = line.split(":")[1].trim().replace("kg", "");
                    quantityType = QuantityType.KILOGRAMS;
                }
                else{
                    quantityString = line.split(":")[1].trim().replace(" τεμάχια", "");
                    quantityType = QuantityType.PIECES;
                }
                int quantity = Integer.parseInt(quantityString);

                products.add(new Product(title, description, category, subcategory, price, quantity, quantityType));
                line = reader.readLine();
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Αποθηκεύει τη λίστα των προϊόντων στο αρχείο productFile στη μορφοποιημένη τους μορφή.
     *
     * @param products Η λίστα των προϊόντων που θα αποθηκευτούν.
     */
    public void saveProducts(List<Product> products){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(productFile))) {
            for (Product product : products){
                writer.write("Τίτλος: " + product.getTitle());
                writer.newLine();
                writer.write("Περιγραφή: " + product.getDescription());
                writer.newLine();
                writer.write("Κατηγορία: " + product.getCategory());
                writer.newLine();
                writer.write("Υποκατηγορία: "+ product.getSubcategory());
                writer.newLine();
                writer.write("Τιμή: " + Double.toString(product.getPrice()).replace(".", ",") + "€");
                writer.newLine();

                String unit = (product.getQuantityType() == QuantityType.KILOGRAMS) ? "kg" : " τεμάχια";
                writer.write("Ποσότητα: " + product.getQuantity() + unit);

                writer.newLine();
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Φορτώνει τους χρήστες από το αρχείο userFile.
     * Διαβάζει κάθε γραμμή του αρχείου, δημιουργεί αντικείμενα User
     * και τα προσθέτει στο Map των χρηστών.
     *
     * @param users Το Map στο οποίο θα φορτωθούν οι χρήστες.
     */
    public void loadUsers(Map<String,User> users){
        users.clear();
        try(BufferedReader reader = new BufferedReader(new FileReader(userFile))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(";");
                if (parts.length == 5){
                    String name = parts[0].trim();
                    String pass = parts[1].trim();
                    Role role = Role.valueOf(parts[2].trim());
                    String firstName = parts[3].trim();
                    String lastName = parts[4].trim();
                    User user = new User(name, pass, role, firstName, lastName);
                    users.put(name.toLowerCase(), user);
                }
                else if (parts.length == 3){
                    String name = parts[0].trim();
                    String pass = parts[1].trim();
                    Role role = Role.valueOf(parts[2].trim());
                    User user = new User(name, pass, role, "", "");
                    users.put(name.toLowerCase(), user);
                }
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Αποθηκεύει τους χρήστες στο αρχείο userFile.
     *
     * @param users Το Map που περιέχει τους χρήστες προς αποθήκευση.
     */
    public void saveUsers(Map<String, User> users){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (User user : users.values()){
                writer.write(user.getUsername()+ ";" + user.getPassword() + ";" + user.getRole().name() + ";" + user.getFirstName() + ";" + user.getLastName());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
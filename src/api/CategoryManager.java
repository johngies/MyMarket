package api;

import java.util.*;
/**
 * Η κλάση CategoryManager διαχειρίζεται τις κατηγορίες και τις υποκατηγορίες
 * του συστήματος, επιτρέποντας τη φόρτωση, την πρόσβαση τους.
 */
public class CategoryManager {
    private final FileManager fileManager = new FileManager();
    private final Map<String, List<String>> categoriesSub;

    /**
     * Δημιουργεί ένα νέο instance της κλάσης CategoryManager.
     * Αρχικοποιεί το categoriesSub και φορτώνει τις κατηγορίες από το αρχείο.
     */
    public CategoryManager() {
        this.categoriesSub = new HashMap<>();
        loadCategories();
    }
    /**
     * Φορτώνει τις κατηγορίες και τις υποκατηγορίες τους από το αρχείο χρησιμοποιώντας τον {@code FileManager}.
     */
    public void loadCategories() {
        fileManager.loadCategories(categoriesSub);
    }
    /**
     * Επιστρέφει το Map<String, List<String>> που περιέχει τις κατηγορίες και τις υποκατηγορίες τους.
     *
     * @return Το Map<String, List<String>> με τις κατηγορίες ως κλειδιά και τις λίστες υποκατηγοριών ως τιμές.
     */
    public Map<String, List<String>> getCategoriesSubcategories(){
        return categoriesSub;
    }
    /**
     * Επιστρέφει το σύνολο των κατηγοριών που υπάρχουν στο σύστημα.
     *
     * @return Ένα Set<String> που περιέχει όλα τα ονόματα των κατηγοριών.
     */
    public List<String> getCategories(){
        List<String> categories = new ArrayList<>(categoriesSub.keySet());
        return categories;
    }
    /**
     * Επιστρέφει τη συλλογή των υποκατηγοριών που υπάρχουν σε όλες τις κατηγορίες.
     *
     * @return Μια Collection που περιέχει όλες τις λίστες υποκατηγοριών.
     */
    public List<String> getSubcategories(String category){
        List<String> selectedSubcategories = new ArrayList<>();
        for (String sub : categoriesSub.get(category)){
            selectedSubcategories.add(sub);
        }
        return selectedSubcategories;
    }
    /**
    * Επιστρέφει την κατηγορία που περιλαμβάνει τη δεδομένη υποκατηγορία.
    *
    * @param subcategory Η υποκατηγορία για την οποία αναζητείται η κατηγορία.
    * @return Το όνομα της κατηγορίας που περιλαμβάνει τη συγκεκριμένη υποκατηγορία ή null αν δε βρεθεί.
    */
    public String getCategoryWithSub(String subcategory){
        for (Map.Entry<String, List<String>> entry : categoriesSub.entrySet()) {
            String category = entry.getKey();
            List<String> subcategories = entry.getValue();

            if (subcategories != null) {
                for (String sub : subcategories) {
                    if (sub.equals(subcategory)) {
                        return category;
                    }
                }
            }
        }
        return null;
    }
    /**
     * Επιστρέφει όλες τις υποκατηγορίες από όλες τις κατηγορίες.
     *
     * @return Μια λίστα με όλες τις υποκατηγορίες. Αν δεν υπάρχουν υποκατηγορίες, αλλιώς μια κενή λίστα.
     */
    public List<String> getAllSubcategories(){
        List<String> subcategories = new ArrayList<>();
        for (List<String> sub : categoriesSub.values()){
            subcategories.addAll(sub);
        }
        return subcategories;
    }
}

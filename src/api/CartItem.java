package api;

/**
 * Η κλάση CartItem αντιπροσωπεύει ένα προϊόν στο καλάθι αγορών μαζί με την ποσότητά του.
 */
public class CartItem {
    private Product product;
    private int quantity;

    /**
     * Δημιουργεί ένα νέο στοιχείο καλαθιού με το καθορισμένο προϊόν και την ποσότητά του.
     * @param product Το προϊόν που προστίθεται στο καλάθι.
     * @param quantity Η ποσότητα του προϊόντος.
     */
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Επιστρέφει το προϊόν που συνδέεται με αυτό το στοιχείο καλαθιού.
     * @return Το αντικείμενο Product.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Ενημερώνει το προϊόν του στοιχείου καλαθιού.
     * @param product Το νέο προϊόν.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Επιστρέφει την ποσότητα του προϊόντος στο καλάθι.
     * @return Η ποσότητα ως ακέραιος αριθμός.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Ενημερώνει την ποσότητα του προϊόντος στο καλάθι.
     * @param quantity Η νέα ποσότητα (πρέπει να είναι μεγαλύτερη από 0).
     * @throws IllegalArgumentException Αν η ποσότητα είναι μικρότερη ή ίση με 0.
     */
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Η ποσότητα πρέπει να είναι μεγαλύτερη από 0.");
        }
        this.quantity = quantity;
    }

    /**
     * Επιστρέφει μια συμβολοσειρά με τον τίτλο του προϊόντος και τον τύπο της ποσότητάς του.
     * @return Η μορφοποιημένη συμβολοσειρά του CartItem.
     */
    @Override
    public String toString(){
        String quantityType = product.getQuantityType().toString().toLowerCase();
        String formattedQuantityType = Character.toUpperCase(quantityType.charAt(0)) + quantityType.substring(1);

        return product.getTitle() + " - Ποσότητα: " + formattedQuantityType;
    }
}
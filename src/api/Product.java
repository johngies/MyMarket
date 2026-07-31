package api;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/** Η κλάση Product αναπαριστά ένα προϊόν στο σύστημα του supermarket.
 * Περιέχει πληροφορίες όπως ο τίτλος, η περιγραφή, η κατηγορία, η υποκατηγορία,
 * η τιμή, η ποσότητα και η μονάδα μέτρησης.
 */
public class Product {
    private String title, description, category, subcategory;
    private double price;
    private int quantity;
    private QuantityType type;

    public Product() {}
    /**
     * Constructor της κλάσης που δημιουργεί ένα νέο προϊόν με τα παρεχόμενα χαρακτηριστικά.
     *
     * @param title       Ο τίτλος του προϊόντος.
     * @param description Η περιγραφή του προϊόντος.
     * @param category    Η κατηγορία στην οποία ανήκει το προϊόν.
     * @param subcategory Η υποκατηγορία του προϊόντος.
     * @param price       Η τιμή του προϊόντος.
     * @param quantity    Η ποσότητα διαθέσιμη του προϊόντος.
     * @param type        Ο τύπος ποσότητας του προϊόντος.
     */
    public Product(String title, String description, String category, String subcategory, double price, int quantity, QuantityType type) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.subcategory = subcategory;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public QuantityType getQuantityType() {
        return type;
    }
    public void setType(QuantityType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        Locale greekLocale = Locale.forLanguageTag("el-GR");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(greekLocale);
        return "Τίτλος: " + title + "\n" +
                "Περιγραφή: " + description + "\n" +
                "Κατηγορία: " + category + "\n" +
                "Υποκατηγορία: " + subcategory + "\n" +
                "Τιμή: " + numberFormat.format(price) + "€\n" +
                "Ποσότητα: " + quantity + type.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return title.equals(product.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}

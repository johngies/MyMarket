package api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
/**
 * Test κλάση για το ProductManager. Παρέχεται άλλο αρχείο για τα tests.
 */
public class ProductManagerTest {

    ProductManager productManager;
    private Product p1, p2, p3, p4;
    @Before
    public void setUp() throws Exception {
        FileManager fileManager = new FileManager("test/api/products_test.txt", "test/api/users_test.txt");
        productManager = new ProductManager(fileManager);

        productManager.getProducts().clear();
        p1 = new Product("Γάλα", "Φρέσκο γάλα.", "Προϊόντα ψυγείου", "Γάλα", 12.99, 4, QuantityType.PIECES);
        p2 = new Product("Πορτοκάλια", "Φρέσκα πορτοκάλια.", "Φρέσκα τρόφιμα", "Φρούτα", 12.99, 6, QuantityType.KILOGRAMS);
        p3 = new Product("Γιαούρτι", "Φρέσκο γιαούρτι.", "Προϊόντα ψυγείου", "Γιαούρτια", 5, 4, QuantityType.PIECES);
        p4 = new Product("Μήλα", "Φρέσκο μήλα.", "Φρέσκα τρόφιμα", "Φρούτα", 2, 10, QuantityType.KILOGRAMS);
    }
    /**
     * Δοκιμάζει τη διαδικασία προσθήκης ενός νέου προϊόντος.
     */
    @Test
    public void addProduct() {

        productManager.addProduct(p1);
        productManager.addProduct(p2);
        productManager.addProduct(p3);
        productManager.addProduct(p4);

        assertEquals(4, productManager.getProducts().size());
    }
    /**
     * Δοκιμάζει τη διαδικασία διαγραφής ενός προϊόντος.
     */
    @Test
    public void removeProduct() {
        productManager.addProduct(p1);
        productManager.addProduct(p2);
        productManager.addProduct(p3);

        productManager.removeProduct(p2);

        assertEquals(2, productManager.getProducts().size());
        assertFalse(productManager.getProducts().contains(p2.getTitle()));
    }

    @Test
    public void findProductByTitle() {
        productManager.addProduct(p1);
        productManager.addProduct(p2);
        productManager.addProduct(p3);
        productManager.addProduct(p4);

        Product foundProduct = productManager.findProductByTitle("γάλα");

        assertEquals(p1, foundProduct);
    }
    @Test
    public void findProductByTitleNull() {
        productManager.addProduct(p1);
        productManager.addProduct(p2);
        productManager.addProduct(p3);
        productManager.addProduct(p4);

        Product foundProduct = productManager.findProductByTitle("Κεράσια");

        assertNull(foundProduct);
    }
    /**
     * Δοκιμάζει την αναζήτηση προϊόντων.
     */
    @Test
    public void searchProducts() {
        productManager.addProduct(p1);
        productManager.addProduct(p2);
        productManager.addProduct(p3);
        productManager.addProduct(p4);

        ArrayList<Product> searchedProducts = productManager.searchProducts("", "προϊόντα ψυγείου","");

        assertEquals(2, searchedProducts.size());
    }
    /**
     * Δοκιμάζει τη διαδικασία ενημέρωσης ενός προϊόντος.
     */
    @Test
    public void editProduct() {
        productManager.addProduct(p1);
        productManager.editProduct(p1, "Γαλατάκι", "Φρέσκο γάλα με γεύση βανίλιας.", "Προϊόντα ψυγείου", "Γάλα", 13.99, 5);

        Product updatedProduct = productManager.findProductByTitle("Γαλατάκι");
        assertNotNull(updatedProduct);
        assertEquals( 13.99, updatedProduct.getPrice(), 0.001);
        assertEquals(5, updatedProduct.getQuantity());
    }

}
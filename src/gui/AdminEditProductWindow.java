package gui;

import api.Product;
import api.SupermarketAPI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Η κλάση AdminEditProductWindow δημιουργεί ένα παράθυρο διαλόγου (JDialog) για την επεξεργασία υπαρχόντων προϊόντων
 * στο σύστημα του SupermarketAPI. Παρέχει πεδία εισαγωγής για τον τίτλο, την περιγραφή, την κατηγορία,
 * την υποκατηγορία, την τιμή και την ποσότητα του προϊόντος. Επιτρέπει επίσης την επιλογή της κατηγορίας
 * και της υποκατηγορίας μέσω JComboBox.
 *
 * Όταν ο χρήστης πατάει το κουμπί "Αποθήκευση", η κλάση επικοινωνεί με τον controller (SupermarketAPI)
 * για να ενημερώσει τα στοιχεία του προϊόντος. Ελέγχει την εγκυρότητα των εισαγόμενων δεδομένων και ενημερώνει
 * τον χρήστη σχετικά με την επιτυχία ή αποτυχία της ενέργειας.
 */
public class AdminEditProductWindow extends JDialog {

    private JTextField titleField, descriptionField, priceField, quantityField;
    private JLabel titleLabel, descriptionLabel, priceLabel, quantityLabel, categoryLabel, subcategoryLabel;
    private JButton saveButton;
    private SupermarketAPI controller;

    /**
     * Δημιουργεί και αρχικοποιεί το παράθυρο επεξεργασίας ενός υπάρχοντος προϊόντος.
     *
     * @param parent Το γονικό παράθυρο (JFrame) πάνω στο οποίο εμφανίζεται ο διάλογος.
     * @param controller Ο controller του συστήματος (SupermarketAPI) για τη διαχείριση των δεδομένων.
     * @param model Το μοντέλο του πίνακα (DefaultTableModel) για την ενημέρωση του GUI.
     * @param product Το αντικείμενο Product που πρόκειται να επεξεργαστεί.
     * @param disProducts Η λίστα των προϊόντων που εμφανίζονται αυτή τη στιγμή στην οθόνη.
     */
    public AdminEditProductWindow(JFrame parent, SupermarketAPI controller, DefaultTableModel model, Product product, List<Product> disProducts) {
        super(parent, "Επεξεργασία Προϊόντος", true);
        this.controller = controller;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(340, 380));
        setSize(380, 420);
        setResizable(true);
        setLocationRelativeTo(parent);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);

        titleLabel = new JLabel("Τίτλος:");
        descriptionLabel = new JLabel("Περιγραφή:");
        categoryLabel = new JLabel("Κατηγορία:");
        subcategoryLabel = new JLabel("Υποκατηγορία:");
        priceLabel = new JLabel("Τιμή:");
        quantityLabel = new JLabel("Ποσότητα:");
        saveButton = new JButton("Αποθήκευση");

        titleField = new JTextField(product.getTitle());
        descriptionField = new JTextField(product.getDescription());
        priceField = new JTextField(String.valueOf(product.getPrice()));
        quantityField = new JTextField(String.valueOf(product.getQuantity()));

        List<String> categories = controller.getCategories();
        JComboBox<String> comboBoxCat = new JComboBox<>();
        for (String subs : categories) {
            comboBoxCat.addItem(subs);
        }
        comboBoxCat.setSelectedItem(product.getCategory());

        JComboBox<String> comboBoxSubCat = new JComboBox<>();
        List<String> subcategories = controller.getSubcategories(product.getCategory());
        for (String subs : subcategories) {
            comboBoxSubCat.addItem(subs);
        }
        comboBoxSubCat.setSelectedItem(product.getSubcategory());

        comboBoxCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxSubCat.removeAllItems();
                String selectedCat = (String) comboBoxCat.getSelectedItem();
                List<String> subcats = controller.getSubcategories(selectedCat);
                for (String subs : subcats) {
                    comboBoxSubCat.addItem(subs);
                }
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(titleLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(descriptionLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(descriptionField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(categoryLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(comboBoxCat, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(subcategoryLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(comboBoxSubCat, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(priceLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(priceField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(quantityLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(quantityField, gbc);

        saveButton.setFocusable(false);
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(saveButton, gbc);

        saveButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText().trim();
                String description = descriptionField.getText().trim();
                String category = (String) comboBoxCat.getSelectedItem();
                String subcategory = (String) comboBoxSubCat.getSelectedItem();
                String price = priceField.getText().trim();
                String quantity = quantityField.getText().trim();

                double priceD;
                int quantityI;

                if (title.isEmpty() || description.isEmpty() || price.isEmpty() || quantity.isEmpty() || subcategory == null) {
                    JOptionPane.showMessageDialog(AdminEditProductWindow.this,
                            "Παρακαλώ συμπληρώστε όλα τα πεδία.",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    priceD = Double.parseDouble(priceField.getText());
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(AdminEditProductWindow.this,
                            "Η τιμή πρέπει να είναι αριθμός.",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    quantityI = Integer.parseInt(quantityField.getText());
                } catch (NumberFormatException e3) {
                    String quantityText = quantityField.getText();
                    if (quantityText.contains(".")) {
                        JOptionPane.showMessageDialog(AdminEditProductWindow.this,
                                "Η ποσότητα πρέπει να είναι ακέραιος αριθμός χωρίς δεκαδικά ψηφία.",
                                "Σφάλμα",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(AdminEditProductWindow.this,
                                "Η ποσότητα πρέπει να είναι ακέραιος αριθμός.",
                                "Σφάλμα",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                }

                if (priceD < 0 || quantityI < 0) {
                    JOptionPane.showMessageDialog(AdminEditProductWindow.this,
                            "Η τιμή και η ποσότητα πρέπει να είναι μην αρνητικές.",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (controller.getProducts().contains(controller.findProductByTitle(title)) && !title.trim().equalsIgnoreCase(product.getTitle())){
                    JOptionPane.showMessageDialog(AdminEditProductWindow.this,
                            "Υπάρχει ήδη προϊόν με τίτλο \"" + title + "\".",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                controller.editProduct(product, title, description, category, subcategory, priceD, quantityI);
                controller.saveProducts(); // Αποθήκευση αλλαγών στο αρχείο
                
                JOptionPane.showMessageDialog(AdminEditProductWindow.this,
                        "Το προϊόν επεξεργάστηκε επιτυχώς!",
                        "Επιτυχία",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        setVisible(true);
    }
}
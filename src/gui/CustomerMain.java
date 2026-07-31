package gui;

import api.Product;
import api.User;
import api.SupermarketAPI;
import api.Cart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Η κλάση CustomerMain διαχειρίζεται το κύριο παράθυρο διεπαφής χρήστη (GUI) για τους πελάτες του SupermarketAPI.
 * 
 * Παρέχει δυνατότητες για την προβολή των διαθέσιμων προϊόντων, την αναζήτηση και το φιλτράρισμά τους 
 * ανά κατηγορία/υποκατηγορία, την προσθήκη προϊόντων στο καλάθι αγορών και την προβολή/διαχείριση του καλαθιού.
 */
public class CustomerMain {
    private final JFrame frame;
    private final JTable table;
    private JLabel titleLabel, categoryLabel, subcategoryLabel, userLabel;
    private JTextField titleField;
    private JButton addToCartButton, viewCartButton, searchButton;
    private JComboBox<String> comboBoxCat, comboBoxSubCat;
    private DefaultTableModel tableModel;
    private SupermarketAPI controller;
    private Cart cart;

    /**
     * Δημιουργεί και αρχικοποιεί το κύριο παράθυρο του πελάτη.
     *
     * @param controller Ο controller του συστήματος (SupermarketAPI) για την επικοινωνία με τη λογική της εφαρμογής.
     * @param user Ο συνδεδεμένος χρήστης/πελάτης.
     */
    public CustomerMain(SupermarketAPI controller, User user) {
        this.controller = controller;
        this.cart = new Cart(); 
        
        frame = new JFrame("Κεντρικό Μενού Πελάτη");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setMinimumSize(new Dimension(850, 500));
        frame.setSize(950, 670);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout(10, 10));

        addToCartButton = new JButton("Προσθήκη στο Καλάθι");
        addToCartButton.setFocusable(false);

        viewCartButton = new JButton("Προβολή Καλαθιού");
        viewCartButton.setFocusable(false);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        topPanel.add(addToCartButton);
        topPanel.add(viewCartButton);
        frame.add(topPanel, BorderLayout.NORTH);

        titleLabel = new JLabel("Τίτλος:");
        categoryLabel = new JLabel("Κατηγορία:");
        subcategoryLabel = new JLabel("Υποκατηγορία:");

        searchButton = new JButton("Αναζήτηση");
        searchButton.setFocusable(false);

        JMenuBar menuBar = new JMenuBar();
        JMenu accountMenu = new JMenu("Λογαριασμός");
        JMenuItem logoutItem = new JMenuItem("Αποσύνδεση");
        accountMenu.add(logoutItem);
        menuBar.add(accountMenu);
        menuBar.add(Box.createHorizontalGlue());
        userLabel = new JLabel("Πελάτης: " + user.getUsername() + " ");
        menuBar.add(userLabel);
        frame.setJMenuBar(menuBar);

        tableModel = new DefaultTableModel(new String[]{"Προϊόν", "Περιγραφή", "Κατηγορία", "Υποκατηγορία", "Τιμή", "Διαθεσιμότητα"}, 0);
        table = new JTable(tableModel);

        List<Product> displayedProducts = new ArrayList<>();
        JScrollPane scrollPane = new JScrollPane(table);

        for (Product product : controller.getProducts()){
            tableModel.addRow(new Object[]{product.getTitle(),
                                           product.getDescription(),
                                           product.getCategory(),
                                           product.getSubcategory(),
                                           product.getPrice() + "€",
                                           product.getQuantity() + "" + product.getQuantityType()});
            displayedProducts.add(product);
        }
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));

        titleField = new JTextField();
        titleField.setPreferredSize(new Dimension(150, 25));

        List<String> categories = controller.getCategories();
        comboBoxCat = new JComboBox<>();
        categories.add("Όλες οι κατηγορίες");
        for (String subs : categories){
            comboBoxCat.addItem(subs);
        }
        comboBoxCat.setSelectedItem("Όλες οι κατηγορίες");
        comboBoxCat.setPreferredSize(new Dimension(160, 25));

        List<String> subcategories = controller.getAllSubcategories();
        Collections.sort(subcategories);
        comboBoxSubCat = new JComboBox<>();
        comboBoxSubCat.addItem("Όλες οι υποκατηγορίες");
        comboBoxSubCat.setSelectedItem("Όλες οι υποκατηγορίες");
        comboBoxSubCat.setPreferredSize(new Dimension(160, 25));

        comboBoxCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) comboBoxCat.getSelectedItem();
                comboBoxSubCat.removeAllItems();
                if (selectedCategory != null && !selectedCategory.equals("Όλες οι κατηγορίες")) {
                    List<String> subcategories = controller.getSubcategories(selectedCategory);
                    for (String subcategory : subcategories) {
                        comboBoxSubCat.addItem(subcategory);
                    }
                    comboBoxSubCat.addItem("Όλες οι υποκατηγορίες");
                    comboBoxSubCat.setSelectedItem("Όλες οι υποκατηγορίες");
                }
            }
        });

        bottomPanel.add(titleLabel);
        bottomPanel.add(titleField);
        bottomPanel.add(categoryLabel);
        bottomPanel.add(comboBoxCat);
        bottomPanel.add(subcategoryLabel);
        bottomPanel.add(comboBoxSubCat);
        bottomPanel.add(searchButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Product selectedProduct = displayedProducts.get(selectedRow);
                    new CartItemWindow(frame, selectedProduct, cart);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Παρακαλώ επιλέξτε ένα προϊόν από τη λίστα.",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                }
                resetSearchAndRefresh();
            }
        });

        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CartWindow(frame, cart, controller, user);
                resetSearchAndRefresh();
            }
        });

        searchButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                displayedProducts.clear();

                String title = titleField.getText().trim();
                String category = (String) comboBoxCat.getSelectedItem();
                String subcategory = (String) comboBoxSubCat.getSelectedItem();

                if (category == null || category.equals("Όλες οι κατηγορίες")){
                    ArrayList<Product> searchedProducts = controller.searchProducts(title, null, null);
                    for (Product product: searchedProducts){
                        tableModel.addRow(new Object[]{ product.getTitle(),
                                                        product.getDescription(),
                                                        product.getCategory(),
                                                        product.getSubcategory(),
                                                        product.getPrice() + "€",
                                                        product.getQuantity() + "" + product.getQuantityType()});
                        displayedProducts.add(product);
                    }
                }
                else if (subcategory == null || subcategory.equals("Όλες οι υποκατηγορίες")){
                    ArrayList<Product> searchedProducts = controller.searchProducts(title, category, null);
                    for (Product product: searchedProducts){
                        tableModel.addRow(new Object[]{ product.getTitle(),
                                                        product.getDescription(),
                                                        product.getCategory(),
                                                        product.getSubcategory(),
                                                        product.getPrice() + "€",
                                                        product.getQuantity() + "" + product.getQuantityType()});
                        displayedProducts.add(product);
                    }
                }
                else {
                    ArrayList<Product> searchedProducts = controller.searchProducts(title, category, subcategory);
                    for (Product product : searchedProducts){
                        tableModel.addRow(new Object[]{ product.getTitle(),
                                                        product.getDescription(),
                                                        product.getCategory(),
                                                        product.getSubcategory(),
                                                        product.getPrice() + "€",
                                                        product.getQuantity() + "" + product.getQuantityType()});
                        displayedProducts.add(product);
                    }
                }
                if (tableModel.getRowCount() == 0){
                    JOptionPane.showMessageDialog(frame,
                            "Δεν βρέθηκε προϊόν",
                            "Μήνυμα",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                int response = JOptionPane.showConfirmDialog(
                        frame,
                        "Είστε σίγουρος ότι θέλετε να εξέλθετε;",
                        "Επιβεβαίωση Εξόδου",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        logoutItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage(controller);
                frame.dispose();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Καθαρίζει τα πεδία αναζήτησης και τα φίλτρα κατηγοριών, 
     * και εκτελεί αυτόματα αναζήτηση για την ανανέωση των προϊόντων στον πίνακα.
     */
    private void resetSearchAndRefresh() {
        titleField.setText("");
        comboBoxCat.setSelectedItem("Όλες οι κατηγορίες");
        searchButton.doClick();
    }
}
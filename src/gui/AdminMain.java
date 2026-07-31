package gui;

import api.Product;
import api.User;
import api.SupermarketAPI;

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
 * Η κλάση AdminMain διαχειρίζεται το κύριο παράθυρο διαχειριστή του SupermarketAPI.
 * Παρέχει λειτουργίες για την προβολή, προσθήκη, διαγραφή, επεξεργασία και αναζήτηση προϊόντων.
 * Επίσης, χειρίζεται την αποσύνδεση του χρήστη και την έξοδο από την εφαρμογή.
 */
public class AdminMain {
    private final JFrame frame;
    private final JTable table;
    private JLabel titleLabel, categoryLabel, subcategoryLabel, userLabel;
    private JTextField titleField;
    private JButton addButton, removeButton, editButton, searchButton;
    private JComboBox<String> comboBoxCat, comboBoxSubCat;
    private DefaultTableModel tableModel;
    private SupermarketAPI controller;

    /**
     * Δημιουργεί και αρχικοποιεί το κύριο παράθυρο του διαχειριστή.
     *
     * @param controller Ο controller του συστήματος (SupermarketAPI) για τη διαχείριση των δεδομένων.
     * @param user Ο συνδεδεμένος χρήστης με ρόλο διαχειριστή (Admin).
     */
    AdminMain(SupermarketAPI controller, User user){
        this.controller = controller;
        frame = new JFrame("Κεντρικό Μενού");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setMinimumSize(new Dimension(850, 500));
        frame.setSize(950, 670);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout(10, 10));

        addButton = new JButton("Προσθήκη");
        addButton.setFocusable(false);

        removeButton = new JButton("Διαγραφή");
        removeButton.setFocusable(false);

        editButton = new JButton("Επεξεργασία");
        editButton.setFocusable(false);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        adminCustomisation(topPanel, user);
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
        userLabel = new JLabel("Χρήστης: " + user.getUsername() + " ");
        menuBar.add(userLabel);
        frame.setJMenuBar(menuBar);

        tableModel = new DefaultTableModel(new String[]{"Προϊόν", "Περιγραφή", "Κατηγορία", "Υποκατηγορία", "Τιμή", "Ποσότητα"}, 0);
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

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminAddProductWindow(AdminMain.this.frame, controller, tableModel);
                resetSearchAndRefresh();
            }
        });

        removeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Product productToRemove = displayedProducts.get(selectedRow);
                    tableModel.removeRow(selectedRow);
                    displayedProducts.remove(selectedRow);
                    controller.removeProduct(productToRemove);
                    JOptionPane.showMessageDialog(frame,
                            "Το προϊόν αφαιρέθηκε με επιτυχία!",
                            "Επιτυχία",
                            JOptionPane.INFORMATION_MESSAGE);
                    resetSearchAndRefresh();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Παρακαλώ επιλέξτε ένα προϊόν για διαγραφή.",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                }
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

        editButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Product productToEdit = displayedProducts.get(selectedRow);
                    new AdminEditProductWindow(AdminMain.this.frame, controller, tableModel, productToEdit, displayedProducts);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Παρακαλώ επιλέξτε ένα προϊόν για επεξεργασία.",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                }
                resetSearchAndRefresh();
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
     * Προσαρμόζει τα ορατά κουμπιά του πανελ ανάλογα με τα δικαιώματα του χρήστη.
     *
     * @param topPanel Το πάνελ στο οποίο προστίθενται τα κουμπιά διαχείρισης.
     * @param user Ο χρήστης για τον οποίο γίνεται ο έλεγχος ρόλου.
     */
    public void adminCustomisation(JPanel topPanel, User user){
        if (controller.isAdmin(user.getUsername())){
            topPanel.add(addButton);
            topPanel.add(removeButton);
            topPanel.add(editButton);
        }
    }

    /**
     * Καθαρίζει το πεδίο αναζήτησης και επαναφέρει τα φίλτρα, 
     * ανανεώνοντας τη λίστα των προϊόντων στον πίνακα.
     */
    private void resetSearchAndRefresh() {
        titleField.setText("");
        comboBoxCat.setSelectedItem("Όλες οι κατηγορίες");
        searchButton.doClick();
    }
}
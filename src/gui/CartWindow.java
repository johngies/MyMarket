package gui;

import api.Cart;
import api.CartItem;
import api.Product;
import api.SupermarketAPI;
import api.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Η κλάση CartWindow δημιουργεί ένα παράθυρο διαλόγου (JDialog) για την προβολή και διαχείριση
 * του καλαθιού αγορών του πελάτη.
 * 
 * Εμφανίζει τα προϊόντα που έχουν προστεθεί στο καλάθι σε πίνακα (τίτλος, τιμή μονάδας, ποσότητα,
 * υποσύνολο) και υπολογίζει το συνολικό κόστος. Επιτρέπει την αφαίρεση επιλεγμένων προϊόντων 
 * και την ολοκλήρωση της παραγγελίας.
 */
public class CartWindow extends JDialog {
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel totalLabel;

    /**
     * Δημιουργεί και αρχικοποιεί το παράθυρο προβολής του καλαθιού αγορών.
     *
     * @param parent Το γονικό παράθυρο (JFrame) πάνω στο οποίο εμφανίζεται ο διάλογος.
     * @param cart Το αντικείμενο Cart που περιέχει τα προϊόντα του καλαθιού.
     * @param controller Ο controller του συστήματος (SupermarketAPI) για τη διαχείριση και αποθήκευση των δεδομένων.
     * @param user Ο συνδεδεμένος χρήστης/πελάτης που πραγματοποιεί την αγορά.
     */
    public CartWindow(JFrame parent, Cart cart, SupermarketAPI controller, User user) {
        super(parent, "Το Καλάθι μου", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        tableModel = new DefaultTableModel(new String[]{"Προϊόν", "Τιμή Μονάδας", "Ποσότητα", "Σύνολο"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Συνολικό Κόστος: 0.00€");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalLabel);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton removeButton = new JButton("Αφαίρεση Επιλεγμένου");
        JButton checkoutButton = new JButton("Ολοκλήρωση Αγοράς");
        removeButton.setFocusable(false);
        checkoutButton.setFocusable(false);
        
        buttonsPanel.add(removeButton);
        buttonsPanel.add(checkoutButton);

        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshCartDisplay(cart);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    CartItem itemToRemove = cart.getCartItems().get(selectedRow);
                    cart.removeProduct(itemToRemove.getProduct());
                    refreshCartDisplay(cart);
                } else {
                    JOptionPane.showMessageDialog(CartWindow.this,
                            "Παρακαλώ επιλέξτε ένα προϊόν από το καλάθι για αφαίρεση.",
                            "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cart.getCartItems().isEmpty()) {
                    JOptionPane.showMessageDialog(CartWindow.this,
                            "Το καλάθι σας είναι άδειο.",
                            "Προσοχή", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                cart.checkout();
                controller.saveProducts();

                JOptionPane.showMessageDialog(CartWindow.this,
                        "Η παραγγελία ολοκληρώθηκε επιτυχώς!",
                        "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        setVisible(true);
    }

    /**
     * Ανανεώνει τα δεδομένα του πίνακα και την ετικέτα του συνολικού κόστους
     * με βάση τα τρέχοντα περιεχόμενα του καλαθιού.
     *
     * @param cart Το καλάθι αγορών από το οποίο αντλούνται τα στοιχεία.
     */
    private void refreshCartDisplay(Cart cart) {
        tableModel.setRowCount(0);
        
        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();
            double subtotal = product.getPrice() * item.getQuantity();
            
            tableModel.addRow(new Object[]{
                    product.getTitle(),
                    String.format("%.2f€", product.getPrice()),
                    item.getQuantity() + " " + product.getQuantityType(),
                    String.format("%.2f€", subtotal)
            });
        }
        
        totalLabel.setText(String.format("Συνολικό Κόστος: %.2f€", cart.calculateTotalPrice()));
    }
}
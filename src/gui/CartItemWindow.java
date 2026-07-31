package gui;

import api.Cart;
import api.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Η κλάση CartItemWindow δημιουργεί ένα παράθυρο διαλόγου (JDialog) για την επιλογή ποσότητας
 * ενός προϊόντος πριν την προσθήκη του στο καλάθι αγορών. 
 * 
 * Εμφανίζει τις πληροφορίες του επιλεγμένου προϊόντος (τίτλο, τιμή, διαθέσιμο απόθεμα) και παρέχει
 * πεδίο εισαγωγής για την ποσότητα. Ελέγχει την εγκυρότητα της ποσότητας και τη διαθεσιμότητα
 * του αποθέματος πριν την προσθήκη στο καλάθι.
 */
public class CartItemWindow extends JDialog {

    /**
     * Δημιουργεί και αρχικοποιεί το παράθυρο επιλογής ποσότητας για ένα προϊόν.
     *
     * @param parent Το γονικό παράθυρο (JFrame) πάνω στο οποίο εμφανίζεται ο διάλογος.
     * @param selectedProduct Το προϊόν που επιλέχθηκε από τον χρήστη για προσθήκη στο καλάθι.
     * @param cart Το καλάθι αγορών (Cart) στο οποίο θα προστεθεί το προϊόν.
     */
    public CartItemWindow(JFrame parent, Product selectedProduct, Cart cart) {
        super(parent, "Επιλογή Ποσότητας", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(350, 240));
        setSize(550, 240);
        setResizable(true);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel titleLabel = new JLabel("Προϊόν: " + selectedProduct.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel stockLabel = new JLabel("Διαθέσιμο απόθεμα: " + selectedProduct.getQuantity() + " " + selectedProduct.getQuantityType());
        JLabel priceLabel = new JLabel("Τιμή: " + selectedProduct.getPrice() + "€");
        
        JLabel quantityLabel = new JLabel("Ποσότητα:");
        JTextField quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(100, 25));
        JButton addButton = new JButton("Προσθήκη");
        addButton.setFocusable(false);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        gbc.gridy = 1;
        add(priceLabel, gbc);

        gbc.gridy = 2;
        add(stockLabel, gbc);

        gbc.gridy = 3; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        add(quantityLabel, gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(quantityField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(addButton, gbc);

        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int quantity = Integer.parseInt(quantityField.getText().trim());

                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(CartItemWindow.this,
                                "Η ποσότητα πρέπει να είναι μεγαλύτερη από 0.",
                                "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (quantity > selectedProduct.getQuantity()) {
                        JOptionPane.showMessageDialog(CartItemWindow.this,
                                "Δεν επαρκεί το απόθεμα. Ζητήσατε " + quantity + " αλλά υπάρχουν μόνο " + selectedProduct.getQuantity() + ".",
                                "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    cart.addProduct(selectedProduct, quantity);
                    
                    JOptionPane.showMessageDialog(CartItemWindow.this,
                            "Το προϊόν προστέθηκε στο καλάθι επιτυχώς!",
                            "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CartItemWindow.this,
                            "Παρακαλώ εισάγετε έναν έγκυρο ακέραιο αριθμό.",
                            "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        setVisible(true);
    }
}
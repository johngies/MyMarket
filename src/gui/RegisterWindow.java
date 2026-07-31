package gui;

import api.Role;
import api.SupermarketAPI;
import api.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Η κλάση RegisterWindow δημιουργεί ένα παράθυρο διαλόγου (JDialog) για την εγγραφή νέων χρηστών
 * στην εφαρμογή SupermarketAPI. Παρέχει πεδία εισαγωγής για το όνομα χρήστη, τον κωδικό πρόσβασης,
 * το όνομα και το επώνυμο του χρήστη. Όταν ο χρήστης πατάει το κουμπί "Αποθήκευση",
 * η κλάση επικοινωνεί με τον controller (SupermarketAPI) για να εγγράψει τον νέο χρήστη.
 * Ελέγχει την εγκυρότητα των εισαγόμενων δεδομένων και ενημερώνει τον χρήστη σχετικά με την
 * επιτυχία ή αποτυχία της εγγραφής.
 */
public class RegisterWindow extends JDialog {
    private JButton saveButton;
    private JPasswordField passwordField;
    private JTextField firstnameField, lastnameField, usernameField;
    private JLabel usernameLabel, passwordLabel, firstnameLabel, lastnameLabel;
    private SupermarketAPI controller;

    /**
     * Δημιουργεί και αρχικοποιεί το παράθυρο εγγραφής νέου χρήστη.
     *
     * @param parent Το γονικό παράθυρο (JFrame) πάνω στο οποίο εμφανίζεται ο διάλογος.
     * @param controller Ο controller του συστήματος (SupermarketAPI) για την καταχώριση του νέου πελάτη.
     */
    public RegisterWindow(JFrame parent, SupermarketAPI controller) {
        super(parent, "Εγγραφή χρήστη", true);
        this.controller = controller;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 260));
        setSize(360, 280);
        setResizable(true);
        setLocationRelativeTo(parent);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        firstnameField = new JTextField();
        lastnameField = new JTextField();
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Κωδικός:");
        firstnameLabel = new JLabel("Όνομα:");
        lastnameLabel = new JLabel("Επίθετο:");
        saveButton = new JButton("Αποθήκευση");
        saveButton.setFocusable(false);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(usernameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(firstnameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(firstnameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(lastnameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lastnameField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        add(saveButton, gbc);

        saveButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String firstname = firstnameField.getText();
                String lastname = lastnameField.getText();

                if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterWindow.this,
                            "Παρακαλώ συμπληρώστε όλα τα πεδία.",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = controller.registerCustomer(username, password, firstname, lastname);

                if (success) {
                    JOptionPane.showMessageDialog(RegisterWindow.this,
                            "Επιτυχής εγγραφή!",
                            "Επιτυχία",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegisterWindow.this,
                            "Η εγγραφή απέτυχε. Αυτό το username υπάρχει ήδη.",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}
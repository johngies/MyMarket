package gui;

import api.SupermarketAPI;
import api.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * Η κλάση LoginPage δημιουργεί το παράθυρο σύνδεσης (GUI) για το σύστημα SupermarketAPI.
 * 
 * Παρέχει πεδία για την εισαγωγή όνοματος χρήστη (username) και κωδικού πρόσβασης (password),
 * καθώς και κουμπιά για τη σύνδεση ή τη μεταφορά στο παράθυρο εγγραφής νέου χρήστη.
 * Αναλόγως με το ρόλο του χρήστη (Admin ή Customer), δρομολογεί την εφαρμογή στο αντίστοιχο κεντρικό μενού.
 */
public class LoginPage {
    private JFrame frame;
    private JButton loginButton, registerButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel, passwordLabel;
    private SupermarketAPI controller;

    /**
     * Δημιουργεί και αρχικοποιεί το παράθυρο σύνδεσης.
     *
     * @param controller Ο controller του συστήματος (SupermarketAPI) για την αυθεντικοποίηση των χρηστών.
     */
    public LoginPage(SupermarketAPI controller) {
        this.controller = controller;
        frame = new JFrame("Σύνδεση");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setMinimumSize(new Dimension(320, 220));
        frame.setSize(360, 240);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        usernameField = new JTextField();
        usernameLabel = new JLabel("Username:");
        passwordField = new JPasswordField();
        passwordLabel = new JLabel("Κωδικός:");

        loginButton = new JButton("Σύνδεση");
        registerButton = new JButton("Εγγραφή");
        loginButton.setFocusable(false);
        registerButton.setFocusable(false);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        frame.add(usernameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        frame.add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        frame.add(buttonPanel, gbc);

        loginButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText();
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Παρακαλώ συμπληρώστε όλα τα πεδία.",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User user = controller.login(username, password);

                if (user != null) {
                    // Έλεγχος ρόλου χρήστη
                    if (controller.isAdmin(user.getUsername())) {
                        new AdminMain(controller, user);
                    } else {
                        new CustomerMain(controller, user);
                    }
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Η σύνδεση απέτυχε. Ελέγξτε τα στοιχεία σας.",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                new RegisterWindow(frame, controller);
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

        frame.setVisible(true);
    }
}
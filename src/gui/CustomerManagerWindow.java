package gui;
import api.CustomerManager;
import api.Order;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 * Η κλάση CustomerManagerWindow δημιουργεί ένα παράθυρο για την προβολή του ιστορικού παραγγελιών ενός πελάτη.
 * Παρέχει τις εξής λειτουργίες: Εισαγωγή του ονόματος χρήστη ενός πελάτη,
 * προβολή του ιστορικού παραγγελιών του πελάτη,
 * ενημέρωση της διεπαφής με τις παραγγελίες ή εμφάνιση μηνύματος αν δεν υπάρχουν παραγγελίες.
 * Το ιστορικό παραγγελιών εμφανίζεται σε ένα JTextArea, το οποίο ενημερώνεται δυναμικά.
 */

public class CustomerManagerWindow extends JFrame{
    private  CustomerManager customerManager;

    /**
     * Δημιουργεί ένα νέο παράθυρο για την προβολή του ιστορικού παραγγελιών πελατών.
     *
     * @param customerManager Το αντικείμενο που διαχειρίζεται τα δεδομένα πελατών και παραγγελιών.
     */
    public CustomerManagerWindow(CustomerManager customerManager){
        this.customerManager=customerManager;

        setTitle("Ιστορικό παραγγελιών πελάτη");
        setSize(270,230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel= new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel=new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel usernameLabel= new JLabel("Όνομα χρήστη");
        JTextField usernameField= new JTextField(15);
        JButton viewHistoryButton= new JButton(" Δες παραγγελίες");

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(viewHistoryButton);

        panel.add(inputPanel,BorderLayout.NORTH);

        JTextArea orderHistoryArea=new JTextArea();
        orderHistoryArea.setEditable(false);
        JScrollPane scrollPane= new JScrollPane(orderHistoryArea);

        panel.add(scrollPane,BorderLayout.CENTER);

        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                if (username.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Παρακαλώ εισάγεται ένα username.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<Order>orders =customerManager.getOrderHistory(username);
                if (orders.isEmpty()){
                    orderHistoryArea.setText("Ο πελάτης"+ username + "δεν έχει παραγγελίες.");
                }else{
                    StringBuilder historyText= new StringBuilder("Order History for"+ username+":\n");
                    for (Order order : orders){
                        historyText.append(order.toString()).append("\n");
                    }
                    orderHistoryArea.setText(historyText.toString());
                }

            }
        });
        add(panel);
        setVisible(true);

    }

}

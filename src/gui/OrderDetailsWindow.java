package gui;

import api.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Η κλάση OrderDetailsWindow δημιουργεί ένα παράθυρο διαλόγου για την εμφάνιση 
 * των αναλυτικών λεπτομερειών μιας συγκεκριμένης παραγγελίας.
 * 
 * Παρέχει τις εξής λειτουργίες: Εμφάνιση του τίτλου με τον αριθμό της παραγγελίας, 
 * περιοχή κειμένου (JTextArea) με τα προϊόντα, τις ποσότητες και το συνολικό κόστος όπως 
 * αυτά προκύπτουν από την κλάση Order, καθώς και κουμπί κλεισίματος του παραθύρου.
 */
public class OrderDetailsWindow {
    private JFrame frame;
    private Order order;

    /**
     * Δημιουργεί και αρχικοποιεί ένα νέο παράθυρο εμφάνισης λεπτομερειών παραγγελίας.
     *
     * @param order Η παραγγελία (Order) της οποίας οι λεπτομέρειες θα εμφανιστούν.
     */
    public OrderDetailsWindow(Order order){
        this.order = order;

        frame = new JFrame("Λεπτομέρειες Παραγγελίας");
        frame.setSize(270,230);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel titleLabel= new JLabel("Παραγγελία #" + order.getOrderId(),SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel,BorderLayout.NORTH);
        
        JTextArea orderDetailsArea = new JTextArea();
        orderDetailsArea.setText(order.toString());
        orderDetailsArea.setEditable(false);
        orderDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(orderDetailsArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);


        JButton closeButton = new JButton("Κλείσιμο");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        frame.add(mainPanel);
    }

    /**
     * Κάνει ορατό το παράθυρο λεπτομερειών παραγγελίας στην οθόνη.
     */
    public void show() {
         frame.setVisible(true);
    }
}
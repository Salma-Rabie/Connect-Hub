/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.Notification;
import Backend.NotificationManager;
import Backend.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author sarar
 */
public class notifications extends javax.swing.JFrame {

    /**
     * Creates new form notifications
     */
      private JFrame previousWindow ;
       private JList<String> notificationList;
       private NotificationManager manager;
     private JPanel notificationsPanel;
       private User user;
    public notifications(JFrame previousWindow, User user) {
        
        manager = NotificationManager.getInstance();
        List<Notification> notifications = manager.getUnreadNotifications(user.getUserId());

        // Set up the JFrame
        setTitle("Notification System");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Create a panel for each notification
        for (Notification notification : notifications) {
            JPanel notificationPanel = createNotificationPanel(notification);
            add(notificationPanel);
        }

        setLocationRelativeTo(null);  // Center the window
        setVisible(true);
//        initComponents();
//        setTitle("Notifications");
//        this.previousWindow = previousWindow;
//        this.user = user;
//       setTitle("Notifications");
//       notificationsPanel = new JPanel();
//        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS));
//        add(new JScrollPane(notificationsPanel), BorderLayout.EAST);
//
//        
//notificationsPanel.setPreferredSize(new Dimension(300, getHeight())); // 300px wide
//notificationsPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE)); // Debugging border
//
//        setVisible(true);
//        setSize(400, 300);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new BorderLayout());
//         manager = NotificationManager.getInstance();
//
//        // Create and add a label
//        JLabel header = new JLabel("Notifications", JLabel.CENTER);
//        header.setFont(new Font("Arial", Font.BOLD, 18));
//        add(header, BorderLayout.NORTH);
//
//        // Create the JList for notifications
//        notificationList = new JList<>();
//        JScrollPane scrollPane = new JScrollPane(notificationList);
//        add(scrollPane, BorderLayout.WEST);
//
//        // Fetch and populate notifications
//        populateNotifications();
//
//        setVisible(true);
//
//        setLocationRelativeTo(null);
        
    }
    

    private JPanel createNotificationPanel(Notification notification) {
        JPanel panel = new JPanel();
      //  panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
          add(panel,BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        // Display notification content and type
        JLabel typeLabel = new JLabel(notification.getType());
        JLabel contentLabel = new JLabel( notification.getContent());

        panel.add(typeLabel);
        panel.add(contentLabel);

        // Add action buttons based on the notification type
        if ("FriendRequest".equals(notification.getType())) {
            JButton acceptButton = new JButton("Accept");
            JButton declineButton = new JButton("Decline");

            // Accept button action
            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    acceptFriendRequest(notification);
                }
            });

            // Decline button action
            declineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    declineFriendRequest(notification);
                }
            });

            panel.add(acceptButton);
            panel.add(declineButton);
        } else if ("GroupActivity".equals(notification.getType())) {
            JButton viewGroupButton = new JButton("View Group");

            // View Group button action
            viewGroupButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewGroup(notification);
                }
            });

            panel.add(viewGroupButton);
        }

        return panel;}
    
    private void acceptFriendRequest(Notification notification) {
        manager.markAsRead(notification.getId());
        showDialog("Friend Request Accepted", "You have accepted the friend request.");
    }

    // Action for declining a friend request
    private void declineFriendRequest(Notification notification) {
        manager.markAsRead(notification.getId());
        showDialog("Friend Request Declined", "You have declined the friend request.");
    }

    // Action for viewing a group
    private void viewGroup(Notification notification) {
        showDialog("Viewing Group", "Opening the group for: " + notification.getContent());
    }

    // Helper method to show a dialog for actions
    private void showDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    
    private void populateNotifications() {
    

  if (user.getUserId() == null || user.getUserId().isEmpty()) {
        System.err.println("User ID is null or empty.");
        return;
    }

    // Fetch unread notifications
    List<Notification> notifications;
    try {
        notifications = NotificationManager.getInstance().getUnreadNotifications(user.getUserId());
    } catch (Exception e) {
        System.err.println("Error fetching notifications: " + e.getMessage());
        return;
    }

    // Map notifications to a String array for display
    String[] notificationContents = notifications.stream()
            .map(Notification::getContent) // Extract content of notifications
            .toArray(String[]::new);

    // Update JList
    notificationList.setListData(notificationContents);
    
}
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 381, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(notifications.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(notifications.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(notifications.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(notifications.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new notifications().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.*;
import Backend.Notification;
import Backend.NotificationManager;
import Backend.User;
import java.awt.BorderLayout;
import java.awt.Color;
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

/**
 *
 * @author sarar
 */
public class notifications extends javax.swing.JFrame {

    /**
     * Creates new form notifications
     */
   
    private JList<String> notificationList;
    private NotificationManager manager;
    private JPanel notificationsPanel;
    private User user;
    private FriendDataBase fdb;
    private GroupDataBase groupDatabase;
    private GroupManagement groupManager;
    private JFrame previous;

    public notifications(User user,GroupManagement groupManager ,JFrame previous) {
        this.previous=previous;
        manager = NotificationManager.getInstance();
        this.groupManager=groupManager;
        List<Notification> notifications = manager.getUnreadNotifications(user.getUserId());
        String filePath="friends.json";
        fdb=FriendDataBase.getInstance(filePath);
        groupDatabase=GroupDataBase.getInstance("groups,json");
        // Set up the JFrame
        setTitle("Notification System");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.user = user;
        
        
          JButton backButton = new JButton("Back");
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            notifications.this.setVisible(false);
            previous.setVisible(true);
        }
    });
 add(backButton, BorderLayout.SOUTH);
    // Add the Back button at the bottom of the layout
    

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
        add(panel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        // Display notification content and type
        JLabel typeLabel = new JLabel(notification.getType());
        JLabel contentLabel = new JLabel(notification.getContent());

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
                    acceptFriendRequest(notification,panel);
                }
            });

            // Decline button action
            declineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    declineFriendRequest(notification,panel);
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
                    viewGroup(notification,panel);
                }
            });

            panel.add(viewGroupButton);
        }

        return panel;
    }

    private void acceptFriendRequest(Notification notification, JPanel panel) {
        manager.markAsRead(notification.getId());
        showDialog("Friend Request Accepted", "You have accepted the friend request.");
        String friendId = extractFriendIdFromContent(notification.getContent());
        System.out.println(user.getUserId());
        fdb.addFriend(user.getUserId(), friendId);
        System.out.println(user.getUserId());
        
        remove(panel);
        revalidate();  // Recalculate layout
        repaint();

    }

    // Action for declining a friend request
    private void declineFriendRequest(Notification notification, JPanel panel) {
        manager.markAsRead(notification.getId());
        showDialog("Friend Request Declined", "You have declined the friend request.");

        remove(panel);
        revalidate();  // Recalculate layout
        repaint();

    }
    private String extractFriendIdFromContent(String content) {
    // Assuming the content follows the format: "User456 sent you a friend request."
    String[] parts = content.split(" ");
    if (parts.length >= 2) {
        return parts[0];  // Extracts "User456"
    }
    return null;  // Return null if the format is not as expected
}

    // Action for viewing a group
    private void viewGroup(Notification notification, JPanel panel) {
        String groupName = extractGroupNameFromContent(notification.getContent());
        System.out.println(groupName);
        if (groupName != null) {
            showDialog("Viewing Group", "Opening the group for: " + groupName);

        }
       JPanel parent = (JPanel) panel.getParent(); // Get the parent container
    parent.remove(panel); // Remove the panel from the parent

    // Refresh the parent container to reflect the changes
    parent.revalidate();
    parent.repaint();

    this.setVisible(false); // Hide the notification window
        //this.setVisible(false);
        if (groupName != null && !groupName.equals("Select Friend Request")) {
            Group group = groupDatabase.getGroupByName(groupName);
            if (group.getOtherAdmins().contains(user.getUserId())) {

                this.setVisible(false);
                OtherAdminsGroupWindow groupWindow = new OtherAdminsGroupWindow(previous, user, group, groupDatabase, groupManager);
                groupWindow.setVisible(true);
            } else if (!group.getPrimaryAdmin().equals(user.getUserId()) && !group.getOtherAdmins().contains(user.getUserId())) {
                this.setVisible(false);
                UserGroupWindow groupWindow = new UserGroupWindow(previous, user, group, groupDatabase, groupManager);
                groupWindow.setVisible(true);
            } else if (group.getPrimaryAdmin().equals(user.getUserId())) {
                this.setVisible(false);
                PrimaryAdminGroupWindow groupWindow = new PrimaryAdminGroupWindow(previous, user, group, groupDatabase, groupManager);
                groupWindow.setVisible(true);
            }
          
        }
      
    }

    private String extractGroupNameFromContent(String content) {
    // Use a regex to match text inside single quotes
    String groupNamePattern = "'(.*?)'";
    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(groupNamePattern);
    java.util.regex.Matcher matcher = pattern.matcher(content);
    if (matcher.find()) {
        return matcher.group(1); // Extract the group name
    }
    return null; // Return null if no match is found
}

    
    // Helper method to show a dialog for actions
    private void showDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

//    private void populateNotifications() {
//
//        if (user.getUserId() == null || user.getUserId().isEmpty()) {
//            System.err.println("User ID is null or empty.");
//            return;
//        }
//
//        // Fetch unread notifications
//        List<Notification> notifications;
//        try {
//            notifications = NotificationManager.getInstance().getUnreadNotifications(user.getUserId());
//        } catch (Exception e) {
//            System.err.println("Error fetching notifications: " + e.getMessage());
//            return;
//        }
//
//        // Map notifications to a String array for display
//        String[] notificationContents = notifications.stream()
//                .map(Notification::getContent) // Extract content of notifications
//                .toArray(String[]::new);
//
//        // Update JList
//        notificationList.setListData(notificationContents);
//
//    }

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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

/**
 *
 * @author Salma Eid
 */
public class PrimaryAdminGroupWindow extends javax.swing.JFrame {

    /**
     * Creates new form PrimaryAdminGroupWindow
     */
    private GroupDataBase groupDatabase;
    private User admin;
    private Group group;
    private JFrame previousWindow;
    private GroupManagement groupManager;
    private content c;
    private factory f = new factory();
    private UserDataBase userDatabase = UserDataBase.getInstance("users.json");
    Map<String,String> mapUsers=new HashMap<>();
Map<String,String> mapAdmins=new HashMap<>();
    public PrimaryAdminGroupWindow(JFrame previousWindow, User user, Group group, GroupDataBase groupDatabase, GroupManagement groupManager) {
        this.groupDatabase = groupDatabase;
        this.admin = user;
        this.group = group;
        this.previousWindow = previousWindow;
        this.groupManager = groupManager;
        initComponents();
        setLocationRelativeTo(null);
        setTitle(group.getName());
        ImageIcon icon = new ImageIcon(group.getPhotoPath());
        Image originalImage = icon.getImage();//profile
        Image scaledImage = originalImage.getScaledInstance(photo.getWidth(), photo.getHeight(), Image.SCALE_SMOOTH);
        photo.setIcon(new ImageIcon(scaledImage));
        showPosts();
         users.removeAllItems();
        users.addItem("Normal Users");
        users.setSelectedIndex(0);
        otherAdmins.removeAllItems();
        otherAdmins.addItem("Other Admins");
        otherAdmins.setSelectedIndex(0);
        allusers.removeAllItems();
        allusers.addItem("All Users");
        allusers.setSelectedIndex(0);
        ArrayList<String>usersGroup=group.getUsers();
        for (int i = 0; i <usersGroup.size() ; i++) {
            User user1=userDatabase.getUserById(usersGroup.get(i));
            mapUsers.put(user1.getUsername(), user1.getUserId());
            if(!user1.getUserId().equals(user.getUserId()))
            allusers.addItem(user1.getUsername());
        }
        for (int i = 0; i <usersGroup.size() ; i++) {
            User user1=userDatabase.getUserById(usersGroup.get(i));
            mapUsers.put(user1.getUsername(), user1.getUserId());
            if(!user1.getUserId().equals(user.getUserId()))
            users.addItem(user1.getUsername());
        }
        ArrayList<String>groupAdmins=group.getOtherAdmins();
        for (int i = 0; i <groupAdmins.size() ; i++) {
            User user1=userDatabase.getUserById(groupAdmins.get(i));
            mapAdmins.put(user1.getUsername(), user1.getUserId());
            if(!user1.getUserId().equals(user.getUserId()))
            otherAdmins.addItem(user1.getUsername());
            users.removeItem(user1.getUsername());
        }
    }


    private void showPosts() {
        // Clear existing components
        jPanel1.removeAll();
        jPanel1.setLayout(new BorderLayout());

        // Create a panel to hold all posts
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // Stack posts vertically

        List<posts> postsList = group.getPosts();
        Collections.shuffle(postsList); // Optional: Randomize the order of posts

        for (posts post : postsList) {
            // Create a panel for each individual post
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BorderLayout());
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            // Add admin name at the top of the post
            User postUser = userDatabase.getUserById(post.getUserId());
            JLabel userLabel = new JLabel("Posted by: " + postUser.getUsername());
            userLabel.setFont(new Font("Arial", Font.BOLD, 14));
            userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
            postPanel.add(userLabel, BorderLayout.NORTH);

            // Add image if available
            String imgPath = post.getImg();
            if (imgPath != null && !imgPath.isEmpty()) {
                try {
                    ImageIcon imageIcon = new ImageIcon(imgPath);
                    Image scaledImage = imageIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                    postPanel.add(imageLabel, BorderLayout.CENTER);
                } catch (Exception e) {
                    System.err.println("Error loading image: " + imgPath);
                    e.printStackTrace();
                }
            }

            // Add post text
            JTextArea textArea = new JTextArea(post.getText());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 12));
            textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            textArea.setBackground(Color.WHITE);

            JScrollPane textScrollPane = new JScrollPane(textArea);
            textScrollPane.setBorder(BorderFactory.createEmptyBorder());
            textScrollPane.setPreferredSize(new Dimension(400, 100));
            postPanel.add(textScrollPane, BorderLayout.SOUTH);

            // Add individual post panel to the main posts panel
            postsPanel.add(postPanel);
        }

        // Create a scroll pane for the posts panel
        JScrollPane scrollPane = new JScrollPane(postsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the scroll pane to jPanel1
        jPanel1.add(scrollPane, BorderLayout.CENTER);

        // Ensure the panel is updated
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        photo = new javax.swing.JLabel();
        back = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        addpost = new javax.swing.JButton();
        deleteGroup = new javax.swing.JButton();
        users = new javax.swing.JComboBox<>();
        promote = new javax.swing.JButton();
        demote = new javax.swing.JButton();
        otherAdmins = new javax.swing.JComboBox<>();
        changePhoto = new javax.swing.JButton();
        allusers = new javax.swing.JComboBox<>();
        removeUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 434, Short.MAX_VALUE)
        );

        photo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        back.setBackground(new java.awt.Color(0, 0, 0));
        back.setForeground(new java.awt.Color(255, 255, 255));
        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        jLabel1.setText("Group Photo");

        addpost.setBackground(new java.awt.Color(0, 0, 0));
        addpost.setForeground(new java.awt.Color(255, 255, 255));
        addpost.setText("Add Post");
        addpost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addpostActionPerformed(evt);
            }
        });

        deleteGroup.setBackground(new java.awt.Color(0, 0, 0));
        deleteGroup.setForeground(new java.awt.Color(255, 255, 255));
        deleteGroup.setText("Delete Group");
        deleteGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteGroupActionPerformed(evt);
            }
        });

        users.setBackground(new java.awt.Color(0, 0, 0));
        users.setForeground(new java.awt.Color(255, 255, 255));
        users.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersActionPerformed(evt);
            }
        });

        promote.setBackground(new java.awt.Color(0, 0, 0));
        promote.setForeground(new java.awt.Color(255, 255, 255));
        promote.setText("Promote To Admin");
        promote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                promoteActionPerformed(evt);
            }
        });

        demote.setBackground(new java.awt.Color(0, 0, 0));
        demote.setForeground(new java.awt.Color(255, 255, 255));
        demote.setText("Demote From Admin");
        demote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                demoteActionPerformed(evt);
            }
        });

        otherAdmins.setBackground(new java.awt.Color(0, 0, 0));
        otherAdmins.setForeground(new java.awt.Color(255, 255, 255));
        otherAdmins.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        otherAdmins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherAdminsActionPerformed(evt);
            }
        });

        changePhoto.setBackground(new java.awt.Color(0, 0, 0));
        changePhoto.setForeground(new java.awt.Color(255, 255, 255));
        changePhoto.setText("Change Profile Photo");
        changePhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePhotoActionPerformed(evt);
            }
        });

        allusers.setBackground(new java.awt.Color(0, 0, 0));
        allusers.setForeground(new java.awt.Color(255, 255, 255));
        allusers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        allusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allusersActionPerformed(evt);
            }
        });

        removeUser.setBackground(new java.awt.Color(0, 0, 0));
        removeUser.setForeground(new java.awt.Color(255, 255, 255));
        removeUser.setText("Remove User");
        removeUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(changePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(back, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addpost, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(deleteGroup, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(allusers, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(users, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(otherAdmins, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(promote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(demote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removeUser, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changePhoto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addpost)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteGroup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(users, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(promote))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(demote)
                    .addComponent(otherAdmins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allusers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(back)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ((NewsFeed) previousWindow).updateUser(admin);
        previousWindow.setVisible(true);
    }//GEN-LAST:event_backActionPerformed

    private void addpostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addpostActionPerformed
        // TODO add your handling code here:
        String input = JOptionPane.showInputDialog("Enter your text for the new post:");
        if (input == null || input.isEmpty()) {
            return;
        }

        JFileChooser choose = new JFileChooser();
        int result = choose.showSaveDialog(this); // Get the admin action
        if (result != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this, "No file selected!");
            LocalDateTime t = LocalDateTime.now();
            c = f.factory("post", t, input, "", admin.getUserId(), admin.getUsername());
            admin.addPost((posts) c);
            group.addPost((posts) c);
            groupManager.addPost(group, admin.getUserId(), (posts) c);
            showPosts();
            return; // Exit if no file is selected
        }

        File profile = choose.getSelectedFile();
        LocalDateTime t = LocalDateTime.now();
        c = f.factory("post", t, input, profile.toPath().toString(), admin.getUserId(), admin.getUsername());
        admin.addPost((posts) c);
        group.addPost((posts) c);
        groupManager.addPost(group, admin.getUserId(), (posts) c);
        showPosts();
    }//GEN-LAST:event_addpostActionPerformed

    private void deleteGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteGroupActionPerformed
        // TODO add your handling code here:
        User user1 = groupManager.deleteGroup(group, admin.getUserId());
        if (user1 != null) {
            admin = user1;
            this.setVisible(false);
            ((NewsFeed) previousWindow).updateUser(admin);
            previousWindow.setVisible(true);

        }

    }//GEN-LAST:event_deleteGroupActionPerformed

    private void promoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_promoteActionPerformed
        // TODO add your handling code here:

                                       
    String userName = (String) users.getSelectedItem();

    if(userName != null && !userName.equals("Users")){
        String userId = mapUsers.get(userName);
        //System.out.println("Found UserId: " + userId);
        
        if (userId != null) {
            User user1 = userDatabase.getUserById(userId);
            Group updatedGroup = groupManager.promoteToAdmin(group, admin.getUserId(), user1.getUserId());
            
            if(updatedGroup != null) {
                group = updatedGroup;
                users.removeItem(userName);
                JOptionPane.showMessageDialog(this, userName + " has been promoted to admin.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to promote " + userName + " to admin.", "Promotion Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "User not found in map: " + userName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    }//GEN-LAST:event_promoteActionPerformed

    private void usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usersActionPerformed

    private void demoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_demoteActionPerformed
        // TODO add your handling code here:
        String userName = (String) otherAdmins.getSelectedItem();

    if(userName != null && !userName.equals("Users")){
        String userId = mapAdmins.get(userName);
        //System.out.println("Found UserId: " + userId);
        
        if (userId != null) {
            User user1 = userDatabase.getUserById(userId);
            Group updatedGroup = groupManager.demoteFromAdmin(group, admin.getUserId(), user1.getUserId());
            
            if(updatedGroup != null) {
                group = updatedGroup;
                otherAdmins.removeItem(userName);
                JOptionPane.showMessageDialog(this, userName + " has been promoted to admin.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to promote " + userName + " to admin.", "Promotion Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "User not found in map: " + userName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_demoteActionPerformed

    private void otherAdminsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherAdminsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otherAdminsActionPerformed

    private void changePhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePhotoActionPerformed
        // TODO add your handling code here:
         JFileChooser choose = new JFileChooser();
        choose.showSaveDialog(this);
        File profile = choose.getSelectedFile();
        if (profile == null) {
            JOptionPane.showMessageDialog(this, "No file selected!");
            return;
        }
        group = groupManager.changeGroupPhoto(group, admin.getUserId(),profile.getPath());
        ImageIcon icon = new ImageIcon(profile.getPath()); // Replace with your image path
        Image originalImage = icon.getImage();
        Image scaledImage = originalImage.getScaledInstance(photo.getWidth(), photo.getHeight(), Image.SCALE_SMOOTH);
        photo.setIcon(new ImageIcon(scaledImage));
       // group =groupManager.changeGroupPhoto(group, admin.getUserId(), group)
    }//GEN-LAST:event_changePhotoActionPerformed

    private void allusersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allusersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_allusersActionPerformed

    private void removeUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeUserActionPerformed
        // TODO add your handling code here:
        String userName = (String) allusers.getSelectedItem();

    if(userName != null && !userName.equals("All Users")){
        String userId = mapUsers.get(userName);
        //System.out.println("Found UserId: " + userId);
        
        if (userId != null) {
            User user1 = userDatabase.getUserById(userId);
            Group updatedGroup = groupManager.removeMember(group, admin.getUserId(), user1.getUserId());
            
            if(updatedGroup != null) {
                group = updatedGroup;
                allusers.removeItem(userName);
                JOptionPane.showMessageDialog(this, userName + " has been promoted to admin.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to promote " + userName + " to admin.", "Promotion Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "User not found in map: " + userName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_removeUserActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PrimaryAdminGroupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(PrimaryAdminGroupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(PrimaryAdminGroupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(PrimaryAdminGroupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new PrimaryAdminGroupWindow().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addpost;
    private javax.swing.JComboBox<String> allusers;
    private javax.swing.JButton back;
    private javax.swing.JButton changePhoto;
    private javax.swing.JButton deleteGroup;
    private javax.swing.JButton demote;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> otherAdmins;
    private javax.swing.JLabel photo;
    private javax.swing.JButton promote;
    private javax.swing.JButton removeUser;
    private javax.swing.JComboBox<String> users;
    // End of variables declaration//GEN-END:variables
}

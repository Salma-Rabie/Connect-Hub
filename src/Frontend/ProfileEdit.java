/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.*;
import static Backend.PasswordHashing.hashPassword;
import java.awt.Image;
import java.io.File;
import javax.swing.*;

/**
 *
 * @author sarar
 */
public class ProfileEdit extends javax.swing.JFrame {

    private User user;

    private ProfileManagement profileManager;
    private JFrame previousWindow;

    public ProfileEdit(JFrame previousWindow, User user, ProfileManagement profileManager) {
        this.previousWindow = previousWindow;
        this.user = user;
        this.profileManager = profileManager;
        initComponents();
        setLocationRelativeTo(null);
        setTitle(user.getUsername() + "'s Profile");
        Bio.setText(user.getBio());

        ImageIcon icon = new ImageIcon(user.getProfilePhotoPath());
        ImageIcon icon2 = new ImageIcon(user.getCoverPhotoPath());
        Image originalImage = icon.getImage();//profile
        Image scaledImage = originalImage.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(), Image.SCALE_SMOOTH);
        jLabel2.setIcon(new ImageIcon(scaledImage));

        Image originalImage2 = icon2.getImage();//cover
        Image scaledImage2 = originalImage2.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
        jLabel1.setIcon(new ImageIcon(scaledImage2));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        changebio = new javax.swing.JButton();
        changeprofile = new javax.swing.JButton();
        changecover = new javax.swing.JButton();
        password = new javax.swing.JTextField();
        pass = new javax.swing.JButton();
        Bio = new javax.swing.JTextField();
        back = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Cover Photo");

        jLabel5.setText("Profile Photo ");

        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("Bio");

        changebio.setBackground(new java.awt.Color(0, 0, 0));
        changebio.setForeground(new java.awt.Color(255, 255, 255));
        changebio.setText("Change Bio");
        changebio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changebioActionPerformed(evt);
            }
        });

        changeprofile.setBackground(new java.awt.Color(0, 0, 0));
        changeprofile.setForeground(new java.awt.Color(255, 255, 255));
        changeprofile.setText("Change Profile Photo");
        changeprofile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeprofileActionPerformed(evt);
            }
        });

        changecover.setBackground(new java.awt.Color(0, 0, 0));
        changecover.setForeground(new java.awt.Color(255, 255, 255));
        changecover.setText("Change Covor Photo");
        changecover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changecoverActionPerformed(evt);
            }
        });

        pass.setBackground(new java.awt.Color(0, 0, 0));
        pass.setForeground(new java.awt.Color(255, 255, 255));
        pass.setText("Change Password");
        pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passActionPerformed(evt);
            }
        });

        back.setBackground(new java.awt.Color(0, 0, 0));
        back.setForeground(new java.awt.Color(255, 255, 255));
        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(changeprofile))
                            .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(pass)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Bio, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(changebio, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(changecover))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Bio, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(changebio, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(changecover))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(changeprofile))))
                .addGap(49, 49, 49)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changebioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changebioActionPerformed
        // TODO add your handling code here:

        String text = Bio.getText().trim();

        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your new bio!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (text.equals(user.getBio())) {
            JOptionPane.showMessageDialog(this, "Please enter a new bio!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        user = profileManager.changeBio(user.getUserId(), text);
        Bio.setText(text);
    }//GEN-LAST:event_changebioActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ((ProfileWindow) previousWindow).updateuser(user);
        previousWindow.setVisible(true);

    }//GEN-LAST:event_backActionPerformed

    private void passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passActionPerformed
        // TODO add your handling code here:
        String text = password.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your new password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (hashPassword(text).equals(user.getPasswordHash())) {
            JOptionPane.showMessageDialog(this, "Enter new password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String input = JOptionPane.showInputDialog("Enter your old password:");
        if (input == null) {
            JOptionPane.showMessageDialog(this, "Please enter your old password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!hashPassword(input).equals(user.getPasswordHash())) {
            JOptionPane.showMessageDialog(this, "Wrong password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = profileManager.changePassword(user.getUserId(), text);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Password Updated!");
        } else {
            JOptionPane.showMessageDialog(this, "Error updating password!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_passActionPerformed

    private void changecoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changecoverActionPerformed
        // TODO add your handling code here:
        JFileChooser choose = new JFileChooser();
        choose.showSaveDialog(this);
        File cover = choose.getSelectedFile();
        if (cover == null) {
            JOptionPane.showMessageDialog(this, "No file selected!");
            return;
        }
        user = profileManager.changeCoverPhoto(user.getUserId(), cover);
        ImageIcon icon = new ImageIcon(cover.getPath()); // Replace with your image path
        Image originalImage = icon.getImage();
        Image scaledImage = originalImage.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
        jLabel1.setIcon(new ImageIcon(scaledImage));
    }//GEN-LAST:event_changecoverActionPerformed

    private void changeprofileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeprofileActionPerformed
        // TODO add your handling code here:
        JFileChooser choose = new JFileChooser();
        choose.showSaveDialog(this);
        File profile = choose.getSelectedFile();
        if (profile == null) {
            JOptionPane.showMessageDialog(this, "No file selected!");
            return;
        }
        user = profileManager.changeProfilePhoto(user.getUserId(), profile);
        ImageIcon icon = new ImageIcon(profile.getPath()); // Replace with your image path
        Image originalImage = icon.getImage();
        Image scaledImage = originalImage.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(), Image.SCALE_SMOOTH);
        jLabel2.setIcon(new ImageIcon(scaledImage));
    }//GEN-LAST:event_changeprofileActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Bio;
    private javax.swing.JButton back;
    private javax.swing.JButton changebio;
    private javax.swing.JButton changecover;
    private javax.swing.JButton changeprofile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton pass;
    private javax.swing.JTextField password;
    // End of variables declaration//GEN-END:variables
}

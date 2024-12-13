/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 *
 * @author alaae
 */
public class ViewProfile extends javax.swing.JFrame {

    /**
     * Creates new form ViewProfile
     */
    private User user;
    private JFrame previousWindow;
    private ProfileWindow profilee;
    private UserManager userManager;
    
    public ViewProfile(User user, JFrame previousWindow, UserManager userManager) {
        this.previousWindow = previousWindow;
        this.user = user;
        this.userManager = userManager;
        initComponents();
        setTitle(user.getUsername() + "'s Profile");
        setLocationRelativeTo(null);
        jLabel5.setText(user.getBio());
        ImageIcon icon = new ImageIcon(user.getProfilePhotoPath());
        ImageIcon icon2 = new ImageIcon(user.getCoverPhotoPath());
        Image originalImage = icon.getImage();//profile
        Image scaledImage = originalImage.getScaledInstance(jLabel6.getWidth(), jLabel6.getHeight(), Image.SCALE_SMOOTH);
        jLabel6.setIcon(new ImageIcon(scaledImage));

        Image originalImage2 = icon2.getImage();//cover
        Image scaledImage2 = originalImage2.getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_SMOOTH);
        jLabel7.setIcon(new ImageIcon(scaledImage2));
        jLabel5.setText(user.getStatus());
        addStoriesAndPostsLayout(user);

    }

    private void addStoriesAndPostsLayout(User user) {
// Panel 1 (Stories)
        user.removeExpiredStories();
        JPanel storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS)); // Vertical layout
        storiesPanel.setBackground(Color.WHITE);

// Header Label for "Stories"
        JLabel storiesLabel = new JLabel("Stories");
        storiesLabel.setFont(new Font("Arial", Font.BOLD, 20));
        storiesLabel.setForeground(new Color(0, 102, 204));  // Modern blue color
        storiesLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around header
        storiesLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        storiesPanel.add(storiesLabel);

         List<stories> s =userManager.getDatabase().getUserById(user.getUserId()).getStories();
        List<posts> p =userManager.getDatabase().getUserById(user.getUserId()).getPosts();
// Iterate over the stories
        for (int i = 0; i < s.size(); i++) {
            stories story = s.get(i);

            // Create a card-like panel for each story
            JPanel storyPanel = new JPanel();
            storyPanel.setLayout(new BoxLayout(storyPanel, BoxLayout.Y_AXIS)); // Vertical layout
            storyPanel.setBackground(new Color(245, 245, 245)); // Light gray background
            storyPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding inside the card
            ));

            // Add the image (if available)
            if (story.getImg() != null) {
                ImageIcon icon = new ImageIcon(story.getImg());
                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Resize image
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // Center the image
                storyPanel.add(imageLabel);
            }

            // Add the text of the story
            JLabel storyLabel = new JLabel("<html><div style='text-align: center;'>" + story.getText() + "</div></html>");
            storyLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Use standard font
            storyLabel.setForeground(Color.DARK_GRAY); // Dark gray text
            storyLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // Center the text
            storyPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between image and text
            storyPanel.add(storyLabel);

            // Add spacing around each story
            storyPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the panel itself
            storiesPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between stories
            storiesPanel.add(storyPanel);
        }

// Scroll Pane for Stories
        JScrollPane storiesScrollPane = new JScrollPane(storiesPanel);
        storiesScrollPane.setPreferredSize(new Dimension(500, 300)); // Adjust dimensions as needed
        storiesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scroll

// Posts Panel (Scrollable)
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // Vertical layout
        postsPanel.setBackground(Color.WHITE);

// Header Label for "Posts"
        JLabel postsLabel = new JLabel("Posts");
        postsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        postsLabel.setForeground(new Color(0, 102, 204));  // Modern blue color
        postsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around header
        postsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        postsPanel.add(postsLabel);

// Iterate over the posts
        for (int i = 0; i < p.size(); i++) {
            posts post = p.get(i);

            // Create a card-like panel for each post
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
            postPanel.setBackground(new Color(245, 245, 245)); // Light gray background
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding inside the card
            ));

            // Add the image (if available)
            if (post.getImg() != null) {
                ImageIcon icon = new ImageIcon(post.getImg());
                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Resize the image for better appearance
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // Center the image horizontally
                postPanel.add(imageLabel); // Add the image to the post panel
            }

            // Add the text of the post
            JLabel postLabel = new JLabel("<html><div style='text-align: center;'>" + post.getText() + "</div></html>");
            postLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set the font for the text
            postLabel.setForeground(Color.DARK_GRAY); // Set a dark gray color for the text
            postLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // Center-align the text
            postPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between the image and the text
            postPanel.add(postLabel);

            // Add spacing around the post panel
            postPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            postsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts
            postsPanel.add(postPanel);
        }

// Scroll Pane for Posts (Ensure it is scrollable)
        JScrollPane postsScrollPane = new JScrollPane(postsPanel);
        postsScrollPane.setPreferredSize(new Dimension(300, 50)); // Adjust size to your needs
        postsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);// Disable horizontal scroll
        postsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
// Add the split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, storiesScrollPane, postsScrollPane);
        splitPane.setDividerLocation(100); // Adjust the divider location as needed
        splitPane.setDividerSize(5); // Adjust divider thickness
        splitPane.setResizeWeight(0.5); // Equal weight for both panels
        splitPane.setContinuousLayout(true); // Continuous layout updates

// Set up the main panel and add the split pane
        jPanel4.setLayout(new BorderLayout());
        jPanel4.add(splitPane, BorderLayout.CENTER);
        jPanel4.revalidate();
        jPanel4.repaint();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Cover Photo");

        jLabel2.setText("Profile Photo");

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        jLabel3.setText("Bio");

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );

        jButton1.setBackground(new java.awt.Color(0, 204, 102));
        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("status");

        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(47, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(26, 26, 26))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
       // ((NewsFeed)previousWindow).updateUser(user);
        ((NewsFeed) previousWindow).setVisible(true);
        previousWindow.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ViewProfile().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}

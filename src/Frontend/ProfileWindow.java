/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.*;
import Backend.factory;
import Backend.posts;
import Backend.stories;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.*;


/**
 *
 * @author Salma Eid
 */
public class ProfileWindow extends javax.swing.JFrame {
private User user;
private UserManager userManager;
private ProfileManagement profileManager;
private JFrame previousWindow;

    public ProfileWindow(JFrame previousWindow ,User user, UserManager userManager, ProfileManagement profileManager) {
         this.previousWindow = previousWindow;
        this.user = user;
        this.userManager = userManager;
        this.profileManager = profileManager;
        initComponents();
        setLocationRelativeTo(null);
        setTitle(user.getUsername() + "'s Profile");
        Bio.setText(user.getBio());
        System.out.println("User Bio: " + user.getBio());
   ID.setText(user.getUserId());
   status.setText(user.getStatus());
     ImageIcon icon  = new ImageIcon(user.getProfilePhotoPath());
 ImageIcon icon2 = new ImageIcon(user.getCoverPhotoPath());
        Image originalImage = icon.getImage();//profile
        Image scaledImage = originalImage.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(), Image.SCALE_SMOOTH);
        jLabel2.setIcon(new ImageIcon(scaledImage));

        Image originalImage2 = icon2.getImage();//cover
        Image scaledImage2 = originalImage2.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
        jLabel1.setIcon(new ImageIcon(scaledImage2));
    
    addStoriesAndPostsLayout(user);
    
   
    }
    public void updateuser(User updatedUser) {
        this.user = updatedUser;
        Bio.setText(user.getBio());
     ImageIcon icon  = new ImageIcon(user.getProfilePhotoPath());
 ImageIcon icon2 = new ImageIcon(user.getCoverPhotoPath());
        Image originalImage = icon.getImage();//profile
        Image scaledImage = originalImage.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(), Image.SCALE_SMOOTH);
        jLabel2.setIcon(new ImageIcon(scaledImage));

        Image originalImage2 = icon2.getImage();//cover
        Image scaledImage2 = originalImage2.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
        jLabel1.setIcon(new ImageIcon(scaledImage2));
ID.setText(user.getUserId());
   status.setText(user.getStatus());
    user.removeExpiredStories();
    }
 private void addStoriesAndPostsLayout(User user){
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

// Iterate over the stories
for (int i = 0; i < user.getStories().size(); i++) {
    stories story = user.getStories().get(i);

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
for (int i = 0; i < user.getPosts().size(); i++) {
    posts post = user.getPosts().get(i);

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
postsScrollPane.setPreferredSize(new Dimension(500, 50)); // Adjust size to your needs
postsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);// Disable horizontal scroll
postsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
// Add the split pane
JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, storiesScrollPane, postsScrollPane);
splitPane.setDividerLocation(200); // Adjust the divider location as needed
splitPane.setDividerSize(5); // Adjust divider thickness
splitPane.setResizeWeight(0.5); // Equal weight for both panels
splitPane.setContinuousLayout(true); // Continuous layout updates

// Set up the main panel and add the split pane
jPanel1.setLayout(new BorderLayout());
jPanel1.add(splitPane, BorderLayout.CENTER);
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

        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Bio = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Logout = new javax.swing.JButton();
        EditProfile = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        back = new javax.swing.JButton();
        ID = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Cover Photo");

        jLabel5.setText("profile photo ");

        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Bio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("Bio");

        Logout.setBackground(new java.awt.Color(0, 0, 0));
        Logout.setForeground(new java.awt.Color(255, 255, 255));
        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        EditProfile.setBackground(new java.awt.Color(0, 0, 0));
        EditProfile.setForeground(new java.awt.Color(255, 255, 255));
        EditProfile.setText("Edit Profile");
        EditProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditProfileActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );

        back.setBackground(new java.awt.Color(0, 0, 0));
        back.setForeground(new java.awt.Color(255, 255, 255));
        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        ID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Your ID:");

        jLabel9.setText("Status:");

        status.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 604, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Bio, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 351, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(EditProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114)))
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EditProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Bio, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
        userManager.logout(user.getUserId(), "offline");
        this.setVisible(false);
        previousWindow.setVisible(true);
    }//GEN-LAST:event_LogoutActionPerformed

    private void EditProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditProfileActionPerformed
        // TODO add your handling code here:
         this.setVisible(false);
        ProfileEdit profileEdit = new ProfileEdit(this, user, profileManager);

        profileEdit.setVisible(true);
        
        
    }//GEN-LAST:event_EditProfileActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        previousWindow.setVisible(true);
    }//GEN-LAST:event_backActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
         
java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
 
 
 JFrame previousWindow;
 String rawPassword = "securePassword123";
        String hashedPassword = PasswordHashing.hashPassword(rawPassword);

        // Step 2: Build a User object
        User user = new User.UserBuilder()
                .userId("1000")
                .username("john-doe")
                .email("johndoe@example.com")
                .passwordHash(hashedPassword)
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .status("online")
                .build();
              stories story1 = new stories(LocalDateTime.now(), "Story 1: My first story!", "ss.jpg", "1000");
        stories story2 = new stories(LocalDateTime.now().minusHours(1), "Story 2: Another day, another story", "ss.jpg", "1000");

        // Add stories to the user
        user.addStory(story1);
        user.addStory(story2);

        // Create some posts for the user
        posts post1 = new posts(LocalDateTime.now(), "Post 1: This is my first post!", "ss.jpg", "1000");
        posts post2 = new posts(LocalDateTime.now().minusHours(2), "Post 2: Here is another post", "ss.jpg", "1000");

        // Add posts to the user
        user.addPost(post1);
        user.addPost(post2);

        // Save the user to the database
        UserDataBase userDataBase = UserDataBase.getInstance("output.json");
        userDataBase.saveUser(user);
       
        // Step 3: Specify the JSON file path
        String filePath = "output.json";

        // Step 4: Save the user to the JSON file
       // UserDataBase userDataBase = UserDataBase.getInstance("output.json");
        ProfileManagement profileManager=new ProfileManagement(userDataBase);
        UserManager userManager= new UserManager(userDataBase);
        userDataBase.saveUser(user);
ProfileWindow profile=new ProfileWindow(new MainWindow(userDataBase),user,userManager,profileManager);
                profile.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bio;
    private javax.swing.JButton EditProfile;
    private javax.swing.JLabel ID;
    private javax.swing.JButton Logout;
    private javax.swing.JButton back;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}

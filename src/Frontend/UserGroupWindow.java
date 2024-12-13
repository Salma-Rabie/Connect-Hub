
package Frontend;

import Backend.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class UserGroupWindow extends javax.swing.JFrame {

    private GroupDataBase groupDatabase;
    private User user;
    private Group group;
    private JFrame previousWindow;
    private GroupManagement groupManager;
    private content c;
    private factory f = new factory();
    private UserDataBase userDatabase=UserDataBase.getInstance("users.json");
    
    public UserGroupWindow( JFrame previousWindow, User user, Group group,GroupDataBase groupDatabase,GroupManagement groupManager) {
        this.groupDatabase = groupDatabase;
        this.user = user;
        this.group = group;
        this.previousWindow = previousWindow;
        this.groupManager=groupManager;
        initComponents();
        setLocationRelativeTo(null);
        setTitle(group.getName());
        ImageIcon icon = new ImageIcon(group.getPhotoPath()); 
        Image originalImage = icon.getImage();//profile
        Image scaledImage = originalImage.getScaledInstance(photo.getWidth(), photo.getHeight(), Image.SCALE_SMOOTH);
        photo.setIcon(new ImageIcon(scaledImage));
        showPosts();
    }
//  private void showPosts() {
//    // Clear existing components
//    jPanel1.removeAll();
//    jPanel1.setLayout(new BorderLayout());
//
//    // Create a panel to hold all posts with a fixed size
//    JPanel postsPanel = new JPanel();
//    postsPanel.setPreferredSize(new Dimension(400, 400)); // Match the initial size in initComponents
//    postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
//
//    List<posts> postsList = group.getPosts();
//    Collections.shuffle(postsList);
//
//    for (posts post : postsList) {
//        // Create a panel for each individual post
//        JPanel postPanel = new JPanel(new BorderLayout());
//        postPanel.setBorder(BorderFactory.createCompoundBorder(
//            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
//            BorderFactory.createEmptyBorder(5, 5, 5, 5)
//        ));
//
//        // Add image if available
//        String imgPath = post.getImg();
//        if (imgPath != null && !imgPath.isEmpty()) {
//            try {
//                ImageIcon imageIcon = new ImageIcon(imgPath);
//                Image scaledImage = imageIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
//                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
//                postPanel.add(imageLabel, BorderLayout.CENTER);
//            } catch (Exception e) {
//                System.err.println("Error loading image: " + imgPath);
//            }
//        }
//
//        // Add post text
//        JTextArea textArea = new JTextArea(post.getText());
//        textArea.setLineWrap(true);
//        textArea.setWrapStyleWord(true);
//        textArea.setEditable(false);
//        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
//        
//        JScrollPane textScrollPane = new JScrollPane(textArea);
//        textScrollPane.setPreferredSize(new Dimension(200, 70));
//        postPanel.add(textScrollPane, BorderLayout.SOUTH);
//
//        // Add individual post panel to the main posts panel
//        postsPanel.add(postPanel);
//    }
//
//    // Create a scroll pane for the entire posts panel
//    JScrollPane scrollPane = new JScrollPane(postsPanel);
//    scrollPane.setPreferredSize(new Dimension(400, 400)); // Match the initial panel size
//    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//
//    // Add the scroll pane to jPanel1
//    jPanel1.add(scrollPane, BorderLayout.CENTER);
//
//    // Ensure the panel is updated
//    jPanel1.revalidate();
//    jPanel1.repaint();
//}
//    private void showPosts() {
//        jPanel1.setLayout(new BorderLayout());
//        // Create the posts panel
//        JPanel postsPanel = new JPanel();
//        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // Vertical stacking of posts
//
//        List<posts> posts = group.getPosts();
//
//        
//        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // Stack posts vertically
//        Collections.shuffle(posts);
//        for (posts post : posts) {
//            // Create a JPanel for each post
//            JPanel postPanel = new JPanel();
//            postPanel.setLayout(new BorderLayout());
//            postPanel.setBorder(BorderFactory.createCompoundBorder(
//                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
//                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
//            ));
//            postPanel.setBackground(Color.WHITE);
//
//            // Create and add image if available
//            String imgPath = post.getImg();
//            if (imgPath != null && !imgPath.isEmpty()) {
//                try {
//                    ImageIcon imageIcon = new ImageIcon(imgPath);
//                    if (imageIcon.getIconWidth() > 0) { // Ensure image loaded correctly
//                        Image scaledImage = imageIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
//                        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
//                        imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//                        postPanel.add(imageLabel, BorderLayout.CENTER);
//                    } else {
//                        System.err.println("Image failed to load: " + imgPath);
//                    }
//                } catch (Exception e) {
//                    System.err.println("Error loading image from path: " + imgPath);
//                    e.printStackTrace();
//                }
//
//            }
//
//            // Create a text area with post text
//            JTextArea textArea = new JTextArea(post.getText());
//            textArea.setLineWrap(true);
//            textArea.setWrapStyleWord(true);
//            textArea.setEditable(false);
//            textArea.setFont(new Font("Arial", Font.PLAIN, 14));
//            textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//            textArea.setBackground(Color.WHITE);
//
//            // Wrap text area into a scroll pane to handle long text
//            JScrollPane scrollPaneForText = new JScrollPane(textArea);
//            scrollPaneForText.setBorder(BorderFactory.createEmptyBorder());
//            scrollPaneForText.setPreferredSize(new Dimension(400, 100));
//            postPanel.add(scrollPaneForText, BorderLayout.SOUTH);
//
//            postsPanel.add(postPanel);
//        }
//
//// Ensure postsPanel is visible
//        postsPanel.revalidate();
//        postsPanel.repaint();
//
//// Wrap the postsPanel in a JScrollPane
//        JScrollPane scrollPane = new JScrollPane(postsPanel);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//
//// Add scrollPane to the main JPanel
//        jPanel1.setLayout(new BorderLayout());
//        jPanel1.add(scrollPane, BorderLayout.CENTER);
//    }
//private void showPosts() {
//    jPanel1.setLayout(new BorderLayout());
//
//    // Create the posts panel
//    JPanel postsPanel = new JPanel();
//    postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // Vertical stacking of posts
//
//    List<posts> posts = group.getPosts();
//    Collections.shuffle(posts); // Randomize post order (optional)
//
//    for (posts post : posts) {
//        // Create a JPanel for each post
//        JPanel postPanel = new JPanel();
//        postPanel.setLayout(new BorderLayout());
//        postPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
//                BorderFactory.createEmptyBorder(5, 5, 5, 5)
//        ));
//        postPanel.setBackground(Color.WHITE);
//
//        // Add user name at the top
//        User user1= userDatabase.getUserById( post.getUserId());
//        JLabel userLabel = new JLabel("Posted by: " + user1.getUsername()); // Replace with user name if available
//        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
//        userLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        postPanel.add(userLabel, BorderLayout.NORTH);
//
//        // Add image if available
//        String imgPath = post.getImg();
//        if (imgPath != null && !imgPath.isEmpty()) {
//            try {
//                ImageIcon imageIcon = new ImageIcon(imgPath);
//                if (imageIcon.getIconWidth() > 0) { // Ensure the image loaded correctly
//                    Image scaledImage = imageIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
//                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
//                    imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//                    postPanel.add(imageLabel, BorderLayout.CENTER);
//                } else {
//                    System.err.println("Image failed to load: " + imgPath);
//                }
//            } catch (Exception e) {
//                System.err.println("Error loading image from path: " + imgPath);
//                e.printStackTrace();
//            }
//        }
//
//        // Add post text
//        JTextArea textArea = new JTextArea(post.getText());
//        textArea.setLineWrap(true);
//        textArea.setWrapStyleWord(true);
//        textArea.setEditable(false);
//        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
//        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        textArea.setBackground(Color.WHITE);
//
//        JScrollPane scrollPaneForText = new JScrollPane(textArea);
//        scrollPaneForText.setBorder(BorderFactory.createEmptyBorder());
//        scrollPaneForText.setPreferredSize(new Dimension(400, 100));
//        postPanel.add(scrollPaneForText, BorderLayout.SOUTH);
//
//        // Add the post panel to the posts panel
//        postsPanel.add(postPanel);
//    }
//
//    // Wrap the postsPanel in a JScrollPane
//    JScrollPane scrollPane = new JScrollPane(postsPanel);
//    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//
//    // Clear and add the scrollPane to the main JPanel
//    jPanel1.removeAll();
//    jPanel1.add(scrollPane, BorderLayout.CENTER);
//    jPanel1.revalidate();
//    jPanel1.repaint();
//}
//private void showPosts() {
//    jPanel1.setLayout(new BorderLayout());
//
//    // Create the posts panel
//    JPanel postsPanel = new JPanel();
//    postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // Vertical stacking of posts
//
//    List<posts> posts = group.getPosts();
//    Collections.shuffle(posts); // Randomize post order (optional)
//
//    for (posts post : posts) {
//        // Create a JPanel for each post
//        JPanel postPanel = new JPanel();
//        postPanel.setLayout(new BorderLayout());
//        postPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
//                BorderFactory.createEmptyBorder(5, 5, 5, 5)
//        ));
//        postPanel.setBackground(Color.WHITE);
//
//        // Add user name at the top
//        User user1 = userDatabase.getUserById(post.getUserId());
//        JLabel userLabel = new JLabel("Posted by: " + user1.getUsername());
//        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
//        userLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        postPanel.add(userLabel, BorderLayout.NORTH);
//
//        // Add image if available
//        String imgPath = post.getImg();
//        if (imgPath != null && !imgPath.isEmpty()) {
//            try {
//                ImageIcon imageIcon = new ImageIcon(imgPath);
//                if (imageIcon.getIconWidth() > 0) {
//                    Image scaledImage = imageIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
//                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
//                    imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//                    postPanel.add(imageLabel, BorderLayout.CENTER);
//                } else {
//                    System.err.println("Image failed to load: " + imgPath);
//                }
//            } catch (Exception e) {
//                System.err.println("Error loading image from path: " + imgPath);
//                e.printStackTrace();
//            }
//        }
//
//        // Add post text
//        JTextArea textArea = new JTextArea(post.getText());
//        textArea.setLineWrap(true);
//        textArea.setWrapStyleWord(true);
//        textArea.setEditable(false);
//        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
//        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        textArea.setBackground(Color.WHITE);
//
//        JScrollPane scrollPaneForText = new JScrollPane(textArea);
//        scrollPaneForText.setBorder(BorderFactory.createEmptyBorder());
//        scrollPaneForText.setPreferredSize(new Dimension(300, 100));
//        postPanel.add(scrollPaneForText, BorderLayout.SOUTH);
//
//        // Add the post panel to the posts panel
//        postsPanel.add(postPanel);
//    }
//
//    // Wrap the postsPanel in a JScrollPane
//    JScrollPane scrollPane = new JScrollPane(postsPanel);
//    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//
//    // Clear and add the scrollPane to the main JPanel
//    jPanel1.removeAll();
//    jPanel1.add(scrollPane, BorderLayout.CENTER);
//
//    // Revalidate and repaint to reflect the changes
//    jPanel1.revalidate();
//    jPanel1.repaint();
//}
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

        // Add user name at the top of the post
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
        jLabel1 = new javax.swing.JLabel();
        addpost = new javax.swing.JButton();
        leaveGroup = new javax.swing.JButton();
        back = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );

        photo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Group Photo");

        addpost.setBackground(new java.awt.Color(0, 0, 0));
        addpost.setForeground(new java.awt.Color(255, 255, 255));
        addpost.setText("Add Post");
        addpost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addpostActionPerformed(evt);
            }
        });

        leaveGroup.setBackground(new java.awt.Color(0, 0, 0));
        leaveGroup.setForeground(new java.awt.Color(255, 255, 255));
        leaveGroup.setText("Leave Group");
        leaveGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveGroupActionPerformed(evt);
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(leaveGroup, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addpost, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                    .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(107, 107, 107)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(53, 53, 53)
                        .addComponent(addpost)
                        .addGap(18, 18, 18)
                        .addComponent(leaveGroup)
                        .addGap(26, 26, 26)
                        .addComponent(back))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addpostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addpostActionPerformed
        // TODO add your handling code here:
        String input = JOptionPane.showInputDialog("Enter your text for the new post:");
        if (input == null || input.isEmpty()) {
            return;
        }

        JFileChooser choose = new JFileChooser();
        int result = choose.showSaveDialog(this); // Get the user action
        if (result != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this, "No file selected!");
            LocalDateTime t = LocalDateTime.now();
            c = f.factory("post", t, input, "", user.getUserId(), user.getUsername());
            user.addPost((posts) c);
            group.addPost((posts)c);
            groupManager.addPost(group, user.getUserId(),(posts) c);
            showPosts();
            return; // Exit if no file is selected
        }

        File profile = choose.getSelectedFile();
        LocalDateTime t = LocalDateTime.now();
        c = f.factory("post", t, input, profile.toPath().toString(), user.getUserId(), user.getUsername());
        user.addPost((posts) c);
      group.addPost((posts)c);
     groupManager.addPost(group,  user.getUserId(),(posts) c);
        showPosts();
    }//GEN-LAST:event_addpostActionPerformed

    private void leaveGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveGroupActionPerformed
        // TODO add your handling code here:
    // Leave the group
    Map<String,Object> map = groupManager.leaveGroup(group,  user.getUserId());
    group=(Group)map.get("group");
    user=(User)map.get("user");
    // Change the leave group button text and functionality
    leaveGroup.setText("Join Group");
    
    // Remove the add post button
    addpost.setVisible(false);
    
    // Add an action listener to the button to allow rejoining
    leaveGroup.removeActionListener(leaveGroup.getActionListeners()[0]); // Remove previous action listener
    leaveGroup.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            // Rejoin group logic
            Map<String, Object> map = groupManager.joinGroup(group,  user.getUserId());
            user=(User)map.get("user");
            group=(Group)map.get("group");
            // Restore the button to original state
            leaveGroup.setText("Leave Group");
            addpost.setVisible(true);
            
            // Restore original action listener
            leaveGroup.removeActionListener(this);
            leaveGroup.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    leaveGroupActionPerformed(evt);
                }
            });
            
            // Refresh posts
            showPosts();
        }
    });
    
    // Refresh posts to remove user's posts
    showPosts();
        
    }//GEN-LAST:event_leaveGroupActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ((NewsFeed)previousWindow).updateUser(user);
        previousWindow.setVisible(true);
    }//GEN-LAST:event_backActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addpost;
    private javax.swing.JButton back;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton leaveGroup;
    private javax.swing.JLabel photo;
    // End of variables declaration//GEN-END:variables
}

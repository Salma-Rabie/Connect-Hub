
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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class GroupWindow extends javax.swing.JFrame {

    private GroupDataBase groupDatabase;
    private User user;
    private Group group;
    private JFrame previousWindow;
    private GroupManagement groupManager;
    private content c;
    private factory f = new factory();
    private UserDataBase userDatabase=UserDataBase.getInstance("users.json");
    
    public GroupWindow( JFrame previousWindow, User user, Group group,GroupDataBase groupDatabase,GroupManagement groupManager) {
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
private void showPosts() {
    jPanel1.setLayout(new BorderLayout());

    // Create the posts panel
    JPanel postsPanel = new JPanel();
    postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // Vertical stacking of posts

    List<posts> posts = group.getPosts();
    Collections.shuffle(posts); // Randomize post order (optional)

    for (posts post : posts) {
        // Create a JPanel for each post
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BorderLayout());
        postPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        postPanel.setBackground(Color.WHITE);

        // Add user name at the top
        User user1= userDatabase.getUserById( post.getUserId());
        JLabel userLabel = new JLabel("Posted by: " + user1.getUsername()); // Replace with user name if available
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        postPanel.add(userLabel, BorderLayout.NORTH);

        // Add image if available
        String imgPath = post.getImg();
        if (imgPath != null && !imgPath.isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(imgPath);
                if (imageIcon.getIconWidth() > 0) { // Ensure the image loaded correctly
                    Image scaledImage = imageIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    postPanel.add(imageLabel, BorderLayout.CENTER);
                } else {
                    System.err.println("Image failed to load: " + imgPath);
                }
            } catch (Exception e) {
                System.err.println("Error loading image from path: " + imgPath);
                e.printStackTrace();
            }
        }

        // Add post text
        JTextArea textArea = new JTextArea(post.getText());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textArea.setBackground(Color.WHITE);

        JScrollPane scrollPaneForText = new JScrollPane(textArea);
        scrollPaneForText.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneForText.setPreferredSize(new Dimension(400, 100));
        postPanel.add(scrollPaneForText, BorderLayout.SOUTH);

        // Add the post panel to the posts panel
        postsPanel.add(postPanel);
    }

    // Wrap the postsPanel in a JScrollPane
    JScrollPane scrollPane = new JScrollPane(postsPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    // Clear and add the scrollPane to the main JPanel
    jPanel1.removeAll();
    jPanel1.add(scrollPane, BorderLayout.CENTER);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(addpost, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(92, 92, 92)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(addpost))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(94, Short.MAX_VALUE))
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
            groupManager.addPost(group, user,(posts) c);
            showPosts();
            return; // Exit if no file is selected
        }

        File profile = choose.getSelectedFile();
        LocalDateTime t = LocalDateTime.now();
        c = f.factory("post", t, input, profile.toPath().toString(), user.getUserId(), user.getUsername());
        user.addPost((posts) c);
      group.addPost((posts)c);
     groupManager.addPost(group, user,(posts) c);
        showPosts();
    }//GEN-LAST:event_addpostActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addpost;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel photo;
    // End of variables declaration//GEN-END:variables
}

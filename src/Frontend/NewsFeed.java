
package Frontend;

import Backend.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 *
 * @author Salma Eid
 */
public class NewsFeed extends javax.swing.JFrame {

    private User user;
    private UserManager userManager;
    private ProfileManagement profileManager;
    private JFrame previousWindow;
    private factory f = new factory();
    private content c;
    private FriendRequestDataBase friendRequestsDatabase;
    private FriendDataBase friendsDatabase;
    private final Map<String, List<String>> friendsMap = new HashMap<>();
    private List<String> currentUserFriends = new ArrayList();
    private GroupDataBase groupDatabase;
   private GroupManagement groupManager;
private ArrayList<String>userGroups;
    /**
     * Creates new form NewsFeed
     */
    public NewsFeed(JFrame previousWindow, User user, UserManager userManager, ProfileManagement profileManager) {
        this.previousWindow = previousWindow;
        this.user = user;
        this.userManager = userManager;
        this.profileManager = profileManager;
        this.groupDatabase=GroupDataBase.getInstance("groups.json"); 
        this.userGroups=user.getGroups();
        this.groupManager=new GroupManagement(userManager.getDatabase(),groupDatabase);
         String filePath = "friend_requests.json";
        friendRequestsDatabase = FriendRequestDataBase.getInstance(filePath);
        initComponents();
        user.removeExpiredStories();
        setLocationRelativeTo(null);
        setTitle("Newsfeed");
        loadFriends(user.getUserId());
        showPosts(user);
        showStories(user);
       mygroups.removeAllItems();
         mygroups.addItem("My Groups");
        mygroups.setSelectedIndex(0);
        for(int i=0;i<userGroups.size();i++){
            mygroups.addItem(userGroups.get(i));
        }
    }

    public void updateUser(User user) {
        this.user = user;
        loadFriends(user.getUserId());
        showPosts(user);
        showStories(user);
        user.removeExpiredStories();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addpost = new javax.swing.JButton();
        addstory = new javax.swing.JButton();
        friendrequests = new javax.swing.JButton();
        Suggestions = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        MyFriends = new javax.swing.JButton();
        GoToProfile = new javax.swing.JButton();
        mygroups = new javax.swing.JComboBox<>();
        choosegroup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        addpost.setBackground(new java.awt.Color(0, 0, 0));
        addpost.setForeground(new java.awt.Color(255, 255, 255));
        addpost.setText("Add Post");
        addpost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addpostActionPerformed(evt);
            }
        });

        addstory.setBackground(new java.awt.Color(0, 0, 0));
        addstory.setForeground(new java.awt.Color(255, 255, 255));
        addstory.setText("Add Story");
        addstory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addstoryActionPerformed(evt);
            }
        });

        friendrequests.setBackground(new java.awt.Color(0, 0, 0));
        friendrequests.setForeground(new java.awt.Color(255, 255, 255));
        friendrequests.setText("Friend Requests");
        friendrequests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendrequestsActionPerformed(evt);
            }
        });

        Suggestions.setBackground(new java.awt.Color(0, 0, 0));
        Suggestions.setForeground(new java.awt.Color(255, 255, 255));
        Suggestions.setText("Suggestions");
        Suggestions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuggestionsActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );

        MyFriends.setBackground(new java.awt.Color(0, 0, 0));
        MyFriends.setForeground(new java.awt.Color(255, 255, 255));
        MyFriends.setText("My Friends");
        MyFriends.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MyFriendsActionPerformed(evt);
            }
        });

        GoToProfile.setBackground(new java.awt.Color(0, 0, 0));
        GoToProfile.setForeground(new java.awt.Color(255, 255, 255));
        GoToProfile.setText("Your Profile");
        GoToProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GoToProfileActionPerformed(evt);
            }
        });

        mygroups.setBackground(new java.awt.Color(0, 0, 0));
        mygroups.setForeground(new java.awt.Color(255, 255, 255));
        mygroups.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        mygroups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mygroupsActionPerformed(evt);
            }
        });

        choosegroup.setBackground(new java.awt.Color(0, 0, 0));
        choosegroup.setForeground(new java.awt.Color(255, 255, 255));
        choosegroup.setText("Go to the Group");
        choosegroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choosegroupActionPerformed(evt);
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
                        .addGap(0, 105, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mygroups, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addpost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addstory, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(friendrequests, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Suggestions, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MyFriends, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(GoToProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(choosegroup))))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addpost)
                    .addComponent(addstory)
                    .addComponent(friendrequests)
                    .addComponent(Suggestions)
                    .addComponent(MyFriends)
                    .addComponent(GoToProfile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mygroups, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choosegroup))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GoToProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GoToProfileActionPerformed
        // TODO add your handling code here:
        ProfileWindow profile = new ProfileWindow(this, user, userManager, profileManager, previousWindow);
        this.setVisible(false);
        profile.setVisible(true);
    }//GEN-LAST:event_GoToProfileActionPerformed

    private void addstoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addstoryActionPerformed
        // TODO add your handling code here:
        String input = JOptionPane.showInputDialog("Enter your text for the new story:");
        if (input == null || input.isEmpty()) {
            return;
        }

        JFileChooser choose = new JFileChooser();
        int result = choose.showSaveDialog(this); // Get the user action
        if (result != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this, "No file selected!");
            LocalDateTime t = LocalDateTime.now();
            c = f.factory("story", t, input, "", user.getUserId(), user.getUsername());
            user.addStory((stories) c);
            userManager.getDatabase().updateUser(user);
            return; // Exit if no file is selected
        }

        File profile = choose.getSelectedFile();
        LocalDateTime t = LocalDateTime.now();
        c = f.factory("story", t, input, profile.toPath().toString(), user.getUserId(), user.getUsername());
        user.addStory((stories) c);
        userManager.getDatabase().updateUser(user);

    }//GEN-LAST:event_addstoryActionPerformed

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
            userManager.getDatabase().updateUser(user);
            return; // Exit if no file is selected
        }

        File profile = choose.getSelectedFile();
        LocalDateTime t = LocalDateTime.now();
        c = f.factory("post", t, input, profile.toPath().toString(), user.getUserId(), user.getUsername());
        user.addPost((posts) c);
        userManager.getDatabase().updateUser(user);

    }//GEN-LAST:event_addpostActionPerformed

    private void friendrequestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendrequestsActionPerformed
        // TODO add your handling code here:
        friendRequest friend = new friendRequest(this, user, userManager,friendRequestsDatabase);
        this.setVisible(false);
        friend.setVisible(true);
    }//GEN-LAST:event_friendrequestsActionPerformed

    private void MyFriendsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MyFriendsActionPerformed
        // TODO add your handling code here:
  FriendsList flist = new FriendsList(this, user);
        flist.setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_MyFriendsActionPerformed

    private void SuggestionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuggestionsActionPerformed
        // TODO add your handling code here:
        SuggestionsList suggestions = new SuggestionsList(this, user);
        suggestions.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_SuggestionsActionPerformed

    private void mygroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mygroupsActionPerformed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_mygroupsActionPerformed

    private void choosegroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choosegroupActionPerformed
        // TODO add your handling code here:
        String groupName=(String) mygroups.getSelectedItem();
        if (groupName != null && !groupName.equals("Select Friend Request")){
            Group group= groupDatabase.getGroupByName(groupName);
            this.setVisible(false);
            GroupWindow groupWindow=new GroupWindow(this,user,group,groupDatabase,groupManager);
            groupWindow.setVisible(true);
        }
    }//GEN-LAST:event_choosegroupActionPerformed

    private void loadFriends(String currentUserId) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("friends.json"));
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
            br.close();

            String jsonString = jsonStringBuilder.toString();

            // Parse friends manually using regex
            Pattern pattern = Pattern.compile("\"(\\d+)\":\\s*\\{\"friends\":\\s*\\[(.*?)\\]\\}");
            Matcher matcher = pattern.matcher(jsonString);

            while (matcher.find()) {
                String userId = matcher.group(1); // e.g., "1000"
                String friendsStr = matcher.group(2); // e.g., "12345, 67890"
                List<String> friendsList = new ArrayList<>();

                if (!friendsStr.isEmpty()) {
                    String[] friends = friendsStr.split(",");
                    for (String friendId : friends) {
                        friendsList.add(friendId.trim().replaceAll("\"", ""));
                    }
                }

                friendsMap.put(userId, friendsList);
            }

            // Only load the friends of the current user
            if (friendsMap.containsKey(currentUserId)) {
                currentUserFriends = friendsMap.get(currentUserId);

                System.out.println("Current user's friends: " + currentUserFriends);
            } else {
                System.out.println("No friends found for the current user.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPosts(User user) {
        jPanel1.setLayout(new BorderLayout());
        // Create the posts panel
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // Vertical stacking of posts

        List<posts> posts = new ArrayList();

        for (String friendId : currentUserFriends) {
            System.out.println(friendId);
            posts = userManager.getDatabase().getUserById(friendId).getPosts();
        }
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // Stack posts vertically
        Collections.shuffle(posts);
        for (posts post : posts) {
            // Create a JPanel for each post
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BorderLayout());
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            postPanel.setBackground(Color.WHITE);

            // Create and add image if available
            String imgPath = post.getImg();
            if (imgPath != null && !imgPath.isEmpty()) {
                try {
                    ImageIcon imageIcon = new ImageIcon(imgPath);
                    if (imageIcon.getIconWidth() > 0) { // Ensure image loaded correctly
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

            // Create a text area with post text
            JTextArea textArea = new JTextArea(post.getText());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));
            textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            textArea.setBackground(Color.WHITE);

            // Wrap text area into a scroll pane to handle long text
            JScrollPane scrollPaneForText = new JScrollPane(textArea);
            scrollPaneForText.setBorder(BorderFactory.createEmptyBorder());
            scrollPaneForText.setPreferredSize(new Dimension(400, 100));
            postPanel.add(scrollPaneForText, BorderLayout.SOUTH);

            postsPanel.add(postPanel);
        }

// Ensure postsPanel is visible
        postsPanel.revalidate();
        postsPanel.repaint();

// Wrap the postsPanel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(postsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

// Add scrollPane to the main JPanel
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(scrollPane, BorderLayout.CENTER);
    }

    private void showStories(User user) {
        jPanel2.setLayout(new BorderLayout());

        // Create the posts panel
        JPanel storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS)); // Vertical stacking of posts

        List<stories> story = new ArrayList();

        for (String friendId : currentUserFriends) {
            System.out.println(friendId);
            story = userManager.getDatabase().getUserById(friendId).getStories();
        }
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS)); // Stack posts vertically
        Collections.shuffle(story);
        for (stories s : story) {
            // Create a JPanel for each story
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BorderLayout());
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            postPanel.setBackground(Color.WHITE);

            // Create and add image if available
            String imgPath = s.getImg();
            if (imgPath != null && !imgPath.isEmpty()) {
                try {
                    ImageIcon imageIcon = new ImageIcon(imgPath);
                    if (imageIcon.getIconWidth() > 0) { // Ensure image loaded correctly
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

            // Create a text area with post text
            JTextArea textArea = new JTextArea(s.getText());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));
            textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            textArea.setBackground(Color.WHITE);

            // Wrap text area into a scroll pane to handle long text
            JScrollPane scrollPaneForText = new JScrollPane(textArea);
            scrollPaneForText.setBorder(BorderFactory.createEmptyBorder());
            scrollPaneForText.setPreferredSize(new Dimension(400, 100));
            postPanel.add(scrollPaneForText, BorderLayout.SOUTH);

            storiesPanel.add(postPanel);
        }
    }

    
  
    
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
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton GoToProfile;
    private javax.swing.JButton MyFriends;
    private javax.swing.JButton Suggestions;
    private javax.swing.JButton addpost;
    private javax.swing.JButton addstory;
    private javax.swing.JButton choosegroup;
    private javax.swing.JButton friendrequests;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> mygroups;
    // End of variables declaration//GEN-END:variables
}

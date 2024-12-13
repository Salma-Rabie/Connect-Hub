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
    private ArrayList<String> userGroups;

    /**
     * Creates new form NewsFeed
     */
    public NewsFeed(JFrame previousWindow, User user, UserManager userManager, ProfileManagement profileManager) {
        this.previousWindow = previousWindow;
        this.user = user;
        this.userManager = userManager;
        this.profileManager = profileManager;
        this.groupDatabase = GroupDataBase.getInstance("groups.json");
        this.userGroups = user.getGroups();
        this.groupManager = new GroupManagement(userManager.getDatabase(), groupDatabase);
        String filePath = "friend_requests.json";
        friendRequestsDatabase = FriendRequestDataBase.getInstance(filePath);
        initComponents();
        user.removeExpiredStories();
        setLocationRelativeTo(null);
        setTitle("Newsfeed");
        loadFriends(user.getUserId());
         addStoriesAndPostsLayout( user);
//        showPosts(user);
//        showStories(user);
        mygroups.removeAllItems();
        mygroups.addItem("My Groups");
        mygroups.setSelectedIndex(0);
        for (int i = 0; i < userGroups.size(); i++) {
            mygroups.addItem(userGroups.get(i));
        }
    }

    public void updateUser(User user) {
        this.user = user;
        this.userGroups = user.getGroups();
        loadFriends(user.getUserId());
        showPosts(user);
        showStories(user);
        user.removeExpiredStories();
        mygroups.removeAllItems();
        mygroups.addItem("My Groups");
        mygroups.setSelectedIndex(0);
        for (int i = 0; i < userGroups.size(); i++) {
            mygroups.addItem(userGroups.get(i));
        }
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

        List<stories> s = new ArrayList();
        List<posts> p =userManager.getDatabase().getUserById(user.getUserId()).getPosts();
        for (String friendId : currentUserFriends) {
            System.out.println(friendId);
            s = userManager.getDatabase().getUserById(friendId).getStories();
        }
           for (String friendId : currentUserFriends) {
            System.out.println(friendId);
            p = userManager.getDatabase().getUserById(friendId).getPosts();
        }
        
       
        
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

        addpost = new javax.swing.JButton();
        addstory = new javax.swing.JButton();
        friendrequests = new javax.swing.JButton();
        Suggestions = new javax.swing.JButton();
        MyFriends = new javax.swing.JButton();
        GoToProfile = new javax.swing.JButton();
        mygroups = new javax.swing.JComboBox<>();
        choosegroup = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        GoToProfile1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();

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

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Create Group");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        GoToProfile1.setBackground(new java.awt.Color(0, 0, 0));
        GoToProfile1.setForeground(new java.awt.Color(255, 255, 255));
        GoToProfile1.setText("Group Suggestions");
        GoToProfile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GoToProfile1ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Search:");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 309, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 233, Short.MAX_VALUE)
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
            .addGap(0, 233, Short.MAX_VALUE)
        );

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Notifications");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addpost, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(mygroups, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addstory, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(friendrequests, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(choosegroup)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(16, 16, 16)
                        .addComponent(Suggestions, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(MyFriends, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GoToProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GoToProfile1, javax.swing.GroupLayout.Alignment.TRAILING))))
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
                    .addComponent(choosegroup)
                    .addComponent(jButton1)
                    .addComponent(GoToProfile1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(247, Short.MAX_VALUE))
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
        friendRequest friend = new friendRequest(this, user, userManager, friendRequestsDatabase);
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
        String groupName = (String) mygroups.getSelectedItem();
        if (groupName != null && !groupName.equals("Select Friend Request")) {
            Group group = groupDatabase.getGroupByName(groupName);
            if(group.getOtherAdmins().contains(user.getUserId()))
           {
               this.setVisible(false);
                OtherAdminsGroupWindow groupWindow = new OtherAdminsGroupWindow(this, user, group, groupDatabase, groupManager);
                groupWindow.setVisible(true);
            }
            else if (!group.getPrimaryAdmin().equals(user.getUserId()) && !group.getOtherAdmins().contains(user.getUserId())) {
                this.setVisible(false);
                UserGroupWindow groupWindow = new UserGroupWindow(this, user, group, groupDatabase, groupManager);
                groupWindow.setVisible(true);
            }
            else if (group.getPrimaryAdmin().equals(user.getUserId())) {
                 this.setVisible(false);
                PrimaryAdminGroupWindow groupWindow = new PrimaryAdminGroupWindow(this, user, group, groupDatabase, groupManager);
                groupWindow.setVisible(true);
            }
          
        }

    }//GEN-LAST:event_choosegroupActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        CreateGroup creatgroup= new CreateGroup(this,user,groupManager);
        creatgroup.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void GoToProfile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GoToProfile1ActionPerformed
        // TODO add your handling code here:
        GroupSuggestions suggestions = new GroupSuggestions(this, user,groupManager);
        suggestions.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_GoToProfile1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JDialog searchDialog = new JDialog(this, "Search Results", true);;
        String text = jTextField1.getText();
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS)); // Vertical stacking
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Empty search text field", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            List<User> users = userManager.getDatabase().getAllUsers();
            List<Group> groups = groupDatabase.getAllGroups(); // Corrected: renamed 'group' to 'groups'
            boolean userFound = false;
            boolean groupFound = false;

            // Search through users
            for (User userr : users) {
                if (userr.getUsername().equalsIgnoreCase(user.getUsername())) {
                    continue;
                }
                // Check if the username contains the input text (case-insensitive)
                if (userr.getUsername().toLowerCase().contains(text.toLowerCase())) {
                    userFound = true;

                    // Create a panel for each user entry
                    JPanel userPanel = new JPanel();
                    userPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Horizontal layout for components
                    userPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                    // Add username as a label
                    JLabel userLabel = new JLabel(userr.getUsername());
                    userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                    userPanel.add(userLabel);

                    boolean[] isFriend = {false}; // Use an array to track friendship status
                    boolean[] isPending = {false}; // Use an array to track pending status

                    // Check if the user is a friend
                    for (String friend : currentUserFriends) {
                        if (friend.equals(userr.getUserId())) {
                            isFriend[0] = true;
                            break;
                        }
                    }

                    // Check if the user is in the pending requests
                    ArrayList<String> pendingRequests = FriendRequestDataBase.getInstance("friend_requests.json").getPendingRequests(user.getUserId());
                    for (String pending : pendingRequests) {
                        if (pending.equals(userr.getUserId())) {
                            isPending[0] = true;
                            break;
                        }
                    }
                    BlockedUserDataBase blocked ;
                    blocked =  BlockedUserDataBase.getInstance("blocked_users.json");
                    if (!(blocked.isUserBlocked(userr.getUserId(), user.getUserId()))) {
                        // Create button based on the state
                        JButton addFriendButton = new JButton(
                            isFriend[0] ? "Remove" : (isPending[0] ? "Pending" : "Add Friend")
                        );

                        addFriendButton.addActionListener(e -> {
                            if (isFriend[0]) {
                                // If the user is a friend, clicking "Remove"
                                JOptionPane.showMessageDialog(null, "You are now removing " + userr.getUsername() + " from your friends.");
                                currentUserFriends.remove(userr.getUserId()); // Remove from friends list
                                addFriendButton.setText("Add Friend"); // Update the button text
                                isFriend[0] = false; // Update the status
                            } else if (isPending[0]) {
                                // If the user is pending, do nothing or display a message
                                JOptionPane.showMessageDialog(null, "Friend request is already pending for " + userr.getUsername() + ".");
                            } else {
                                // If the user is not a friend or pending, send a friend request
                                JOptionPane.showMessageDialog(null, "Friend request sent to " + userr.getUsername());
                                pendingRequests.add(userr.getUserId()); // Add to pending requests
                                FriendRequestDataBase.getInstance("friend_requests.json").addFriendRequest(user.getUserId(), userr.getUserId()); // Save updated pending requests
                                addFriendButton.setText("Pending"); // Update the button text
                                isPending[0] = true; // Update the status
                            }
                        });

                        // Add the button to the panel
                        userPanel.add(addFriendButton);}

                    // Add "Block" button

                    if(!(blocked.isUserBlocked(userr.getUserId(), user.getUserId()))){
                        JButton blockButton = new JButton("Block");
                        blockButton.addActionListener(e -> {
                            // Action to block user
                            BlockedUserDataBase.getInstance("blocked_users.json").saveBlockedUser(user.getUserId(), userr.getUserId());
                            FriendDataBase.getInstance("friends.json").removeFriend(user.getUserId(), userr.getUserId());
                            JOptionPane.showMessageDialog(null, userr.getUsername() + " has been blocked.");
                        });
                        userPanel.add(blockButton);
                    }
                    else
                    {
                        JButton unblock = new JButton("Blocked");
                        unblock.addActionListener(e -> {
                            // Action to block use
                            JOptionPane.showMessageDialog(null, userr.getUsername() + " user already blocked.");
                        });
                    }
                    // Add "View Profile" button
                    JButton viewProfileButton = new JButton("View Profile");
                    viewProfileButton.addActionListener(e -> {
                        searchDialog.setVisible(false);
                        // Action to view profile
                        ViewProfile profile = new ViewProfile(userr, this, userManager);
                        profile.setVisible(true);
                    });

                    userPanel.add(viewProfileButton);
                    panel2.add(userPanel); // Add user panel to the main panel
                }
            }

            // Search through groups (should be independent of user search)
            for (Group group : groups) {
                // Check if the group name contains the input text (case-insensitive)
                if (group.getName().toLowerCase().contains(text.toLowerCase())) {
                    groupFound = true;

                    // Create a panel for each group entry
                    JPanel groupPanel = new JPanel();
                    groupPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    groupPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                    // Add group name as a label
                    JLabel groupLabel = new JLabel(group.getName());
                    groupLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                    groupPanel.add(groupLabel);

                    // "Join" or "Leave" button for groups
                    JButton joinLeaveButton = new JButton();
                    boolean[] isMember = {false};
                    isMember[0] = user.getGroups().contains(group.getName());
                    //boolean hasJoinRequest = group.getJoinRequests().contains(user.getUserId());  // Track join requests

                    if (isMember[0]) {
                        joinLeaveButton.setText("Leave");
                    } 
                     else {
                        joinLeaveButton.setText("Join");
                    }

                    joinLeaveButton.addActionListener(e -> {

                        if (isMember[0]) {
                            // If user is a member, allow them to leave the group
                            JOptionPane.showMessageDialog(null, "You left the group: " + group.getName());
                            user.getGroups().remove(group.getName()); // Remove user from the group
                            joinLeaveButton.setText("Join"); // Change button text to "Join"
                            joinLeaveButton.setEnabled(true); // Enable button again for joining
                            isMember[0] = false;
                            
                             Map<String, Object> map = groupManager.leaveGroup(group, user.getUserId());
                              //group = (Group) map.get("group");
                user = (User) map.get("user");
                            //System.out.println(g.getNumMembers());
                            mygroups.removeItem(group.getName());
                        } 
                         else {
                            // If the user has not yet requested to join, send a join request
                            JOptionPane.showMessageDialog(null, "joined to " + group.getName());
                             Map<String, Object> map = groupManager.joinGroup(group, user.getUserId());
                //group = (Group) map.get("group");
                user = (User) map.get("user");
                            //group.getJoinRequests().add(user.getUserId()); // Send join request
                            joinLeaveButton.setText("Leave"); // Change button text to "Cancel Request"
                        }

                        // Revalidate and repaint the button itself to ensure it updates correctly
                        joinLeaveButton.revalidate();
                        joinLeaveButton.repaint();

                        // If you're still experiencing layout issues, revalidate the panel too
                        groupPanel.revalidate();
                        groupPanel.repaint();
                    });

                    // "View Group" button for members
                    if (isMember[0]) {
                        JButton viewGroupButton = new JButton("View Group");
                        viewGroupButton.addActionListener(e -> {
                            // Action to view the group
                            JOptionPane.showMessageDialog(null, "Viewing the group: " + group.getName());
                           
           
            if(group.getOtherAdmins().contains(user.getUserId()))
           {
               this.setVisible(false);
                OtherAdminsGroupWindow groupWindow = new OtherAdminsGroupWindow(this, user, group, groupDatabase, groupManager);
                groupWindow.setVisible(true);
            }
            else if (!group.getPrimaryAdmin().equals(user.getUserId()) && !group.getOtherAdmins().contains(user.getUserId())) {
                this.setVisible(false);
                UserGroupWindow groupWindow = new UserGroupWindow(this, user, group, groupDatabase, groupManager);
                groupWindow.setVisible(true);
            }
            else if (group.getPrimaryAdmin().equals(user.getUserId())) {
                 this.setVisible(false);
                PrimaryAdminGroupWindow groupWindow = new PrimaryAdminGroupWindow(this, user, group, groupDatabase, groupManager);
                groupWindow.setVisible(true);
            }
          
        
                        });
                        groupPanel.add(viewGroupButton);
                    }

                    groupPanel.add(joinLeaveButton);

                    // Add the group panel to the main panel
                    panel2.add(groupPanel);
                }
            }

            // If no user or group is found
            if (!userFound && !groupFound) {
                JLabel noResultsLabel = new JLabel("No users or groups found with matching text.");
                noResultsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
                noResultsLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel2.add(noResultsLabel);
            }

            // Wrap the panel in a JScrollPane
            JScrollPane scrollPane = new JScrollPane(panel2);
            scrollPane.setPreferredSize(new Dimension(500, 400)); // Adjust size as needed

            // Add the scrollPane to the JDialog
            searchDialog.add(scrollPane);
            searchDialog.pack(); // Adjust size of the dialog
            searchDialog.setLocationRelativeTo(this); // Center the dialog

            // Show the dialog
            searchDialog.setVisible(true);
        
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.setVisible(true);
        notifications notification=new notifications(user,groupManager,this);
        notification.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

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
    private javax.swing.JButton GoToProfile1;
    private javax.swing.JButton MyFriends;
    private javax.swing.JButton Suggestions;
    private javax.swing.JButton addpost;
    private javax.swing.JButton addstory;
    private javax.swing.JButton choosegroup;
    private javax.swing.JButton friendrequests;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox<String> mygroups;
    // End of variables declaration//GEN-END:variables
}

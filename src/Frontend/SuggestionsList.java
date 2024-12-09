package Frontend;

import Backend.FriendRequestClass;
import Backend.FriendRequestDataBase;
import Backend.SuggestedFriends;
import Backend.Suggestions;
import Backend.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class SuggestionsList extends JFrame {

    private User user; // User object passed from NewsFeed
    private JFrame previousWindow; // Reference to the NewsFeed JFrame
    private HashMap<String, String> usernameToIdMap; // Map usernames to user IDs
    private DefaultTableModel tableModel; // Table model for dynamic updates
public SuggestionsList(JFrame previousWindow, User user) {
    this.previousWindow = previousWindow;
    this.user = user;
    usernameToIdMap = new HashMap<>();

    setTitle("Friend Suggestions");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // Fetch suggested friends
    SuggestedFriends suggestions = new SuggestedFriends();
    ArrayList<User> suggestedUsers = Suggestions.getInstance().suggestFriends(user.getUserId());

    // Fetch pending friend requests
    FriendRequestDataBase friendRequestsDatabase = FriendRequestDataBase.getInstance("friend_requests.json");
    ArrayList<FriendRequestClass> pendingRequests = (ArrayList<FriendRequestClass>) friendRequestsDatabase.getRequestsReceivedByUserId(user.getUserId());
    pendingRequests.addAll(friendRequestsDatabase.getPendingRequests(user.getUserId())); // Add sent requests as well

    // Extract IDs of users already involved in friend requests
    HashSet<String> excludedIds = new HashSet<>();
    for (FriendRequestClass request : pendingRequests) {
        excludedIds.add(request.getSenderId());
        excludedIds.add(request.getReceiverId());
    }

    // Filter suggestions to exclude users already in friend requests
    suggestedUsers.removeIf(suggestedUser -> excludedIds.contains(suggestedUser.getUserId()));

    // Populate the map with usernames and user IDs
    for (User suggestedUser : suggestedUsers) {
        usernameToIdMap.put(suggestedUser.getUsername(), suggestedUser.getUserId());
    }

    // Create table model
    tableModel = new DefaultTableModel(new Object[]{"Username", "Action"}, 0);

    // Add rows to the table model (display usernames)
    for (String username : usernameToIdMap.keySet()) {
        tableModel.addRow(new Object[]{username, "Add Request"});
    }

    // Create JTable
    JTable table = new JTable(tableModel) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1; // Only "Action" column is editable
        }
    };

    // Add button renderer and editor
    table.getColumn("Action").setCellRenderer(new ButtonRenderer());
    table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), table));

    // Add table to scroll pane
    JScrollPane scrollPane = new JScrollPane(table);
    getContentPane().add(scrollPane, BorderLayout.CENTER);

    // Add "Back" button to return to NewsFeed
    JButton backButton = new JButton("Back");
    backButton.addActionListener(e -> {
        this.dispose(); // Close SuggestionsList
        ((NewsFeed) previousWindow).updateUser(user);
        previousWindow.setVisible(true); // Show NewsFeed
    });
    getContentPane().add(backButton, BorderLayout.SOUTH);

    setVisible(true);
}

//    public SuggestionsList(JFrame previousWindow, User user) {
//        this.previousWindow = previousWindow;
//        this.user = user;
//        usernameToIdMap = new HashMap<>();
//
//        setTitle("Friend Suggestions");
//        setSize(600, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout());
//
//        // Fetch suggested friends
//        SuggestedFriends suggestions = new SuggestedFriends();
//        ArrayList<User> suggestedUsers = Suggestions.getInstance().suggestFriends(user.getUserId());
//
//        // Populate the map with usernames and user IDs
//        for (User suggestedUser : suggestedUsers) {
//            usernameToIdMap.put(suggestedUser.getUsername(), suggestedUser.getUserId());
//        }
//
//        // Create table model
//        tableModel = new DefaultTableModel(new Object[]{"Username", "Action"}, 0);
//
//        // Add rows to the table model (display usernames)
//        for (String username : usernameToIdMap.keySet()) {
//            tableModel.addRow(new Object[]{username, "Add Request"});
//        }
//
//        // Create JTable
//        JTable table = new JTable(tableModel) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return column == 1; // Only "Action" column is editable
//            }
//        };
//
//        // Add button renderer and editor
//        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
//        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), table));
//
//        // Add table to scroll pane
//        JScrollPane scrollPane = new JScrollPane(table);
//        getContentPane().add(scrollPane, BorderLayout.CENTER);
//
//        // Add "Back" button to return to NewsFeed
//        JButton backButton = new JButton("Back");
//        backButton.addActionListener(e -> {
//            this.dispose(); // Close SuggestionsList
//            ((NewsFeed)previousWindow).updateUser(user);
//            previousWindow.setVisible(true); // Show NewsFeed
//        });
//        getContentPane().add(backButton, BorderLayout.SOUTH);
//
//        setVisible(true);
//    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

    private JButton button;
    private String label;
    private boolean clicked;
    private JTable table; // Reference to the JTable for dynamic row removal

    public ButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

  private boolean inProgress = false; // Flag to prevent re-entrance

@Override
public Object getCellEditorValue() {
     System.out.println("getCellEditorValue called for row: " + table.getEditingRow());
    if (clicked && !inProgress) {
        inProgress = true; // Set the flag to indicate action in progress
        try {
            int row = table.getEditingRow();
            if (row != -1) { // Ensure the row is valid
                String username = (String) table.getValueAt(row, 0);
                String friendId = usernameToIdMap.get(username);
                System.out.println("(inside getCellEditorValue )Sending friend request for userId = " + user.getUserId() + ", friendId = " + friendId);
                sendFriendRequest(user.getUserId(), friendId);

                // Remove the row from the table model safely
                SwingUtilities.invokeLater(() -> tableModel.removeRow(row));
            }
        } finally {
            inProgress = false; // Reset the flag
        }
    }
    clicked = false; // Reset clicked state
    return label;
}

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    private void sendFriendRequest(String userId, String friendId) {
        try {
            // Call your backend logic to add the friend request
           System.out.println("(inside sendFriendRequest methode)Sending friend request from " + userId + " to " + friendId);
           FriendRequestDataBase requestsDatabase= FriendRequestDataBase.getInstance("friend_requests");
           requestsDatabase.addFriendRequest(userId, friendId);
            JOptionPane.showMessageDialog(button, "Friend request sent to " + friendId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(button, "Failed to send friend request: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
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
            .addGap(0, 408, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

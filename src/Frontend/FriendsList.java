/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.BlockedUserDataBase;
import Backend.FriendDataBase;
import Backend.FriendRequestClass;
import Backend.FriendRequestDataBase;
import Backend.SuggestedFriends;
import Backend.Suggestions;
import Backend.User;
import Backend.UserDataBase;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author DELL
 */
public class FriendsList extends javax.swing.JFrame {

    /**
     * Creates new form FriendsList
     */
    private User user; // User object passed from NewsFeed
    private JFrame previousWindow; // Reference to the NewsFeed JFrame
    private HashMap<String, String> idToUsernameMap;
    private HashMap<String, String> usernameToIdMap;
    private DefaultTableModel tableModel;

    public FriendsList(JFrame previousWindow, User user) {
        this.previousWindow = previousWindow;
        this.user = user;
        this.idToUsernameMap = new HashMap<>();
        this.usernameToIdMap = new HashMap<>();
        setTitle("My Friends");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        ArrayList<User> allUsers = UserDataBase.getInstance("user_data.json").getAllUsers();
        for (User u : allUsers) {
            idToUsernameMap.put(u.getUserId(), u.getUsername());
            usernameToIdMap.put(u.getUsername(), u.getUserId());
        }
        setupTable(); // Create the JTable with Remove and Block buttons
        setupBackButton(); // Add a Back button
        setVisible(true);
    }

    private void setupTable() {
        // Fetch friends
//        ArrayList<String> friendIds = (ArrayList<String>) FriendDataBase.getInstance("friends.json").getFriends(user.getUserId());
        ArrayList<String> friendIds = new ArrayList<>(FriendDataBase.getInstance("friends.json").getFriends(user.getUserId()));

        // Initialize the table model with two actions: Remove and Block
        tableModel = new DefaultTableModel(new Object[]{"Username", "Remove", "Block"}, 0);

        for (String friendId : friendIds) {
            String username = idToUsernameMap.get(friendId);
            if (username != null) {
                tableModel.addRow(new Object[]{username, "Remove", "Block"});
            }
        }

        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2; // Only "Remove" and "Block" columns are editable
            }
        };

        // Add Button Renderers and Editors
        table.getColumn("Remove").setCellRenderer(new ButtonRenderer());
        table.getColumn("Remove").setCellEditor(new ButtonEditor(new JCheckBox(), tableModel, table));

        table.getColumn("Block").setCellRenderer(new ButtonRenderer());
        table.getColumn("Block").setCellEditor(new ButtonEditor(new JCheckBox(), tableModel, table));

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

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
    private DefaultTableModel tableModel;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, DefaultTableModel tableModel, JTable table) {
        super(checkBox);
        this.tableModel = tableModel;
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

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            int row = table.getSelectedRow();

            // Ensure that the row index is valid before performing actions
            if (row >= 0 && row < tableModel.getRowCount()) {
                String username = (String) table.getValueAt(row, 0);
                String friendId = usernameToIdMap.get(username);

                // Perform the action (remove or block)
                if (label.equals("Remove")) {
                    handleRemove(friendId, row);
                } else if (label.equals("Block")) {
                    handleBlock(friendId, row);
                }
            }
        }
        clicked = false;
        return label;
    }

    private void handleRemove(String friendId, int row) {
        try {
            FriendDataBase.getInstance("friends.json").removeFriend(user.getUserId(), friendId);
            // Safely remove the row from the table model
            if (row >= 0 && row < tableModel.getRowCount()) {
                tableModel.removeRow(row);
            }
            updateTable();
            JOptionPane.showMessageDialog(null, "Friend removed successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to remove friend: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBlock(String friendId, int row) {
        try {
            BlockedUserDataBase.getInstance("blocked_users.json").saveBlockedUser(user.getUserId(), friendId);
            FriendDataBase.getInstance("friends.json").removeFriend(user.getUserId(), friendId);
            // Safely remove the row from the table model
            if (row >= 0 && row < tableModel.getRowCount()) {
                tableModel.removeRow(row);
            }
            updateTable();
            JOptionPane.showMessageDialog(null, "User blocked successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to block user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateTable() {
    ArrayList<String> friendIds = new ArrayList<>(FriendDataBase.getInstance("friends.json").getFriends(user.getUserId()));

    // Clear previous rows
    tableModel.setRowCount(0);

    // Add the updated list of friends
    for (String friendId : friendIds) {
        String username = idToUsernameMap.get(friendId);
        if (username != null) {
            tableModel.addRow(new Object[]{username, "Remove"});
        }
    }

    // Refresh the table to reflect changes
    table.revalidate();
    table.repaint();
}

}





    private void setupBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose(); // Close the current window
            previousWindow.setVisible(true); // Show the previous window
        });
        add(backButton, BorderLayout.SOUTH);
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
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

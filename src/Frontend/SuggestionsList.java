/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.FriendRequestDataBase;
import Backend.SuggestedFriends;
import Backend.Suggestions;
import static Backend.Suggestions.getInstance;
import Backend.User;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author DELL
 */
public class SuggestionsList extends javax.swing.JFrame {

    /**
     * Creates new form SuggestionsList
     */
    
     private JFrame previousWindow;
     private User user;
    public SuggestionsList(JFrame previousWindow,User user) {
        this.previousWindow = previousWindow;
        this.user = user;
      initComponents();

        setTitle("Friend Suggestions");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        // Fetch suggested friends
        SuggestedFriends suggestions = new SuggestedFriends();
        ArrayList<String> suggestedFriends = suggestions.getSuggestedFriendNames(user.getUserId());

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Username", "Action"}, 0);

        // Add rows to the table model
        for (String friend : suggestedFriends) {
            tableModel.addRow(new Object[]{friend, "Add Request"});  
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
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), user.getUserId(), suggestedFriends));

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        // Add "Back" button to return to NewsFeed
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose(); // Close SuggestionsList
            previousWindow.setVisible(true); // Show NewsFeed
        });
        getContentPane().add(backButton, "South");

        setVisible(true);
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
        private String userId;
        private ArrayList<String> suggestedFriends;

        public ButtonEditor(JCheckBox checkBox, String userId, ArrayList<String> suggestedFriends) {
            super(checkBox);
            this.userId = userId;
            this.suggestedFriends = suggestedFriends;
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
                String friend = suggestedFriends.get(((JTable) button.getParent()).getSelectedRow());
                sendFriendRequest(userId, friend);
            }
            clicked = false;
            return label;
        }
        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        private void sendFriendRequest(String userId, String friendId) {
            // Replace with FriendManager logic to send a friend request
          try {
            // Call your addFriend method here
            FriendRequestDataBase.getInstance("friend_requests.json").addFriendRequest(userId, friendId);

            // Show success message
            JOptionPane.showMessageDialog(button, "Friend request sent to " + friendId);
        } catch (Exception e) {
            // Show error message if something goes wrong
            JOptionPane.showMessageDialog(button, "Failed to send friend request: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
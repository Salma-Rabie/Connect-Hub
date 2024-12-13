/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.Group;
import Backend.GroupDataBase;
import Backend.GroupManagement;
import Backend.ProfileManagement;
import Backend.SuggestedFriends;
import Backend.Suggestions;
import Backend.User;
import Backend.UserManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author alaae
 */
public class GroupSuggestions extends javax.swing.JFrame {

    /**
     * Creates new form GroupSuggestions
     */
    
   private User user; // Current user
    private JFrame previousWindow; // Reference to previous window
    private HashMap<String, Group> groupNameToGroupMap; // Map group names to Group objects
    private DefaultTableModel tableModel; // Table model for the JTable
private GroupManagement groupmanager;
    public GroupSuggestions(JFrame previousWindow, User user,GroupManagement groupmanager) {
        this.previousWindow = previousWindow;
        this.user = user;
        this.groupNameToGroupMap = new HashMap<>();
this.groupmanager=groupmanager;
        setTitle("Group Suggestions");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fetch group suggestions
        ArrayList<Group> suggestedGroups = GroupDataBase.getInstance("groups.json").getAllGroups();
        filterGroupSuggestions(suggestedGroups);

        // Initialize table
        setupTable();

        // Add back button
        setupBackButton();

        setVisible(true);
    }
    private void filterGroupSuggestions(ArrayList<Group> suggestedGroups) {
        // Filter groups: exclude groups the user is already a member of or has join requests
        for (Group group : suggestedGroups) {
            if (!group.getUsers().contains(user) ) {
                groupNameToGroupMap.put(group.getName(), group);
            }
        }
    }
    private void setupTable() {
        // Create table model
        tableModel = new DefaultTableModel(new Object[]{"Group Name", "Members", "Action"}, 0);

        // Populate table model with group suggestions
        for (Group group : groupNameToGroupMap.values()) {
            tableModel.addRow(new Object[]{group.getName(), group.getNumMembers(), "Join"});
        }

        // Create JTable
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only "Action" column is editable
            }
        };

        // Add button renderer and editor for the "Action" column
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), table));

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose(); // Close current window
            previousWindow.setVisible(true); // Show previous window
        });
        add(backButton, BorderLayout.SOUTH);
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
        private JTable table;

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

        @Override
       public Object getCellEditorValue() {
    if (clicked) {
        int row = table.getSelectedRow();
        if (row >= 0 && row < tableModel.getRowCount()) { // Ensure valid row index
            String groupName = (String) table.getValueAt(row, 0);
            Group group = groupNameToGroupMap.get(groupName);

            if (label.equals("Join")) {
                handleJoinRequest(group, row);
            }
        } else {
            JOptionPane.showMessageDialog(button, "No valid row selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    clicked = false;
    return label;
}

      private void handleJoinRequest(Group group, int row) {
    try {
        // Send join request
        //group.addJoinRequest(user.getUserId());
      
                GroupDataBase groupdatabase=GroupDataBase.getInstance("groups.json");
                group=groupmanager.joinGroup(group, user);
        groupdatabase.updateGroup(group);
        JOptionPane.showMessageDialog(button, "Joined to " + group.getName());

        // Remove the row from the table
        if (row >= 0 && row < tableModel.getRowCount()) { // Double-check row validity
            tableModel.removeRow(row);
        }

        // Check if the table is now empty
        if (tableModel.getRowCount() == 0) {
            // Clear the UI and show a message for no suggestions
            getContentPane().removeAll();
            JLabel noGroupsLabel = new JLabel("No more group suggestions available.");
            noGroupsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(noGroupsLabel, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(button, "Failed to send join request: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }}



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
    }}   
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GroupSuggestions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GroupSuggestions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GroupSuggestions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GroupSuggestions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new GroupSuggestions().setVisible(true);
//            }
//        });
//    }
//}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


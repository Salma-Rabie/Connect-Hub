/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Frontend;

import Backend.*;
import javax.swing.JFrame;

/**
 *
 * @author Salma Eid
 */
public class SuperJFrame extends javax.swing.JFrame {

    private final  UserManager manager;
    private JFrame previousWindow;

    public SuperJFrame(JFrame previousWindow,UserManager manager) {
        this.manager = manager;
        this.previousWindow = previousWindow;
    }

    public JFrame getPreviousWindow() {
        return previousWindow;
    }

    public void setPreviousWindow(JFrame previousWindow) {
        this.previousWindow = previousWindow;
    }

    public UserManager getManager() {
        return manager;
    }

   

    public void back() {
        previousWindow.setVisible(true);
    }

}
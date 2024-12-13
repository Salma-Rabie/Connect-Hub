/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Backend;

/**
 *
 * @author sarar
 */
public interface NotificationListener {
     static int counter=1;
    void onNotification(String userId, String type, String content);
}

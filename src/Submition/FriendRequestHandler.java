/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author sarar
 */
public class FriendRequestHandler implements NotificationListener {
    private static int counter=1;   
    @Override
    public void onNotification(String userId, String type, String content) {
        if (!type.equals("FriendRequest")) {
            return; // Ignore notifications not related to Friend Requests
        }
        Notification notification = new Notification.Builder()
                .id("Friend"+counter++)
                .userId(userId)
                .type(type)
                .content(content)
                .timestamp(new Date().toString())
                .build();
        NotificationManager.getInstance().addNotification(notification);
    }
}

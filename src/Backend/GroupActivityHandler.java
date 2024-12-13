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
class GroupActivityHandler implements NotificationListener {
    private static int counter=1;
    
    @Override
    public void onNotification(String userId, String type, String content) {
        if (!type.equals("GroupActivity")) {
            return; // Ignore notifications not related to Group Activities
        }
        Notification notification = new Notification.Builder()
                .id("Group"+counter++)
                .userId(userId)
                .type(type)
                .content(content)
                .timestamp(new Date().toString())
                .build();
        NotificationManager.getInstance().addNotification(notification);
    }
}

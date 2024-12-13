/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sarar
 */
public class NotificationService {
    private final List<NotificationListener> listeners = new ArrayList<>();

    public void registerListener(NotificationListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners(String userId, String type, String content) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onNotification(userId, type, content);
        }
    }
}

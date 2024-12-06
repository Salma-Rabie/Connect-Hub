/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class Status {
   
    private static Map<String, String> userStatuses = new HashMap<>();

    // Set user status (online/offline)
    public void setStatus(String userId, String status) {
        userStatuses.put(userId, status);
    }

    // Get user status
    public String getStatus(String userId) {
        return userStatuses.getOrDefault(userId, "Offline");
    }
}



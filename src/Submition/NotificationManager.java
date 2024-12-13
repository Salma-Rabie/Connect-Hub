/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author sarar
 */
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class NotificationManager {
    private static NotificationManager instance;
    private final List<Notification> notifications;

    private NotificationManager() {
        notifications = new ArrayList<>();
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
        saveToFile();
    }

    public List<Notification> getUnreadNotifications(String userId) {
        return notifications.stream()
                .filter(notification -> notification.getUserId().equals(userId) && !notification.isRead())
                .collect(Collectors.toList());
    }

    public void markAsRead(String notificationId) {
        notifications.stream()
                .filter(notification -> notification.getId().equals(notificationId))
                .forEach(Notification::markAsRead);
        saveToFile();
    }

    public void saveToFile() {
       try (FileWriter fileWriter = new FileWriter("notifications.json")) {
            JSONArray jsonArray = new JSONArray();

            for (Notification notification : notifications) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", notification.getId());
                jsonObject.put("userId", notification.getUserId());
                jsonObject.put("type", notification.getType());
                jsonObject.put("content", notification.getContent());
                jsonObject.put("timestamp", notification.getTimestamp());
                jsonObject.put("isRead", notification.isRead());

                // Add to the JSONArray (which will be wrapped in the file)
                jsonArray.put(jsonObject);
            }

            // Write the full JSON array to the file
            fileWriter.write(jsonArray.toString(2)); // Pretty print with indentation
            System.out.println("Notifications saved to file (as JSON array).");
        } catch (IOException e) {
            System.err.println("Error saving notifications: " + e.getMessage());
        }
    }

    public void loadFromFile() {
         File file = new File("notifications.json");
        if (!file.exists()) {
            System.out.println("No existing file found. Starting with an empty list.");
            return;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonContent.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonContent.toString());
            notifications.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Notification notification = new Notification.Builder()
                        .id(jsonObject.getString("id"))
                        .userId(jsonObject.getString("userId"))
                        .type(jsonObject.getString("type"))
                        .content(jsonObject.getString("content"))
                        .timestamp(jsonObject.getString("timestamp"))
                        .read(jsonObject.getBoolean("isRead"))
                        .build();
                notifications.add(notification);
            }
            System.out.println("Notifications loaded from file.");
        } catch (IOException | org.json.JSONException e) {
            System.err.println("Error loading notifications: " + e.getMessage());
        }
    }
}

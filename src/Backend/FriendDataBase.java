/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
public class FriendDataBase {
    private static FriendDataBase instance = null;
    private final String filePath;

    private FriendDataBase(String filePath) {
        this.filePath = filePath;
    }

    public static FriendDataBase getInstance(String filePath) {
        if (instance == null) {
            instance = new FriendDataBase(filePath);
        }
        return instance;
    }

    // Add a friend pair (userId, friendId) to the friend list
    
    public void addFriend(String userId, String friendId) {
    try {
        File file = new File(filePath);
        JSONObject database;
        if (file.exists()) {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            database = content.trim().isEmpty() ? new JSONObject() : new JSONObject(content);
        } else {
            database = new JSONObject();
        }

        if (!database.has(userId)) {
            database.put(userId, new JSONObject());
        }
        if (!database.has(friendId)) {
            database.put(friendId, new JSONObject());
        }

        database.getJSONObject(userId).put(friendId, true);
        database.getJSONObject(friendId).put(userId, true);  

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(database.toString(5));
            System.out.println("Friendship saved successfully.");
        }
    } catch (IOException e) {
        System.err.println("Error adding friendship: " + e.getMessage());
    }
}

    public void removeFriend(String userId, String friendId) {
    FriendDataBase.getInstance("friends.json").removeFriend(userId, friendId);
    
}
// Retrieve friends for a given userId
    public Set<String> getFriends(String userId) {
        Set<String> friends = new HashSet<>();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                if (!content.trim().isEmpty()) {
                    JSONObject database = new JSONObject(content);
                    if (database.has(userId)) {
                        JSONObject userFriends = database.getJSONObject(userId);
                        for (String friendId : userFriends.keySet()) {
                            friends.add(friendId);
                        }
                    }
                }
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error retrieving friends: " + e.getMessage());
        }
        return friends;
    }
}


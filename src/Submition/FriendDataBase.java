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
import org.json.JSONArray;
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
        // Load the existing JSON file or create a new one if it doesn't exist
        JSONObject database;
        File file = new File(filePath);

        if (file.exists()) {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            if (content.trim().isEmpty()) {
                database = new JSONObject();  // Empty file, start fresh
            } else {
                try {
                    database = new JSONObject(content);  // Parse existing content
                } catch (JSONException e) {
                    System.err.println("Invalid JSON format, creating a new JSON object.");
                    database = new JSONObject();  // Reset to empty if parsing fails
                }
            }
        } else {
            database = new JSONObject();  // File does not exist, create a new JSON object
        }

        // Check if the users already exist in the database, if not, create them with empty friend lists
        if (!database.has(userId)) {
            database.put(userId, new JSONObject().put("friends", new JSONArray()));
        }
        if (!database.has(friendId)) {
            database.put(friendId, new JSONObject().put("friends", new JSONArray()));
        }

        // Add friendId to userId's friends list only if it's not a request ID (doesn't start with "REQ")
        if (!friendId.startsWith("REQ") && !database.getJSONObject(userId).getJSONArray("friends").toList().contains(friendId)) {
            database.getJSONObject(userId).getJSONArray("friends").put(friendId);
        }

        // Add userId to friendId's friends list only if it's not a request ID (doesn't start with "REQ")
        if (!userId.startsWith("REQ") && !database.getJSONObject(friendId).getJSONArray("friends").toList().contains(userId)) {
            database.getJSONObject(friendId).getJSONArray("friends").put(userId);
        }

        // Save the updated database back to the file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(database.toString(5));  // Indented JSON output for better readability
            System.out.println("Friendship saved successfully.");
        }

    } catch (IOException e) {
        System.err.println("Error adding friendship: " + e.getMessage());
    }

}

// Helper method to check if a JSONArray contains a specific value
private boolean arrayContains(JSONArray array, String value) {
    for (int i = 0; i < array.length(); i++) {
        if (array.getString(i).equals(value)) {
            return true;
        }
    }
    return false;
}

   public void removeFriend(String userId, String friendId) {
    try {
        // Load the existing friends data
        File file = new File(filePath);
        if (file.exists()) {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            if (!content.trim().isEmpty()) {
                JSONObject database = new JSONObject(content);

                // Check if the user exists in the database
                if (database.has(userId)) {
                    JSONObject userFriends = database.getJSONObject(userId);
                    // Check if the user has a "friends" key
                    if (userFriends.has("friends")) {
                        JSONArray friendsArray = userFriends.getJSONArray("friends");

                        // Remove the friendId from the array
                        for (int i = 0; i < friendsArray.length(); i++) {
                            if (friendsArray.getString(i).equals(friendId)) {
                                friendsArray.remove(i);
                                break;
                            }
                        }

                        // Save the updated friends list back to the database
                        userFriends.put("friends", friendsArray);
                        database.put(userId, userFriends);
                    }
                }

                // Do the same for the friend (remove userId from their friends list)
                if (database.has(friendId)) {
                    JSONObject friendFriends = database.getJSONObject(friendId);
                    if (friendFriends.has("friends")) {
                        JSONArray friendFriendsArray = friendFriends.getJSONArray("friends");

                        for (int i = 0; i < friendFriendsArray.length(); i++) {
                            if (friendFriendsArray.getString(i).equals(userId)) {
                                friendFriendsArray.remove(i);
                                break;
                            }
                        }

                        friendFriends.put("friends", friendFriendsArray);
                        database.put(friendId, friendFriends);
                    }
                }

                // Save the updated database
                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(database.toString(4));  // Write the updated JSON data
                }
                System.out.println("Friend removed successfully.");
            }
        }
    } catch (IOException | JSONException e) {
        System.err.println("Error removing friend: " + e.getMessage());
    }
}

// Retrieve friends for a given userId
//    public Set<String> getFriends(String userId) {
//        Set<String> friends = new HashSet<>();
//        try {
//            File file = new File(filePath);
//            if (file.exists()) {
//                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
//                if (!content.trim().isEmpty()) {
//                    JSONObject database = new JSONObject(content);
//                    if (database.has(userId)) {
//                        JSONObject userFriends = database.getJSONObject(userId);
//                        for (String friendId : userFriends.keySet()) {
//                            friends.add(friendId);
//                        }
//                    }
//                }
//            }
//        } catch (IOException | JSONException e) {
//            System.err.println("Error retrieving friends: " + e.getMessage());
//        }
//        return friends;
//    } 
    public Set<String> getFriends(String userId) {
    Set<String> friends = new HashSet<>();
    try {
        File file = new File(filePath);
        if (file.exists()) {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            if (!content.trim().isEmpty()) {
                JSONObject database = new JSONObject(content);
                
                // Check if userId exists in the database
                if (database.has(userId)) {
                    JSONObject userFriends = database.getJSONObject(userId);
                    // Get the friends array for the user
                    if (userFriends.has("friends")) {
                        JSONArray friendsArray = userFriends.getJSONArray("friends");
                        for (int i = 0; i < friendsArray.length(); i++) {
                            friends.add(friendsArray.getString(i));
                        }
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


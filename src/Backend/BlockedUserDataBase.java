/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author DELL
 */
import org.json.JSONObject;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;

public class BlockedUserDataBase {

    private static BlockedUserDataBase instance = null;
    private final String filePath;

    // Singleton pattern to ensure only one instance of the database
    private BlockedUserDataBase(String filePath) {
        this.filePath = filePath;
    }

    public static BlockedUserDataBase getInstance(String filePath) {
        if (instance == null) {
            instance = new BlockedUserDataBase(filePath);
        }
        return instance;
    }

    // Save blocked user information (userId and blockedUserId)
    public void saveBlockedUser(String userId, String blockedUserId) {
        try {
            File file = new File(filePath);
            JSONObject database;

            // Load existing data or create a new database if file doesn't exist
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                if (content.trim().isEmpty()) {
                    database = new JSONObject(); // Start fresh if file is empty
                } else {
                    database = new JSONObject(content); // Parse existing data
                }
            } else {
                database = new JSONObject(); // If file doesn't exist, create a new one
            }

            // Create a unique key for the block relationship
            String key = userId + ":" + blockedUserId;

            // Add the block relationship to the database
            if (!database.has(key)) {
                database.put(key, new JSONObject() {{
                    put("userId", userId);
                    put("blockedUserId", blockedUserId);
                }});

                // Save the updated database back to the file
                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(database.toString(5)); // Indented JSON for readability
                    System.out.println("User " + userId + " has blocked " + blockedUserId);
                }
            } else {
                System.out.println(userId + " has already blocked " + blockedUserId);
            }

        } catch (IOException e) {
            System.err.println("Error saving blocked user: " + e.getMessage());
        }
    }

    // Check if a user has blocked another user
    public boolean isUserBlocked(String userId, String blockedUserId) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                if (!content.trim().isEmpty()) {
                    JSONObject database = new JSONObject(content);

                    // Check if the block relationship exists
                    String key = userId + ":" + blockedUserId;
            
                    
                    if (database.has(key)) {
                    return true;
                } else {
                    System.out.println("No block relationship found for key: " + key);
                }
                }
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error checking blocked status: " + e.getMessage());
        }
        return false; // Return false if no block exists
    }
//    public boolean Blocked(String userId, String blockedUserId)
//    {
//        
//    }

    // Get all blocked users for a given user
    public Set<String> getBlockedUsers(String userId) {
        Set<String> blockedUsers = new HashSet<>();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                if (!content.trim().isEmpty()) {
                    JSONObject database = new JSONObject(content);

                    // Iterate through all records and find the user's blocked users
                    for (String key : database.keySet()) {
                        if (key.startsWith(userId + ":")) {
                            String blockedUserId = database.getJSONObject(key).getString("blockedUserId");
                            blockedUsers.add(blockedUserId);
                        }
                    }
                }
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error retrieving blocked users: " + e.getMessage());
        }
        return blockedUsers; // Return set of blocked user IDs
    }
}



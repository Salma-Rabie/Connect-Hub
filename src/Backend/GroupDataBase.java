/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Salma Eid
 */
public class GroupDataBase {
    private static GroupDataBase instance = null;
    private final String filePath;
      private GroupDataBase(String filePath) {
        this.filePath = filePath;
    }

    //Singleton design pattern 
    public static GroupDataBase getInstance(String filePath) {
        if (instance == null) {
            instance = new GroupDataBase(filePath);
        }
        return instance;
    }
     public void saveGroup(Group group) {
        // Check if the user already exists in the database (assuming getUserById is defined elsewhere)
        if (getGroupbyName(group.getName()) != null) {
            return;  // User already exists, no need to save again
        }

        File defaultPhoto = new File("ss.jpg");  // Default photo in case the user doesn't have one

        // Create a new JSON object to hold the user's data
        JSONObject userJson = new JSONObject();

        // Add user-specific fields
        userJson.put("username", user.getUsername());
        userJson.put("email", user.getEmail());
        userJson.put("passwordHash", user.getPasswordHash());
        userJson.put("dateOfBirth", user.getDateOfBirth().toString());
        userJson.put("status", "online");

        // Bio field
        if (user.getBio() == null) {
            userJson.put("bio", "");  // Empty string for bio if null
        } else {
            userJson.put("bio", user.getBio());
        }

        // Profile photo path (default or user-defined)
        if (user.getProfilePhotoPath() == null) {
            userJson.put("profilePhotoPath", defaultPhoto.getPath());
        } else {
            userJson.put("profilePhotoPath", user.getProfilePhotoPath());
        }

       
        

        // Add stories array
        JSONArray storiesArray = new JSONArray();
        for (stories story : user.getStories()) {
            JSONObject storyJson = new JSONObject();
            storyJson.put("contentId", story.getContentId());
            storyJson.put("date", story.getDate().toString());
            storyJson.put("text", story.getText());
            storyJson.put("img", story.getImg());
            storyJson.put("userId", story.getUserId());
            storiesArray.put(storyJson);
        }
        userJson.put("stories", storiesArray);

        // Add posts array
        JSONArray postsArray = new JSONArray();
        for (posts post : user.getPosts()) {
            JSONObject postJson = new JSONObject();
            postJson.put("contentId", post.getContentId());
            postJson.put("date", post.getDate().toString());
            postJson.put("text", post.getText());
            postJson.put("img", post.getImg());
            postJson.put("userId", post.getUserId());
            postsArray.put(postJson);
        }
        userJson.put("posts", postsArray);

        // Now, the user data is directly under their userId key (e.g., "12345")
        JSONObject finalJson = new JSONObject();
        finalJson.put(String.valueOf(user.getUserId()), userJson);  // UserId as key, userJson as value

        try {
            // Load the existing JSON file or create a new one if it doesn't exist
            JSONObject database;
            File file = new File(filePath);

            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
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

            // Put the updated user data under the userId key in the database
            database.put(String.valueOf(user.getUserId()), userJson);

            // Write the updated database back to the file
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(database.toString(4));  // Indented JSON output for better readability
                System.out.println("User saved successfully.");
            }

        } catch (IOException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }
    
}

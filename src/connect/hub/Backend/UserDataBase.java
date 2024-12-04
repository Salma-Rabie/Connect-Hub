
package connect.hub.Backend;

import java.io.*;
import java.time.LocalDate;
import org.json.*;
public class UserDataBase {
 private static UserDataBase instance=null;
  private final String filePath;
    private UserDataBase(String filePath) {
        this.filePath = filePath;
    }
    //Singleton design pattern 
    public static UserDataBase getInstance(String filePath){
        if(instance==null) 
            instance=new UserDataBase(filePath);
        return instance;   
    }
    public void saveUser(User user) {
        if(getUserById(user.getUserId())!=null){
            return;
        }
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("username", user.getUsername());
    jsonObject.put("Email", user.getEmail());
    jsonObject.put("passwordHash", user.getPasswordHash());
    jsonObject.put("dateOfBirth", user.getDateOfBirth());
    jsonObject.put("status", user.getStatus());

    try {
        // Load the existing JSON file or create a new one if it doesn't exist
        JSONObject database;
        File file = new File(filePath);
        if (file.exists()) {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            if (content.trim().isEmpty()) {
                database = new JSONObject(); // Empty file, start fresh
            } else {
                try {
                    database = new JSONObject(content); // Parse existing content
                } catch (JSONException e) {
                    System.err.println("Invalid JSON format, creating a new JSON object.");
                    database = new JSONObject(); // Reset to empty if parsing fails
                }
            }
        } else {
            database = new JSONObject(); // File does not exist, create a new JSON object
        }

        // Use userid as the key
        database.put(user.getUserId(), jsonObject);

        // Write back to the file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(database.toString(5)); // Indented JSON output
            System.out.println("User saved successfully.");
        }

    } catch (IOException e) {
        System.err.println("Error saving user: " + e.getMessage());
    }
}
     public User getUserById(String userId) {
        try {
           
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                if (!content.trim().isEmpty()) {//if file is not empty ignoring white spaces 
                    JSONObject database = new JSONObject(content);    //parse content of file to a json object 
                    if (database.has(userId)) {
                        // Get the user's JSON object by userid
                        JSONObject jsonObject = database.getJSONObject(userId);

                        // Use the builder to create and return a User object
                        return new User.UserBuilder()
                                .userId(userId)
                                .email(jsonObject.getString("Email"))
                                .username(jsonObject.getString("username"))
                                .passwordHash(jsonObject.getString("passwordHash"))
                                .status(jsonObject.getString("status"))
                                .dateOfBirth(LocalDate.parse(jsonObject.getString("dateOfBirth")))
                                .build();
                    }
                }
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }
        // Return null if user not found or error occurs
        return null;
    }

    
}
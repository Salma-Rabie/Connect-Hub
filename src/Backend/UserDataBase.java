package Backend;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.*;

public class UserDataBase {

    private static UserDataBase instance = null;
    private final String filePath;

    private UserDataBase(String filePath) {
        this.filePath = filePath;
    }

    //Singleton design pattern 
    public static UserDataBase getInstance(String filePath) {
        if (instance == null) {
            instance = new UserDataBase(filePath);
        }
        return instance;
    }

    public void saveUser(User user) {
        if (getUserById(user.getUserId()) != null) {
            return;  // User already exists, no need to save again
        }
        File defultphoto = new File("ss.jpg");
        // Create a new JSON object to hold the user's data
        JSONObject jsonObject = new JSONObject();
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
                 jsonObject.put("stories", storiesArray);

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
                 jsonObject.put("posts", postsArray);
        jsonObject.put("username", user.getUsername());
        jsonObject.put("Email", user.getEmail());
        jsonObject.put("passwordHash", user.getPasswordHash());
        jsonObject.put("dateOfBirth", user.getDateOfBirth().toString());  // Convert LocalDate to String
        jsonObject.put("status", user.getStatus());
        if (user.getBio() == null) {
            jsonObject.put("bio", "");// Add bio field
        } else {
            jsonObject.put("bio", user.getBio());// Add bio field

        }
        if (user.getProfilePhotoPath() == null) {
            jsonObject.put("profilePhotoPath", defultphoto);
        } else {
            jsonObject.put("profilePhotoPath", user.getProfilePhotoPath());// Add profile photo path field
        }
        if (user.getCoverPhotoPath() == null) {
            jsonObject.put("coverPhotoPath", defultphoto);
        } else {
            jsonObject.put("coverPhotoPath", user.getCoverPhotoPath());// Add cover photo path field
        }

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

            // Use userId as the key and update the user's data in the database
            database.put(user.getUserId(), jsonObject);

            // Write the updated database back to the file
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(database.toString(8));  // Indented JSON output for better readability
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
JSONArray storiesArray = jsonObject.optJSONArray("stories");
                    ArrayList<stories> userStories = new ArrayList<>();
                    if (storiesArray != null) {
                        for (int i = 0; i < storiesArray.length(); i++) {
                            JSONObject storyJson = storiesArray.getJSONObject(i);
                            LocalDateTime date = LocalDateTime.parse(storyJson.getString("date"));
                            String text = storyJson.getString("text");
                            String img = storyJson.getString("img");
                            String userIdForStory = storyJson.getString("userId");
                            stories userStory = new stories(date, text, img, userIdForStory);
                            userStories.add(userStory);
                        }
                    }

                    // Extracting posts
                    JSONArray postsArray = jsonObject.optJSONArray("posts");
                    ArrayList<posts> userPosts = new ArrayList<>();
                    if (postsArray != null) {
                        for (int i = 0; i < postsArray.length(); i++) {
                            JSONObject postJson = postsArray.getJSONObject(i);
                            LocalDateTime date = LocalDateTime.parse(postJson.getString("date"));
                            String text = postJson.getString("text");
                            String img = postJson.getString("img");
                            String userIdForPost = postJson.getString("userId");
                            posts userPost = new posts(date, text, img, userIdForPost);
                            userPosts.add(userPost);}}
                        // Use the builder to create and return a User object
                        return new User.UserBuilder()
                                .userId(userId)
                                .email(jsonObject.getString("Email"))
                                .username(jsonObject.getString("username"))
                                .passwordHash(jsonObject.getString("passwordHash"))
                                .status(jsonObject.getString("status"))
                                .dateOfBirth(LocalDate.parse(jsonObject.getString("dateOfBirth")))
                                .bio(jsonObject.getString("bio"))
                                .profilePhotoPath(jsonObject.getString("profilePhotoPath"))
                                .coverPhotoPath(jsonObject.getString("coverPhotoPath"))
                                .setstories(userStories)
                                .setposts(userPosts)
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

    public void updateUser(User updatedUser) {
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
            if (database.has(updatedUser.getUserId())) {
  JSONArray storiesArray = new JSONArray();
                for (stories story : updatedUser.getStories()) {
                    JSONObject storyJson = new JSONObject();
                    storyJson.put("contentId", story.getContentId());
                    storyJson.put("date", story.getDate().toString());
                    storyJson.put("text", story.getText());
                    storyJson.put("img", story.getImg());
                    storyJson.put("userId", story.getUserId());
                    storiesArray.put(storyJson);
                }
                database.put("stories", storiesArray);

                JSONArray postsArray = new JSONArray();
                for (posts post : updatedUser.getPosts()) {
                    JSONObject postJson = new JSONObject();
                    postJson.put("contentId", post.getContentId());
                    postJson.put("date", post.getDate().toString());
                    postJson.put("text", post.getText());
                    postJson.put("img", post.getImg());
                    postJson.put("userId", post.getUserId());
                    postsArray.put(postJson);
                }
                database.put("posts", postsArray);
                // Update the existing user in the database
                database.put(updatedUser.getUserId(), new JSONObject() {
                    {
                        put("username", updatedUser.getUsername());
                        put("Email", updatedUser.getEmail());
                        put("passwordHash", updatedUser.getPasswordHash());
                        put("dateOfBirth", updatedUser.getDateOfBirth().toString());
                        put("status", updatedUser.getStatus());
                        put("bio", updatedUser.getBio());
                        put("profilePhotoPath", updatedUser.getProfilePhotoPath());
                        put("coverPhotoPath", updatedUser.getCoverPhotoPath());
                    }
                });

                // Write the updated database back to the file
                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(database.toString(8)); // Indented JSON output
                    //System.out.println("User updated successfully.");
                }
            }

        } catch (IOException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                if (!content.trim().isEmpty()) {
                    JSONObject database = new JSONObject(content);
                    for (String userId : database.keySet()) {
                        JSONObject jsonObject = database.getJSONObject(userId);
                        User user = new User.UserBuilder()
                                .userId(userId)
                                .email(jsonObject.getString("Email"))
                                .username(jsonObject.getString("username"))
                                .passwordHash(jsonObject.getString("passwordHash"))
                                .status(jsonObject.getString("status"))
                                .bio(jsonObject.getString("bio"))
                                .dateOfBirth(LocalDate.parse(jsonObject.getString("dateOfBirth")))
                                .profilePhotoPath(jsonObject.getString("profilePhotoPath"))
                                .coverPhotoPath(jsonObject.getString("coverPhotoPath"))
                                .build();

                        userList.add(user);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
        return userList;
    }

}

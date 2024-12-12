package Backend;

import java.io.*;
import java.nio.file.Files;
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
        // Check if the user already exists in the database (assuming getUserById is defined elsewhere)
        if (getUserById(user.getUserId()) != null) {
            System.out.println("user already exist");
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

        // Cover photo path (default or user-defined)
        if (user.getCoverPhotoPath() == null) {
            userJson.put("coverPhotoPath", defaultPhoto.getPath());
        } else {
            userJson.put("coverPhotoPath", user.getCoverPhotoPath());
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
        JSONArray groupsArray = new JSONArray();
        for (int i = 0; i < user.getGroups().size(); i++) {
            groupsArray.put(user.getGroups().get(i));
        }
        userJson.put("groups", groupsArray);

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
                //System.out.println("User saved successfully.");
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
            
            // Check if the file is empty
            if (content.trim().isEmpty()) {
                System.err.println("File is empty.");
                return null;
            }

            // Parse JSON content
            JSONObject database = new JSONObject(content);
            
            // Check if user exists
            if (database.has(userId)) {
                // Extract and return the user
                JSONObject jsonObject = database.getJSONObject(userId);

                // Extract stories
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

                // Extract posts
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
                        userPosts.add(userPost);
                    }
                }

                // Extract groups
                JSONArray groupsArray = jsonObject.optJSONArray("groups");
                ArrayList<String> groupNames = new ArrayList<>();
                if (groupsArray != null) {
                    for (int i = 0; i < groupsArray.length(); i++) {
                        groupNames.add(groupsArray.getString(i));
                    }
                }

                // Build and return the user
                return new User.UserBuilder()
                        .userId(userId)
                        .email(jsonObject.getString("email"))
                        .username(jsonObject.getString("username"))
                        .passwordHash(jsonObject.getString("passwordHash"))
                        .status(jsonObject.getString("status"))
                        .dateOfBirth(LocalDate.parse(jsonObject.getString("dateOfBirth")))
                        .bio(jsonObject.getString("bio"))
                        .profilePhotoPath(jsonObject.getString("profilePhotoPath"))
                        .coverPhotoPath(jsonObject.getString("coverPhotoPath"))
                        .setstories(userStories)
                        .setposts(userPosts)
                        .groups(groupNames)
                        .build();
            } 
        } else {
            System.err.println("File does not exist.");
        }
    } catch (IOException | JSONException e) {
        System.err.println("Error retrieving user: " + e.getMessage());
    }
    // Return null if the user is not found
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

            // Check if the user exists in the database (if not, we don't update)
            if (database.has(String.valueOf(updatedUser.getUserId()))) {
                JSONObject userJson = new JSONObject();

                // Add/update user-specific fields
                userJson.put("username", updatedUser.getUsername());
                userJson.put("email", updatedUser.getEmail());
                userJson.put("passwordHash", updatedUser.getPasswordHash());
                userJson.put("dateOfBirth", updatedUser.getDateOfBirth().toString());
                userJson.put("status", updatedUser.getStatus());
                userJson.put("bio", updatedUser.getBio() != null ? updatedUser.getBio() : "");
                userJson.put("profilePhotoPath", updatedUser.getProfilePhotoPath() != null ? updatedUser.getProfilePhotoPath() : "ss.jpg");
                userJson.put("coverPhotoPath", updatedUser.getCoverPhotoPath() != null ? updatedUser.getCoverPhotoPath() : "ss.jpg");

                // Add/update stories
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
                userJson.put("stories", storiesArray);

                // Add/update posts
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
                userJson.put("posts", postsArray);
                JSONArray groupsArray = new JSONArray();
                for (int i = 0; i < updatedUser.getGroups().size(); i++) {
                    groupsArray.put(updatedUser.getGroups().get(i));
                }
                userJson.put("groups", groupsArray);
                // Update the user in the database
                database.put(String.valueOf(updatedUser.getUserId()), userJson);

                // Write the updated database back to the file
                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(database.toString(4));  // Indented JSON output
                    System.out.println("User updated successfully.");
                }

            } else {
                System.err.println("User with ID " + updatedUser.getUserId() + " not found in the database.");
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

                        // Load stories
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

                        // Load posts
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
                                userPosts.add(userPost);
                            }
                        }
                        // Extracting groups
                        JSONArray groupsArray = jsonObject.optJSONArray("groups");
                        ArrayList<String> groupNames = new ArrayList<>();
                        if (groupsArray != null) {
                            for (int i = 0; i < groupsArray.length(); i++) {
                                groupNames.add(groupsArray.getString(i));
                            }
                        }
                        // Build user with posts and stories
                        User user = new User.UserBuilder()
                                .userId(userId)
                                .email(jsonObject.getString("email"))
                                .username(jsonObject.getString("username"))
                                .passwordHash(jsonObject.getString("passwordHash"))
                                .status(jsonObject.getString("status"))
                                .bio(jsonObject.getString("bio"))
                                .dateOfBirth(LocalDate.parse(jsonObject.getString("dateOfBirth")))
                                .profilePhotoPath(jsonObject.getString("profilePhotoPath"))
                                .coverPhotoPath(jsonObject.getString("coverPhotoPath"))
                                .setstories(userStories)
                                .setposts(userPosts)
                                 .groups(groupNames)
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

//    public ArrayList<User> getAllUsers() {
//        ArrayList<User> userList = new ArrayList<>();
//        try {
//            File file = new File(filePath);
//            if (file.exists()) {
//                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
//                if (!content.trim().isEmpty()) {
//                    JSONObject database = new JSONObject(content);
//                    for (String userId : database.keySet()) {
//                        JSONObject jsonObject = database.getJSONObject(userId);
//                        User user = new User.UserBuilder()
//                                .userId(userId)
//                                .email(jsonObject.getString("email"))
//                                .username(jsonObject.getString("username"))
//                                .passwordHash(jsonObject.getString("passwordHash"))
//                                .status(jsonObject.getString("status"))
//                                .bio(jsonObject.getString("bio"))
//                                .dateOfBirth(LocalDate.parse(jsonObject.getString("dateOfBirth")))
//                                .profilePhotoPath(jsonObject.getString("profilePhotoPath"))
//                                .coverPhotoPath(jsonObject.getString("coverPhotoPath"))
//                                .build();
//
//                        userList.add(user);
//                    }
//                }
//            }
//        } catch (IOException | JSONException e) {
//            System.err.println("Error retrieving users: " + e.getMessage());
//        }
//        return userList;
//    }
// //public User addGroup(String userId,Group group);
}

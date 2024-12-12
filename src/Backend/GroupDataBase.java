//
//package Backend;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.util.ArrayList;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// *
// * @author Salma Eid
// */
//public class GroupDataBase {
//    private static GroupDataBase instance = null;
//    private final String filePath;
//    private  ArrayList<Group> allGroups;
//      private GroupDataBase(String filePath) {
//        this.filePath = filePath;
//    }
//
//    public ArrayList<Group> getAllGroups() {
//        return allGroups;
//    }
//
//    //Singleton design pattern 
//    public static GroupDataBase getInstance(String filePath) {
//        if (instance == null) {
//            instance = new GroupDataBase(filePath);
//        }
//        return instance;
//    }
//    
//     public void saveGroup(Group group) {
//        JSONObject databaseJson;
//
//    // Load existing data from file, if any
//    try {
//        File file = new File(filePath);
//        if (file.exists()) {
//            String content = new String(Files.readAllBytes(file.toPath()));
//            databaseJson = new JSONObject(content);
//        } else {
//            databaseJson = new JSONObject(); // Start with an empty JSON object
//        }
//    } catch (IOException | JSONException e) {
//        e.printStackTrace();
//        return; // Handle file read error
//    }
//
//    // Check if the group already exists
//    if (databaseJson.has(group.getName())) {
//        //System.out.println("Group already exists in the database.");
//        return; // Avoid overwriting an existing group
//    }
//
//    // Create a JSON representation of the group
//    JSONObject groupJson = new JSONObject();
//    groupJson.put("name", group.getName());
//    groupJson.put("numMembers", group.getNumMembers());
//    groupJson.put("photoPath", group.getPhotoPath());
//    groupJson.put("description", group.getDescription());
//
//    if (group.getPrimaryAdmin() != null) {
//        groupJson.put("primaryAdmin", group.getPrimaryAdmin().getUserId());
//    }
//
//    JSONArray otherAdminsJson = new JSONArray();
//    for (User admin : group.getOtherAdmins()) {
//        otherAdminsJson.put(admin.getUserId());
//    }
//    groupJson.put("otherAdmins", otherAdminsJson);
//
//    JSONArray usersJson = new JSONArray();
//    for (User user : group.getUsers()) {
//        usersJson.put(user.getUserId());
//    }
//    groupJson.put("users", usersJson);
//
//    JSONArray postsJson = new JSONArray();
//    for (posts post : group.getPosts()) {
//        postsJson.put(post.toString()); // Replace with appropriate post serialization
//    }
//    groupJson.put("posts", postsJson);
//
//    // Add the group to the main database JSON object
//    databaseJson.put(group.getName(), groupJson);
//
//    // Write the updated JSON object back to the file
//    try (FileWriter writer = new FileWriter(filePath)) {
//        writer.write(databaseJson.toString(4)); // Pretty print with an indent of 4 spaces
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//     }
//    public Group updateGroup(Group updatedGroup) {
//    JSONObject databaseJson;
//
//    // Load existing data from file, if any
//    try {
//        File file = new File(filePath);
//        if (file.exists()) {
//            String content = new String(Files.readAllBytes(file.toPath()));
//            databaseJson = new JSONObject(content);
//        } else {
//            databaseJson = new JSONObject(); // Start with an empty JSON object
//        }
//    } catch (IOException | JSONException e) {
//        e.printStackTrace();
//        return null; // Handle file read error
//    }
//
//    // Check if the group exists in the database
//    if (!databaseJson.has(updatedGroup.getName())) {
//        //System.out.println("Group does not exist in the database.");
//        return null; // Group doesn't exist, so no update
//    }
//
//    // Create a JSON representation of the updated group
//    JSONObject updatedGroupJson = new JSONObject();
//    updatedGroupJson.put("name", updatedGroup.getName());
//    updatedGroupJson.put("numMembers", updatedGroup.getNumMembers());
//    updatedGroupJson.put("photoPath", updatedGroup.getPhotoPath());
//    updatedGroupJson.put("description", updatedGroup.getDescription());
//
//    if (updatedGroup.getPrimaryAdmin() != null) {
//        updatedGroupJson.put("primaryAdmin", updatedGroup.getPrimaryAdmin().getUsername());
//    }
//
//    JSONArray otherAdminsJson = new JSONArray();
//    for (User admin : updatedGroup.getOtherAdmins()) {
//        otherAdminsJson.put(admin.getUsername());
//    }
//    updatedGroupJson.put("otherAdmins", otherAdminsJson);
//
//    JSONArray usersJson = new JSONArray();
//    for (User user : updatedGroup.getUsers()) {
//        usersJson.put(user.getUsername());
//    }
//    updatedGroupJson.put("users", usersJson);
//
//    JSONArray postsJson = new JSONArray();
//    for (posts post : updatedGroup.getPosts()) {
//        postsJson.put(post.getContentId()); // Replace with appropriate post serialization
//    }
//    updatedGroupJson.put("posts", postsJson);
//
//    // Update the group in the main database JSON object
//    databaseJson.put(updatedGroup.getName(), updatedGroupJson);
//
//    // Write the updated JSON object back to the file
//    try (FileWriter writer = new FileWriter(filePath)) {
//        writer.write(databaseJson.toString(4)); // Pretty print with an indent of 4 spaces
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//
//    // Return the updated group
//    return updatedGroup;
//}
//    
//    
//    
//    public Group getGroupByName(String name) {
//    try {
//        File file = new File(filePath);
//        if (!file.exists()) {
//            return null; // No database file exists
//        }
//
//        // Read the entire file content
//        String content = new String(Files.readAllBytes(file.toPath()));
//        JSONObject databaseJson = new JSONObject(content);
//
//        // Check if the group exists
//        if (!databaseJson.has(name)) {
//            return null; // Group not found
//        }
//
//        // Retrieve the group's JSON object
//        JSONObject groupJson = databaseJson.getJSONObject(name);
//
//        // Retrieve UserDataBase instance to load users
//        UserDataBase userDB = UserDataBase.getInstance("user_data.json");
//
//        // Create a GroupBuilder to reconstruct the Group
//        Group.GroupBuilder groupBuilder = new Group.GroupBuilder()
//            .name(groupJson.getString("name"))
//            .description(groupJson.getString("description"))
//            .photoPath(groupJson.getString("photoPath"))
//            .numMembers(groupJson.getInt("numMembers"));
//
//        // Load primary admin
//        if (groupJson.has("primaryAdmin")) {
//            User primaryAdmin = userDB.getUserById(groupJson.getString("primaryAdmin"));
//            groupBuilder.primaryAdmin(primaryAdmin);
//        }
//
//        // Load other admins
//        if (groupJson.has("otherAdmins")) {
//            JSONArray otherAdminsJson = groupJson.getJSONArray("otherAdmins");
//            for (int i = 0; i < otherAdminsJson.length(); i++) {
//                User admin = userDB.getUserById(otherAdminsJson.getString(i));
//                groupBuilder.addOtherAdmin(admin);
//            }
//        }
//
//        // Load users
//        if (groupJson.has("users")) {
//            JSONArray usersJson = groupJson.getJSONArray("users");
//            for (int i = 0; i < usersJson.length(); i++) {
//                User user = userDB.getUserById(usersJson.getString(i));
//                groupBuilder.addUser(user);
//            }
//        }
//
//        // TODO: Load posts (you might need to implement a method to deserialize posts)
//        // For now, we'll leave the posts empty
//        
//        return groupBuilder.build();
//
//    } catch (IOException | JSONException e) {
//        e.printStackTrace();
//        return null;
//    }
//}
//}
package Backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GroupDataBase {
    private static GroupDataBase instance = null;
    private final String filePath;
    private ArrayList<Group> allGroups;

    private GroupDataBase(String filePath) {
        this.filePath = filePath;
        this.allGroups = new ArrayList<>();
        loadGroupsFromFile();
    }
// Singleton method remains the same
    public static GroupDataBase getInstance(String filePath) {
        if (instance == null) {
            instance = new GroupDataBase(filePath);
        }
        return instance;
    }
    // Load groups from file during initialization
    private void loadGroupsFromFile() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                JSONObject databaseJson = new JSONObject(content);
                
                // Iterate through group names (keys)
                for (String groupName : databaseJson.keySet()) {
                    JSONObject groupJson = databaseJson.getJSONObject(groupName);
                    
                    // Reconstruct the group using builder
                    Group.GroupBuilder builder = new Group.GroupBuilder()
                        .name(groupJson.getString("name"))
                        .description(groupJson.getString("description"))
                        .photoPath(groupJson.getString("photoPath"));

                    // Add users
                    JSONArray usersArray = groupJson.getJSONArray("users");
                    UserDataBase userDB = UserDataBase.getInstance("user_data.json");
                    for (int i = 0; i < usersArray.length(); i++) {
                        String userId = usersArray.getString(i);
                        User user = userDB.getUserById(userId);
                        if (user != null) {
                            builder.addUser(user);
                        }
                    }

                    // Set primary admin
                    String primaryAdminId = groupJson.getString("primaryAdmin");
                    User primaryAdmin = userDB.getUserById(primaryAdminId);
                    builder.primaryAdmin(primaryAdmin);

                    // Add other admins
                    JSONArray otherAdminsArray = groupJson.getJSONArray("otherAdmins");
                    for (int i = 0; i < otherAdminsArray.length(); i++) {
                        String adminId = otherAdminsArray.getString(i);
                        User admin = userDB.getUserById(adminId);
                        if (admin != null) {
                            builder.addOtherAdmin(admin);
                        }
                    }

                    // Add posts
                    JSONArray postsArray = groupJson.getJSONArray("posts");
                    for (int i = 0; i < postsArray.length(); i++) {
                        JSONObject postJson = postsArray.getJSONObject(i);
                        posts post = new posts(
                            LocalDateTime.parse(postJson.getString("date")),
                            postJson.getString("text"),
                            postJson.getString("img"),
                            postJson.getString("userId")
                        );
                        builder.addPost(post);
                    }

                    // Build and add to allGroups
                    Group group = builder.build();
                    allGroups.add(group);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    

    // Modify saveGroup to use group name as key
    public void saveGroup(Group group) {
        try {
            // Read existing JSON or create new
            JSONObject databaseJson;
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                databaseJson = new JSONObject(content);
            } else {
                databaseJson = new JSONObject();
            }

            // Check if group already exists
            if (databaseJson.has(group.getName())) {
                return; // Avoid overwriting
            }
            allGroups.add(group);
            // Create group JSON object
            JSONObject groupJson = new JSONObject();
            groupJson.put("name", group.getName());
            groupJson.put("description", group.getDescription());
            groupJson.put("photoPath", group.getPhotoPath());
            groupJson.put("numMembers", group.getNumMembers());

            // Primary admin
            if (group.getPrimaryAdmin() != null) {
                groupJson.put("primaryAdmin", group.getPrimaryAdmin().getUserId());
            }

            // Other admins
            JSONArray otherAdminsArray = new JSONArray();
            for (User admin : group.getOtherAdmins()) {
                otherAdminsArray.put(admin.getUserId());
            }
            groupJson.put("otherAdmins", otherAdminsArray);

            // Users
            JSONArray usersArray = new JSONArray();
            for (User user : group.getUsers()) {
                usersArray.put(user.getUserId());
            }
            groupJson.put("users", usersArray);

            // Posts
            JSONArray postsArray = new JSONArray();
            for (posts post : group.getPosts()) {
                JSONObject postJson = new JSONObject();
                postJson.put("date", post.getDate().toString());
                postJson.put("text", post.getText());
                postJson.put("img", post.getImg());
                postJson.put("userId", post.getUserId());
                postJson.put("contentId", post.getContentId()); // Assuming posts has a contentId method
                postsArray.put(postJson);
            }
            groupJson.put("posts", postsArray);

            // Add to database JSON with group name as key
            databaseJson.put(group.getName(), groupJson);

            // Write back to file
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(databaseJson.toString(4));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    // Modify updateGroup to use group name as key
    public Group updateGroup(Group updatedGroup) {
        try {
            // Read existing JSON
            JSONObject databaseJson;
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                databaseJson = new JSONObject(content);
            } else {
                databaseJson = new JSONObject();
            }

            // Check if group exists
            if (!databaseJson.has(updatedGroup.getName())) {
                return null;
            }
            // Create updated group JSON object (same as saveGroup method)
            JSONObject groupJson = new JSONObject();
            groupJson.put("name", updatedGroup.getName());
            groupJson.put("description", updatedGroup.getDescription());
            groupJson.put("photoPath", updatedGroup.getPhotoPath());
            groupJson.put("numMembers", updatedGroup.getNumMembers());

            // Primary admin
            if (updatedGroup.getPrimaryAdmin() != null) {
                groupJson.put("primaryAdmin", updatedGroup.getPrimaryAdmin().getUserId());
            }

            // Other admins
            JSONArray otherAdminsArray = new JSONArray();
            for (User admin : updatedGroup.getOtherAdmins()) {
                otherAdminsArray.put(admin.getUserId());
            }
            groupJson.put("otherAdmins", otherAdminsArray);

            // Users
            JSONArray usersArray = new JSONArray();
            for (User user : updatedGroup.getUsers()) {
                usersArray.put(user.getUserId());
            }
            groupJson.put("users", usersArray);

            // Posts
            JSONArray postsArray = new JSONArray();
            for (posts post : updatedGroup.getPosts()) {
                JSONObject postJson = new JSONObject();
                postJson.put("date", post.getDate().toString());
                postJson.put("text", post.getText());
                postJson.put("img", post.getImg());
                postJson.put("userId", post.getUserId());
                postJson.put("contentId", post.getContentId());
                postsArray.put(postJson);
            }
            groupJson.put("posts", postsArray);

            // Update in database JSON
            databaseJson.put(updatedGroup.getName(), groupJson);

            // Write back to file
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(databaseJson.toString(4));
            }

            // Update local cache
            updateLocalGroupCache(updatedGroup);

            return updatedGroup;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to get group by name
    public Group getGroupByName(String name) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                JSONObject databaseJson = new JSONObject(content);

                // Check if group exists
                if (!databaseJson.has(name)) {
                    return null;
                }

                JSONObject groupJson = databaseJson.getJSONObject(name);

                // Reconstruct the group using builder
                Group.GroupBuilder builder = new Group.GroupBuilder()
                    .name(groupJson.getString("name"))
                    .description(groupJson.getString("description"))
                    .photoPath(groupJson.getString("photoPath"));

                // Add users
                JSONArray usersArray = groupJson.getJSONArray("users");
                UserDataBase userDB = UserDataBase.getInstance("user_data.json");
                for (int i = 0; i < usersArray.length(); i++) {
                    String userId = usersArray.getString(i);
                    User user = userDB.getUserById(userId);
                    if (user != null) {
                        builder.addUser(user);
                    }
                }

                // Set primary admin
                String primaryAdminId = groupJson.getString("primaryAdmin");
                User primaryAdmin = userDB.getUserById(primaryAdminId);
                builder.primaryAdmin(primaryAdmin);

                // Add other admins
                JSONArray otherAdminsArray = groupJson.getJSONArray("otherAdmins");
                for (int i = 0; i < otherAdminsArray.length(); i++) {
                    String adminId = otherAdminsArray.getString(i);
                    User admin = userDB.getUserById(adminId);
                    if (admin != null) {
                        builder.addOtherAdmin(admin);
                    }
                }

                // Add posts
                JSONArray postsArray = groupJson.getJSONArray("posts");
                for (int i = 0; i < postsArray.length(); i++) {
                    JSONObject postJson = postsArray.getJSONObject(i);
                    posts post = new posts(
                        LocalDateTime.parse(postJson.getString("date")),
                        postJson.getString("text"),
                        postJson.getString("img"),
                        postJson.getString("userId")
                    );
                    builder.addPost(post);
                }

                // Build and return group
                return builder.build();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to update local group cache
    private void updateLocalGroupCache(Group updatedGroup) {
        // Remove existing group if present
        allGroups.removeIf(g -> g.getName().equals(updatedGroup.getName()));
        
        // Add updated group
        allGroups.add(updatedGroup);
    }

    // Getter for all groups
    public ArrayList<Group> getAllGroups() {
        return new ArrayList<>(allGroups); // Return a copy to prevent direct modification
    }
    public boolean deleteGroup(Group group, User primaryAdmin) {
    // Check if the user is the primary admin
    if (!group.getPrimaryAdmin().equals(primaryAdmin)) {
        return false;
    }

    try {
        File file = new File(filePath);
        if (!file.exists()) {
            return false; // No database file exists
        }

        // Read the existing database
        String content = new String(Files.readAllBytes(file.toPath()));
        JSONObject databaseJson = new JSONObject(content);

        // Remove the group from the database
        if (!databaseJson.has(group.getName())) {
            return false; // Group not found
        }
        databaseJson.remove(group.getName());

        // Write the updated database back to the file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(databaseJson.toString(4)); // Pretty print with an indent of 4 spaces
        }

        return true;
    } catch (IOException | JSONException e) {
        e.printStackTrace();
        return false;
    }
}
}

package Backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
    private  ArrayList<Group> allGroups;
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
        JSONObject databaseJson;

    // Load existing data from file, if any
    try {
        File file = new File(filePath);
        if (file.exists()) {
            String content = new String(Files.readAllBytes(file.toPath()));
            databaseJson = new JSONObject(content);
        } else {
            databaseJson = new JSONObject(); // Start with an empty JSON object
        }
    } catch (IOException | JSONException e) {
        e.printStackTrace();
        return; // Handle file read error
    }

    // Check if the group already exists
    if (databaseJson.has(group.getName())) {
        //System.out.println("Group already exists in the database.");
        return; // Avoid overwriting an existing group
    }

    // Create a JSON representation of the group
    JSONObject groupJson = new JSONObject();
    groupJson.put("name", group.getName());
    groupJson.put("numMembers", group.getNumMembers());
    groupJson.put("photoPath", group.getPhotoPath());
    groupJson.put("description", group.getDescription());

    if (group.getPrimaryAdmin() != null) {
        groupJson.put("primaryAdmin", group.getPrimaryAdmin().getUsername());
    }

    JSONArray otherAdminsJson = new JSONArray();
    for (User admin : group.getOtherAdmins()) {
        otherAdminsJson.put(admin.getUsername());
    }
    groupJson.put("otherAdmins", otherAdminsJson);

    JSONArray usersJson = new JSONArray();
    for (User user : group.getUsers()) {
        usersJson.put(user.getUsername());
    }
    groupJson.put("users", usersJson);

    JSONArray postsJson = new JSONArray();
    for (posts post : group.getPosts()) {
        postsJson.put(post.toString()); // Replace with appropriate post serialization
    }
    groupJson.put("posts", postsJson);

    // Add the group to the main database JSON object
    databaseJson.put(group.getName(), groupJson);

    // Write the updated JSON object back to the file
    try (FileWriter writer = new FileWriter(filePath)) {
        writer.write(databaseJson.toString(4)); // Pretty print with an indent of 4 spaces
    } catch (IOException e) {
        e.printStackTrace();
    }
     }
    public Group updateGroup(Group updatedGroup) {
    JSONObject databaseJson;

    // Load existing data from file, if any
    try {
        File file = new File(filePath);
        if (file.exists()) {
            String content = new String(Files.readAllBytes(file.toPath()));
            databaseJson = new JSONObject(content);
        } else {
            databaseJson = new JSONObject(); // Start with an empty JSON object
        }
    } catch (IOException | JSONException e) {
        e.printStackTrace();
        return null; // Handle file read error
    }

    // Check if the group exists in the database
    if (!databaseJson.has(updatedGroup.getName())) {
        //System.out.println("Group does not exist in the database.");
        return null; // Group doesn't exist, so no update
    }

    // Create a JSON representation of the updated group
    JSONObject updatedGroupJson = new JSONObject();
    updatedGroupJson.put("name", updatedGroup.getName());
    updatedGroupJson.put("numMembers", updatedGroup.getNumMembers());
    updatedGroupJson.put("photoPath", updatedGroup.getPhotoPath());
    updatedGroupJson.put("description", updatedGroup.getDescription());

    if (updatedGroup.getPrimaryAdmin() != null) {
        updatedGroupJson.put("primaryAdmin", updatedGroup.getPrimaryAdmin().getUsername());
    }

    JSONArray otherAdminsJson = new JSONArray();
    for (User admin : updatedGroup.getOtherAdmins()) {
        otherAdminsJson.put(admin.getUsername());
    }
    updatedGroupJson.put("otherAdmins", otherAdminsJson);

    JSONArray usersJson = new JSONArray();
    for (User user : updatedGroup.getUsers()) {
        usersJson.put(user.getUsername());
    }
    updatedGroupJson.put("users", usersJson);

    JSONArray postsJson = new JSONArray();
    for (posts post : updatedGroup.getPosts()) {
        postsJson.put(post.toString()); // Replace with appropriate post serialization
    }
    updatedGroupJson.put("posts", postsJson);

    // Update the group in the main database JSON object
    databaseJson.put(updatedGroup.getName(), updatedGroupJson);

    // Write the updated JSON object back to the file
    try (FileWriter writer = new FileWriter(filePath)) {
        writer.write(databaseJson.toString(4)); // Pretty print with an indent of 4 spaces
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Return the updated group
    return updatedGroup;
}
    
}

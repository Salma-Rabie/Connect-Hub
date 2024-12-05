/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;
import java.time.LocalDate;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
public class FriendRequestDataBase {
    private static FriendRequestDataBase instance = null;
    private final String filePath;

    private FriendRequestDataBase(String filePath) {
        this.filePath = filePath;
    }

    public static FriendRequestDataBase getInstance(String filePath) {
        if (instance == null) {
            instance = new FriendRequestDataBase(filePath);
        }
        return instance;
    }

    // Save friend request to file
    public void saveFriendRequest(FriendRequest friendRequest) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("senderId", friendRequest.getSenderId());
        jsonObject.put("receiverId", friendRequest.getReceiverId());
        jsonObject.put("status", friendRequest.getStatus());
        jsonObject.put("requestDate", friendRequest.getRequestDate().toString());  // Convert LocalDate to String

        try {
            File file = new File(filePath);
            JSONObject database;
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                if (content.trim().isEmpty()) {
                    database = new JSONObject();
                } else {
                    database = new JSONObject(content);
                }
            } else {
                database = new JSONObject();
            }

            database.put(friendRequest.getRequestId(), jsonObject);

            // Save to file
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(database.toString(5));  
                System.out.println("Friend request saved successfully.");
            }

        } catch (IOException e) {
            System.err.println("Error saving friend request: " + e.getMessage());
        }
    }
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

    public FriendRequest getFriendRequestById(String requestId) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                if (!content.trim().isEmpty()) {
                    JSONObject database = new JSONObject(content);

                    // Check if the requestId exists in the database
                    if (database.has(requestId)) {
                        JSONObject jsonObject = database.getJSONObject(requestId);

                        // Construct and return the FriendRequest object
                        return new FriendRequest(
                                requestId,
                                jsonObject.getString("senderId"),
                                jsonObject.getString("receiverId"),
                                jsonObject.getString("status"),
                                LocalDate.parse(jsonObject.getString("requestDate"))
                        );
                    }
                }
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error retrieving friend request: " + e.getMessage());
        }
        return null;  
    }
   public void acceptFriendRequest(String requestId) {
    FriendRequest request = getFriendRequestById(requestId);  
    if (request != null) {
        request.setStatus("Accepted");

        saveFriendRequest(request);  // Save the updated object to the file
    
    }
}

public void declineFriendRequest(String requestId) {
    FriendRequest request = FriendRequestDataBase.getInstance("friend_requests.json")
                            .getFriendRequestById(requestId);
    if (request != null) {
        request.setStatus("Declined");
        saveFriendRequest(request);
    }
}

}

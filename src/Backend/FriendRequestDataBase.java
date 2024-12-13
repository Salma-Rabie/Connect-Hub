
package Backend;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.*;
import org.json.JSONException;

public class FriendRequestDataBase {
    private static FriendRequestDataBase instance = null;
    private final String filePath;
    private final Map<Integer, String> requestIndexMap; // Index to request ID mapping
    private final List<FriendRequestClass> requestsList; // List of all pending requests
    private static int requestCounter=0; // Counter for generating unique request IDs

    private FriendRequestDataBase(String filePath) {
        this.filePath = filePath;
        this.requestIndexMap = new HashMap<>();
        this.requestsList = new ArrayList<>();
        this.requestCounter = 1; // Start the counter at 1
        loadRequestsFromFile();
    }

    public static FriendRequestDataBase getInstance(String filePath) {
        if (instance == null) {
            instance = new FriendRequestDataBase(filePath);
        }
        return instance;
    }

    // Generate a unique request ID
    private String generateRequestId() {
        return "REQ" + requestCounter++;
    }

private void loadRequestsFromFile() {
    try {
        File file = new File(filePath);
        if (file.exists()) {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            if (content.trim().isEmpty()) return;

            JSONArray requestsArray = new JSONArray(content);

            for (int i = 0; i < requestsArray.length(); i++) {
                JSONObject requestJSON = requestsArray.getJSONObject(i);

                // Read fields from JSON with default for "status"
                String requestId = requestJSON.getString("requestId");
                String senderId = requestJSON.getString("senderId");
                String receiverId = requestJSON.getString("receiverId");
                String status = requestJSON.optString("status", "Pending");

                FriendRequestClass request = new FriendRequestClass(requestId, senderId, receiverId, status);

                // Avoid duplicate entries
                if (!requestsList.stream().anyMatch(r -> r.getRequestId().equals(requestId))) {
                    requestsList.add(request);
                    requestIndexMap.put(requestsList.size() - 1, requestId);
                }
            }

            // Update the counter to avoid ID conflicts
            requestCounter = requestsList.size() + 1;
        }
    } catch (IOException | JSONException e) {
        System.err.println("Error loading friend requests: " + e.getMessage());
    }
}



   
 private void saveRequestsToFile() {
    try {
        JSONArray requestsArray = new JSONArray();
        for (FriendRequestClass request : requestsList) {
            JSONObject requestJSON = new JSONObject();
            requestJSON.put("requestId", request.getRequestId());
            requestJSON.put("senderId", request.getSenderId());
            requestJSON.put("receiverId", request.getReceiverId());
            requestJSON.put("status", request.getStatus()); // Include status
            requestsArray.put(requestJSON);
        }
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(requestsArray.toString(4)); // Indented for readability
        }
    } catch (IOException e) {
        System.err.println("Error saving friend requests: " + e.getMessage());
    }
}


    // Add a new friend request
//    public void addFriendRequest(String senderId, String receiverId) throws IOException {
//        System.out.println("(inside addFriendRequest methode)addFriendRequest called with: senderId = " + senderId + ", receiverId = " + receiverId);
//        for (FriendRequestClass request : requestsList) {
//        if (request.getSenderId().equals(senderId) && request.getReceiverId().equals(receiverId)) {
//            System.out.println("Friend request already exists.");
//            return; // Prevent adding a duplicate request
//        }
//    } 
    // If no duplicates, proceed to add the new request
//    String requestId = generateRequestId();
//    FriendRequestClass newRequest = new FriendRequestClass(requestId, senderId, receiverId);
//    requestsList.add(newRequest);
//
//    // Update the index map
//    requestIndexMap.put(requestsList.size() - 1, requestId);
//
//    // Save the updated requests to the file
//    saveRequestsToFile();
//    }
public void addFriendRequest(String senderId, String receiverId) {
    System.out.println("(addFriendRequest) Called with senderId = " + senderId + ", receiverId = " + receiverId);

    // Check for duplicates
    for (FriendRequestClass request : requestsList) {
        if (request.getSenderId().equals(senderId) && request.getReceiverId().equals(receiverId)) {
            System.out.println("Friend request already exists.");
            return;
        }
    }

    // Create a new request
    String requestId = generateRequestId();
    FriendRequestClass newRequest = new FriendRequestClass(requestId, senderId, receiverId, "Pending");
    requestsList.add(newRequest);

    // Save the updated list
    saveRequestsToFile();
}

    // Accept a friend request
    public void acceptFriendRequest(String requestId) throws IOException {
        for (int i = 0; i < requestsList.size(); i++) {
            if (requestsList.get(i).getRequestId().equals(requestId)) {
                FriendRequestClass request = requestsList.get(i);

                // Add the friendship in FriendDataBase
                FriendDataBase.getInstance("friends.json").addFriend(request.getSenderId(), request.getReceiverId());
                // Remove the request and update index map
                requestsList.remove(i);
                rebuildIndexMap();
                saveRequestsToFile();
                return;
            }
        }
    }

    // Decline a friend request
    public void declineFriendRequest(String requestId) throws IOException {
    System.out.println("Decline Request Called for requestId = " + requestId);

    boolean removed = requestsList.removeIf(request -> request.getRequestId().equals(requestId));
    if (removed) {
        System.out.println("Request removed successfully.");
        rebuildIndexMap();
        saveRequestsToFile();
    } else {
        System.out.println("No matching request found to decline.");
    }
}


    // Rebuild the index map after a modification
    private void rebuildIndexMap() {
        requestIndexMap.clear();
        for (int i = 0; i < requestsList.size(); i++) {
            requestIndexMap.put(i, requestsList.get(i).getRequestId());
        }
    }

    // Get a request by its index
    public FriendRequestClass getRequestByIndex(int index) {
        if (index >= 0 && index < requestsList.size()) {
            return requestsList.get(index);
        }
        return null;
    }
    public ArrayList<FriendRequestClass> getRequestsReceivedByUserId(String userId) {
    ArrayList<FriendRequestClass> result = new ArrayList<>();
    for (FriendRequestClass request : requestsList) {
        if (request.getReceiverId().equals(userId)) {
            result.add(request);
        }
    }
    return result;
}
public ArrayList<String> getPendingRequests(String userId) {
    ArrayList<String> pendingRequests = new ArrayList<>();

    try {
        File file = new File(filePath);

        // Initialize file if it doesn't exist
        if (!file.exists() || file.length() == 0) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]"); // Initialize as empty JSON array
            }
        }

        // Read file content
        String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
        JSONArray requestsArray = new JSONArray(content);

        for (int i = 0; i < requestsArray.length(); i++) {
            JSONObject request = requestsArray.getJSONObject(i);

            // Check if this request is pending and involves this user
            String status = request.optString("status", "Pending");
            if (status.equalsIgnoreCase("Pending") &&
                (request.getString("receiverId").equals(userId) || request.getString("senderId").equals(userId))) {
                pendingRequests.add(request.getString("receiverId"));
            }
        }
    } catch (IOException | JSONException e) {
        System.err.println("Error retrieving pending requests: " + e.getMessage());
    }

    return pendingRequests;
}


}


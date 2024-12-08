/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author sarar
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import Backend.FriendDataBase;
import Backend.FriendRequestClass;
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

    // Load requests from the file into the list and index map
    private void loadRequestsFromFile() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                JSONArray requestsArray = new JSONArray(content);

                for (int i = 0; i < requestsArray.length(); i++) {
                    JSONObject requestJSON = requestsArray.getJSONObject(i);
                    FriendRequestClass request = new FriendRequestClass(
                            requestJSON.getString("requestId"),
                            requestJSON.getString("senderId"),
                            requestJSON.getString("receiverId")
                    );
                    requestsList.add(request);
                    requestIndexMap.put(i, request.getRequestId());
                }

                // Update request counter to avoid ID conflicts
                if (!requestsList.isEmpty()) {
                    requestCounter = requestsList.size() + 1;
                }
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error loading friend requests: " + e.getMessage());
        }
    }

    // Save the list back to the file
    private void saveRequestsToFile() {
        try {
            JSONArray requestsArray = new JSONArray();
            for (FriendRequestClass request : requestsList) {
                JSONObject requestJSON = new JSONObject();
                requestJSON.put("requestId", request.getRequestId());
                requestJSON.put("senderId", request.getSenderId());
                requestJSON.put("receiverId", request.getReceiverId());
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
    public void addFriendRequest(String senderId, String receiverId) {
        String requestId = generateRequestId();
        FriendRequestClass newRequest = new FriendRequestClass(requestId, senderId, receiverId);
        requestsList.add(newRequest);

        // Update the index map
        requestIndexMap.put(requestsList.size() - 1, requestId);
        saveRequestsToFile();
    }

    // Accept a friend request
    public void acceptFriendRequest(String requestId) {
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
    public void declineFriendRequest(String requestId) {
        for (int i = 0; i < requestsList.size(); i++) {
            if (requestsList.get(i).getRequestId().equals(requestId)) {
                // Remove the request and update index map
                requestsList.remove(i);
                rebuildIndexMap();
                saveRequestsToFile();
                return;
            }
        }
    }

    // Rebuild the index map after a modification
    private void rebuildIndexMap() {
        requestIndexMap.clear();
        for (int i = 0; i < requestsList.size(); i++) {
            requestIndexMap.put(i, requestsList.get(i).getRequestId());
        }
    }

    // Get all pending requests for a specific receiver
    public List<FriendRequestClass> getPendingRequests(String receiverId) {
        List<FriendRequestClass> result = new ArrayList<>();
        for (FriendRequestClass request : requestsList) {
            if (request.getReceiverId().equals(receiverId)) {
                result.add(request);
            }
        }
        return result;
    }

    // Get a request by its index
    public FriendRequestClass getRequestByIndex(int index) {
        if (index >= 0 && index < requestsList.size()) {
            return requestsList.get(index);
        }
        return null;
    }
    public List<FriendRequestClass> getRequestsReceivedByUserId(String userId) {
    List<FriendRequestClass> result = new ArrayList<>();
    for (FriendRequestClass request : requestsList) {
        if (request.getReceiverId().equals(userId)) {
            result.add(request);
        }
    }
    return result;
}
}
//package Backend;
//import java.time.LocalDate;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// *
// * @author DELL
// */
//public class FriendRequestDataBase {
//    private static FriendRequestDataBase instance = null;
//    private final String filePath;
//
//    private FriendRequestDataBase(String filePath) {
//        this.filePath = filePath;
//    }
//
//    public static FriendRequestDataBase getInstance(String filePath) {
//        if (instance == null) {
//            instance = new FriendRequestDataBase(filePath);
//        }
//        return instance;
//    }
//
//    // Save friend request to file
//    public void saveFriendRequest(FriendRequestClass friendRequest) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("senderId", friendRequest.getSenderId());
//        jsonObject.put("receiverId", friendRequest.getReceiverId());
//        jsonObject.put("status","pending");
//        jsonObject.put("requestDate", friendRequest.getRequestDate().toString());  // Convert LocalDate to String
//
//        try {
//            File file = new File(filePath);
//            JSONObject database;
//            if (file.exists()) {
//                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
//                if (content.trim().isEmpty()) {
//                    database = new JSONObject();
//                } else {
//                    database = new JSONObject(content);
//                }
//            } else {
//                database = new JSONObject();
//            }
//
//            database.put(friendRequest.getRequestId(), jsonObject);
//
//            // Save to file
//            try (FileWriter writer = new FileWriter(filePath)) {
//                writer.write(database.toString(5));  
//                System.out.println("Friend request saved successfully.");
//            }
//
//        } catch (IOException e) {
//            System.err.println("Error saving friend request: " + e.getMessage());
//        }
//    }
//    private void loadRequestsFromFile() {
//        try {
//            File file = new File(filePath);
//            if (file.exists()) {
//                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
//                JSONArray requestsArray = new JSONArray(content);
//
//                for (int i = 0; i < requestsArray.length(); i++) {
//                    JSONObject requestJSON = requestsArray.getJSONObject(i);
//                    FriendRequestClass request = new FriendRequestClass(
//                            requestJSON.getString("requestId"),
//                            requestJSON.getString("senderId"),
//                            requestJSON.getString("receiverId")
//                    );
//                    requestsList.add(request);
//                    requestIndexMap.put(i, request.getRequestId());
//                }
//
//                // Update request counter to avoid ID conflicts
//                if (!requestsList.isEmpty()) {
//                    requestCounter = requestsList.size() + 1;
//                }
//            }
//        } catch (IOException | JSONException e) {
//            System.err.println("Error loading friend requests: " + e.getMessage());
//        }
//    }
//
//    public void addFriend(String userId, String friendId) {
//    try {
//        File file = new File(filePath);
//        JSONObject database;
//        if (file.exists()) {
//            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
//            database = content.trim().isEmpty() ? new JSONObject() : new JSONObject(content);
//        } else {
//            database = new JSONObject();
//        }
//
//        if (!database.has(userId)) {
//            database.put(userId, new JSONObject());
//        }
//        if (!database.has(friendId)) {
//            database.put(friendId, new JSONObject());
//        }
//
//        database.getJSONObject(userId).put(friendId, true);
//        //database.getJSONObject(friendId).put(userId, true);  
//
//        try (FileWriter writer = new FileWriter(filePath)) {
//            writer.write(database.toString(5));
//            System.out.println("Friendship saved successfully.");
//        }
//    } catch (IOException e) {
//        System.err.println("Error adding friendship: " + e.getMessage());
//    }
//}
//
//    public FriendRequestClass getFriendRequestById(String requestId) {
//        try {
//            File file = new File(filePath);
//            if (file.exists()) {
//                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
//                if (!content.trim().isEmpty()) {
//                    JSONObject database = new JSONObject(content);
//
//                    // Check if the requestId exists in the database
//                    if (database.has(requestId)) {
//                        JSONObject jsonObject = database.getJSONObject(requestId);
//
//                        // Construct and return the FriendRequestClass object
////                        return new FriendRequestClass(
////                                requestId,
////                                jsonObject.getString("senderId"),
////                                jsonObject.getString("receiverId"),
////                                jsonObject.getString("status"),
////                                LocalDate.parse(jsonObject.getString("requestDate"))
////                        );
//                    }
//                }
//            }
//        } catch (IOException | JSONException e) {
//            System.err.println("Error retrieving friend request: " + e.getMessage());
//        }
//        return null;  
//    }
//   public void acceptFriendRequest(String requestId) {
//    FriendRequestClass request = getFriendRequestById(requestId);  
//    if (request != null) {
//        //request.setStatus("Accepted");
//
//        saveFriendRequest(request);  // Save the updated object to the file
//    
//    }
//}
//
//public void declineFriendRequest(String requestId) {
//    FriendRequestClass request = FriendRequestDataBase.getInstance("friend_requests.json")
//                            .getFriendRequestById(requestId);
//    if (request != null) {
//        //request.setStatus("Declined");
//        saveFriendRequest(request);
//    }
//}
//
//public ArrayList<String> getPendingRequests(String receiverId) {
//        ArrayList<String> pendingRequests = new ArrayList<>();
//
//        try {
//            File file = new File(filePath);
//            if (file.exists()) {
//                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
//                JSONObject database = new JSONObject(content);
//
//                for (String requestId : database.keySet()) {
//                    JSONObject request = database.getJSONObject(requestId);
//
//                    // Check if the request is pending and sent to this user
//                    if (request.getString("receiverId").equals(receiverId) &&
//                        request.getString("status").equals("Pending")) {
//                        String senderId = request.getString("senderId");
//                        pendingRequests.add(senderId);  // Add senderId or customize display as needed
//                    }
//                }
//            }
//        } catch (IOException | JSONException e) {
//            System.err.println("Error retrieving pending requests: " + e.getMessage());
//        }
//
//        return pendingRequests;
//    }
//}
//
//

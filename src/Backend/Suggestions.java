/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;


import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author DELL
 */
public class Suggestions {
   
    
     private static Suggestions instance = null;

    // Private constructor to prevent direct instantiation
    private Suggestions() {
    }

    // Method to return the singleton instance
    public static Suggestions getInstance() {
        if (instance == null) {
            instance = new Suggestions();
        }
        return instance;
    }

   public ArrayList<User> suggestFriends(String userId) {
    ArrayList<User> suggestedFriends = new ArrayList<>();

    // Get the user database instance
    UserDataBase userDB = UserDataBase.getInstance("user_data.json");

    // Get all users from the UserDataBase
    ArrayList<User> allUsers = userDB.getAllUsers();

    // Get user's existing friends, blocked users, and pending requests
    Set<String> friends = FriendDataBase.getInstance("friends.json").getFriends(userId);
    Set<String> blockedUsers = BlockedUserDataBase.getInstance("blocked_users.json").getBlockedUsers(userId);
    ArrayList<String> pendingRequests = FriendRequestDataBase.getInstance("friend_requests.json").getPendingRequests(userId);

    for (User user : allUsers) {
        String potentialFriendId = user.getUserId();

        // Skip if already a friend, blocked, pending request, or is the current user
        if (!friends.contains(potentialFriendId)
                && !blockedUsers.contains(potentialFriendId)
                && !pendingRequests.contains(potentialFriendId)
                && !potentialFriendId.equals(userId)) {
            suggestedFriends.add(user);
        }
    }
    return suggestedFriends;
}

}

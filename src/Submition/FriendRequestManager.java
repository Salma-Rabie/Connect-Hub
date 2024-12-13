/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
package Backend;

import java.time.LocalDate;

public class FriendRequestManager implements FriendManager{
    public void manage(String userId,String requestedId){
         // Here we define the logic of sending a request
        //System.out.println("Sending friend request from " + userId + " to " + requestedId);
        
        // Create a new friend request object
      //  FriendRequest request = new FriendRequest("req" + System.currentTimeMillis(), userId, requestedId, "Pending", LocalDate.now());
        
        // Call FriendRequestDataBase to save the request
       // FriendRequestDataBase.getInstance("friend_requests.json").saveFriendRequest(request);
    }
}

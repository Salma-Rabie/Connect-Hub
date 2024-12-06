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
public class FriendStatuses {
    public ArrayList<String>FriendStatuses(String userID){
    Set<String>friends=FriendDataBase.getInstance("friends.json").getFriends(userID);
    ArrayList<String>statuses=new ArrayList<>();
    UserDataBase userDataBase = UserDataBase.getInstance("users.json");
    for(String friendId:friends){
        User friend=userDataBase.getUserById(friendId);
        if(friend!=null){
            statuses.add(friend.getUsername()+"is"+friend.getStatus());
        }
    }
    return statuses;
}
}

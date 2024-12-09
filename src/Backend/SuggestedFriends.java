/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class SuggestedFriends {
 public ArrayList<String> getSuggestedFriendNames(String userId) {
        ArrayList<User> suggested = Suggestions.getInstance().suggestFriends(userId);
        ArrayList<String> names = new ArrayList<>();
        for (User user : suggested) {
            names.add(user.getUserId());
        }
        return names;
    }
    
}

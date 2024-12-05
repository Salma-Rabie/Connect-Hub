/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
package Backend;
public class BlockManager implements FriendManager {
    @Override
    public void manage(String userId, String targetId) {
        // Logic for blocking a user
        System.out.println(userId + " blocked " + targetId);
        BlockedUserDataBase.getInstance("blocked_users.json").saveBlockedUser(userId, targetId);
    }

    
}



package Backend;


public class BlockManager implements FriendManager {
    @Override
    public void manage(String userId, String targetId) {
        // Logic for blocking a user
        System.out.println(userId + " blocked " + targetId);
        BlockedUserDataBase.getInstance("blocked_users.json").saveBlockedUser(userId, targetId);
    }


}


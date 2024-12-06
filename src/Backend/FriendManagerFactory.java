/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author DELL
 */
public class FriendManagerFactory {
    public static FriendManager getManager(String actionType) {
        switch (actionType.toLowerCase()) {
            case "request":
                return new FriendRequestManager();
            case "block":
                return new BlockManager();
            default:
                throw new IllegalArgumentException("Invalid action type: " + actionType);
        }
    }
}

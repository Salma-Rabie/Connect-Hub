public ArrayList<User> getAllUsers() {
    ArrayList<User> userList = new ArrayList<>();
    try {
        File file = new File(filePath);
        if (file.exists()) {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            if (!content.trim().isEmpty()) {
                JSONObject database = new JSONObject(content);
                for (String userId : database.keySet()) {
                    JSONObject jsonObject = database.getJSONObject(userId);
                    User user = new User.UserBuilder()
                            .userId(userId)
                            .email(jsonObject.getString("Email"))
                            .username(jsonObject.getString("username"))
                            .passwordHash(jsonObject.getString("passwordHash"))
                            .status(jsonObject.getString("status"))
                            .dateOfBirth(LocalDate.parse(jsonObject.getString("dateOfBirth")))
                            .profilePhotoPath(jsonObject.optString("profilePhotoPath", null))
                            .coverPhotoPath(jsonObject.optString("coverPhotoPath", null))
                            .bio(jsonObject.optString("bio", ""))
                            .build();
                    userList.add(user);
                }
            }
        }
    } catch (IOException | JSONException e) {
        System.err.println("Error retrieving users: " + e.getMessage());
    }
    return userList;
}/*
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


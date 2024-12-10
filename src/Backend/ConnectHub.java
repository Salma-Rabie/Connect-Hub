
package Backend;

import Frontend.MainWindow;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;


public class ConnectHub {

   
    public static void main(String[] args) {
        
//          String filePath = "users.json";
//         UserDataBase userDataBase = UserDataBase.getInstance(filePath);
//         ArrayList<User> allusers= userDataBase.getAllUsers();
//         for(int i=0;i<allusers.size();i++){
//             System.out.println(allusers.get(i).getUserId());
//         }
//        
        String rawPassword = "56789";
        String hashedPassword = PasswordHashing.hashPassword(rawPassword);
File def=new File("ss.jpg");
        // Step 2: Build a User object
        User user = new User.UserBuilder()
                .userId("12345")
                .username("JohnDoe")
                .email("johndoe@example.com")
                .passwordHash(hashedPassword)
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .status("online")
                .bio("sjfxsghgxdwkwdhew")
                .coverPhotoPath(def.getPath())
                .profilePhotoPath(def.getPath())
                .build();

        // Step 3: Specify the JSON file path
        String filePath = "users.json";

        // Step 4: Save the user to the JSON file
        UserDataBase userDataBase = UserDataBase.getInstance(filePath);
        userDataBase.saveUser(user);
MainWindow main=new MainWindow(userDataBase);
main.setVisible(true);
        // Confirming that the user has been saved
        System.out.println("User saved to JSON file: " + filePath);


        // TODO code application logic here
//       String rawPassword = "56789";
//        String hashedPassword = PasswordHashing.hashPassword(rawPassword);
//File def=new File("ss.jpg");
//        // Step 2: Build a User object
//        User user = new User.UserBuilder()
//                .userId("12345")
//                .username("JohnDoe")
//                .email("johndoe@example.com")
//                .passwordHash(hashedPassword)
//                .dateOfBirth(LocalDate.of(1990, 5, 15))
//                .status("online")
//                .bio("sjfxsghgxdwkwdhew")
//                .coverPhotoPath(def.getPath())
//                .profilePhotoPath(def.getPath())
//                .build();
//
//        // Step 3: Specify the JSON file path
//        String filePath = "users.json";
//
//        // Step 4: Save the user to the JSON file
//        UserDataBase userDataBase = UserDataBase.getInstance(filePath);
//        userDataBase.saveUser(user);
//MainWindow main=new MainWindow(userDataBase);
//main.setVisible(true);
//        // Confirming that the user has been saved
//        System.out.println("User saved to JSON file: " + filePath);
//        
    } 
//// Step 1: Set up test data
//        setupSampleData();
//
//        // Step 2: Test getSuggestedFriendNames
//        SuggestedFriends suggestedFriends = new SuggestedFriends();
//        String userId = "user1";  // ID of the user for whom suggestions are fetched
//        ArrayList<String> suggestedNames = suggestedFriends.getSuggestedFriendNames(userId);
//
//        // Step 3: Print results
//        System.out.println("Suggested friends for " + userId + ":");
//        for (String name : suggestedNames) {
//            System.out.println(name);
        
    
//    private static void setupSampleData() {
//        // User Database Setup
//        UserDataBase userDB = UserDataBase.getInstance("user_data.json");
//        userDB.saveUser(new User.UserBuilder()
//                .userId("user1")
//                .username("Alice")
//                .email("alice@example.com")
//                .passwordHash("hashedpassword")
//                .dateOfBirth(java.time.LocalDate.of(1990, 1, 1))
//                .status("online")
//                .build());
//
//        userDB.saveUser(new User.UserBuilder()
//                .userId("user2")
//                .username("Bob")
//                .email("bob@example.com")
//                .passwordHash("hashedpassword")
//                .dateOfBirth(java.time.LocalDate.of(1985, 2, 15))
//                .status("offline")
//                .build());
//
//        userDB.saveUser(new User.UserBuilder()
//                .userId("user3")
//                .username("Charlie")
//                .email("charlie@example.com")
//                .passwordHash("hashedpassword")
//                .dateOfBirth(java.time.LocalDate.of(1992, 6, 20))
//                .status("online")
//                .build());
//
//        userDB.saveUser(new User.UserBuilder()
//                .userId("user4")
//                .username("David")
//                .email("david@example.com")
//                .passwordHash("hashedpassword")
//                .dateOfBirth(java.time.LocalDate.of(1988, 8, 10))
//                .status("online")
//                .build());
//
//        // Friend Database Setup
//        FriendDataBase friendDB = FriendDataBase.getInstance("friends.json");
//        friendDB.addFriend("user1", "user2");  // Alice and Bob are friends
//
//        // Blocked User Database Setup
//        BlockedUserDataBase blockedDB = BlockedUserDataBase.getInstance("blocked_users.json");
//        blockedDB.saveBlockedUser("user1", "user3");  // Alice has blocked Charlie
//    }
    
}

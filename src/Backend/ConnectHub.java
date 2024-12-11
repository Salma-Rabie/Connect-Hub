
package Backend;

import Frontend.MainWindow;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;


public class ConnectHub {

   
    public static void main(String[] args) {
        
        User adminUser = new User.UserBuilder().username("AdminUser").build();
        User user1 = new User.UserBuilder().username("User1").build();
        User user2 = new User.UserBuilder().username("User2").build();
        User user3 = new User.UserBuilder().username("User3").build();

        // Create the original group ("Java Enthusiasts")
        Group javaGroup = new Group.GroupBuilder()
            .name("Java Enthusiasts")
            .description("A group for people passionate about Java programming.")
            .photoPath("path/to/photo.jpg")
            .primaryAdmin(adminUser)
            .addUser(adminUser)
            .addUser(user1)
            .addUser(user2)
            .addUser(user3)
            .addOtherAdmin(user1)
            .build();

        // Create an instance of GroupDataBase with a file path (use an actual file path on your system)
        GroupDataBase db = GroupDataBase.getInstance("groups.json");

        // Save the group to the database (in JSON file)
        db.saveGroup(javaGroup);
        System.out.println("Group saved to database.");

        // Update some details of the group
        Group updatedGroup = new Group.GroupBuilder()
            .name("Java Enthusiasts")
            .description("A group for saraand yhnjuyjukmu enthusiasts and advanced learners.")
            .photoPath("path/to/new_photo.jpg")  // Update the photo path
            .primaryAdmin(user2)  // Update the primary admin
            .addUser(adminUser)
            .addUser(user1)
            .addUser(user2)
            .addUser(user3)
            .addOtherAdmin(user2)  // Adding a different admin for testing
            .build();

        // Update the group in the database
        Group result = db.updateGroup(updatedGroup);

        // Check if the group was updated successfully and print the updated details
        if (result != null) {
            System.out.println("Group updated successfully!");
            System.out.println("Updated Group Name: " + result.getName());
            System.out.println("Updated Description: " + result.getDescription());
            System.out.println("Updated Primary Admin: " + result.getPrimaryAdmin().getUsername());
            System.out.println("Updated Photo Path: " + result.getPhotoPath());
        } else {
            System.out.println("Failed to update the group.");
        }

       
    }}
        
         // Specify the file path for the JSON file
//        String filePath = "groupDatabase.json";
//
//        // Initialize the GroupDataBase singleton
//        GroupDataBase groupDataBase = GroupDataBase.getInstance(filePath);
//
//        // Create some User objects
//        User primaryAdmin = new User.UserBuilder()
//                .userId("1")
//                .username("AdminUser")
//                .email("admin@example.com")
//                .build();
//
//        User user1 = new User.UserBuilder()
//                .userId("2")
//                .username("User1")
//                .email("user1@example.com")
//                .build();
//
//        User user2 = new User.UserBuilder()
//                .userId("3")
//                .username("User2")
//                .email("user2@example.com")
//                .build();
//
//        User user3 = new User.UserBuilder()
//                .userId("4")
//                .username("User3")
//                .email("user3@example.com")
//                .build();
//
//        // Create a Group using the builder
//        Group group = new Group.GroupBuilder()
//                .name("Java Enthusiasts")
//                .description("A group for people passionate about Java programming.")
//                .photoPath("path/to/photo.jpg")
//                .primaryAdmin(primaryAdmin)
//                .addOtherAdmin(user1)
//                .addUser(primaryAdmin)
//                .addUser(user1)
//                .addUser(user2)
//                .addUser(user3)
//                .build();
//
//        // Save the group to the database
//        groupDataBase.saveGroup(group);
//
//        // Confirm the group has been saved
//        System.out.println("Group saved to database!");
        
        
//          String filePath = "users.json";
//         UserDataBase userDataBase = UserDataBase.getInstance(filePath);
//         ArrayList<User> allusers= userDataBase.getAllUsers();
//         for(int i=0;i<allusers.size();i++){
//             System.out.println(allusers.get(i).getUserId());
//         }
//        
// 
//        String rawPassword = "56789";
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
   // } 
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
    


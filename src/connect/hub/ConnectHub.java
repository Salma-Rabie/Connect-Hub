/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package connect.hub;

import connect.hub.Backend.PasswordHashing;
import connect.hub.Backend.ProfileManagement;
import connect.hub.Backend.User;
import connect.hub.Backend.UserDataBase;
import java.io.File;
import java.time.LocalDate;


public class ConnectHub {

   
    public static void main(String[] args) {
        // TODO code application logic here
//        String pass="abc123";
//        PasswordHashing passhash=new PasswordHashing();
//        String pass2=passhash.hashPassword(pass);
//        System.out.println(pass2);
   // Initialize UserDataBase with the file path to the JSON database
        UserDataBase database = UserDataBase.getInstance("users.json");

        // Initialize ProfileManagement with the UserDataBase instance
        ProfileManagement profileManager = new ProfileManagement(database);

        // Create new users using the User Builder and save them to the database
        User user1 = createNewUser("john_doe", "john@example.com", "password123", LocalDate.of(1990, 5, 15));
        database.saveUser(user1);  // Save to the database (JSON)

        User user2 = createNewUser("sara", "sara@example.com", "password456", LocalDate.of(1992, 8, 25));
        database.saveUser(user2);  // Save to the database (JSON)

        // Now that users are created, we can test updating their profile details.

        // Example 1: Change the bio for user with ID "john_doe"
       profileManager.changeBio("john_doe", "This is my updated bio!");

        // Example 2: Change the password for user with ID "john_doe"
//        profileManager.changePassword("john_doe", "newSecurePassword123");
//
//        // Example 3: Change the profile photo for user with ID "john_doe"
//        File newProfilePhoto = new File("path/to/new/photo.jpg");
//        profileManager.changeProfilePhoto("john_doe", newProfilePhoto);
//
//        // Example 4: Change the cover photo for user with ID "john_doe"
//        File newCoverPhoto = new File("path/to/new/cover_photo.jpg");
//        profileManager.changeCoverPhoto("john_doe", newCoverPhoto);

        // Output success messages will be printed within each method after they run.
    }

    // Helper method to create a new user using the UserBuilder
    public static User createNewUser(String username, String email, String password, LocalDate dateOfBirth) {
        // Hash the password before creating the user (to mimic real-world use)
        String hashedPassword = PasswordHashing.hashPassword(password);

        // Create and return a new user using the builder pattern
        return new User.UserBuilder()
                .username(username)
                .email(email)
                .passwordHash(hashedPassword)
                .dateOfBirth(dateOfBirth)
                .status("offline")  // Default status
                .userId(username)  // Use username as userId (can be replaced with a unique ID generator if needed)
                .bio("Default bio")  // Set the bio to a default value
                .build();
    }
    }
    


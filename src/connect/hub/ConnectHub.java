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
        // UserDataBase database = UserDataBase.getInstance("users.json");

        // Initialize ProfileManagement with the UserDataBase instance
//        ProfileManagement profileManager = new ProfileManagement(database);
//
//        // Step 1: Create and save the user "sara" if not already in the database
//        User sara = database.getUserById("sara");
//        if (sara == null) {
//            sara = createNewUser("sara", "sara@example.com", "password456", LocalDate.of(1992, 8, 25));
//            database.saveUser(sara); // Save to the database
//            System.out.println("User 'sara' created.");
//        } else {
//            System.out.println("User 'sara' already exists.");
//        }
//
//        // Step 2: Assign the profile photo for sara
//        File newProfilePhoto = new File("C:\\Users\\sarar\\Downloads\\quran.jpeg");
//        if (newProfilePhoto.exists()) {
//            profileManager.changeProfilePhoto("sara", newProfilePhoto);
//        } else {
//            System.err.println("Profile photo file does not exist: " + newProfilePhoto.getPath());
//        }
//
//        // Step 3: Verify the photo path in the JSON file
//        sara = database.getUserById("sara");
//        if (sara != null) {
//            System.out.println("Updated profile photo path in database: " + sara.getProfilePhotoPath());
//        } else {
//            System.err.println("Failed to retrieve user 'sara' from the database.");
//        }
UserDataBase database = UserDataBase.getInstance("users.json");

        // Path to Ahmed's profile photo
        String ahmedPhotoPath = "C:\\Users\\sarar\\Downloads\\quran.jpeg";

        // Create the user "Ahmed" with his profile photo
        User ahmed = new User.UserBuilder()
                .userId("ahmed")  // Assign Ahmed's ID
                .username("ahmed")
                .email("ahmed@example.com")
                .passwordHash(PasswordHashing.hashPassword("ahmed_password"))  // Hash Ahmed's password
                .dateOfBirth(LocalDate.of(1995, 3, 10))  // Ahmed's date of birth
                .status("offline")  // Default status
                .bio("This is Ahmed's profile bio.")  // Assign Ahmed's bio
                .profilePhotoPath(ahmedPhotoPath)  // Assign Ahmed's profile photo path
                .coverPhotoPath(null)  // No cover photo initially
                .build();

        // Save Ahmed to the database
        database.saveUser(ahmed);

        // Print confirmation
        System.out.println("User 'ahmed' has been added with the profile photo: " + ahmedPhotoPath);
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
                .userId(username)  // Use username as userId
                .bio("Default bio")  // Set the bio to a default value
                .build();
    }

    }

    
    
    


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connect.hub.Backend;

/**
 *
 * @author sarar
 */
//see the factory point 
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import org.json.*;

public class ProfileManagement {

    private final UserDataBase userDataBase;

    public ProfileManagement(UserDataBase userDataBase) {
        this.userDataBase = userDataBase;
    }

    public void changeProfilePhoto(String userId, File newPhotoFile) {
        User user = userDataBase.getUserById(userId);

        if (user == null) {
            System.err.println("User not found.");
            return ;
        }

        // Define the directory and file path for the profile photo
        String profilePhotoDir = "user_data/profile_photos/";
        String newProfilePhotoPath = profilePhotoDir + userId + "_profile.jpg";

        try {
            // Ensure the directory exists
            File directory = new File(profilePhotoDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Copy the new photo to the profile photo directory
            Files.copy(newPhotoFile.toPath(), new File(newProfilePhotoPath).toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Update the user's profile photo path
            User updatedUser = new User.UserBuilder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .passwordHash(user.getPasswordHash())
                    .dateOfBirth(user.getDateOfBirth())
                    .status(user.getStatus())
                    .bio(user.getBio())
                    .profilePhotoPath(newProfilePhotoPath) // Set the new path
                    .coverPhotoPath(user.getCoverPhotoPath())
                    .build();

            // Use updateUser to save changes
            userDataBase.updateUser(updatedUser);

            System.out.println("Profile photo updated successfully.");
            

        } catch (IOException e) {
            System.err.println("Error updating profile photo: " + e.getMessage());
        }
    }
    
public void changePassword(String userId, String newPassword) {
    // get the user from the database
    User user = userDataBase.getUserById(userId);
    
    if (user == null) {
        // If the user doesn't exist, log an error and return
        System.err.println("User not found.");
        return;
    }
    // Hash the new password before storing it
    String hashedPassword = PasswordHashing.hashPassword(newPassword);
    
    // Create a new user object with the updated password
    User updatedUser = new User.UserBuilder()
            .bio(user.getBio())  // Keep the other fields as they are
            .coverPhotoPath(user.getCoverPhotoPath())
            .dateOfBirth(user.getDateOfBirth())
            .email(user.getEmail())
            .passwordHash(hashedPassword)  // Update the password hash (you can apply hashing here if needed)
            .profilePhotoPath(user.getProfilePhotoPath())
            .status(user.getStatus())
            .userId(user.getUserId())
            .username(user.getUsername())
            .build();
    
    // Save the updated user to the database
    userDataBase.updateUser(updatedUser);
    
    // Log the successful update
    System.out.println("Password updated successfully.");
}
    
    public void changeCoverPhoto(String userId, File newCoverPhotoFile) {
    // Fetch the user from the database
    User user = userDataBase.getUserById(userId);
    
    if (user == null) {
        // If the user doesn't exist, log an error and return
        System.err.println("User not found.");
        return;
    }
    
    // Define the directory and file path for the cover photo
    String coverPhotoDir = "user_data/cover_photos/";
    String newCoverPhotoPath = coverPhotoDir + userId + "_cover.jpg";

    try {
        // Ensure the directory exists
        File directory = new File(coverPhotoDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Copy the new cover photo to the cover photo directory
        Files.copy(newCoverPhotoFile.toPath(), new File(newCoverPhotoPath).toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Create a new user object with the updated cover photo path
        User updatedUser = new User.UserBuilder()
                .bio(user.getBio())  // Keep the other fields as they are
                .coverPhotoPath(newCoverPhotoPath)  // Update the cover photo path
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .profilePhotoPath(user.getProfilePhotoPath())
                .status(user.getStatus())
                .userId(user.getUserId())
                .username(user.getUsername())
                .build();

        // Save the updated user to the database
        userDataBase.updateUser(updatedUser);
        
        // Log the successful update
        System.out.println("Cover photo updated successfully.");
        
    } catch (IOException e) {
        // Handle any file-related errors
        System.err.println("Error updating cover photo: " + e.getMessage());
    }
}
   public void changeBio(String userId,String bio){
       User user = userDataBase.getUserById(userId);
       if (user == null) {
            System.err.println("User not found.");
            return ;
        }
        User updatedUser = new User.UserBuilder()
            .bio(bio)  // Update the bio
            .coverPhotoPath(user.getCoverPhotoPath())  // Keep the other fields as they are
            .dateOfBirth(user.getDateOfBirth())
            .email(user.getEmail())
            .passwordHash(user.getPasswordHash())
            .profilePhotoPath(user.getProfilePhotoPath())
            .status(user.getStatus())
            .userId(user.getUserId())
            .username(user.getUsername())
            .build();
    
    // Save the updated user to the database
    userDataBase.updateUser(updatedUser);
    
    // Log the successful update
    System.out.println("Bio updated successfully.");
   }
}


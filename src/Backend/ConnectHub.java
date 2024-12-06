/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Backend;

import java.time.LocalDate;


public class ConnectHub {

   
    public static void main(String[] args) {
        // TODO code application logic here
       String rawPassword = "securePassword123";
        String hashedPassword = PasswordHashing.hashPassword(rawPassword);

        // Step 2: Build a User object
        User user = new User.UserBuilder()
                .userId("12345")
                .username("JohnDoe")
                .email("johndoe@example.com")
                .passwordHash(hashedPassword)
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .status("online")
                .build();

        // Step 3: Specify the JSON file path
        String filePath = "users.json";

        // Step 4: Save the user to the JSON file
        UserDataBase userDataBase = UserDataBase.getInstance(filePath);
        userDataBase.saveUser(user);

        // Confirming that the user has been saved
        System.out.println("User saved to JSON file: " + filePath);
        
    }
    
}

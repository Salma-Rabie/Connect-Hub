
package connect.hub.Backend;

import java.time.LocalDate;


public class UserManager {
    private static int idCounter=1000;
   
    public User sigup(String email, String username, String password, LocalDate dateOfBirth){
        
        if(!isValidEmail(email))
            return null;
        else
        {
            return new User.UserBuilder().dateOfBirth(dateOfBirth)
                    .email(email).passwordHash(password)
                    .status("online").username(username).userId(username)
                    .userId(String.valueOf(idCounter++)).build();
            
        }
    }
    
   private  boolean isValidEmail(String email) {
        //for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
  
}

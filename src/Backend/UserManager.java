
package Backend;

import static Backend.PasswordHashing.hashPassword;
import java.time.LocalDate;


public class UserManager {
    private static int idCounter=1000;
    private final UserDataBase database;

    public UserDataBase getDatabase() {
        return database;
    }

    public UserManager(UserDataBase database) {
        this.database = database;
    }
   
    public User sigup(String email, String username, String password, LocalDate dateOfBirth){
        
        
            return new User.UserBuilder().dateOfBirth(dateOfBirth)
                    .email(email).passwordHash(password)
                    .status("online").username(username).userId(username)
                    .userId(String.valueOf(idCounter++)).build();
            
        
    }
    public User login(String userId, String password){
        User user= database.getUserById(userId);
        if(user==null){
            return null;
        }
            else if(!user.getPasswordHash().equals(hashPassword(password))){
                  return null;  
                    }
            else 
                return user;
        
    }
    public void logout(){
        
    }
   public  boolean isValidEmail(String email) {
        //for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
  
}

package Backend;

import static Backend.PasswordHashing.hashPassword;
import java.time.LocalDate;

public class UserManager {

    private static int idCounter;
    private final UserDataBase database;

    public UserDataBase getDatabase() {
        return database;
    }

    public UserManager(UserDataBase database) {
        this.database = database;
        idCounter = 1000;
    }

    public User signup(String email, String username, String password, LocalDate dateOfBirth) {

        User user = new User.UserBuilder().dateOfBirth(dateOfBirth)
                .email(email).passwordHash(password)
                .status("online").username(username).userId(username)
                .userId(String.valueOf(idCounter)).build();
        idCounter++;
        System.out.println(idCounter);
        database.saveUser(user);
        return user;

    }

    public User login(String userId, String password) {
        User user = database.getUserById(userId);
        if (user == null) {
            return null;
        } else if (!user.getPasswordHash().equals(hashPassword(password))) {
            return null;
        } else {
            return user;
        }

    }

    public void logout(String userId, String status) {
        User user = database.getUserById(userId);
        user.setStatus(status);

    }

    public boolean isValidEmail(String email) {
        // For email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
}

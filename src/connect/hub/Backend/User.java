
package connect.hub.Backend;

import java.time.LocalDate;

public class User implements Cloneable {
    private final String userId;
    private String email;
    private String username;
    private String passwordHash;
    private LocalDate dateOfBirth;
    private String status;

    private User(UserBuilder builder) {
        this.userId = builder.userId;
        this.email = builder.email;
        this.username = builder.username;
        this.passwordHash = builder.passwordHash;
        this.dateOfBirth = builder.dateOfBirth;
        this.status = "offline";
    }

    public static class UserBuilder {
        private String userId;
        private String email;
        private String username;
        private String passwordHash;
        private LocalDate dateOfBirth;
         private String status;

        public UserBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }
        
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }
        public UserBuilder status(String status) {
            this.status = status;
            return this;
        }

        public UserBuilder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public UserBuilder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStatus() {
        return status;
    }


    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }
}
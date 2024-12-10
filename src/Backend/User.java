package Backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User implements Cloneable {
    private final String userId;
    private String email;
    private String username;
    private String passwordHash;
    private LocalDate dateOfBirth;
    private String status;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
 private List<stories> stories; 
    private List<posts> posts; 
    private ArrayList<Group> groups;
    private User(UserBuilder builder) {
        this.userId = builder.userId;
        this.email = builder.email;
        this.username = builder.username;
        this.passwordHash = builder.passwordHash;
        this.dateOfBirth = builder.dateOfBirth;
        this.status = builder.status != null ? builder.status : "offline";
        this.profilePhotoPath = builder.profilePhotoPath;
        this.coverPhotoPath = builder.coverPhotoPath;
        this.bio = builder.bio;
        this.posts=builder.user_posts;
        this.stories=builder.user_stories;
        this.groups=builder.groups;
    }

    public static class UserBuilder {
     private String userId;
    private String email;
    private String username;
    private String passwordHash;
    private LocalDate dateOfBirth;
    private String status;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
 private List<stories> user_stories = new ArrayList<>();
    private List<posts> user_posts = new ArrayList<>(); 
    private ArrayList<Group> groups=new ArrayList<>(); 

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
      public UserBuilder groups(ArrayList<Group> groups) {
        this.groups = groups;
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

    public UserBuilder status(String status) {
        this.status = status;
        return this;
    }

    public UserBuilder profilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
        return this;
    }

    public UserBuilder coverPhotoPath(String coverPhotoPath) {
        this.coverPhotoPath = coverPhotoPath;
        return this;
    }

    public UserBuilder bio(String bio) {
        this.bio = bio;
        return this;
    }
    public UserBuilder story(stories story)
    {
       this.user_stories.add(story);
       return this;
    }
      public UserBuilder post(posts post)
    {
       this.user_posts.add(post);
       return this;
    }
public UserBuilder setstories(List<stories>u)      
      {
          this.user_stories = u;
          return this;
      }
      public UserBuilder setposts(List<posts>p)
      {
          this.user_posts=p;
          return this;
      }

   public User build() {
        User user = new User(this);
        user.posts=this.user_posts;
        user.stories= this.user_stories;
        return user;
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
        return LocalDate.parse(dateOfBirth.toString());
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public String getCoverPhotoPath() {
        return coverPhotoPath;
    }

    public String getBio() {
        return bio;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
public List<stories> getStories() {
        return stories;
    }

    public void addStory(stories story) {
        this.stories.add(story);
    }

    public List<posts> getPosts() {
        return posts;
    }

    public void addPost(posts post) {
        this.posts.add(post);
    }
  
    public void removeExpiredStories() {
    this.stories.removeIf( user_stories -> user_stories.isExpired());}

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }
}
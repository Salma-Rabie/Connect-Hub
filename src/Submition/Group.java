package Backend;
import java.util.ArrayList;

public class Group {
    private String name;
    private int numMembers;
    private ArrayList<String> users;
    private String photoPath;
    private String description;
    private String primaryAdmin;
    private ArrayList<String> otherAdmins;
    private ArrayList<posts> posts;

    // Private constructor to enforce the use of the builder
    private Group(GroupBuilder builder) {
        this.name = builder.name;
        this.numMembers = builder.numMembers;
        this.users = builder.users;
        this.photoPath = builder.photoPath;
        this.description = builder.description;
        this.primaryAdmin = builder.primaryAdmin;
        this.otherAdmins = builder.otherAdmins;
        this.posts = builder.posts;
    }

    // Getter methods for all fields (optional for usage)
    public String getName() {
        return name;
    }

    public int getNumMembers() {
        return numMembers;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getDescription() {
        return description;
    }

    public String getPrimaryAdmin() {
        return primaryAdmin;
    }
 public void addPost(posts post) {
            this.posts.add(post);
           
        }
    public ArrayList<String> getOtherAdmins() {
        return otherAdmins;
    }

    public ArrayList<posts> getPosts() {
        return posts;
    }

    // Builder class
    public static class GroupBuilder {
        private String name;
        private int numMembers;
        private ArrayList<String> users = new ArrayList<>();
        private String photoPath;
        private String description;
        private String primaryAdmin;
        private ArrayList<String> otherAdmins = new ArrayList<>();
        private ArrayList<posts> posts = new ArrayList<>();

        // Setter methods in the builder
        public GroupBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GroupBuilder numMembers(int numMembers) {
            this.numMembers = numMembers;
            return this;
        }

        public GroupBuilder users(ArrayList<String> users) {
            this.users = users;
            return this;
        }

        public GroupBuilder addUser(String user) {
            this.users.add(user);
            this.numMembers = this.users.size();
            return this;
        }

        public GroupBuilder photoPath(String photoPath) {
            this.photoPath = photoPath;
            return this;
        }

        public GroupBuilder description(String description) {
            this.description = description;
            return this;
        }

        public GroupBuilder primaryAdmin(String primaryAdmin) {
            this.primaryAdmin = primaryAdmin;
            return this;
        }

        public GroupBuilder otherAdmins(ArrayList<String> otherAdmins) {
            this.otherAdmins = otherAdmins;
            return this;
        }

        public GroupBuilder addOtherAdmin(String admin) {
            this.otherAdmins.add(admin);
            return this;
        }

        public GroupBuilder posts(ArrayList<posts>posts) {
            this.posts = posts;
            return this;
        }

        public GroupBuilder addPost(posts post) {
            this.posts.add(post);
            return this;
        }

        // Build method to create the Group object
        public Group build() {
            return new Group(this);
        }
    }
    
}

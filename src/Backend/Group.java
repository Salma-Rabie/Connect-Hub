package Backend;

import java.util.ArrayList;

public class Group {

    private String name;
    private int numMembers;
    private ArrayList<User> users;
    private String photoPath;
    private String description;
    private User primaryAdmin;
    private ArrayList<User> otherAdmins;
    private ArrayList<posts> posts;
    private ArrayList<String> joinRequests;

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
        this.joinRequests = builder.joinRequests;
    }

    // Getter methods for all fields (optional for usage)
    public String getName() {
        return name;
    }

    public int getNumMembers() {
        return numMembers;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getDescription() {
        return description;
    }

    public User getPrimaryAdmin() {
        return primaryAdmin;
    }

    public ArrayList<User> getOtherAdmins() {
        return otherAdmins;
    }

    public ArrayList<posts> getPosts() {
        return posts;
    }

    public ArrayList<String> getJoinRequests() {
        return joinRequests;
    }

    public void addJoinRequest(String userName) {
        this.joinRequests.add(userName);
    }

    public void removeJoinRequest(String userName) {
        this.joinRequests.remove(userName);
    }

    // Builder class
    public static class GroupBuilder {

        private String name;
        private int numMembers;
        private ArrayList<User> users = new ArrayList<>();
        private String photoPath;
        private String description;
        private User primaryAdmin;
        private ArrayList<User> otherAdmins = new ArrayList<>();
        private ArrayList<posts> posts = new ArrayList<>();
        private ArrayList<String> joinRequests = new ArrayList<>();

        // Setter methods in the builder
        public GroupBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GroupBuilder numMembers(int numMembers) {
            this.numMembers = numMembers;
            return this;
        }

        public GroupBuilder users(ArrayList<User> users) {
            this.users = users;
            return this;
        }

        public GroupBuilder addUser(User user) {
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

        public GroupBuilder primaryAdmin(User primaryAdmin) {
            this.primaryAdmin = primaryAdmin;
            return this;
        }

        public GroupBuilder otherAdmins(ArrayList<User> otherAdmins) {
            this.otherAdmins = otherAdmins;
            return this;
        }

        public GroupBuilder addOtherAdmin(User admin) {
            this.otherAdmins.add(admin);
            return this;
        }

        public GroupBuilder posts(ArrayList<posts> posts) {
            this.posts = posts;
            return this;
        }

        public GroupBuilder addPost(posts post) {
            this.posts.add(post);
            return this;
        }

        public GroupBuilder joinRequests(ArrayList<String> joinRequests) {
            this.joinRequests = joinRequests;
            return this;
        }

        public GroupBuilder addJoinRequest(String userName) {
            this.joinRequests.add(userName);
            return this;
        }

        // Build method to create the Group object
        public Group build() {
            return new Group(this);
        }

    }

}

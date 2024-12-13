package Backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupManagement {

    private UserDataBase userDataBase;
    private GroupDataBase groupDataBase;

    // Dependency Injection (Dependency Inversion Principle)
    public GroupManagement(UserDataBase userDataBase, GroupDataBase groupDataBase) {
        this.userDataBase = userDataBase;
        this.groupDataBase = groupDataBase;
    }

    public Group addPost(Group group, String userID, posts post) {
        // check if user is memeber of the group
        //User user=userDataBase.getUserById(userID);
        if (!group.getUsers().contains(userID)) {
            return null;
        }
        ArrayList<posts> allposts = new ArrayList<>(group.getPosts());
        allposts.add(post);
        Group updatedGroup = new Group.GroupBuilder()
                .name(group.getName())
                .description(group.getDescription())
                .photoPath(group.getPhotoPath())
                .primaryAdmin(group.getPrimaryAdmin())
                .users(new ArrayList<>(group.getUsers()))
                .otherAdmins(new ArrayList<>(group.getOtherAdmins()))
                .posts(allposts)
                .numMembers(group.getNumMembers())
                .build();
        groupDataBase.updateGroup(updatedGroup);
        return updatedGroup;
    }

    public Group editPost(Group group, String userID, posts newpost, posts oldpost) {
        // User user=userDataBase.getUserById(userID);
        // check if user is memeber of the group
        if (!group.getUsers().contains(userID)) {
            return null;
        }
        // check who want to edit the post
        if (!oldpost.getUserId().equals(userID) || !isAdminWithRemovalRights(group, userID)) {
            return null;
        }
        ArrayList<posts> allposts = new ArrayList<>(group.getPosts());
        allposts.add(newpost);
        allposts.remove(oldpost);
        Group updatedGroup = new Group.GroupBuilder()
                .name(group.getName())
                .description(group.getDescription())
                .photoPath(group.getPhotoPath())
                .primaryAdmin(group.getPrimaryAdmin())
                .users(new ArrayList<>(group.getUsers()))
                .otherAdmins(new ArrayList<>(group.getOtherAdmins()))
                .posts(allposts)
                 .numMembers(group.getNumMembers())
                .build();
        groupDataBase.updateGroup(updatedGroup);
        return updatedGroup;
    }

    public Group deletPost(Group group, String userID, posts post) {
        //User user=userDataBase.getUserById(userID);
        // check if user is memeber of the group
        if (!group.getUsers().contains(userID)) {
            return null;
        }
        // check who want to delet the post
        if (!post.getUserId().equals(userID) || !isAdminWithRemovalRights(group, userID)) {
            return null;
        }
        ArrayList<posts> allposts = new ArrayList<>(group.getPosts());
        allposts.remove(post);
        Group updatedGroup = new Group.GroupBuilder()
                .name(group.getName())
                .description(group.getDescription())
                .photoPath(group.getPhotoPath())
                .primaryAdmin(group.getPrimaryAdmin())
                .users(new ArrayList<>(group.getUsers()))
                .otherAdmins(new ArrayList<>(group.getOtherAdmins()))
                .posts(allposts)
                 .numMembers(group.getNumMembers())
                .build();
        groupDataBase.updateGroup(updatedGroup);
        return updatedGroup;
    }

    // Method to create a group (follows Single Responsibility Principle)
    public Map<String, Object> createGroup(String name, String description, String primaryAdminID, String photoPath) {
        User primaryAdmin=userDataBase.getUserById(primaryAdminID);
        //check if group name already exist
        if (groupDataBase.getGroupByName(name) != null) {
            return null;
        }
        // Use Builder pattern to create Group
        Group newGroup = new Group.GroupBuilder()
                .name(name)
                .description(description)
                .primaryAdmin(primaryAdminID)
                .photoPath(photoPath)
                .addUser(primaryAdminID)
                 .numMembers(1)
                .build();

        // Save to database
        groupDataBase.saveGroup(newGroup);
    primaryAdmin.addGroup(newGroup.getName());
        userDataBase.updateUser(primaryAdmin);
         Map<String, Object> map = new HashMap<>();
        map.put("group", newGroup);
        map.put("user", primaryAdmin);
        return map;    }

    // Method to join a group
    public Map<String, Object> joinGroup(Group group, String userID) {
        User user=userDataBase.getUserById(userID);
        // Check if user is already in the group
        if (group.getUsers().contains(userID)) {
            return null;
        }

        // Create a new group with the added user
        Group updatedGroup = new Group.GroupBuilder()
                .name(group.getName())
                .description(group.getDescription())
                .photoPath(group.getPhotoPath())
                .primaryAdmin(group.getPrimaryAdmin())
                .users(new ArrayList<>(group.getUsers()))
                .otherAdmins(new ArrayList<>(group.getOtherAdmins()))
                .posts(new ArrayList<>(group.getPosts()))
                .addUser(userID)
                 .numMembers(group.getNumMembers()+1)
                .build();
        user.addGroup(group.getName());
        userDataBase.updateUser(user);
        // Update in database
        groupDataBase.updateGroup(updatedGroup);

         Map<String, Object> map = new HashMap<>();
        map.put("group", group);
        map.put("user", user);
        return map;
    }

    // Method to leave a group
    public Map<String, Object> leaveGroup(Group group, String userID) {
        User user = userDataBase.getUserById(userID);

        // Prevent primary admin from leaving
        if (group.getPrimaryAdmin().equals(userID)) {
            System.out.println("he is primary admin");
            return null;
        }
        //check if user aleady in the group
        if (!group.getUsers().contains(userID)) {
            System.out.println("he is not in the group");
            return null;
        }

        // Remove user from group
        ArrayList<String> updatedUsers = new ArrayList<>(group.getUsers());
        updatedUsers.remove(userID);
        ArrayList<posts> groupPosts = new ArrayList<>(group.getPosts());

        // Remove from admins if they are an admin
        ArrayList<String> updatedOtherAdmins = new ArrayList<>(group.getOtherAdmins());
        updatedOtherAdmins.remove(userID);

        Group updatedGroup = new Group.GroupBuilder()
                .name(group.getName())
                .description(group.getDescription())
                .photoPath(group.getPhotoPath())
                .primaryAdmin(group.getPrimaryAdmin())
                .users(updatedUsers)
                .otherAdmins(updatedOtherAdmins)
                .posts(new ArrayList<>(group.getPosts()))
                .numMembers(group.getNumMembers()-1)
                .build();

        // Update in database
        groupDataBase.updateGroup(updatedGroup);
        user.removeGroup(group.getName());
        userDataBase.updateUser(user);
        Map<String, Object> map = new HashMap<>();
        map.put("group", group);
        map.put("user", user);
        return map;
    }

    public Group changeGroupPhoto(Group group, String primaryAdminID, String photoPath) {
        // User primaryAdmin=userDataBase.getUserById(userID);
        if (!group.getPrimaryAdmin().equals(primaryAdminID)) {
            return null;
        }
        Group updatedGroup = new Group.GroupBuilder()
                .name(group.getName())
                .description(group.getDescription())
                .photoPath(photoPath)
                .primaryAdmin(group.getPrimaryAdmin())
                .users(new ArrayList<>(group.getUsers()))
                .otherAdmins(new ArrayList<>(group.getOtherAdmins()))
                .posts(new ArrayList<>(group.getPosts()))
                 .numMembers(group.getNumMembers())
                .build();
        groupDataBase.updateGroup(updatedGroup);
        return updatedGroup;

    }

    // Method to promote a user to admin
    public Group promoteToAdmin(Group group, String primaryAdminID, String userToPromote) {
        //  User primaryAdmin=userDataBase.getUserById(userID);
        // Check if the action is performed by primary admin
        if (!group.getPrimaryAdmin().equals(primaryAdminID)) {
            return null;
        }

        // Ensure the user is in the group
        if (!group.getUsers().contains(userToPromote)) {
            return null;
        }

        // Create updated group with new admin
        Group updatedGroup = new Group.GroupBuilder()
                .name(group.getName())
                .description(group.getDescription())
                .photoPath(group.getPhotoPath())
                .primaryAdmin(group.getPrimaryAdmin())
                .users(new ArrayList<>(group.getUsers()))
                .otherAdmins(new ArrayList<>(group.getOtherAdmins()))
                .addOtherAdmin(userToPromote)
                .posts(new ArrayList<>(group.getPosts()))
                 .numMembers(group.getNumMembers())
                .build();

        // Update in database
        groupDataBase.updateGroup(updatedGroup);

        return updatedGroup;
    }

    public Group demoteFromAdmin(Group group, String primaryAdminID, String userTodemote) {
        //User primaryAdmin=userDataBase.getUserById(userID);
        // Check if the action is performed by primary admin
        if (!group.getPrimaryAdmin().equals(primaryAdminID)) {
            return null;
        }

        // Ensure the user is an admin
        if (!group.getOtherAdmins().contains(userTodemote)) {
            return null;
        }
        ArrayList<String> otherAdmins = new ArrayList<>(group.getOtherAdmins());
        otherAdmins.remove(userTodemote);
        // Create updated group with deleted admin
        Group updatedGroup = new Group.GroupBuilder()
                .name(group.getName())
                .description(group.getDescription())
                .photoPath(group.getPhotoPath())
                .primaryAdmin(group.getPrimaryAdmin())
                .users(new ArrayList<>(group.getUsers()))
                .otherAdmins(otherAdmins)
                .posts(new ArrayList<>(group.getPosts()))
                 .numMembers(group.getNumMembers())
                .build();

        // Update in database
        groupDataBase.updateGroup(updatedGroup);

        return updatedGroup;
    }

    // Method to remove a member (for admins)
    public Group removeMember(Group group, String adminID, String memberToRemoveID) {
        User memberToRemove=userDataBase.getUserById(memberToRemoveID);
        // Check if the admin has removal rights
        if (!isAdminWithRemovalRights(group, adminID)) {
            return null;
        }

        // Cannot remove primary admin
        if (memberToRemoveID.equals(group.getPrimaryAdmin())) {
            return null;
        }
        //otheradmin cannot remove otheradmin
        if (group.getOtherAdmins().contains(adminID) && group.getOtherAdmins().contains(memberToRemoveID)) {
            return null;

        }
        // Remove user from group
        ArrayList<String> updatedUsers = new ArrayList<>(group.getUsers());
        updatedUsers.remove(memberToRemoveID);

        // Remove from other admins if they are an admin
        ArrayList<String> updatedOtherAdmins = new ArrayList<>(group.getOtherAdmins());
        updatedOtherAdmins.remove(memberToRemoveID);
        memberToRemove.removeGroup(group.getName());
        userDataBase.updateUser(memberToRemove);

        Group updatedGroup = new Group.GroupBuilder()
                .name(group.getName())
                .description(group.getDescription())
                .photoPath(group.getPhotoPath())
                .primaryAdmin(group.getPrimaryAdmin())
                .users(updatedUsers)
                .otherAdmins(updatedOtherAdmins)
                .posts(new ArrayList<>(group.getPosts()))
                 .numMembers(group.getNumMembers()-1)
                .build();

        // Update in database
        groupDataBase.updateGroup(updatedGroup);

        return updatedGroup;
    }

    // Helper method to check admin removal rights
    private boolean isAdminWithRemovalRights(Group group, String userID) {
        // User admin=userDataBase.getUserById(userID);
        return group.getPrimaryAdmin().equals(userID)
                || group.getOtherAdmins().contains(userID);
    }

    // Method to delete a group (only by primary admin)
    public User deleteGroup(Group group, String userID) {
         ArrayList<String> usersIDs=group.getUsers();
         User primaryAdmin=userDataBase.getUserById(userID);
        if(groupDataBase.deleteGroup(group, userID)){
            for(int i=0;i<usersIDs.size();i++){
                User user=userDataBase.getUserById(usersIDs.get(i));
                user.removeGroup(group.getName());
                  userDataBase.updateUser(user);
            }
         primaryAdmin.removeGroup(group.getName());
         userDataBase.updateUser(primaryAdmin);
        
         return primaryAdmin;
        }
         return null;
    }
}

package Backend;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GroupManagement {
    private UserDataBase userDataBase;
    private GroupDataBase groupDataBase;
    

    // Dependency Injection (Dependency Inversion Principle)
    public GroupManagement(UserDataBase userDataBase, GroupDataBase groupDataBase) {
        this.userDataBase = userDataBase;
        this.groupDataBase = groupDataBase;
    }
public Group addPost(Group group,User user, posts post){
    // check if user is memeber of the group
    if (!group.getUsers().contains(user)) {
            return null;
        }
    ArrayList<posts> allposts=new ArrayList<>(group.getPosts());
    allposts.add(post);
     Group updatedGroup = new Group.GroupBuilder()
            .name(group.getName())
            .description(group.getDescription())
            .photoPath(group.getPhotoPath())
            .primaryAdmin(group.getPrimaryAdmin())
            .users(new ArrayList<>(group.getUsers()))
            .otherAdmins(new ArrayList<>(group.getOtherAdmins()))
            .posts(allposts)
            .build();
     groupDataBase.updateGroup(updatedGroup);
     return updatedGroup;
}
public Group editPost(Group group,User user, posts newpost,posts oldpost){
    // check if user is memeber of the group
    if (!group.getUsers().contains(user)) {
            return null;
        }
    // check who want to edit the post
     if(!oldpost.getUserId().equals(user.getUserId())||!isAdminWithRemovalRights(group, user)){
        return null;
    }
    ArrayList<posts> allposts=new ArrayList<>(group.getPosts());
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
            .build();
     groupDataBase.updateGroup(updatedGroup);
     return updatedGroup;
}
public Group deletPost(Group group,User user, posts post){
    // check if user is memeber of the group
    if (!group.getUsers().contains(user)) {
            return null;
        }
    // check who want to delet the post
    if(!post.getUserId().equals(user.getUserId())||!isAdminWithRemovalRights(group, user)){
        return null;
    }
    ArrayList<posts> allposts=new ArrayList<>(group.getPosts());
    allposts.remove(post);
     Group updatedGroup = new Group.GroupBuilder()
            .name(group.getName())
            .description(group.getDescription())
            .photoPath(group.getPhotoPath())
            .primaryAdmin(group.getPrimaryAdmin())
            .users(new ArrayList<>(group.getUsers()))
            .otherAdmins(new ArrayList<>(group.getOtherAdmins()))
            .posts(allposts)
            .build();
     groupDataBase.updateGroup(updatedGroup);
     return updatedGroup;
}
    // Method to create a group (follows Single Responsibility Principle)
    public Group createGroup(String name, String description, User primaryAdmin, String photoPath) {
        //check if group name already exist
if(groupDataBase.getGroupByName(name)!=null){
    return null;
}
        // Use Builder pattern to create Group
        Group newGroup = new Group.GroupBuilder()
            .name(name)
            .description(description)
            .primaryAdmin(primaryAdmin)
            .photoPath(photoPath)
            .addUser(primaryAdmin)
            .primaryAdmin(primaryAdmin)
            .build();

        // Save to database
        groupDataBase.saveGroup(newGroup);

        return newGroup;
    }

    // Method to join a group
    public Group joinGroup(Group group, User user) {
        // Check if user is already in the group
        if (group.getUsers().contains(user)) {
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
            .addUser(user)
            .build();

        // Update in database
        groupDataBase.updateGroup(updatedGroup);

        return updatedGroup;
    }

    // Method to leave a group
    public Group leaveGroup(Group group, User user) {
        // Prevent primary admin from leaving
        if (group.getPrimaryAdmin().equals(user)) {
            return null;
        }
        //check if user aleady in the group
        if (!group.getUsers().contains(user)) {
            return null;
        }

        // Remove user from group
        ArrayList<User> updatedUsers = new ArrayList<>(group.getUsers());
        updatedUsers.remove(user);

        // Remove from admins if they are an admin
        ArrayList<User> updatedOtherAdmins = new ArrayList<>(group.getOtherAdmins());
        updatedOtherAdmins.remove(user);

        Group updatedGroup = new Group.GroupBuilder()
            .name(group.getName())
            .description(group.getDescription())
            .photoPath(group.getPhotoPath())
            .primaryAdmin(group.getPrimaryAdmin())
            .users(updatedUsers)
            .otherAdmins(updatedOtherAdmins)
            .posts(new ArrayList<>(group.getPosts()))
            .build();

        // Update in database
        groupDataBase.updateGroup(updatedGroup);

        return updatedGroup;
    }
    public Group changeGroupPhoto(Group group, User primaryAdmin,String photoPath){
          if (!group.getPrimaryAdmin().equals(primaryAdmin)) {
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
            .build();
            groupDataBase.updateGroup(updatedGroup);
           return updatedGroup;

    }
    // Method to promote a user to admin
    public Group promoteToAdmin(Group group, User primaryAdmin, User userToPromote) {
        // Check if the action is performed by primary admin
        if (!group.getPrimaryAdmin().equals(primaryAdmin)) {
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
            .build();

        // Update in database
        groupDataBase.updateGroup(updatedGroup);

        return updatedGroup;
    }
    public Group demoteFromAdmin(Group group, User primaryAdmin, User userTodemote) {
        // Check if the action is performed by primary admin
        if (!group.getPrimaryAdmin().equals(primaryAdmin)) {
            return null;
        }

        // Ensure the user is an admin
        if (!group.getOtherAdmins().contains(userTodemote)) {
            return null;
        }
ArrayList<User> otherAdmins=new ArrayList<>(group.getOtherAdmins());
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
            .build();

        // Update in database
        groupDataBase.updateGroup(updatedGroup);

        return updatedGroup;
    }

    // Method to remove a member (for admins)
    public Group removeMember(Group group, User admin, User memberToRemove) {
        // Check if the admin has removal rights
        if (!isAdminWithRemovalRights(group, admin)) {
            return null;
        }

        // Cannot remove primary admin
        if (memberToRemove.equals(group.getPrimaryAdmin())) {
            return null;
        }
        //otheradmin cannot remove otheradmin
if(group.getOtherAdmins().contains(admin)&&group.getOtherAdmins().contains(memberToRemove)){
    return null;
    
}
        // Remove user from group
        ArrayList<User> updatedUsers = new ArrayList<>(group.getUsers());
        updatedUsers.remove(memberToRemove);

        // Remove from other admins if they are an admin
        ArrayList<User> updatedOtherAdmins = new ArrayList<>(group.getOtherAdmins());
        updatedOtherAdmins.remove(memberToRemove);

        Group updatedGroup = new Group.GroupBuilder()
            .name(group.getName())
            .description(group.getDescription())
            .photoPath(group.getPhotoPath())
            .primaryAdmin(group.getPrimaryAdmin())
            .users(updatedUsers)
            .otherAdmins(updatedOtherAdmins)
            .posts(new ArrayList<>(group.getPosts()))
            .build();

        // Update in database
        groupDataBase.updateGroup(updatedGroup);

        return updatedGroup;
    }

    // Helper method to check admin removal rights
    private boolean isAdminWithRemovalRights(Group group, User admin) {
        return group.getPrimaryAdmin().equals(admin) || 
               group.getOtherAdmins().contains(admin);
    }

   

    // Method to delete a group (only by primary admin)
    public boolean deleteGroup(Group group, User primaryAdmin) {
        return groupDataBase.deleteGroup(group, primaryAdmin);
    }
}
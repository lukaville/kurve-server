package model;

import java.io.Serializable;

/**
 * nickolay, 13.09.2014.
 */
public class UserProfile implements Serializable {
    private static final String ADMIN_DMITRY_VK_ID = "201443862";
    private static final String ADMIN_NICKOLAY_VK_ID = "95212144";

    private String id;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private int authProvider;
    private String socialID;
    private boolean isAdmin = false;

    public UserProfile(String id, String firstName, String lastName, String avatarUrl, int authProvider, String socialID) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarUrl = avatarUrl;
        this.authProvider = authProvider;
        this.socialID = socialID;
    }

    public UserProfile(String firstName, String lastName, String avatarUrl, int authProvider, String socialID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarUrl = avatarUrl;
        this.authProvider = authProvider;
        this.socialID = socialID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(int authProvider) {
        this.authProvider = authProvider;
    }

    public String getSocialID() {
        return socialID;
    }

    public void setSocialID(String socialID) {
        this.socialID = socialID;
    }

    public boolean isAdmin() {
        return socialID.equals(ADMIN_DMITRY_VK_ID) || socialID.equals(ADMIN_NICKOLAY_VK_ID) || isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}

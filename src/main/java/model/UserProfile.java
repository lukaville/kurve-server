package model;

import com.google.gson.*;
import frontend.response.GetUserResponse;

import java.lang.reflect.Type;

/**
 * nickolay, 13.09.2014.
 */
public class UserProfile {
    private static final String ADMIN_DMITRY_EMAIL = "didika914@gmail.com";

    private String login;
    private String password;
    private String email;
    private boolean isAdmin = false;

    public UserProfile(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;

        // TODO: remove this
        if (email.equals(ADMIN_DMITRY_EMAIL)) {
            isAdmin = true;
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public static class serializer implements JsonSerializer<UserProfile> {
        public JsonElement serialize(UserProfile src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("username", src.getLogin());
            jsonObject.addProperty("email", src.getEmail());
            jsonObject.addProperty("global_rating", 0);

            if (src.isAdmin()) {
                jsonObject.addProperty("isAdmin", true);
            }

            return jsonObject;
        }
    }
}

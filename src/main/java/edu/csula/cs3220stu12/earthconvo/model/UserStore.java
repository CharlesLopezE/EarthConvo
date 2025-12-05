package edu.csula.cs3220stu12.earthconvo.model;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserStore {

    private final Map<String, String> users = new HashMap<>();

    public UserStore() {
        // pre-existing users
        users.put("alice@example.com", "ali123");
        users.put("bob@example.com", "abcd");
    }

    public boolean emailExists(String email) {
        return users.containsKey(email);
    }

    public void addUser(String email, String password) {
        users.put(email, password);
    }

    public boolean isValidLogin(String email, String password) {
        return password.equals(users.get(email));
    }
}

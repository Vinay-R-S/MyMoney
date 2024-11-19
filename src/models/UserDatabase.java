package models;

import java.util.HashMap;

public class UserDatabase {
    private HashMap<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    public User authenticate(String email, String password) {
        User user = users.get(email);
        if (user != null) {
//            System.out.println("Stored Email: " + user.getEmail());
//            System.out.println("Entered Email: " + email);
//            System.out.println("Stored Hashed Password: " + user.getPassword());
//            System.out.println("Entered Password Hash: " + PasswordUtil.hashPassword(password));
            
            if (user.getPassword().equals(PasswordUtil.hashPassword(password))) {
                return user;
            }
        }
        return null;
    }
}

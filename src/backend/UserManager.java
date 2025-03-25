package backend;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users = new HashMap<>();
    private static final String USER_FILE = "users.txt";
    private LibraryModel currentLibrary;

    public UserManager() {
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    User user = new User(parts[0], "");
                    user.setSalt(parts[1]);
                    user.setHashedPassword(parts[2]);
                    users.put(parts[0], user);
                }
            }
        } catch (IOException e) {
            // File may not exist yet
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users.values()) {
                writer.write(user.getUsername() + "," + user.getSalt() + "," + user.getHashedPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public boolean createUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        User user = new User(username, password);
        users.put(username, user);
        saveUsers();
        return true;
    }

    public LibraryModel login(String username, String password, MusicStore store) {
        User user = users.get(username);
        if (user != null && user.verifyPassword(password)) {
            currentLibrary = new LibraryModel(store);
            currentLibrary.setUsername(username);
            currentLibrary.loadFromFile("users/" + username + ".dat", store);
            return currentLibrary;
        }
        return null;
    }

    public void saveCurrentLibrary() {
        if (currentLibrary != null) {
            currentLibrary.saveToFile("users/" + currentLibrary.getUsername() + ".dat");
        }
    }
}
package backend;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final String USER_DB_FILE = "users.txt";
    private Map<String, String> users = new HashMap<>(); 

    // Load users from a text file
    public void loadUsers() {
        try {
            File file = new File(USER_DB_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    
                    String[] userData = line.split(":");
                    if (userData.length == 2) {
                        String username = userData[0];
                        String passwordHash = userData[1];
                        users.put(username, passwordHash);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save a new user to the text file
    public void saveUser(User user) throws IOException {
        users.put(user.getUsername(), user.getPasswordHash());
        BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DB_FILE, true)); // Append mode
        writer.write(user.getUsername() + ":" + user.getPasswordHash() + "\n");
        writer.close();
    }

    // Check if a user already exists
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    // Retrieve the password hash for a user
    public String getPasswordHash(String username) {
        return users.get(username);
    }
}

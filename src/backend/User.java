package backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class User {
    private String username;
    private String salt;
    private String hashedPassword;

    public User(String username, String password) {
        this.username = username;
        this.salt = generateSalt();
        this.hashedPassword = hashPassword(password, salt);
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashed = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    public boolean verifyPassword(String password) {
        String hashedAttempt = hashPassword(password, salt);
        return hashedAttempt.equals(hashedPassword);
    }

    public String getUsername() { return username; }
    public String getSalt() { return salt; }
    public String getHashedPassword() { return hashedPassword; }
    public void setSalt(String salt) { this.salt = salt; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
}
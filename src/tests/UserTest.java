package tests;

import org.junit.jupiter.api.Test;

import backend.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testPasswordVerification() {
        User user = new User("testuser", "password");

        // Verify that the correct password is accepted and incorrect password is rejected
        assertTrue(user.verifyPassword("password"), "Correct password should be verified");
        assertFalse(user.verifyPassword("wrongpassword"), "Incorrect password should not be verified");
    }
}
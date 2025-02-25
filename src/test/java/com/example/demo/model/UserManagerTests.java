package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import com.example.demo.entity.User;
import java.util.List;
import java.util.Optional;

public class UserManagerTests {

    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
    }

    @Test
    @DisplayName("Should add user successfully")
    void testAddUser() {
        User user = userManager.addUser("John Doe", "john@example.com");

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    @DisplayName("Should increment user ID for each new user")
    void testAddMultipleUsers() {
        User user1 = userManager.addUser("John", "john@example.com");
        User user2 = userManager.addUser("Jane", "jane@example.com");

        assertEquals(1, user1.getId());
        assertEquals(2, user2.getId());
    }

    @Test
    @DisplayName("Should find user by ID when user exists")
    void testFindUserByIdWhenExists() {
        User addedUser = userManager.addUser("John", "john@example.com");
        Optional<User> foundUser = userManager.findUserById(addedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(addedUser.getId(), foundUser.get().getId());
        assertEquals(addedUser.getName(), foundUser.get().getName());
        assertEquals(addedUser.getEmail(), foundUser.get().getEmail());
    }

    @Test
    @DisplayName("Should return empty Optional when user ID not found")
    void testFindUserByIdWhenNotExists() {
        Optional<User> foundUser = userManager.findUserById(999);
        assertFalse(foundUser.isPresent());
    }

    @Test
    @DisplayName("Should delete user successfully when user exists")
    void testDeleteUserWhenExists() {
        User user = userManager.addUser("John", "john@example.com");
        boolean result = userManager.deleteUser(user.getId());

        assertTrue(result);
        assertTrue(userManager.getAllUsers().isEmpty());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent user")
    void testDeleteUserWhenNotExists() {
        boolean result = userManager.deleteUser(999);
        assertFalse(result);
    }

    @Test
    @DisplayName("Should get all users successfully")
    void testGetAllUsers() {
        userManager.addUser("John", "john@example.com");
        userManager.addUser("Jane", "jane@example.com");

        List<User> users = userManager.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("John", users.get(0).getName());
        assertEquals("Jane", users.get(1).getName());
    }

    @Test
    @DisplayName("Should return empty list when no users exist")
    void testGetAllUsersWhenEmpty() {
        List<User> users = userManager.getAllUsers();
        assertTrue(users.isEmpty());
    }
}

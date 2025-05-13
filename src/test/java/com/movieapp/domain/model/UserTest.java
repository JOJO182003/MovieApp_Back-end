package com.movieapp.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.movieapp.util.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final Role role = roleAdmin();
    private final Theatre theatre = theatreLyon1();
    private final LocalDateTime now = LocalDateTime.now();

    @Test
    void create_valid_user_should_succeed() {
        User user = new User(
                1,
                "john",
                "john@example.com",
                "hashed_password",
                role,
                List.of(theatre),
                now,
                now
        );

        assertEquals("john", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("admin", user.getRole().getName());
        assertEquals(now, user.getCreatedAt());
    }

    @Test
    void create_user_without_username_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(0, "", "x@x.com", "x", role, List.of(), now, now));
    }

    @Test
    void create_user_without_role_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(0, "valid", "valid@mail.com", "pwd", null, List.of(), now, now));
    }

    @Test
    void withHashedPassword_should_return_updated_user() {
        User original = ownerBdx();
        LocalDateTime later = original.getUpdatedAt().plusSeconds(1);

        User updated = original.withHashedPassword("newhash", later);

        assertEquals("newhash", updated.getPasswordHash());
        assertNotEquals(original.getUpdatedAt(), updated.getUpdatedAt());
        assertEquals(original.getUsername(), updated.getUsername());
    }

    @Test
    void withRole_should_change_user_role() {
        User user = standardUser();
        Role newRole = roleAdmin();
        LocalDateTime later = user.getUpdatedAt().plusSeconds(1);

        User updated = user.withRole(newRole, later);

        assertEquals("admin", updated.getRole().getName());
        assertEquals(user.getUsername(), updated.getUsername());
        assertNotEquals(user.getUpdatedAt(), updated.getUpdatedAt());
    }

}

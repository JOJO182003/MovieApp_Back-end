package com.movieapp.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void create_valid_role_should_succeed() {
        Role role = new Role(1, "ADMIN");

        assertEquals(1, role.getId());
        assertEquals("ADMIN", role.getName());
    }

    @Test
    void create_role_with_blank_name_should_fail() {
        assertThrows(IllegalArgumentException.class, () -> new Role(1, "  "));
    }

    @Test
    void create_role_with_null_name_should_fail() {
        assertThrows(IllegalArgumentException.class, () -> new Role(1, null));
    }

    @Test
    void withName_should_create_new_instance_with_updated_name() {
        Role original = new Role(1, "USER");
        Role updated = original.withName("MODERATOR");

        assertEquals("MODERATOR", updated.getName());
        assertEquals(1, updated.getId());
        assertNotSame(original, updated);
    }
}

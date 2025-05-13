package com.movieapp.util;

import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Méthodes utilitaires réutilisables dans les tests unitaires.
 */
public class MockUtils {

    /**
     * Simule un utilisateur authentifié dans le SecurityContext.
     * @param username le nom d'utilisateur à injecter
     */
    public static void mockAuthenticatedUser(String username) {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(username);
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);

        SecurityContext context = Mockito.mock(SecurityContext.class);
        Mockito.when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);
    }

    /**
     * Réinitialise le contexte de sécurité après un test.
     */
    public static void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }
}

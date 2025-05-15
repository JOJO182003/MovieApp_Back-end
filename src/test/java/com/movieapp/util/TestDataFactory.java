package com.movieapp.util;

import com.movieapp.api.rest.dto.request.CreateMovieRequest;
import com.movieapp.api.rest.dto.request.CreateMovieScheduleRequest;
import com.movieapp.api.rest.dto.request.CreateTheatreRequest;
import com.movieapp.api.rest.dto.request.CreateUserRequest;
import com.movieapp.domain.model.*;
import com.movieapp.infrastructure.persistence.entity.RoleEntity;
import com.movieapp.infrastructure.persistence.entity.UserEntity;
import com.movieapp.infrastructure.persistence.entity.UserTheatreAssignmentEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TestDataFactory {

    // ==== ROLES ====

    public static Role roleAdmin() {
        return new Role(1, "admin");
    }

    public static Role roleOwner() {
        return new Role(2, "propriétaires de cinéma");
    }

    public static Role roleModerator() {
        return new Role(3, "moderator");
    }

    public static RoleEntity roleEntityOwner() {
        return RoleEntity.builder()
                .id(2)
                .name("propriétaires de cinéma")
                .build();
    }

    // ==== THEATRES ====

    public static Theatre theatreLyon1() {
        return new Theatre(1, "Gaumont 1", "Lyon", "94 rue de la Ville, Lyon");
    }

    public static Theatre theatreMarseille3() {
        return new Theatre(3, "MK2 3", "Marseille", "109 rue de la Ville, Marseille");
    }

    public static Theatre theatreToulouse10() {
        return new Theatre(10, "UGC Ciné 10", "Toulouse", "34 rue de la Ville, Toulouse");
    }

    public static Theatre theatreLille8() {
        return new Theatre(8, "Gaumont 8", "Lille", "74 rue de la Ville, Lille");
    }

    public static Theatre theatreBordeaux11() {
        return new Theatre(11, "CGR Bordeaux", "Bordeaux", "48 rue Sainte-Catherine");
    }

    public static Theatre theatreNice12() {
        return new Theatre(12, "Ciné Lumière 12", "Nice", "10 avenue des Anglais");
    }

    // ==== CREATE THEATRE REQUESTS ====

    public static CreateTheatreRequest createTheatreRequestLyon1() {
        CreateTheatreRequest req =  new CreateTheatreRequest();
        req.setName(theatreLyon1().getName());
        req.setCity(theatreLyon1().getCity());
        req.setAddress(theatreLyon1().getAddress());
        return req;
    }


    // ==== USERS ====
    public static UserEntity ownerUserEntity() {
        return UserEntity.builder()
                .id(34)
                .username("owner1")
                .email("owner1@movieapp.com")
                .passwordHash("hashed_owner")
                .role(roleEntityOwner()) // lien vers le rôle
                .build();
    }

    public static CreateUserRequest createUserRequest() {
        CreateUserRequest req = new CreateUserRequest();
        req.setUsername("newuser");
        req.setEmail("newuser@movieapp.com");
        req.setPassword("securePass123");
        req.setRoleId(1);
        return req;
    }

    public static User adminUser() {
        return new User(31, "admin1", "admin1@movieapp.com", "hashed_admin",
                roleAdmin(), List.of(theatreLyon1()), now(), now());
    }

    public static User ownerBdx() {
        return new User(33, "owner_bdx", "bdx@movieapp.com", "hashed_bdx",
                roleOwner(), List.of(theatreBordeaux11()), now(), now());
    }

    public static User ownerLille() {
        return new User(34, "owner_lille", "lille@movieapp.com", "hashed_lille",
                roleOwner(), List.of(theatreLille8()), now(), now());
    }

    public static User standardUser() {
        return new User(35, "user35", "user35@movieapp.com", "hashed_user",
                roleModerator(), List.of(theatreMarseille3(), theatreToulouse10()), now(), now());
    }

    // ==== MOVIES ====

    public static Movie movieInception() {
        return new Movie(9, "Film 9", "Synopsis du film 9.", 127, "Anglais (VO st FR)",
                "James Cameron", 0, LocalDate.of(2010, 4, 15), now(), now());
    }

    public static Movie movieSpielberg() {
        return new Movie(10, "Film 10", "Synopsis du film 10.", 86, "Anglais (VO st FR)",
                "Steven Spielberg", 16, LocalDate.of(2021, 2, 1), now(), now());
    }

    public static Movie movieTarentino() {
        return new Movie(20, "Film 20", "Synopsis du film 20.", 155, "Allemand",
                "Quentin Tarantino", 0, LocalDate.of(2011, 3, 2), now(), now());
    }

    public static Movie movieFuturePast() {
        return new Movie(23, "Future Past", "Une boucle temporelle sans fin à New York", 145,
                "Anglais", "David Fincher", 16, LocalDate.of(2024, 6, 21), now(), now());
    }

    public static Movie movieFrenchDrama() {
        return new Movie(22, "Cinéma Vérité", "Un cinéma lutte contre les géants du streaming.",
                108, "Français", "Jean-Pierre Jeunet", 10, LocalDate.of(2022, 12, 15), now(), now());
    }

    // ==== SCHEDULES ====
    public static CreateMovieScheduleRequest createMovieScheduleRequest1() {
        CreateMovieScheduleRequest request = new CreateMovieScheduleRequest();
        request.setMovieId(1);
        request.setTheatreId(1);
        request.setStartTime(LocalDateTime.of(2025, 6, 1, 20, 0));
        return request;
    }


    public static MovieSchedule scheduleInceptionMarseille() {
        return new MovieSchedule(1001, movieInception(), theatreMarseille3(),
                LocalDate.of(2025, 6, 26), LocalDate.of(2025, 7, 13),
                "Lundi,Mardi,Mercredi", LocalTime.of(15, 30), now());
    }

    public static MovieSchedule scheduleFuturePastBdx() {
        return new MovieSchedule(1002, movieFuturePast(), theatreBordeaux11(),
                LocalDate.of(2025, 7, 5), LocalDate.of(2025, 7, 20),
                "Mardi,Jeudi,Samedi", LocalTime.of(14, 0), now());
    }

    public static MovieSchedule scheduleTarentinoLille() {
        return new MovieSchedule(1003, movieTarentino(), theatreLille8(),
                LocalDate.of(2025, 6, 2), LocalDate.of(2025, 6, 18),
                "Lundi,Dimanche,Samedi", LocalTime.of(19, 0), now());
    }

    public static MovieSchedule scheduleFrenchDramaNice() {
        return new MovieSchedule(1004, movieFrenchDrama(), theatreNice12(),
                LocalDate.of(2025, 7, 3), LocalDate.of(2025, 7, 17),
                "Mercredi,Samedi,Dimanche", LocalTime.of(20, 30), now());
    }

    // ==== USER-THEATRE ASSIGNMENTS ====

    public static UserTheatreAssignment assignmentAdminLyon() {
        return new UserTheatreAssignment(adminUser(), theatreLyon1(), now());
    }

    public static UserTheatreAssignment assignmentBdxOwner() {
        return new UserTheatreAssignment(ownerBdx(), theatreBordeaux11(), now());
    }

    public static UserTheatreAssignment assignmentStandardToulouse() {
        return new UserTheatreAssignment(standardUser(), theatreToulouse10(), now());
    }

    public static UserTheatreAssignment assignmentLilleOwner() {
        return new UserTheatreAssignment(ownerLille(), theatreLille8(), now());
    }

    // ==== UTILS ====

    private static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static CreateMovieRequest createMovieRequest1() {
        CreateMovieRequest request = new CreateMovieRequest();
        request.setTitle("Inception");
        request.setSynopsis("A mind-bending thriller");
        request.setDurationMinutes(148);
        return request;
    }

}

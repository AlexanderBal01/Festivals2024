package com.example.festivals2024;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.UserRepository;
import service.MusicGenreService;
import service.RegioService;

import java.util.Arrays;
import java.util.List;

@Component
public class InitDataConfig implements CommandLineRunner {
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MusicGenreService musicGenreService;

    @Autowired
    private RegioService regioService;

    @Override
    public void run(String... args) {
        insertUsers();
        insertMusicGenres();
        insertRegios();
        insertFestivals();
    }

    private void insertUsers() {
        var user1 =
                MyUser.builder()
                        .username("AlexanderBal")
                        .role(Role.USER)
                        .password(encoder.encode("password"))
                        .fullname("Alexander Bal")
                        .build();
        var user2 =
                MyUser.builder()
                        .username("Jules1")
                        .role(Role.USER)
                        .password(encoder.encode("test123"))
                        .fullname("Jules Pieters")
                        .build();
        var user3 =
                MyUser.builder()
                        .username("Geertje")
                        .role(Role.USER)
                        .password(encoder.encode("user3"))
                        .fullname("Geert Bal")
                        .build();
        var admin1 =
                MyUser.builder()
                        .username("admin")
                        .role(Role.ADMIN)
                        .password(encoder.encode("admin1"))
                        .fullname("Admin First")
                        .build();
        var admin2 =
                MyUser.builder()
                        .username("admin2")
                        .role(Role.ADMIN)
                        .password(encoder.encode("adminos"))
                        .fullname("Admin Adminos")
                        .build();

        List<MyUser> users = Arrays.asList(user1, user2, user3, admin1, admin2);

        userRepository.saveAll(users);
    }

    private void insertMusicGenres() {
        var drumAndBass =
                MusicGenre.builder()
                        .name("Drum and Bass")
                        .build();

        var pop =
                MusicGenre.builder()
                        .name("Pop")
                        .build();
        var rock =
                MusicGenre.builder()
                        .name("Rock")
                        .build();

        var metal =
                MusicGenre.builder()
                        .name("Metal")
                        .build();
        var hipHop =
                MusicGenre.builder()
                        .name("Hip Hop")
                        .build();

        List<MusicGenre> genres = Arrays.asList(drumAndBass, pop, rock, metal,hipHop);

        musicGenreService.saveAll(genres);
    }

    private void insertRegios() {
        var antwerpen =
                Regio.builder()
                        .name("Antwerpen")
                        .build();

        var brussel =
                Regio.builder()
                        .name("Brussel")
                        .build();

        var henegouwen =
                Regio.builder()
                        .name("Henegouwen")
                        .build();

        var limburg =
                Regio.builder()
                        .name("Limburg")
                        .build();

        var luik =
                Regio.builder()
                        .name("Luik")
                        .build();

        var oostVlaanderen =
                Regio.builder()
                        .name("Oost-Vlaanderen")
                        .build();

        var westVlaanderen =
                Regio.builder()
                        .name("West-Vlaanderen")
                        .build();



        var vlaamsBrabant =
                Regio.builder()
                        .name("Vlaams-Brabant")
                        .build();

        var luxemburg =
                Regio.builder()
                        .name("Luxemburg")
                        .build();

        var namen =
                Regio.builder()
                        .name("Namen")
                        .build();

        var waalsBrabant =
                Regio.builder()
                        .name("Waals-Brabant")
                        .build();

        List<Regio> regios = Arrays.asList(antwerpen, brussel, henegouwen, limburg, luik, oostVlaanderen, westVlaanderen, vlaamsBrabant, luxemburg, namen, waalsBrabant);

        regioService.saveAll(regios);
    }

    private void insertFestivals() {
        var luik = regioService.findByName("Luik");
        var antwerpen = regioService.findByName("Antwerpen");
        var westVlaanderen = regioService.findByName("West-Vlaanderen");
        var oostVlaanderen = regioService.findByName("Oost-Vlaanderen");
        var limburg = regioService.findByName("Limburg");
        var vlaamsBrabant = regioService.findByName("Vlaams-Brabant");
        var brussel = regioService.findByName("Brussel");

        var hipHop = musicGenreService.findByName("Hip Hop");
        var metal = musicGenreService.findByName("Metal");

        var lesArdentes =
                Festival.builder()
                        .name("Les Ardentes")
                        .regio(luik)
                        .musicGenre(hipHop)
                        .logoLocation("/images/logos/lesArdentes.jpeg")
                        .logoDescription("Logo les Ardentes")
                        .build();

        var graspop =
                Festival.builder()
                        .name("Graspop Medtal Meeting")
                        .regio(antwerpen)
                        .musicGenre(metal)
                        .logoLocation("/images/logos/graspop.png")
                        .logoDescription("Logo graspop")
                        .build();
    }
}

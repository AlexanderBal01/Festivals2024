package com.example.festivals2024;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.UserRepository;
import service.FestivalService;
import service.MusicGenreService;
import service.RegioService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private FestivalService festivalService;

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

        var elektronisch =
                MusicGenre.builder()
                        .name("Elektronisch")
                        .build();

        List<MusicGenre> genres = Arrays.asList(drumAndBass, pop, rock, metal, hipHop, elektronisch);

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        var luik = regioService.findByName("Luik");
        var antwerpen = regioService.findByName("Antwerpen");
        var westVlaanderen = regioService.findByName("West-Vlaanderen");
        var oostVlaanderen = regioService.findByName("Oost-Vlaanderen");
        var limburg = regioService.findByName("Limburg");
        var vlaamsBrabant = regioService.findByName("Vlaams-Brabant");
        var brussel = regioService.findByName("Brussel");

        var hipHop = musicGenreService.findByName("Hip Hop");
        var metal = musicGenreService.findByName("Metal");
        var elektronisch = musicGenreService.findByName("Elektronisch");
        var pop = musicGenreService.findByName("Pop");
        var drumAndBass = musicGenreService.findByName("Drum and Bass");
        var rock = musicGenreService.findByName("Rock");

        var lesArdentes =
                Festival.builder()
                        .name("Les Ardentes")
                        .regio(luik)
                        .musicGenre(hipHop)
                        .logoLocation("/images/logos/lesArdentes.jpeg")
                        .logoDescription("Logo les Ardentes")
                        .datum(LocalDateTime.parse("11-07-2024 12:30", formatter))
                        .ticket(200)
                        .ticketPrijs(75.00)
                        .build();

        var graspop =
                Festival.builder()
                        .name("Graspop Medtal Meeting")
                        .regio(antwerpen)
                        .musicGenre(metal)
                        .logoLocation("/images/logos/graspop.png")
                        .logoDescription("Logo Graspop Metal Meeting")
                        .datum(LocalDateTime.parse("20-06-2024 11:25", formatter))
                        .ticket(200)
                        .ticketPrijs(75.00)
                        .build();

        var tomorrowland =
                Festival.builder()
                        .name("Tomorrowland")
                        .regio(antwerpen)
                        .musicGenre(elektronisch)
                        .logoLocation("/images/logos/tomorrowland.png")
                        .logoDescription("Logo Tomorrowland")
                        .datum(LocalDateTime.parse("19-07-2024 12:00", formatter))
                        .ticket(200)
                        .ticketPrijs(75.00)
                        .build();

        var wecandance =
                Festival.builder()
                        .name("WECANDANCE")
                        .regio(westVlaanderen)
                        .musicGenre(elektronisch)
                        .logoLocation("/images/logos/wecandance.jpeg")
                        .logoDescription("Logo WECANDANCE")
                        .datum(LocalDateTime.parse("03-08-2024 14:00", formatter))
                        .ticket(200)
                        .ticketPrijs(75.00)
                        .build();

        var lokerseFeesten =
                Festival.builder()
                        .name("Lokerse Feesten")
                        .regio(oostVlaanderen)
                        .musicGenre(pop)
                        .logoLocation("/images/logos/lokerseFeesten.jpeg")
                        .logoDescription("Logo Lokerse Feesten")
                        .datum(LocalDateTime.parse("02-08-2024 20:00", formatter))
                        .ticket(200)
                        .ticketPrijs(75.00)
                        .build();

        var rampage =
                Festival.builder()
                        .name("Rampage Open Air")
                        .regio(limburg)
                        .musicGenre(drumAndBass)
                        .logoLocation("/images/logos/rampage.jpeg")
                        .logoDescription("Logo Rampage Open Air")
                        .datum(LocalDateTime.parse("05-07-2024 14:00", formatter))
                        .ticket(200)
                        .ticketPrijs(75.00)
                        .build();

        var sunrise =
                Festival.builder()
                        .name("Sunrise Festival")
                        .regio(antwerpen)
                        .musicGenre(elektronisch)
                        .logoLocation("/images/logos/sunrise.png")
                        .logoDescription("Logo Sunrise Festival")
                        .datum(LocalDateTime.parse("28-06-2024 14:00", formatter))
                        .ticket(200)
                        .ticketPrijs(75.00)
                        .build();

        var rockWerchter =
                Festival.builder()
                        .name("Rock Werchter")
                        .regio(vlaamsBrabant)
                        .musicGenre(rock)
                        .logoLocation("/images/logos/rockWerchter.jpeg")
                        .logoDescription("Logo Rock Werchter")
                        .datum(LocalDateTime.parse("04-07-2024 12:45", formatter))
                        .ticket(200)
                        .ticketPrijs(75.00)
                        .build();

        var couleurCafe =
                Festival.builder()
                        .name("Couleur Cafe")
                        .regio(brussel)
                        .musicGenre(hipHop)
                        .logoLocation("/images/logos/couleurCafe.jpeg")
                        .logoDescription("Logo Couleur Cafe")
                        .datum(LocalDateTime.parse("28-06-2024 17:00", formatter))
                        .ticket(200)
                        .ticketPrijs(75.00)
                        .build();

        List<Festival> festivals = Arrays.asList(lesArdentes, graspop, tomorrowland, wecandance, lokerseFeesten, rampage, sunrise, rockWerchter, couleurCafe);
        festivalService.saveAll(festivals);
    }
}

package com.example.festivals2024;

import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import repository.UserRepository;
import service.ArtistService;
import service.FestivalService;
import validator.ArtistValidation;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AddControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FestivalService festivalService;

    @Mock
    private ArtistService artistService;

    @Mock
    private ArtistValidation artistValidation;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @Mock
    private BindingResult result;

    @InjectMocks
    private AddController addController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPerformanceFestival() {
        MyUser user = new MyUser(1, "user", "password", "user user", Role.USER, null);
        Festival festival = new Festival();
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(festivalService.findById(anyInt())).thenReturn(Optional.of(festival));

        String viewName = addController.performanceFestival(1, model, principal);

        verify(model).addAttribute("user", null);
        verify(model).addAttribute(eq("artist"), any(Artist.class));
        verify(model).addAttribute("festival", festival);
        assertEquals("add/performance", viewName);
    }

    @Test
    public void testAddPerformance_HasErrors() {
        MyUser user = new MyUser();
        Festival festival = new Festival();
        Artist artist = new Artist();
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(festivalService.findById(anyInt())).thenReturn(Optional.of(festival));
        when(result.hasErrors()).thenReturn(true);

        String viewName = addController.addPerformance(1, artist, result, model, principal);

        verify(model).addAttribute("user", null);
        verify(model).addAttribute("artist", artist);
        verify(model).addAttribute("festival", festival);
        assertEquals("add/performance", viewName);
    }

    @Test
    public void testAddPerformance_NoErrors() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        MyUser user = new MyUser();
        MusicGenre genre = MusicGenre.builder().musicgenreid(1).name("Drum and bass").build();
        Regio regio = Regio.builder().regioid(1).name("Limburg").build();
        Festival festival = new Festival(1, "Rampage Open Air", null, null, LocalDateTime.parse("11-07-2024 12:00", formatter), genre, regio, new ArrayList<>(), 200, 75.00, new ArrayList<>());
        Artist artist = new Artist(1, "Used", festival, null, "Jump-Up", null, "3000", "4000");
        artist.setStartingHour(LocalDateTime.parse("11-07-2024 12:00", formatter));
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(festivalService.findById(anyInt())).thenReturn(Optional.of(festival));
        when(result.hasErrors()).thenReturn(false);
        when(artistService.save(any(Artist.class))).thenReturn(artist);

        String viewName = addController.addPerformance(1, artist, result, model, principal);

        verify(artistService).save(artist);
        verify(festivalService).save(festival);
        assertEquals("redirect:/home/overview?artistAdded", viewName);
    }

    @Test
    public void testDeletePerformance() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        MusicGenre genre = MusicGenre.builder().musicgenreid(1).name("Drum and bass").build();
        Regio regio = Regio.builder().regioid(1).name("Limburg").build();
        Festival festival = Festival.builder().festivalid(1).name("Rampage Open Air").logoDescription(null).logoLocation(null).datum(LocalDateTime.parse("11-07-2024 12:00", formatter)).musicGenre(genre).regio(regio).artists(new ArrayList<>()).ticket(200).ticketPrijs(75.00).festivalVisitors(new ArrayList<>()).build();
        Artist artist = new Artist(1, "Used", festival, null, "Jump-Up", null, "3000", "4000");
        artist.setStartingHour(LocalDateTime.parse("11-07-2024 12:00", formatter));
        festival.getArtists().add(artist);
        when(festivalService.findById(1)).thenReturn(Optional.of(festival));
        when(artistService.findById(1)).thenReturn(Optional.of(artist));

        String viewName = addController.deletePerformance(1, "1");

        verify(artistService).delete(artist);
        verify(festivalService).save(festival);
        assertEquals("redirect:/home/overview?artistDeleted", viewName);
    }
}

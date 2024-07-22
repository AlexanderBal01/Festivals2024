package com.example.festivals2024;

import domain.Festival;
import domain.MusicGenre;
import domain.MyUser;
import domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import repository.UserRepository;
import service.FestivalService;
import service.MusicGenreService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GenreControllerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private MusicGenreService musicGenreService;

    @InjectMocks
    private FestivalService festivalService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @Mock
    private GenreController genreController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenreFestivalOverview() {
        MyUser user = new MyUser(1, "user", "password", "user user", Role.USER, null);
        MusicGenre genre = new MusicGenre();
        Festival festival = new Festival();
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(musicGenreService.findById(anyInt())).thenReturn(Optional.of(genre));
        when(festivalService.findFestivalsByMusicGenre_MusicgenreidOrderByRegioAscDatumAsc(1)).thenReturn(List.of(festival));

        String viewName = genreController.genreFestivalOverview(1, model, principal);

        verify(model).addAttribute("user", null);
        verify(model).addAttribute("genre", genre);
        verify(model).addAttribute("festival", festival);
        assertEquals("genre/overview", viewName);
    }
}

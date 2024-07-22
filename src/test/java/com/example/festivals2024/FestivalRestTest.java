package com.example.festivals2024;

import domain.*;
import exceptions.FestivalGenreNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import service.FestivalService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
public class FestivalRestTest {
    @Mock
    private FestivalService mock;

    private FestivalRestController controller;
    private MockMvc mockMvc;

    private final int GENRE_ID = 1;
    private final int FESTIVAL_ID = 1;
    private final String FESTIVAL_NAME = "Rampage Open Air";

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
        controller = new FestivalRestController();
        mockMvc = standaloneSetup(controller).build();
        ReflectionTestUtils.setField(controller, "festivalService", mock);
    }

    private Festival aFestival(int id, String name, String logoLocation, String logoDescription, LocalDateTime datum, MusicGenre musicGenre, Regio regio, List<Artist> artists, Integer tickets, Double prijs, List<MyUser> visitors) {
        return new Festival(id, name, logoLocation, logoDescription, datum, musicGenre, regio, artists, tickets, prijs, visitors);
    }

    @Test
    public void targetFestivalsGenre_notFound() throws Exception {
        Mockito.when(mock.findFestivalsByMusicGenre_Musicgenreid(GENRE_ID)).thenThrow(new FestivalGenreNotFoundException(GENRE_ID));
        Exception exception = assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/festival/genre/" + GENRE_ID)).andReturn();
        });
        assertTrue(exception.getCause() instanceof FestivalGenreNotFoundException);
        Mockito.verify(mock).findFestivalsByMusicGenre_Musicgenreid(GENRE_ID);
    }

    @Test
    public void targetFestivals_noEmptyList() throws Exception {
        MusicGenre genre = MusicGenre.builder().musicgenreid(GENRE_ID).festivals(new HashSet<>()).build();

        Festival festival = aFestival(FESTIVAL_ID, FESTIVAL_NAME, null, null, null, genre, null, new ArrayList<>(), null, null, null);
        genre.getFestivals().add(festival);

        List<Festival> list = List.of(festival);

        Mockito.when(mock.findFestivalsByMusicGenre_Musicgenreid(GENRE_ID)).thenReturn(list);

        mockMvc.perform(get("/festival/genre/" + GENRE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].festival_id").value(FESTIVAL_ID))
                .andExpect(jsonPath("$[0].festival_name").value(FESTIVAL_NAME));

        Mockito.verify(mock).findFestivalsByMusicGenre_Musicgenreid(GENRE_ID);
    }
}
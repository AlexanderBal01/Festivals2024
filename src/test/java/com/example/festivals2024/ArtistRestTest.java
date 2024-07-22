package com.example.festivals2024;

import domain.Artist;
import domain.Festival;
import exceptions.ArtistFestivalNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import service.ArtistService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
class ArtistRestTest {

	@Mock
	private ArtistService mock;
	
	private ArtistRestController controller;
	private MockMvc mockMvc;

	private final int FESTIVAL_ID = 1;
	private final int ARTIST_ID = 1;
	private final String NAME = "USED";

    @BeforeEach
	public void before() {
		MockitoAnnotations.openMocks(this);
		controller = new ArtistRestController();
		mockMvc = standaloneSetup(controller).build();
		ReflectionTestUtils.setField(controller, "artistService", mock);
	}

	private Artist anArtist(int id, String name, Festival festival, LocalDateTime dateTime, String subgenre1, String subgenre2, String festivalNummer1, String FestivalNummer2) {
        return new Artist(id, name, festival, dateTime, subgenre1, subgenre2, festivalNummer1, FestivalNummer2);
	}
	
	@Test
	public void testgetArtistsFestival_notFound() throws Exception {
		Mockito.when(mock.findArtistsByBooking_Festivalid(FESTIVAL_ID)).thenThrow(new ArtistFestivalNotFoundException(FESTIVAL_ID));
		Exception exception = assertThrows(Exception.class, () -> {
			mockMvc.perform(get("/artist/festival/" + FESTIVAL_ID)).andReturn();
	    });
		assertTrue(exception.getCause() instanceof ArtistFestivalNotFoundException);
		Mockito.verify(mock).findArtistsByBooking_Festivalid(FESTIVAL_ID);
	}

	@Test
	public void testgetArtistsFestival_noEmptyList() throws Exception {
		Festival festival = Festival.builder().festivalid(FESTIVAL_ID).artists(new ArrayList<>()).build();

		Artist artist1 = anArtist(ARTIST_ID, NAME, festival, null, null, null, null, null);

		Artist artist2 = anArtist(2, "HEDEX", festival, null, null, null, null, null);
		festival.getArtists().add(artist1);
		festival.getArtists().add(artist2);
		List<Artist> listFruit = List.of(artist1, artist2);

		Mockito.when(mock.findArtistsByBooking_Festivalid(FESTIVAL_ID)).thenReturn(listFruit);

		mockMvc.perform(get("/artist/festival/"+ FESTIVAL_ID))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$[0].artist_id").value(ARTIST_ID))
				.andExpect(jsonPath("$[0].artist_name").value(NAME))
				.andExpect(jsonPath("$[1].artist_id").value(2))
				.andExpect(jsonPath("$[1].artist_name").value("HEDEX"));
		
		Mockito.verify(mock).findArtistsByBooking_Festivalid(FESTIVAL_ID);
	}

}

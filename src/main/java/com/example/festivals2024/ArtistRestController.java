package com.example.festivals2024;

import domain.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ArtistService;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistRestController {
    @Autowired
    private ArtistService artistService;

    @GetMapping(value = "/festival/{festivalId}")
    public List<Artist> getArtistsFestival(@PathVariable int festivalId) {
        return artistService.findArtistsByBooking_Festivalid(festivalId);
    }
}

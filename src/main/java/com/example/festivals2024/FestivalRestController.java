package com.example.festivals2024;

import domain.Festival;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.FestivalService;

import java.util.List;

@RestController
@RequestMapping("/festival")
public class FestivalRestController {
    @Autowired
    private FestivalService festivalService;

    @GetMapping(value = "/genre/{genreId}")
    public List<Festival> getFestivalByGenre(@PathVariable int genreId) {
        return festivalService.findFestivalsByMusicGenre_Musicgenreid(genreId);
    }
}

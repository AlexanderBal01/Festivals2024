package com.example.festivals2024;

import domain.Festival;
import domain.MusicGenre;
import domain.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.UserRepository;
import service.FestivalService;
import service.MusicGenreService;

import java.security.Principal;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MusicGenreService musicGenreService;

    @Autowired
    private FestivalService festivalService;

    @GetMapping(value = "/{genreId}/overview")
    public String genreFestivalOverview(@PathVariable Integer genreId, Model model, Principal principal) {
        MyUser user = userRepository.findByUsername(principal.getName());
        Optional<MusicGenre> musicGenre = musicGenreService.findById(genreId);
        List<Festival> festivals = festivalService.findFestivalsByMusicGenre_MusicgenreidOrderByRegio(genreId);

        Dictionary<Integer, Integer> festivalUserTickets = new Hashtable<>();

        model.addAttribute("user", user);

        model.addAttribute("festivals", festivals);

        if (musicGenre.isPresent()) {
            model.addAttribute("musicGenre", musicGenre.get());
            for (int i = 0; i < musicGenre.get().getFestivals().size(); i++) {
                Festival festival = festivals.get(i);
                long userTickets = festival.getFestivalVisitors().stream().filter(user::equals).count();
                festivalUserTickets.put(festival.getFestivalid(), (int) userTickets);
                i++;
            }

            model.addAttribute("userTickets", festivalUserTickets);
        }

        return "genre/overview";
    }
}

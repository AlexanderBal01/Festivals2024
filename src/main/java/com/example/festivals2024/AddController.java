package com.example.festivals2024;

import domain.Artist;
import domain.Festival;
import domain.MyUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import repository.UserRepository;
import service.ArtistService;
import service.FestivalService;
import validator.ArtistValidation;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/add")
public class AddController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FestivalService festivalService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ArtistValidation artistValidation;

    @GetMapping(value = "/performance/festival/{festivalId}")
    public String performanceFestival(@PathVariable int festivalId, Model model, Principal principal) {
        MyUser user = userRepository.findByUsername(principal.getName());
        Optional<Festival> festival = festivalService.findById(festivalId);

        model.addAttribute("user", user);

        Artist artist = new Artist();

        model.addAttribute("artist", artist);

        festival.ifPresent(value -> model.addAttribute("festival", value));

        return "add/performance";
    }

    @PostMapping(value = "/performance/festival/{festivalId}/add")
    public String addPerformance(@PathVariable int festivalId, @Valid Artist artist, BindingResult result, Model model, Principal principal) {
        artistValidation.validate(artist, result);
        MyUser user = userRepository.findByUsername(principal.getName());
        Optional<Festival> festival = festivalService.findById(festivalId);

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("artist", artist);
            festival.ifPresent(value -> model.addAttribute("festival", value));
            return "add/performance";
        }

        if (festival.isPresent()) {
            boolean bezet = festival.get().getArtists().stream().anyMatch(a -> a.getStartingHour().equals(artist.getStartingHour()));
            if (bezet) {
                model.addAttribute("user", user);
                model.addAttribute("artist", artist);
                model.addAttribute("festival", festival.get());
                model.addAttribute("errorStartingHour", "error");
                return "add/performance";
            }

            artist.setBooking(festival.get());
            festival.get().getArtists().add(artist);
            artistService.save(artist);
            festivalService.save(festival.get());

        }
        return "redirect:/home/overview?artistAdded";
    }

    @PostMapping(value = "/performance/festival/{festivalId}/delete")
    public String deletePerformance(@PathVariable int festivalId, @RequestPayload String artistId) {
        Integer artistIdNumber = Integer.parseInt(artistId);
        Optional<Festival> festival = festivalService.findById(festivalId);
        Optional<Artist> artist = artistService.findById(artistIdNumber);
        if (festival.isPresent() && artist.isPresent()) {
            festival.get().getArtists().remove(artist.get());
            festivalService.save(festival.get());
            artistService.delete(artist.get());
        }

        return "redirect:/home/overview?artistDeleted";
    }
}

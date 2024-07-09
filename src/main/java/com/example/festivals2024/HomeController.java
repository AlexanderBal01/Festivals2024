package com.example.festivals2024;

import domain.MusicGenre;
import domain.MyUser;
import domain.Regio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repository.UserRepository;
import service.MusicGenreService;
import service.RegioService;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MusicGenreService musicGenreService;

    @Autowired
    private RegioService regioService;

    @GetMapping(value = "/overview")
    public String overviewHome(Model model, Principal principal, @RequestParam(value = "artistAdded", required = false) String artistAdded, @RequestParam(value = "ticketsPurchased", required = false) String tickets, @RequestParam(value = "artistDeleted", required = false) String artistDeleted) {
        MyUser user = userRepository.findByUsername(principal.getName());
        Iterable<MusicGenre> musicGenres = musicGenreService.findAll();
        Iterable<Regio> regios = regioService.findAll();

        if (artistAdded != null) {
            model.addAttribute("artistAdded", "test");
        }
        if (artistDeleted != null) {
            model.addAttribute("artistDeleted", "test2");
        }
        if (tickets != null) {
            model.addAttribute("tickets", tickets);
        }

        model.addAttribute("user", user);
        model.addAttribute("musicGenres", musicGenres);
        model.addAttribute("regios", regios);

        return"home/overview";
    }
}

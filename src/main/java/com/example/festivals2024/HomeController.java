package com.example.festivals2024;

import domain.MusicGenre;
import domain.MyUser;
import domain.Regio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String overviewHome(Model model, Principal principal) {
        MyUser user = userRepository.findByUsername(principal.getName());
        Iterable<MusicGenre> musicGenres = musicGenreService.findAll();
        Iterable<Regio> regios = regioService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("musicGenres", musicGenres);
        model.addAttribute("regios", regios);

        return"home/overview";
    }
}

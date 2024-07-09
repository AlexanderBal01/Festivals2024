package com.example.festivals2024;

import domain.Festival;
import domain.MyUser;
import domain.Regio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.UserRepository;
import service.FestivalService;
import service.RegioService;

import java.security.Principal;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/region")
public class RegioController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegioService regioService;
    @Autowired
    private FestivalService festivalService;

    @GetMapping(value = "/{regioId}/overview")
    public String overview(@PathVariable Integer regioId, Model model, Principal principal) {
        MyUser user = userRepository.findByUsername(principal.getName());
        Optional<Regio> regio = regioService.findById(regioId);
        List<Festival> festivals = festivalService.findFestivalsByRegio_RegioidOrderByMusicGenreAscDatumAsc(regioId);

        Dictionary<Integer, Integer> festivalUserTickets = new Hashtable<>();

        model.addAttribute("user", user);

        model.addAttribute("festivals", festivals);

        if (regio.isPresent()) {
            model.addAttribute("regio", regio.get());
            for (Festival festival : festivals) {
                long userTickets = festival.getFestivalVisitors().stream().filter(user::equals).count();
                festivalUserTickets.put(festival.getFestivalid(), (int) userTickets);
            }

            model.addAttribute("userTickets", festivalUserTickets);
        }

        return "regio/overview";
    }
}

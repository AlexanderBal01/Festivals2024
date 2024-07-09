package com.example.festivals2024;

import domain.Festival;
import domain.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import repository.UserRepository;
import service.FestivalService;

import java.util.Optional;

@Controller
@RequestMapping("/buy")
public class BuyController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FestivalService festivalService;

    @GetMapping(value = "/festival/{festivalId}/tickets/{userName}")
    public String festivalTickets(@PathVariable int festivalId, Model model, @PathVariable String userName) {
        MyUser user = userRepository.findByUsername(userName);
        Optional<Festival> festival = festivalService.findById(festivalId);

        model.addAttribute("user", user);

        if (festival.isPresent()) {
            model.addAttribute("festival", festival.get());
            long userTickets = festival.get().getFestivalVisitors().stream().filter(user::equals).count();
            model.addAttribute("userTickets", userTickets);
        }

        return "buy/tickets";
    }

    @PostMapping(value = "/festival/{festivalId}/tickets/{userName}/buy")
    public String festivalTicketsBuy(@PathVariable int festivalId, Model model, @PathVariable String userName, @RequestPayload Integer aantal) {
        MyUser user = userRepository.findByUsername(userName);
        Optional<Festival> festival = festivalService.findById(festivalId);
        if (festival.isPresent()) {
            long userTicketsFestival = festival.get().getFestivalVisitors().stream().filter(user::equals).count();
            long userTickets = user.getTickets().size();
            if (userTicketsFestival+aantal > 15 || userTickets >= 50) {
                model.addAttribute("festival", festival.get());
                model.addAttribute("userTickets", userTicketsFestival);
                model.addAttribute("user", user);
                model.addAttribute("errorAantal", "error");
                return "buy/tickets";
            }

            for (int i = 0; i < aantal; i++) {
                user.getTickets().add(festival.get());
                festival.get().getFestivalVisitors().add(user);
                Integer overgeblevenTickets = festival.get().getTicket() - 1;
                festival.get().setTicket(overgeblevenTickets);
                userRepository.save(user);
                festivalService.save(festival.get());
            }
        }

        return "redirect:/home/overview?ticketsPurchased=" + aantal;
    }
}

package com.example.festivals2024;

import domain.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showTickets(Model model, Principal principal) {
        MyUser user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);

        return "tickets/tickets";
    }
}

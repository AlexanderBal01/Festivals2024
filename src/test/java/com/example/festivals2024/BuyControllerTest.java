package com.example.festivals2024;

import domain.Festival;
import domain.MyUser;
import domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import repository.UserRepository;
import service.FestivalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BuyControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FestivalService festivalService;

    @Mock
    private Model model;

    @Mock
    private BindingResult result;

    @InjectMocks
    private BuyController buyController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFestivalTickets() {
        MyUser user = new MyUser(1, "user", "password", "user user", Role.USER, new ArrayList<>());
        Festival festival = Festival.builder().ticket(200).festivalid(1).festivalVisitors(List.of(user)).build();
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(festivalService.findById(1)).thenReturn(Optional.of(festival));

        String viewName = buyController.festivalTickets(1, model, user.getUsername());

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("userTickets", 1L);
        verify(model).addAttribute("festival", festival);
        assertEquals("buy/tickets", viewName);
    }

    @Test
    public void testfestivalTicketsBuy_HasErrors() {
        MyUser user = new MyUser(1, "user", "password", "user user", Role.USER, new ArrayList<>());
        Festival festival = Festival.builder().ticket(200).festivalid(1).festivalVisitors(new ArrayList<>()).build();
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(festivalService.findById(1)).thenReturn(Optional.of(festival));
        when(result.hasErrors()).thenReturn(true);

        String viewName = buyController.festivalTicketsBuy(1, model, user.getUsername(), 16);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("userTickets", 0L);
        verify(model).addAttribute("festival", festival);
        assertEquals("buy/tickets", viewName);
    }

    @Test
    public void testfestivalTicketsBuy_NoErrors() {
        MyUser user = new MyUser(1, "user", "password", "user user", Role.USER, new ArrayList<>());
        Festival festival = Festival.builder().ticket(200).festivalid(1).festivalVisitors(new ArrayList<>()).build();
        user.getTickets().add(festival);
        festival.setTicket(festival.getTicket() - 1);
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(festivalService.findById(1)).thenReturn(Optional.of(festival));
        when(result.hasErrors()).thenReturn(false);
        when(userRepository.save(any(MyUser.class))).thenReturn(user);

        String viewName = buyController.festivalTicketsBuy(1, model, user.getUsername(), 1);

        verify(userRepository).save(user);
        assertEquals("redirect:/home/overview?ticketsPurchased=" + 1, viewName);
    }

}

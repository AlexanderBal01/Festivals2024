package com.example.festivals2024;

import domain.MyUser;
import domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import repository.UserRepository;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class SecurityTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MyUser normalUser = MyUser.builder().username("user").password("admin").role(Role.USER).fullname("user name").build();
        MyUser adminUser = MyUser.builder().username("admin").password("adminos").role(Role.ADMIN).fullname("admin name").build();

        GrantedAuthority authorityUser = new SimpleGrantedAuthority("ROLE_USER");
        GrantedAuthority authorityAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");

        User user = new User(normalUser.getUsername(), normalUser.getPassword(), Collections.singletonList(authorityUser));
        User admin = new User(adminUser.getUsername(), adminUser.getPassword(), Collections.singletonList(authorityAdmin));

        when(userDetailsService.loadUserByUsername("user")).thenReturn(user);
        when(userRepository.findByUsername("user")).thenReturn(normalUser);

        when(userDetailsService.loadUserByUsername("admin")).thenReturn(admin);
        when(userRepository.findByUsername("admin")).thenReturn(adminUser);
    }

    @Test
    public void loginGet() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"));
    }

    @Test
    public void accesDeniedPageGet() throws Exception {
        mockMvc.perform(get("/403"))
                .andExpect(status().isOk())
                .andExpect(view().name("403/403"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testAccessWithUserRole() throws Exception {
        mockMvc.perform(get("/home/overview"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/overview"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testAccessWithAdmin () throws Exception {
        mockMvc.perform(get("/home/overview")).andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void testAnonymousAccess() throws Exception {
        mockMvc.perform(get("/home/overview")).andExpect(redirectedUrlPattern("**/login"));
    }
}

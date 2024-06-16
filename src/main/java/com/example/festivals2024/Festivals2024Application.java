package com.example.festivals2024;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import service.MusicGenreService;
import service.MyUserDetailService;
import service.RegioService;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class Festivals2024Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Festivals2024Application.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/music/overview");
        registry.addViewController("/403").setViewName("403/403");
    }

    @Bean
    UserDetailsService myUserDetailsService() {
        return new MyUserDetailService();
    }

    @Bean
    MusicGenreService musicGenreService() { return new MusicGenreService(); }

    @Bean
    RegioService regioService() { return new RegioService(); }
}

package com.example.festivals2024;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import perform.PerformRestFestival;
import service.*;
import validator.ArtistValidation;

import java.util.Locale;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class Festivals2024Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Festivals2024Application.class, args);

        try {
            new PerformRestFestival();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/home/overview");
        registry.addViewController("/403").setViewName("403/403");
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("language/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


    @Bean
    UserDetailsService myUserDetailsService() {
        return new MyUserDetailService();
    }

    @Bean
    MusicGenreService musicGenreService() { return new MusicGenreService(); }

    @Bean
    RegioService regioService() { return new RegioService(); }

    @Bean
    FestivalService festivalService() { return new FestivalService(); }

    @Bean
    ArtistService artistService() { return new ArtistService(); }

    @Bean
    ArtistValidation artistValidation() { return new ArtistValidation(); }
}

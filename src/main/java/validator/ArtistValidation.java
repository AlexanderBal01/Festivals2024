package validator;

import domain.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

public class ArtistValidation implements Validator {
    @Autowired
    private MessageSource messageSource;


    @Override
    public boolean supports(Class<?> klass) {
        return Artist.class.isAssignableFrom(klass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Artist artist = (Artist) target;
        Integer festivalNummer1 = null;
        Integer festivalNummer2 = null;
        if (!artist.getFestivalnummer1().isEmpty() && !artist.getFestivalnummer2().isEmpty()) {
            festivalNummer1 = Integer.parseInt(artist.getFestivalnummer1());
            festivalNummer2 = Integer.parseInt(artist.getFestivalnummer2());
        }
        if (!Objects.equals(artist.getSubgenre1(), "") && !Objects.equals(artist.getFestivalnummer2(), "")) {
            if (artist.getSubgenre1().equals(
                    artist.getSubgenre2())) {
                errors.rejectValue("subgenre2",
                        "validator.artist.subgenre",
                        messageSource.getMessage("validator.artist.subgenre", null, LocaleContextHolder.getLocale())
                        );
            }
        }

        if (festivalNummer1 == null || festivalNummer1 <= 1000 && festivalNummer1 >= 9999 && festivalNummer1 % 3 > 0) {
            errors.rejectValue("festivalnummer1",
                    "validator.artist.festivalnummer1",
                    messageSource.getMessage("validator.artist.festivalnummer1", null, LocaleContextHolder.getLocale())
                    );
        }
        if (festivalNummer2 == null || ((festivalNummer2 > (festivalNummer1+1000)) || festivalNummer2<festivalNummer1)) {
            errors.rejectValue("festivalnummer2",
                    "validator.artist.festivalnummer2",
                    messageSource.getMessage("validator.artist.festivalnummer2", null, LocaleContextHolder.getLocale())
            );
        }
    }
}

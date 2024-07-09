package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class StartingHourValidator implements ConstraintValidator<ValidStartingHour, LocalDateTime>
{
    @Override
    public void initialize(ValidStartingHour constraintAnnotation) {}

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        
        return value != null && value.getHour() >= 10 && value.getHour() <= 22 && value.getMinute() == 0;
    }
}
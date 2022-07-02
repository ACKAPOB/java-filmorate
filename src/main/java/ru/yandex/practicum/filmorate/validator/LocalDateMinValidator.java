package ru.yandex.practicum.filmorate.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocalDateMinValidator implements
        ConstraintValidator<LocalDateMin, LocalDate> { // указываем аннотацию и класс в котором будем ее пользовать

    private LocalDate minDate;

    @Override
    public void initialize(LocalDateMin annotation) {
        this.minDate = LocalDate.parse(annotation.value());
    }

    @Override
    public boolean isValid(LocalDate value,
                           ConstraintValidatorContext context) {
        return value != null && !value.isBefore(minDate);
    }
}

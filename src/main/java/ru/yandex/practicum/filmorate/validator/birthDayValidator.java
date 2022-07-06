package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
@Slf4j
public class birthDayValidator implements
        ConstraintValidator<birthDay, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getBirthday().isBefore(LocalDate.now());
    }
}

package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;;
import java.time.LocalDate;

public class birthDayValidator implements
        ConstraintValidator<birthDay, User> {

    @Override
    public void initialize(birthDay constraintAnnotation) {
        //ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(LocalDate.now() + " LocalDate.now()");
        System.out.println(user.getBirthday() + " user.getBirthday()");
        return user.getBirthday().isBefore(LocalDate.now());
    }
}

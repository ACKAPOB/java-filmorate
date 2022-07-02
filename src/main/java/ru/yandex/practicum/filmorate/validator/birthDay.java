package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = birthDayValidator.class)
@Target (ElementType.TYPE)//( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface birthDay {
    String message() default "{errorbirthDayValidator}";
    Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}
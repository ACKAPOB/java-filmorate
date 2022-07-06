package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalDateMinValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateMin {
    String message() default "{errorMinDate} {value}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String value();
}
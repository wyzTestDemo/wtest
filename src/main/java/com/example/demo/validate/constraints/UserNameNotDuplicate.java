package com.example.demo.validate.constraints;

import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface UserNameNotDuplicate {
    String message() default "用户名已存在";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link UserNameNotDuplicate} annotations on the same element.
     *
     * @see UserNameNotDuplicate
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        UserNameNotDuplicate[] value();
    }
}

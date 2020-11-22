package ru.Test.TestProject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface MappedEnum {
    Class<? extends Enum> enumClass();
}

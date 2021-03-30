package me.ronygomes.anoread.annotation;

import me.ronygomes.anoread.converter.InputConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Converter {

    Class<? extends InputConverter<?>> value();
}

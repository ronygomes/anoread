package me.ronygomes.anoread.annotation.handler;

import me.ronygomes.anoread.handler.provider.MultiLineReadHandlerProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@ReadHandlerType(provider = MultiLineReadHandlerProvider.class)
public @interface ReadMultiLine {

    String DEFAULT_JOINER = " ";

    String joiner() default DEFAULT_JOINER;
}

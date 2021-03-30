package me.ronygomes.anoread.annotation.handler;

import me.ronygomes.anoread.handler.provider.SingleLineReadHandlerProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@ReadHandlerType(provider = SingleLineReadHandlerProvider.class)
public @interface ReadSingleLine {
}

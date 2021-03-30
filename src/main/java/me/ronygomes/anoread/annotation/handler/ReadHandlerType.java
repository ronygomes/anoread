package me.ronygomes.anoread.annotation.handler;

import me.ronygomes.anoread.handler.ReadHandlerProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ReadHandlerType {
    Class<? extends ReadHandlerProvider> provider();
}

package me.ronygomes.anoread.annotation.formatter;

import me.ronygomes.anoread.formatter.ReadPromptFormatterProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ReadPromptFormatterType {
    Class<? extends ReadPromptFormatterProvider> provider();
}

package me.ronygomes.anoread.annotation.formatter;

import me.ronygomes.anoread.formatter.provider.BasicErrorPromptFormatterProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@ErrorPromptFormatterType(provider = BasicErrorPromptFormatterProvider.class)
public @interface FormatErrorBasic {

    boolean showErrorType() default false;
}

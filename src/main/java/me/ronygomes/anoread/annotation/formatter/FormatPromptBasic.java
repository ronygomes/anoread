package me.ronygomes.anoread.annotation.formatter;

import me.ronygomes.anoread.formatter.provider.BasicReadPromptFormatterProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static me.ronygomes.anoread.formatter.impl.BasicReadPromptFormatter.DEFAULT_PROMPT_SUFFIX;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@ReadPromptFormatterType(provider = BasicReadPromptFormatterProvider.class)
public @interface FormatPromptBasic {

    boolean hintVisible() default false;

    boolean fullLinePrompt() default false;

    String promptSuffix() default DEFAULT_PROMPT_SUFFIX;
}

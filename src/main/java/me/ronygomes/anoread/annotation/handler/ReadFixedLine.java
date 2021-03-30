package me.ronygomes.anoread.annotation.handler;

import me.ronygomes.anoread.handler.provider.FixedLineReadHandlerProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@ReadHandlerType(provider = FixedLineReadHandlerProvider.class)
public @interface ReadFixedLine {

    String DEFAULT_JOINER = " ";

    int lineCount();

    String joiner() default DEFAULT_JOINER;

    boolean showPromptLine() default true;

    boolean startInNewLine() default true;
}

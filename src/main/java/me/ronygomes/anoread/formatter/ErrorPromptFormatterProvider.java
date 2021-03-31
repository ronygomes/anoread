package me.ronygomes.anoread.formatter;

import java.lang.annotation.Annotation;

public interface ErrorPromptFormatterProvider<T extends Annotation> {

    ErrorPromptFormatter create(T annotation);
}

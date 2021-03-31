package me.ronygomes.anoread.formatter;

import java.lang.annotation.Annotation;

public interface ReadPromptFormatterProvider<T extends Annotation> {

    ReadPromptFormatter create(T annotation);
}

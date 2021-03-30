package me.ronygomes.anoread.handler;

import java.lang.annotation.Annotation;

public interface ReadHandlerProvider<T extends Annotation> {
    ReadHandler create(T annotation);
}

package me.ronygomes.anoread.util;

import me.ronygomes.anoread.annotation.handler.ReadHandlerType;
import me.ronygomes.anoread.handler.ReadHandler;

import java.lang.annotation.Annotation;
import java.util.Objects;

public class AnnotationHelper {

    public static ReadHandler extractReadHandler(Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            ReadHandlerType rht = annotation.annotationType().getAnnotation(ReadHandlerType.class);

            if (Objects.nonNull(rht)) {
                try {
                    return rht.provider().newInstance().create(annotation);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}

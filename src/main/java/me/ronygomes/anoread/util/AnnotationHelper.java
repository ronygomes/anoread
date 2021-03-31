package me.ronygomes.anoread.util;

import me.ronygomes.anoread.annotation.Converter;
import me.ronygomes.anoread.annotation.ReadAttributes;
import me.ronygomes.anoread.annotation.extractor.InputExtractorType;
import me.ronygomes.anoread.annotation.formatter.ErrorPromptFormatterType;
import me.ronygomes.anoread.annotation.formatter.ReadPromptFormatterType;
import me.ronygomes.anoread.annotation.handler.ReadHandlerType;
import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.model.ReadMeta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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

    public static InputExtractor extractInputExtractor(Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            InputExtractorType iet = annotation.annotationType().getAnnotation(InputExtractorType.class);

            if (Objects.nonNull(iet)) {
                try {
                    return iet.provider().newInstance().create(annotation);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static ReadPromptFormatter extractReadPromptFormatter(Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            ReadPromptFormatterType rmf = annotation.annotationType().getAnnotation(ReadPromptFormatterType.class);

            if (Objects.nonNull(rmf)) {
                try {
                    return rmf.provider().newInstance().create(annotation);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static ErrorPromptFormatter extractErrorPromptFormatter(Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            ErrorPromptFormatterType epf = annotation.annotationType().getAnnotation(ErrorPromptFormatterType.class);

            if (Objects.nonNull(epf)) {
                try {
                    return epf.provider().newInstance().create(annotation);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static InputConverter<?> extractConverter(Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            if (annotation instanceof Converter) {
                Converter c = (Converter) annotation;

                try {
                    return c.value().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static ReadMeta createMeta(Field field) {

        ReadAttributes ra = field.getAnnotation(ReadAttributes.class);

        if (Objects.nonNull(ra)) {
            return new ReadMeta(field.getName(), ra.prompt(), ra.hint());
        }

        return null;
    }
}

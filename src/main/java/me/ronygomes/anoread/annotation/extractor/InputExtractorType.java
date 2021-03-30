package me.ronygomes.anoread.annotation.extractor;

import me.ronygomes.anoread.extractor.InputExtractorProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface InputExtractorType {
    Class<? extends InputExtractorProvider> provider();
}

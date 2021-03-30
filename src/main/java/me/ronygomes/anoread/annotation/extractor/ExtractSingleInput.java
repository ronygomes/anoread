package me.ronygomes.anoread.annotation.extractor;

import me.ronygomes.anoread.extractor.provider.SingleInputExtractorProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@InputExtractorType(provider = SingleInputExtractorProvider.class)
public @interface ExtractSingleInput {

    boolean trim() default true;
}

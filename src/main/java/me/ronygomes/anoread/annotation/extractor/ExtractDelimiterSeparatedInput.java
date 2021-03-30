package me.ronygomes.anoread.annotation.extractor;

import me.ronygomes.anoread.extractor.provider.DelimiterSeparatedInputExtractorProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static me.ronygomes.anoread.extractor.impl.DelimiterSeparatedInputExtractor.DEFAULT_DELIMITER;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@InputExtractorType(provider = DelimiterSeparatedInputExtractorProvider.class)
public @interface ExtractDelimiterSeparatedInput {

    String delimiter() default DEFAULT_DELIMITER;

    boolean trim() default true;
}

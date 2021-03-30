package me.ronygomes.anoread.extractor;

import java.lang.annotation.Annotation;

public interface InputExtractorProvider<T extends Annotation> {
    InputExtractor create(T annotation);
}
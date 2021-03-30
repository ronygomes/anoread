package me.ronygomes.anoread.extractor.provider;

import me.ronygomes.anoread.annotation.extractor.ExtractSingleInput;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.extractor.InputExtractorProvider;
import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;

public class SingleInputExtractorProvider implements InputExtractorProvider<ExtractSingleInput> {

    @Override
    public InputExtractor create(ExtractSingleInput annotation) {
        return new SingleInputExtractor(annotation.trim());
    }
}

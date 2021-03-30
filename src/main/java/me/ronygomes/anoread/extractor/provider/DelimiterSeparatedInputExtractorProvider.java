package me.ronygomes.anoread.extractor.provider;

import me.ronygomes.anoread.annotation.extractor.ExtractDelimiterSeparatedInput;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.extractor.InputExtractorProvider;
import me.ronygomes.anoread.extractor.impl.DelimiterSeparatedInputExtractor;

public class DelimiterSeparatedInputExtractorProvider
        implements InputExtractorProvider<ExtractDelimiterSeparatedInput> {

    @Override
    public InputExtractor create(ExtractDelimiterSeparatedInput annotation) {
        return new DelimiterSeparatedInputExtractor(annotation.delimiter(), annotation.trim());
    }
}

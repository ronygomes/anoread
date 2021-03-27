package me.ronygomes.anoread.extractor.impl;

import me.ronygomes.anoread.extractor.InputExtractor;

import java.util.Arrays;

import static me.ronygomes.anoread.extractor.InputExtractor.throwNullPointerExceptionIfNull;

public class DelimiterSeparatedInputExtractor implements InputExtractor {

    public static final String DEFAULT_DELIMITER = ",";

    private String delimiter;
    private boolean trim;

    public DelimiterSeparatedInputExtractor() {
        this(DEFAULT_DELIMITER, true);
    }

    public DelimiterSeparatedInputExtractor(boolean trim) {
        this(DEFAULT_DELIMITER, trim);
    }

    public DelimiterSeparatedInputExtractor(String delimiter, boolean trim) {
        this.delimiter = delimiter;
        this.trim = trim;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public boolean isTrim() {
        return trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }

    @Override
    public String[] extract(String line) {
        throwNullPointerExceptionIfNull(line);

        String[] data = line.split(delimiter);
        if (!trim) {
            return data;
        }

        return Arrays.stream(data).map(String::trim).toArray(String[]::new);
    }


}

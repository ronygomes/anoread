package me.ronygomes.anoread.extractor.impl;

import me.ronygomes.anoread.extractor.InputExtractor;

import static me.ronygomes.anoread.extractor.InputExtractor.throwNullPointerExceptionIfNull;

public class SingleInputExtractor implements InputExtractor {

    private boolean trim;

    public SingleInputExtractor() {
        this(true);
    }

    public SingleInputExtractor(boolean trim) {
        this.trim = trim;
    }

    @Override
    public String[] extract(String line) {
        throwNullPointerExceptionIfNull(line);

        if (trim) {
            return new String[]{line.trim()};
        }

        return new String[]{line};
    }
}

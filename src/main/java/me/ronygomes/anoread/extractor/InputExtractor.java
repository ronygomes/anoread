package me.ronygomes.anoread.extractor;

public interface InputExtractor {

    String[] extract(String line);

    static void throwNullPointerExceptionIfNull(String data) {

        if (data == null) {
            throw new NullPointerException("Got null value in extractor");
        }
    }
}

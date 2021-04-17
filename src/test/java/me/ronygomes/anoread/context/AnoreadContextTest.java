package me.ronygomes.anoread.context;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnoreadContextTest {

    @Test
    void testDefaultConverters() {
        assertEquals(4, AnoreadContext.converterCount());
    }

    @Test
    void testDefaultExtractors() {
        assertEquals(1, AnoreadContext.extractorCount());
    }
}

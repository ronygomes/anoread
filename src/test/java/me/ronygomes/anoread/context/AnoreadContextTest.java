package me.ronygomes.anoread.context;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnoreadContextTest {

    @Test
    void testDefaultConverters() {
        assertEquals(3, AnoreadContext.converterCount());
    }

    @Test
    void testDefaultExtractors() {
        assertEquals(0, AnoreadContext.extractorCount());
    }
}

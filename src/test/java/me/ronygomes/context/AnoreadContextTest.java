package me.ronygomes.context;

import me.ronygomes.anoread.context.AnoreadContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnoreadContextTest {

    @Test
    void testDefaultConverters() {
        assertEquals(2, AnoreadContext.converterCount());
    }

    @Test
    void testDefaultExtractors() {
        assertEquals(0, AnoreadContext.extractorCount());
    }
}

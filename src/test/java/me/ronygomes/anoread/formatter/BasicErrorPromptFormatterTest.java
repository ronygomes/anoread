package me.ronygomes.anoread.formatter;

import me.ronygomes.anoread.exception.AnoReadException;
import me.ronygomes.anoread.exception.ConversionException;
import me.ronygomes.anoread.exception.ExtractionException;
import me.ronygomes.anoread.exception.ValidationError;
import me.ronygomes.anoread.formatter.impl.BasicErrorPromptFormatter;
import me.ronygomes.anoread.model.ReadMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicErrorPromptFormatterTest {

    private static final String ERROR_META_NAME = "emName";
    private static final String ERROR_META_PROMPT = "emPrompt";
    private static final String ERROR_META_HINT = "emHint";
    private static final String ERROR_DISPLAY_MESSAGE = "Message";
    private static final String ERROR_INPUT = "input";

    private ReadMeta meta;

    @BeforeEach
    public void setup() {
        this.meta = new ReadMeta(ERROR_META_NAME, ERROR_META_PROMPT, ERROR_META_HINT);
    }

    @Test
    void testExtractionExceptionWithoutPrompt() {
        AnoReadException e = new ExtractionException(ERROR_DISPLAY_MESSAGE);
        ErrorPromptFormatter ef = new BasicErrorPromptFormatter();
        assertEquals("emName[input] - Message", ef.format(meta, ERROR_INPUT, e));
    }

    @Test
    void testExtractionExceptionWithoutOutPrompt() {
        AnoReadException e = new ExtractionException(ERROR_DISPLAY_MESSAGE);
        ErrorPromptFormatter ef = new BasicErrorPromptFormatter(true);
        assertEquals("[EXTRACTION] emName[input] - Message", ef.format(meta, ERROR_INPUT, e));
    }

    @Test
    void testConversionExceptionWithoutPrompt() {
        AnoReadException e = new ConversionException(ERROR_DISPLAY_MESSAGE);
        ErrorPromptFormatter ef = new BasicErrorPromptFormatter();
        assertEquals("emName[input] - Message", ef.format(meta, ERROR_INPUT, e));
    }

    @Test
    void testConversionExceptionWithoutOutPrompt() {
        AnoReadException e = new ConversionException(ERROR_DISPLAY_MESSAGE);
        ErrorPromptFormatter ef = new BasicErrorPromptFormatter(true);
        assertEquals("[CONVERSION] emName[input] - Message", ef.format(meta, ERROR_INPUT, e));
    }

    @Test
    void testValidationErrorWithoutPrompt() {
        AnoReadException e = new ValidationError(ERROR_DISPLAY_MESSAGE);
        ErrorPromptFormatter ef = new BasicErrorPromptFormatter();
        assertEquals("emName[input] - Message", ef.format(meta, ERROR_INPUT, e));
    }

    @Test
    void testValidationErrorWithoutOutPrompt() {
        AnoReadException e = new ValidationError(ERROR_DISPLAY_MESSAGE);
        ErrorPromptFormatter ef = new BasicErrorPromptFormatter(true);
        assertEquals("[VALIDATION] emName[input] - Message", ef.format(meta, ERROR_INPUT, e));
    }
}

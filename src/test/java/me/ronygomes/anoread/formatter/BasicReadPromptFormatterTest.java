package me.ronygomes.anoread.formatter;

import me.ronygomes.anoread.formatter.impl.BasicReadPromptFormatter;
import me.ronygomes.anoread.model.ReadMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicReadPromptFormatterTest {

    private static final String READ_META_NAME = "rmName";
    private static final String READ_META_PROMPT = "rmPrompt";
    private static final String READ_META_HINT = "rmHint";

    private static final String CUSTOM_PROMPT_SUFFIX = "> ";

    private ReadMeta meta;

    @BeforeEach
    public void setup() {
        this.meta = new ReadMeta(READ_META_NAME, READ_META_PROMPT, READ_META_HINT);
    }

    @Test
    public void testVisibleHintWithFullLinePrompt() {
        ReadPromptFormatter rpf = new BasicReadPromptFormatter(true, true);
        assertEquals("rmPrompt (rmHint): " + System.lineSeparator(), rpf.format(meta));
    }

    @Test
    public void testVisibleHintSingleLinePrompt() {
        ReadPromptFormatter rpf = new BasicReadPromptFormatter(true, false);
        assertEquals("rmPrompt (rmHint): ", rpf.format(meta));
    }

    @Test
    public void testInvisibleHintWithFullLinePrompt() {
        ReadPromptFormatter rpf = new BasicReadPromptFormatter(false, true);
        assertEquals("rmPrompt: " + System.lineSeparator(), rpf.format(meta));
    }

    @Test
    public void testInvisibleHintSingleLinePrompt() {
        ReadPromptFormatter rpf = new BasicReadPromptFormatter(false, false);
        assertEquals("rmPrompt: ", rpf.format(meta));
    }

    @Test
    public void testCustomPromptSuffix() {
        BasicReadPromptFormatter brpf = new BasicReadPromptFormatter(false, false, CUSTOM_PROMPT_SUFFIX);

        assertEquals(brpf.getPromptSuffix(), CUSTOM_PROMPT_SUFFIX);
        assertEquals("rmPrompt> ", brpf.format(meta));
    }
}

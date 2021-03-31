package me.ronygomes.anoread.formatter.provider;

import me.ronygomes.anoread.annotation.formatter.FormatPromptBasic;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatterProvider;
import me.ronygomes.anoread.formatter.impl.BasicReadPromptFormatter;

public class BasicReadPromptFormatterProvider implements ReadPromptFormatterProvider<FormatPromptBasic> {

    @Override
    public ReadPromptFormatter create(FormatPromptBasic annotation) {
        return new BasicReadPromptFormatter(annotation.hintVisible(),
                annotation.fullLinePrompt(), annotation.promptSuffix());
    }
}

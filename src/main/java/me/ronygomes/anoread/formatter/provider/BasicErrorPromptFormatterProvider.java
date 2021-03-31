package me.ronygomes.anoread.formatter.provider;

import me.ronygomes.anoread.annotation.formatter.FormatErrorBasic;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ErrorPromptFormatterProvider;
import me.ronygomes.anoread.formatter.impl.BasicErrorPromptFormatter;

public class BasicErrorPromptFormatterProvider implements ErrorPromptFormatterProvider<FormatErrorBasic> {

    @Override
    public ErrorPromptFormatter create(FormatErrorBasic annotation) {
        return new BasicErrorPromptFormatter(annotation.showErrorType());
    }
}

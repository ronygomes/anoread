package me.ronygomes.anoread.formatter;

import me.ronygomes.anoread.model.ReadMeta;

public interface ErrorPromptFormatter {

    String format(ReadMeta meta, String[] value);
}
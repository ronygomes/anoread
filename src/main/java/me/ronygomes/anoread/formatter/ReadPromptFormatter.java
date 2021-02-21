package me.ronygomes.anoread.formatter;

import me.ronygomes.anoread.model.ReadMeta;

public interface ReadPromptFormatter {

    String format(ReadMeta meta);
}

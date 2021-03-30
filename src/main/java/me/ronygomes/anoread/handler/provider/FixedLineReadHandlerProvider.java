package me.ronygomes.anoread.handler.provider;

import me.ronygomes.anoread.annotation.handler.ReadFixedLine;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.handler.ReadHandlerProvider;
import me.ronygomes.anoread.handler.impl.FixedLineReadHandler;

public class FixedLineReadHandlerProvider implements ReadHandlerProvider<ReadFixedLine> {

    @Override
    public ReadHandler create(ReadFixedLine rfl) {
        return new FixedLineReadHandler(rfl.lineCount(), rfl.joiner(), rfl.showPromptLine(), rfl.startInNewLine());
    }
}

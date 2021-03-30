package me.ronygomes.anoread.handler.provider;

import me.ronygomes.anoread.annotation.handler.ReadSingleLine;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.handler.ReadHandlerProvider;
import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;

public class SingleLineReadHandlerProvider implements ReadHandlerProvider<ReadSingleLine> {

    @Override
    public ReadHandler create(ReadSingleLine rsl) {
        return new SingleLineReadHandler();
    }
}

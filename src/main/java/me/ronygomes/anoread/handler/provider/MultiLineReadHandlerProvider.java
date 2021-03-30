package me.ronygomes.anoread.handler.provider;

import me.ronygomes.anoread.annotation.handler.ReadMultiLine;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.handler.ReadHandlerProvider;
import me.ronygomes.anoread.handler.impl.MultiLineReadHandler;

public class MultiLineReadHandlerProvider implements ReadHandlerProvider<ReadMultiLine> {

    @Override
    public ReadHandler create(ReadMultiLine rml) {
        return new MultiLineReadHandler(rml.joiner());
    }
}

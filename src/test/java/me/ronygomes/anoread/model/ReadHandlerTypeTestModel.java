package me.ronygomes.anoread.model;

import me.ronygomes.anoread.annotation.NotImportant;
import me.ronygomes.anoread.annotation.handler.ReadFixedLine;
import me.ronygomes.anoread.annotation.handler.ReadMultiLine;
import me.ronygomes.anoread.annotation.handler.ReadSingleLine;

public class ReadHandlerTypeTestModel {

    @NotImportant
    private String field0;

    @ReadSingleLine
    private String field1;

    @ReadMultiLine
    private String field2;

    @ReadFixedLine(lineCount = 2)
    private String field3;

    @NotImportant
    @ReadSingleLine
    private String field4;

    @ReadSingleLine
    @ReadMultiLine
    private String field5;

}

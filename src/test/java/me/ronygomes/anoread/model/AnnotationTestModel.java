package me.ronygomes.anoread.model;

import me.ronygomes.anoread.annotation.Converter;
import me.ronygomes.anoread.annotation.NotImportant;
import me.ronygomes.anoread.annotation.ReadAttributes;
import me.ronygomes.anoread.annotation.extractor.ExtractDelimiterSeparatedInput;
import me.ronygomes.anoread.annotation.extractor.ExtractSingleInput;
import me.ronygomes.anoread.annotation.handler.ReadFixedLine;
import me.ronygomes.anoread.annotation.handler.ReadMultiLine;
import me.ronygomes.anoread.annotation.handler.ReadSingleLine;
import me.ronygomes.anoread.converter.impl.IntegerConverter;

public class AnnotationTestModel {

    @NotImportant
    @Converter(IntegerConverter.class)
    private String field0;

    @ReadSingleLine
    @ExtractSingleInput
    private String field1;

    @ReadMultiLine
    @ExtractDelimiterSeparatedInput
    private String field2;

    @ReadFixedLine(lineCount = 2)
    private String field3;

    @NotImportant
    @ReadSingleLine
    private String field4;

    @ReadSingleLine
    @ReadMultiLine
    private String field5;

    @ReadAttributes(prompt = "Enter field6: ", hint = "eg. Dog, Cat")
    private String field6;

}

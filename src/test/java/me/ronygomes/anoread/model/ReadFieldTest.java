package me.ronygomes.anoread.model;

import me.ronygomes.anoread.annotation.ReadField;

public class ReadFieldTest {

    @ReadField(order = 2)
    private String field1;

    @ReadField(order = 3)
    private String field2;

    private String field3;

    @ReadField(order = 1)
    private String field4;
}

package me.ronygomes.anoread.model;

import me.ronygomes.anoread.annotation.*;
import me.ronygomes.anoread.annotation.extractor.ExtractDelimiterSeparatedInput;
import me.ronygomes.anoread.annotation.handler.ReadFixedLine;
import me.ronygomes.anoread.annotation.handler.ReadMultiLine;

import java.io.InputStream;
import java.io.PrintStream;

@ReadMultiLine
public class Person {

    @ReadFixedLine(lineCount = 3)
    @ExtractDelimiterSeparatedInput
    private String name;

    @ReadAttributes(prompt = "Yap", hint = "dummy")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @ReadBegin
    @ReadEnd
    private void method0(InputStream in, PrintStream out, PrintStream err) {
        out.print("0-1");
        err.print("0-2");
    }

    @ReadEachPre
    private boolean method1(InputStream in, PrintStream out, PrintStream err, ReadMeta meta) {
        out.print("1-1");
        err.print("1-2");

        return false;
    }

    @ReadEachPost
    private void method2(InputStream in, PrintStream out, PrintStream err, ReadMeta meta) {
        out.print("2-1");
        err.print("2-2");
    }

    @Validator
    private void method3(Object object, ReadMeta meta) {
        meta.setHint("4");
    }
}

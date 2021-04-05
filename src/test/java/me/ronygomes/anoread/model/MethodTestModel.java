package me.ronygomes.anoread.model;

import me.ronygomes.anoread.annotation.ReadEachPre;

import java.io.InputStream;
import java.io.PrintStream;

public class MethodTestModel {

    private Boolean method0(InputStream in, PrintStream out, PrintStream err, ReadMeta meta) {
        return false;
    }

    @ReadEachPre
    private Boolean method1(InputStream in, PrintStream out, PrintStream err, ReadMeta meta) {
        return false;
    }

    @ReadEachPre
    private boolean method2(InputStream in, PrintStream out, PrintStream err, ReadMeta meta) {
        return false;
    }

    @ReadEachPre
    private Boolean method3(InputStream in, PrintStream out, PrintStream err) {
        return false;
    }

    @ReadEachPre
    private Boolean method4(PrintStream err, InputStream in, PrintStream out, ReadMeta meta) {
        return false;
    }

    @ReadEachPre
    private String method5(InputStream in, PrintStream out, PrintStream err, ReadMeta meta) {
        return null;
    }
}

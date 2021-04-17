package me.ronygomes.anoread.model.test;

import me.ronygomes.anoread.annotation.*;
import me.ronygomes.anoread.model.ReadMeta;

import java.io.InputStream;
import java.io.PrintStream;

public class MethodReadLifeCycleHookModel {

    @ReadBegin
    private void method1(InputStream in, PrintStream out, PrintStream err) {
    }

    @ReadEnd
    private void method2(InputStream in, PrintStream out, PrintStream err) {
    }

    @ReadEachPre
    private boolean method3(InputStream in, PrintStream out, PrintStream err, ReadMeta meta) {
        return false;
    }

    @ReadEachPost
    private void method4(InputStream in, PrintStream out, PrintStream err, ReadMeta meta) {
    }

    @Validator
    private void method5(Object object, ReadMeta meta) {
    }
}

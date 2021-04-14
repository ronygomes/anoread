package me.ronygomes.anoread.model;

import me.ronygomes.anoread.util.function.TriConsumer;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class AnnotatedReadEngineCmd extends ReadEngineCmd {

    private Object target;

    private Method readInit;
    private Method readEnd;

    public AnnotatedReadEngineCmd(Object target, ReadLifeCycleHookHolder holder,
                                  Method readInit, Method readEnd) {

        super(holder.getReadBegin(), holder.getReadEnd());
        this.target = target;
        this.readInit = readInit;
        this.readEnd = readEnd;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getReadInit() {
        return readInit;
    }

    public void setReadInit(Method readInit) {
        this.readInit = readInit;
    }

    public Method getReadEnd() {
        return readEnd;
    }

    public void setReadEnd(Method readEnd) {
        this.readEnd = readEnd;
    }

    @Override
    public TriConsumer<InputStream, PrintStream, PrintStream> getBeginConsumer() {
        return getNonNullSuperOrInvokeMethod(super.getBeginConsumer(), readInit);
    }

    @Override
    public TriConsumer<InputStream, PrintStream, PrintStream> getEndConsumer() {
        return getNonNullSuperOrInvokeMethod(super.getEndConsumer(), readEnd);
    }

    private TriConsumer<InputStream, PrintStream, PrintStream> getNonNullSuperOrInvokeMethod(
            TriConsumer<InputStream, PrintStream, PrintStream> superConsumer,
            Method method) {

        if (Objects.isNull(superConsumer) && Objects.nonNull(method)) {
            return (in, out, err) -> {
                try {
                    if (Modifier.isPrivate(method.getModifiers())) {
                        method.setAccessible(true);
                    }
                    method.invoke(target, in, out, err);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            };
        }

        return superConsumer;
    }
}

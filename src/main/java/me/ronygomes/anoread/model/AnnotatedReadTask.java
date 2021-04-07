package me.ronygomes.anoread.model;

import me.ronygomes.anoread.util.function.QuadConsumer;
import me.ronygomes.anoread.util.function.QuadFunction;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.BiConsumer;

public class AnnotatedReadTask<T> extends ReadTask<T> {

    private Object target;

    private Method readEachPre;
    private Method readEachPost;

    private Method validationMethod;

    public AnnotatedReadTask(Object target, ReadLifeCycleHookHolder holder,
                             Method readEachPre, Method readEachPost, Method validationMethod) {

        this.target = target;
        setBefore(holder.getReadEachPre());
        setAfter(holder.getReadEachPost());
        setValidator(holder.getValidator());

        this.readEachPre = readEachPre;
        this.readEachPost = readEachPost;
        this.validationMethod = validationMethod;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getReadEachPre() {
        return readEachPre;
    }

    public void setReadEachPre(Method readEachPre) {
        this.readEachPre = readEachPre;
    }

    public Method getReadEachPost() {
        return readEachPost;
    }

    public void setReadEachPost(Method readEachPost) {
        this.readEachPost = readEachPost;
    }

    public Method getValidationMethod() {
        return validationMethod;
    }

    public void setValidationMethod(Method validationMethod) {
        this.validationMethod = validationMethod;
    }

    @Override
    public QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> getBefore() {
        if (Objects.isNull(super.getBefore()) && Objects.nonNull(readEachPre)) {

            return (in, out, err, meta) -> {
                Object result = null;
                try {
                    result = readEachPre.invoke(target, in, out, err, meta);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return Boolean.TRUE.equals(result);
            };
        }

        return super.getBefore();
    }

    @Override
    public QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> getAfter() {
        if (Objects.isNull(super.getAfter()) && Objects.nonNull(readEachPost)) {
            return (in, out, err, meta) -> {
                try {
                    readEachPre.invoke(target, in, out, err, meta);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            };
        }

        return super.getAfter();
    }

    @Override
    public BiConsumer<Object, ReadMeta> getValidator() {
        if (Objects.isNull(super.getValidator()) && Objects.nonNull(validationMethod)) {
            return (value, readMeta) -> {
                try {
                    readEachPre.invoke(target, value, readMeta);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            };
        }

        return super.getValidator();
    }
}

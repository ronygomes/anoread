package me.ronygomes.anoread.model;

import me.ronygomes.anoread.util.function.QuadConsumer;
import me.ronygomes.anoread.util.function.QuadFunction;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.Objects;
import java.util.function.BiConsumer;

import static java.util.Arrays.copyOf;

public class AnnotatedReadTask extends ReadTask {

    private Object target;
    private Field field;

    private Method readEachPre;
    private Method readEachPost;

    private Method validationMethod;

    public AnnotatedReadTask() {
    }

    public AnnotatedReadTask(Object target, Field field, ReadLifeCycleHookHolder holder, ReadMeta meta,
                             Method readEachPre, Method readEachPost, Method validationMethod) {

        this.target = target;
        this.field = field;
        setBefore(holder.getReadEachPre());
        setAfter(holder.getReadEachPost());
        setValidator(holder.getValidator());
        setMeta(meta);

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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
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
    public ConversionPayload getPayload() {
        if (Objects.isNull(super.getPayload())) {
            ConversionPayload payload = new ConversionPayload();
            payload.setPropertyType(field.getType());

            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                payload.setPropertySubTypes(copyOf(actualTypeArguments, actualTypeArguments.length, Class[].class));
            }

            setPayload(payload);
        }

        return super.getPayload();
    }

    @Override
    public QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> getBefore() {
        if (Objects.isNull(super.getBefore()) && Objects.nonNull(readEachPre)) {

            return (in, out, err, meta) -> {
                Object result = null;
                try {
                    if (Modifier.isPrivate(readEachPre.getModifiers())) {
                        readEachPre.setAccessible(true);
                    }
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
                    if (Modifier.isPrivate(readEachPost.getModifiers())) {
                        readEachPost.setAccessible(true);
                    }
                    readEachPost.invoke(target, in, out, err, meta);
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
                    if (Modifier.isPrivate(validationMethod.getModifiers())) {
                        validationMethod.setAccessible(true);
                    }
                    validationMethod.invoke(target, value, readMeta);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            };
        }

        return super.getValidator();
    }
}

package me.ronygomes.anoread.util;

import me.ronygomes.anoread.model.*;
import me.ronygomes.anoread.processor.HookProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static me.ronygomes.anoread.util.AnnotationHelper.*;

public class ReadEngineHelper {

    public static ReadEngineCmd make(Object target, List<Field> fields,
                                     ReadLifeCycleHookHolder holder,
                                     HookProvider parentHookProvider) {

        Method[] methods = target.getClass().getDeclaredMethods();
        ReadEngineCmd cmd = new AnnotatedReadEngineCmd(target, holder,
                extractReadBegin(methods), extractReadEnd(methods));

        HookProvider classLevelHookProvider = createClassHookProvider(parentHookProvider,
                target.getClass().getAnnotations());

        List<ReadTask<?>> tasks = new ArrayList<>();

        for (Field field : fields) {
            tasks.add(createAnnotatedReadTask(target, field, holder, classLevelHookProvider));
        }

        cmd.setTasks(tasks);
        return cmd;
    }

    public static ReadTask<?> createAnnotatedReadTask(Object target, Field field,
                                                      ReadLifeCycleHookHolder holder,
                                                      HookProvider classLevelHookProvider) {

        Method[] methods = target.getClass().getDeclaredMethods();
        ReadTask<?> task = new AnnotatedReadTask<>(target, holder, extractReadEachPre(methods),
                extractReadEachPost(methods), extractValidator(methods));

        HookProvider fieldHookProvider = createFieldHookProvider(classLevelHookProvider,
                field.getDeclaredAnnotations());

        fieldHookProvider.updateTask(target, field, task);
        return task;
    }
}

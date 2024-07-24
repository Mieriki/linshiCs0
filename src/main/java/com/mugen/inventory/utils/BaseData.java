package com.mugen.inventory.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public interface BaseData {

    default <V> V asLiftObject(LinkedHashMap<String, Object> map) {
        try {
            Class<?> clazz = this.getClass();
            V v = (V) clazz.getDeclaredConstructor().newInstance();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);

                // 获取字段的类型
                Class<?> fieldType = field.getType();

                // 进行类型转换
                value = convertValue(value, fieldType);

                // 设置字段值
                field.set(v, value);
            }

            return v;
        } catch (ReflectiveOperationException | IllegalArgumentException | SecurityException e) {
            throw new RuntimeException("在将LinkedHashMap转换为对象时发生错误: " + e.getMessage(), e);
        }
    }

    default <V> V asLiftObject(Object map) {
        if (map instanceof LinkedHashMap) {
            return asLiftObject((LinkedHashMap<String, Object>) map);
        }
        throw new RuntimeException("在将LinkedHashMap转换为对象时发生错误");
    }

    private Object convertValue(Object value, Class<?> targetType) {
        // 如果 value 为 null，直接返回 null
        if (value == null) {
            return null;
        }

        // 如果目标类型是 Long，而值是 Integer 类型，进行转换
        if (targetType == Long.class && value instanceof Integer) {
            return Long.valueOf((Integer) value);
        }

        // 其他类型转换逻辑...

        // 如果无法进行类型转换，直接返回原始值
        return value;
    }
    default <V> V asViewObject(Class<V> clazz) {
        try {
            Field[] declaredFields = clazz.getDeclaredFields();
            Constructor<V> constructor = clazz.getConstructor();
            V v = constructor.newInstance();
            for (Field field : declaredFields) convert(field, v);
            return v;
        } catch (ReflectiveOperationException exception) {
            Logger logger = LoggerFactory.getLogger(BaseData.class);
            logger.error("在Entity之间转换时出现了一些错误", exception);
            throw new RuntimeException(exception.getMessage());
        }
    }

    default <V> V asViewObject(Class<V> clazz, Consumer<V> consumer) {
        V v = this.asViewObject(clazz);
        consumer.accept(v);
        return v;
    }

    private void convert(Field field, Object vo) {
        try {
            Field source = this.getClass().getDeclaredField(field.getName());
            field.setAccessible(true);
            source.setAccessible(true);
            field.set(vo, source.get(this));
        } catch (NoSuchFieldException | IllegalAccessException ignorde) {}
    }
}

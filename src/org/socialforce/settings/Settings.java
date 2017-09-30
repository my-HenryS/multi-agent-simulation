package org.socialforce.settings;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Created by Ledenel on 2017/9/30.
 */
public class Settings {
    public static void loadFromJson(File file) {
        try {
            byte[] content = Files.readAllBytes(file.toPath());
            JSONObject json = new JSONObject(new String(content, Charset.forName("UTF-8")));
            SettingScanner.inject(clazz -> {
                for (Field field : clazz.getFields()) {
                    LoadFrom annotation = field.getAnnotation(LoadFrom.class);
                        if (annotation != null) {
                            if (Modifier.isStatic(field.getModifiers())) {
                                if (json.has(annotation.value())) {
                                    try {
                                        field.set(null, json.get(annotation.value()));
                                    } catch (IllegalAccessException e) {
                                        throw new IllegalArgumentException(String.format("%s 是不可访问的静态变量。", field.getName()));

                                    }
                                }
                            } else {
                                throw new IllegalArgumentException(String.format("不能对非静态字段 %s 进行注解配置。", field.getName()));
                            }
                        }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

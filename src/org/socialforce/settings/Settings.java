package org.socialforce.settings;

import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.File;
import java.io.FileWriter;
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
            SettingScanner.inject(clazz -> Settings.fields(clazz, (field, value) -> {
                if (json.has(value)) {
                    try {
                        field.set(null, json.get(value));
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException(String.format("%s 是不可访问的静态变量。", field.getName()));

                    }
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fields(Class clazz, InjectFieldFunction<String> func) {
        // System.out.println("checked " + clazz.getName());
        for (Field field : clazz.getDeclaredFields()) {
            LoadFrom annotation = field.getAnnotation(LoadFrom.class);
            if (annotation != null) {
                if(! Modifier.isPublic(field.getModifiers())) {
                    throw new IllegalArgumentException(String.format("不能对非公共成员 %s.%s 进行注解配置。",clazz.getName(),field.getName()));
                }
                if (Modifier.isStatic(field.getModifiers())) {
                    func.inject(field, annotation.value());
                } else {
                    throw new IllegalArgumentException(String.format("不能对非静态字段 %s.%s 进行注解配置。",clazz.getName(),field.getName()));
                }
            }
        }
    }

    public static void saveToJson(File file) {
        System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            JSONWriter writer = new JSONWriter(fileWriter);
            writer.object();
            SettingScanner.inject(clazz -> Settings.fields(clazz, (field, value) -> {
                writer.key(value);
                try {
                    writer.value(field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }));
            writer.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

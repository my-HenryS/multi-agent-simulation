package org.socialforce.settings;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Ledenel on 2017/9/30.
 */
public class SettingScanner {
    public static final String PACKAGE_NAME = "org.socialforce";
    public static final String SEP = File.separator;

    public static void inject(InjectValueFunction func) {
        String path = ClassLoader.getSystemResource("").getPath() + PACKAGE_NAME.replace(".", SEP);
        _inject(path, func);
    }

    private static void _inject(String path, InjectValueFunction func){
        File root = new File(path);
        if(root.listFiles() != null) {
            for (File file : root.listFiles()) {
                if (file.isDirectory()) {
                    _inject(file.getPath(), func);
                } else {
                    String fileName = file.getAbsolutePath();
                    String clazzToken = "classes" + SEP;
                    if(fileName.endsWith(".class")) {
                        try {
                            Class clazz = ClassLoader.getSystemClassLoader().loadClass(
                                    fileName.substring(
                                            fileName.indexOf(clazzToken) + clazzToken.length(),
                                            fileName.lastIndexOf(".class")
                                    )
                                            .replace(SEP, ".")
                            );
                            func.inject(clazz);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

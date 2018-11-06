package org.socialforce.settings;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by Ledenel on 2017/9/30.
 */
public interface InjectFieldFunction<T> {
    void inject(Field field, T value);
}

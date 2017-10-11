package org.socialforce.settings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Ledenel on 2017/9/29.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)


public @interface LoadFrom {
    String value();
}

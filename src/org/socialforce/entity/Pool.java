package org.socialforce.entity;

import java.util.Collection;

/**
 * Created by Ledenel on 2016/8/2.
 */
public interface Pool<T extends InteractiveEntity> extends Collection<T> {
    Iterable<T> select(Shape shape);
}

package org.socialforce.entity;

import org.socialforce.entity.InteractiveEntity;
import org.socialforce.entity.Shape;

import java.util.Collection;

/**
 * Created by Ledenel on 2016/8/1.
 */
public interface EntityPool extends Collection<InteractiveEntity> {
    Iterable<InteractiveEntity> select(Shape shape);
}

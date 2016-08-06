package org.socialforce.entity;

import java.util.Collection;

/**
 * a specific collection for the Interactive Entity management.
 * support range-select in a scene.
 * @author Ledenel
 * Created by Ledenel on 2016/8/2.
 */
public interface Pool<T extends InteractiveEntity> extends Collection<T> {
    /**
     * select a set of entity which is in she shape.
     * @param shape the shape to select
     * @return a set of selected entity.
     */
    Iterable<T> select(Shape shape);
}

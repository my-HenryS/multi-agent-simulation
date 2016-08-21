package org.socialforce.container.impl;

import org.socialforce.container.Pool;
import org.socialforce.geom.Shape;
import org.socialforce.model.InteractiveEntity;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Ledenel on 2016/8/21.
 */
public class LinkListPool<T extends InteractiveEntity> extends LinkedList<T> implements Pool<T> {
    /**
     * select a set of entity which is in she shape.
     *
     * @param shape the shape to select
     * @return a set of selected entity.
     */
    @Override
    public Iterable<T> select(Shape shape) {
        return new PoolSelectIterable(shape);
    }

    public class PoolSelectIterable implements Iterable<T> {
        public PoolSelectIterable(Shape shape) {
            this.shape = shape;
        }

        private Shape shape;

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<T> iterator() {
            return LinkListPool.this.stream().filter(agent ->
                    shape.contains(agent.getShape().getReferencePoint())).iterator();
        }
    }

}

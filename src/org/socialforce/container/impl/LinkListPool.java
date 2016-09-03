package org.socialforce.container.impl;

import org.socialforce.container.Pool;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Point;
import org.socialforce.model.InteractiveEntity;
import sun.nio.cs.StreamEncoder;

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
    public Iterable<T> select(DistanceShape shape) {
        return new PoolShapeSelectIterable(shape);
    }

    /**
     * 选择覆盖指定点的所有实体。
     * 在确认只会选中一个实体时，也可使用 {@link #selectTop(Point)} 或 {@link #selectBottom(Point)}。
     *
     * @param point 指定点。
     * @return 一个只读的集合，包含了覆盖指定点的所有实体。
     * @see #removeSelect(Point)
     * @see #selectTop(Point)
     * @see #selectBottom(Point)
     */
    @Override
    public Iterable<T> select(Point point) {
        return new PoolPointSelectIterale(point);
    }

    protected boolean shapeContains(T entity, DistanceShape shape) {
        return shape.contains(entity.getShape().getReferencePoint());
    }

    protected boolean nameEquals(T entity, String name) {
        return name.equals(entity.getName());
    }

    protected boolean pointContains(T entity, Point point) {
        return entity.getShape().contains(point);
    }

    /**
     * 移除与指定形状相交的所有实体。
     *
     * @param shape 指定的形状。
     * @return 如果实体池中确实含有这样的对象，返回true；否则返回false。
     * @see #select(DistanceShape)
     * @see #selectTop(DistanceShape)
     * @see #selectBottom(DistanceShape)
     */
    @Override
    public boolean removeSelect(DistanceShape shape) {
        return this.removeIf(entity -> shapeContains(entity, shape));
    }

    /**
     * 移除覆盖指定点的所有实体。
     *
     * @param point 指定点。
     * @return 如果实体池中确实含有这样的对象，返回true；否则返回false。
     */
    @Override
    public boolean removeSelect(Point point) {
        return this.removeIf(entity -> pointContains(entity, point));
    }

    /**
     * 选择在池中覆盖在最上边的，与指定形状相交的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最后添加的覆盖在最上面。
     *
     * @param shape 指定的形状。
     * @return 所选择的实体。如果池中没有与指定形状相交的实体，返回null。
     */
    @Override
    public T selectTop(DistanceShape shape) {
        return this.stream()
                .filter(entity -> shapeContains(entity, shape))
                .findFirst()
                .orElse(null);
    }

    /**
     * 选择在池中覆盖在最上边的，覆盖指定点的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最后添加的覆盖在最上面。
     *
     * @param point 指定点。
     * @return 所选择的实体。如果池中没有覆盖指定点的实体，返回null。
     */
    @Override
    public T selectTop(Point point) {
        return this.stream()
                .filter(entity -> pointContains(entity, point))
                .reduce((a, b) -> b)
                .orElse(null);
    }

    /**
     * 选择在池中被覆盖在最下边的，与指定形状相交的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最先添加的被覆盖在最下面。
     *
     * @param shape 指定的形状。
     * @return 所选择的实体。如果池中没有与指定形状相交的实体，返回null。
     */
    @Override
    public T selectBottom(DistanceShape shape) {
        return this.stream()
                .filter(entity -> shapeContains(entity, shape))
                .findFirst()
                .orElse(null);
    }

    /**
     * 选择在池中被覆盖在最下边的，覆盖指定点的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最先添加的被覆盖在最下面。
     *
     * @param point 指定点。
     * @return 所选择的实体。如果池中没有覆盖指定点的实体，返回null。
     */
    @Override
    public T selectBottom(Point point) {
        return this.stream()
                .filter(entity -> pointContains(entity, point))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterable<T> find(String name) {
        return new PoolNameIterable(name);
    }

    /**
     * 查找具有指定名称的，覆盖在最上层的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最后添加的覆盖在最上层。
     *
     * @param name 要查找的名称。
     * @return 查找到的实体。如果池中没有指定名称的实体，返回null。
     */
    @Override
    public T findTop(String name) {
        return this.stream()
                .filter(entity -> nameEquals(entity, name))
                .reduce((a, b) -> b)
                .orElse(null);
    }

    /**
     * 查找具有指定名称的，覆盖在最下层的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最先添加的覆盖在最下层。
     *
     * @param name 要查找的名称。
     * @return 查找到的实体。如果池中没有指定名称的实体，返回null。
     */
    @Override
    public T findBottom(String name) {
        return this.stream()
                .filter(entity -> nameEquals(entity, name))
                .findFirst()
                .orElse(null);
    }

    public class PoolNameIterable implements Iterable<T> {
        private String name;

        public PoolNameIterable(String name) {
            this.name = name;
        }

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<T> iterator() {
            return LinkListPool.this.stream()
                    .filter(entity -> nameEquals(entity, name))
                    .iterator();
        }


    }

    public class PoolShapeSelectIterable implements Iterable<T> {
        public PoolShapeSelectIterable(DistanceShape shape) {
            this.shape = shape;
        }

        private DistanceShape shape;

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<T> iterator() {
            return LinkListPool.this.stream()
                    .filter(entity -> shapeContains(entity, shape))
                    .iterator();
        }

    }

    public class PoolPointSelectIterale implements Iterable<T> {

        public PoolPointSelectIterale(Point point) {
            this.point = point;
        }

        private Point point;

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<T> iterator() {
            return LinkListPool.this.stream()
                    .filter(entity -> pointContains(entity, point))
                    .iterator();
        }


    }
}

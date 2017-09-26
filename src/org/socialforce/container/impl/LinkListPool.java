package org.socialforce.container.impl;

import org.socialforce.container.Pool;
import org.socialforce.geom.DistancePhysicalEntity;
import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.Point;
import org.socialforce.model.InteractiveEntity;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Ledenel on 2016/8/21.
 */
public class LinkListPool<T extends InteractiveEntity> extends LinkedBlockingDeque<T> implements Pool<T> {
    /**
     * 选中与shape相交的实体
     *
     * @param shape the physicalEntity to select
     * @return a set of selected entity.
     */
    @Override
    public Iterable<T> select(DistancePhysicalEntity shape) {
        return new PoolShapeSelectIterable(shape);
    }

    @Override
    public Iterable<T> selectContains(PhysicalEntity physicalEntity){
        return new PoolShapeSelectContainsIterable(physicalEntity);
    }

    @Override
    public Iterable<T> selectClass(Class aClass){ return new PoolClassIterable(aClass);}

    @Override
    public T selectTopByClass(Point point, Class aClass){
            return this.stream()
                    .filter(entity -> classEquals(entity, aClass) && pointContains(entity, point))
                    .findFirst()
                    .orElse(null);
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

    protected boolean shapeContains(T entity, DistancePhysicalEntity shape) {
        return shape.intersects(entity.getPhysicalEntity());
    }

    protected boolean refpointContains(T entity, PhysicalEntity physicalEntity){
        return physicalEntity.contains(entity.getPhysicalEntity().getReferencePoint());
    }

     protected boolean nameEquals(T entity, String name) {
     return name.equals(entity.getName());
     }

     protected boolean pointContains(T entity, Point point) {
     return entity.getPhysicalEntity().contains(point);
     }

    protected boolean classEquals(T entity, Class aclass) {
        return aclass.isInstance(entity);
    }

     /**
     * 移除与指定形状相交的所有实体。
     *
     * @param shape 指定的形状。
     * @return 如果实体池中确实含有这样的对象，返回true；否则返回false。
     * @see #select(DistancePhysicalEntity)
     * @see #selectTop(DistancePhysicalEntity)
     * @see #selectBottom(DistancePhysicalEntity)
     */
    @Override
    public boolean removeSelect(DistancePhysicalEntity shape) {
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
    public T selectTop(DistancePhysicalEntity shape) {
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
     * 选择在池中被覆盖在最下边的，被指定形状包含的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最先添加的被覆盖在最下面。
     *
     * @param shape 指定的形状。
     * @return 所选择的实体。如果池中没有与指定形状相交的实体，返回null。
     */
    @Override
    public T selectBottom(DistancePhysicalEntity shape) {
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

    public LinkListPool clone(){
        LinkListPool<InteractiveEntity> newpool = new LinkListPool();
        for(InteractiveEntity t: this){
            newpool.addLast(t.clone());
        }
        return newpool;
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

    public class PoolClassIterable implements Iterable<T> {
        private Class aClass;

        public PoolClassIterable(Class tClass) {
            this.aClass = tClass;
        }

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<T> iterator() {
            return LinkListPool.this.stream()
                    .filter(entity -> classEquals(entity, aClass))
                    .iterator();
        }


    }

    public class PoolShapeSelectIterable implements Iterable<T> {
        public PoolShapeSelectIterable(DistancePhysicalEntity shape) {
            this.shape = shape;
        }

        private DistancePhysicalEntity shape;

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

    public class PoolShapeSelectContainsIterable implements Iterable<T>{
        private PhysicalEntity physicalEntity;

        public PoolShapeSelectContainsIterable(PhysicalEntity physicalEntity) {
            this.physicalEntity = physicalEntity;
        }

        @Override
        public Iterator<T> iterator() {
            return LinkListPool.this.stream()
                    .filter(entity -> refpointContains(entity, physicalEntity))
                    .iterator();        }
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

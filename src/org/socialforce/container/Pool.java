package org.socialforce.container;

import org.socialforce.geom.DistanceModelShape;
import org.socialforce.geom.ModelShape;
import org.socialforce.geom.Point;
import org.socialforce.model.InteractiveEntity;

import java.util.Collection;

/**
 * 代表一个实体池，实体池能够像普通集合一样添加及删除实体。
 * 实体池像一个地图，可以获取指定范围内的一个实体，或者获取覆盖指定点的一个实体。
 *
 * <p>
 *     注意向池中添加实体只添加了对实体的引用，并不会复制一个实体，
 *     即持有池中对象的引用即可操作池中对象。
 * </p>
 *
 * @param <T> 池中的实体类型。
 * @author Ledenel
 * @see java.util.Collection
 */

public interface Pool<T extends InteractiveEntity> extends Collection<T> {
    /**
     * 选择与指定形状相交的所有实体。
     * 在确认只会选中一个实体时，也可使用 {@link #selectTop(DistanceModelShape)} 或 {@link #selectBottom(DistanceModelShape)}。
     *
     * @param shape 指定形状。
     * @return 一个只读的集合，包含了与指定形状相交的所有实体。
     * @see #removeSelect(DistanceModelShape)
     * @see #selectTop(DistanceModelShape)
     * @see #selectBottom(DistanceModelShape)
     */
    Iterable<T> select(DistanceModelShape shape);

    /**
     * 选定包含其refpoint的实体
     * @param modelShape
     * @return refpoint被包含的实体
     */
    Iterable<T> selectContains(ModelShape modelShape);
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
    Iterable<T> select(Point point);

    @Override
    boolean add(T t);

    @Override
    boolean remove(Object o);

    /**
     * 移除与指定形状相交的所有实体。
     *
     * @param shape 指定的形状。
     * @return 如果实体池中确实含有这样的对象，返回true；否则返回false。
     * @see #select(DistanceModelShape)
     * @see #selectTop(DistanceModelShape)
     * @see #selectBottom(DistanceModelShape)
     */
    boolean removeSelect(DistanceModelShape shape);

    /**
     * 移除覆盖指定点的所有实体。
     *
     * @param point 指定点。
     * @return 如果实体池中确实含有这样的对象，返回true；否则返回false。
     */
    boolean removeSelect(Point point);

    /**
     * 选择在池中覆盖在最上边的，与指定形状相交的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最后添加的覆盖在最上面。
     *
     * @param shape 指定的形状。
     * @return 所选择的实体。如果池中没有与指定形状相交的实体，返回null。
     */
    T selectTop(DistanceModelShape shape);


    /**
     * 选择在池中覆盖在最上边的，覆盖指定点的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最后添加的覆盖在最上面。
     *
     * @param point 指定点。
     * @return 所选择的实体。如果池中没有覆盖指定点的实体，返回null。
     */
    T selectTop(Point point);

    /**
     * 选择在池中被覆盖在最下边的，与指定形状相交的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最先添加的被覆盖在最下面。
     *
     * @param shape 指定的形状。
     * @return 所选择的实体。如果池中没有与指定形状相交的实体，返回null。
     */
    T selectBottom(DistanceModelShape shape);

    /**
     * 选择在池中被覆盖在最下边的，覆盖指定点的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最先添加的被覆盖在最下面。
     *
     * @param point 指定点。
     * @return 所选择的实体。如果池中没有覆盖指定点的实体，返回null。
     */
    T selectBottom(Point point);

    /**
     * 查找具有指定名称的所有实体。
     * 若确认只有一个实体，也可以使用 {@link #findTop(String)} 或 {@link #findBottom(String)}。
     *
     * @param name 要查找的名称。
     * @return 一个只读集合，包含所有具有该名称的实体。
     * @see #findTop(String)
     * @see #findBottom(String)
     */
    Iterable<T> find(String name);

    /**
     * 查找具有指定名称的，覆盖在最上层的实体。
     * 按照 {@link #add(InteractiveEntity)} 的添加顺序，最后添加的覆盖在最上层。
     *
     * @param name 要查找的名称。
     * @return 查找到的实体。如果池中没有指定名称的实体，返回null。
     */
    T findTop(String name);

    /**
     * 查找具有指定名称的，覆盖在最下层的实体。
     * 按照 {@link #add(InteractiveEntity)}} 的添加顺序，最先添加的覆盖在最下层。
     *
     * @param name 要查找的名称。
     * @return 查找到的实体。如果池中没有指定名称的实体，返回null。
     */
    T findBottom(String name);

    Pool clone();
}

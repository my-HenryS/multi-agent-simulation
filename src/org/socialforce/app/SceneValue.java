package org.socialforce.app;

/**
 * 代表一个场景参数的具体赋值。
 * 场景参数值的主要作用是存储一个特定类型的参数的赋值，
 * 并且将这个赋值应用于场景。
 *
 * @param <T> 内部表示值的类型。
 */
public interface SceneValue<T extends Comparable<T>> extends Comparable<SceneValue<T>> {
    /**
     * 获取参数的名称。
     *
     * @return 参数名称。
     */
    String getName();

    /**
     * 设置参数的名称。
     */
    void setName();

    /**
     * 获得该场景参数赋值的值。
     *
     * @return 返回的具体值。
     */
    T getValue();

    /**
     * 将该赋值运用于特定场景。
     * 即，使用该赋值更改一个指定的场景。
     *
     * @param scene 要被更改的场景。
     */
    void apply(Scene scene);
}

package org.socialforce.app;

/**
 * 场景一个可变参数的Value
 * 如门的位置，agent的密度，障碍物的位置等
 * 场景所有的Value的集合构成一个Parameter
 * @see SceneParameter
 */
public interface SceneValue<T extends Comparable<T>> extends Comparable<SceneValue<T>> {
    String getName();
    void setName();
    T getValue();
    void apply(Scene scene);
}

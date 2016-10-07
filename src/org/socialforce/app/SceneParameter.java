package org.socialforce.app;

/**
 * 代表一个场景参数。
 * 场景参数可以生成一个或者一系列SceneValue的可能取值，用以枚举场景。
 */
public interface SceneParameter<T extends Comparable<T>> extends Comparable<SceneParameter<T>> {
    String getName();
    void setName();
    boolean isValid(SceneValue<T> value);
    int getPreferedSize();
    Iterable<SceneValue<T>> sample(int size);
    Iterable<SceneValue<T>> sample();
}

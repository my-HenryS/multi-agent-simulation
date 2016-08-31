package org.socialforce.app;

/**
 * 场景的所有可变参数的集合，由一系列SceneValue组成
 * 一个SceneParameter唯一确定一个场景
 */
public interface SceneParameter<T extends Comparable<T>> extends Comparable<SceneParameter<T>> {
    String getName();
    void setName();
    boolean isValid(SceneValue<T> value);
    int getPreferedSize();
    Iterable<SceneValue<T>> sample(int size);
    Iterable<SceneValue<T>> sample();
}

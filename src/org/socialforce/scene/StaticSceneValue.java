package org.socialforce.scene;

import org.socialforce.model.InteractiveEntity;

/**
 * Created by Ledenel on 2016/8/29.
 */
public abstract class StaticSceneValue<T> implements SceneValue<T> {

    public void setEntityName(String name) {
        this.name = name;
    }

    protected String name;

    public void setValue(T value) {
        this.value = value;
    }

    protected T value;

    @Override
    public String getEntityName() {
        return name;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void apply(Scene scene) {
        scene.getStaticEntities().find(getEntityName()).forEach(this::applyEach);
    }

    public abstract void applyEach(InteractiveEntity entity);
}

package org.socialforce.scene;

import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;
import org.socialforce.scene.impl.EntityGenerator2D;

/**
 * Created by Ledenel on 2016/8/29.
 */
public abstract class EntityGenerator<T extends InteractiveEntity> implements SceneValue<T> {

    private int priority = -1;
    protected Model model;
    protected String commonName;

    @Override
    public int compareTo(SceneValue<T> o) {
        return o.getPriority() - this.getPriority();
    }
    @Override
    public int getPriority() {
        return priority;
    }
    @Override
    public EntityGenerator<T> setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public void setModel(Model model){
        this.model = model;
    }


    public String getCommonName() {
        return commonName;
    }

    public EntityGenerator<T> setCommonName(String name) {
        this.commonName = name;
        return this;
    }


}

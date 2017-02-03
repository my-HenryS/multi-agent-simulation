package org.socialforce.scene.impl;

import org.socialforce.geom.Shape;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;

/**
 * Created by Administrator on 2017/2/3.
 */
public class SV_Wall implements SceneValue<Shape> {
    @Override
    public String getEntityName() {
        return name;
    }

    protected String name;
    @Override
    public void setEntityName(String name) {
        this.name = name;
    }

    @Override
    public Shape getValue() {
        return wallshape;
    }

    protected Shape wallshape;
    @Override
    public void setValue(Shape value) {
        wallshape = value;
    }


    @Override
    public void apply(Scene scene) {
        Wall temp = new Wall(wallshape);
        temp.setModel(new SimpleSocialForceModel());
        scene.getStaticEntities().add(temp);
    }

    @Override
    public int compareTo(SceneValue<Shape> o) {
        return 0;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    protected int priority;
    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
}

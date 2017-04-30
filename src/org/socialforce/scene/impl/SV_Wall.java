package org.socialforce.scene.impl;

import org.socialforce.geom.Shape;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;

/**
 * Created by Administrator on 2017/2/3.
 */
public class SV_Wall implements SceneValue<Shape[]> {
    public SV_Wall(Shape[] wallshape) {
        this.wallshape = wallshape;
    }

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
    public Shape[] getValue() {
        return wallshape;
    }


    @Override
    public void setValue(Shape[] value) {
        wallshape = value;
    }

    protected Shape[] wallshape;


    @Override
    public void apply(Scene scene) {
        for (Shape shape : wallshape) {
            Wall temp = new Wall(shape.clone());
            temp.setModel(new SimpleSocialForceModel());
            scene.getStaticEntities().add(temp);
        }
    }

    @Override
    public int compareTo(SceneValue<Shape[]> o) {
        return priority - o.getPriority();
    }


    @Override
    public int getPriority() {
        return priority;
    }

    protected int priority = 3;
    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
}

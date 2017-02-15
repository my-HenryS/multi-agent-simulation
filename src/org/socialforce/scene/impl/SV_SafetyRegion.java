package org.socialforce.scene.impl;

import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;
import org.socialforce.geom.Shape;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.SimpleSocialForceModel;

/**
 * Created by Whatever on 2016/9/16.
 */
public class SV_SafetyRegion implements SceneValue<Shape>{
    protected Shape shape;
    protected String name;
    public SV_SafetyRegion(){}
    public SV_SafetyRegion(Shape shape){this.shape = shape;}
    @Override
    public String getEntityName() {
        return name;
    }

    @Override
    public void setEntityName(String name) {
        this.name = name;
    }

    @Override
    public Shape getValue() {
        return shape;
    }

    @Override
    public void setValue(Shape value) {
        this.shape = value;
    }
    @Override
    public void apply(Scene scene) {
        SafetyRegion safetyRegion = new SafetyRegion(shape);
        safetyRegion.setName("SafetyRegion");
        scene.getStaticEntities().add(safetyRegion);
        safetyRegion.setScene(scene);
        safetyRegion.setModel(new SimpleSocialForceModel());
    }

    @Override
    public int compareTo(SceneValue<Shape> o) {
        return o.getPriority() - this.getPriority();
    }
    private int priority;
    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
}

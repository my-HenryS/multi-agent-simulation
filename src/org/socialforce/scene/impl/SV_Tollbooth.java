package org.socialforce.scene.impl;

import org.socialforce.geom.Shape;
import org.socialforce.model.impl.ETC_Tollbooth;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;

/**
 * Created by sunjh1999 on 2017/3/26.
 */
public class SV_Tollbooth implements SceneValue<Shape> {
    protected Shape shape;
    protected double interval;
    public SV_Tollbooth(Shape shape, double interval){
        this.shape = shape;
        this.interval = interval;
    }
    @Override
    public String getEntityName() {
        return null;
    }

    @Override
    public void setEntityName(String name) {

    }

    @Override
    public Shape getValue() {
        return shape;
    }

    @Override
    public void setValue(Shape value) {

    }

    @Override
    public void apply(Scene scene) {
        ETC_Tollbooth tollbooth = new ETC_Tollbooth(shape.clone(), interval);
        scene.getStaticEntities().add(tollbooth);
        tollbooth.setScene(scene);
        tollbooth.setModel(new SimpleSocialForceModel());
    }

    @Override
    public int compareTo(SceneValue<Shape> o) {
        return 0;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void setPriority(int priority) {

    }
}

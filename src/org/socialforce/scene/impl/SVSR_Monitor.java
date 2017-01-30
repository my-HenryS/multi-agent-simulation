package org.socialforce.scene.impl;

import org.socialforce.geom.Shape;
import org.socialforce.model.impl.Monitor;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class SVSR_Monitor implements SceneValue<Monitor> {
    protected String name;
    protected Monitor scenery;
    public SVSR_Monitor(Shape shape){this.scenery= new Monitor(shape);}
    @Override
    public String getEntityName() {
        return name;
    }

    @Override
    public void setEntityName(String name) {
        this.name = name;
    }

    @Override
    public Monitor getValue() {
        return scenery;
    }

    @Override
    public void setValue(Monitor value) {
        this.scenery = value;
    }

    @Override
    public void apply(Scene scene) {
        scenery.setName("SimpleTollbooth");
        scene.getStaticEntities().add(scenery);
        scenery.setScene(scene);
        scenery.setModel(new SimpleSocialForceModel());
    }

    @Override
    public int compareTo(SceneValue<Monitor> o) {
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

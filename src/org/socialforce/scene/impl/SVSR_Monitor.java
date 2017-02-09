package org.socialforce.scene.impl;

import org.socialforce.geom.ModelShape;
import org.socialforce.model.impl.Monitor;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class SVSR_Monitor implements SceneValue<Monitor> {
    protected String name;
    protected Monitor monitor;
    public SVSR_Monitor(ModelShape modelShape){this.monitor = new Monitor(modelShape);}
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
        return monitor;
    }

    @Override
    public void setValue(Monitor value) {
        this.monitor = value;
    }

    @Override
    public void apply(Scene scene) {
        monitor.setName("SimpleMonitor");
        scene.getStaticEntities().add(monitor);
        monitor.setScene(scene);
        monitor.setModel(new SimpleSocialForceModel());
        monitor.setTimePerStep(monitor.getModel().getTimePerStep());
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

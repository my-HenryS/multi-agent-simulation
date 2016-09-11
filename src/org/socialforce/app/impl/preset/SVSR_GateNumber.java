package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;

/**
 * Created by Whatever on 2016/8/31.
 */
public class SVSR_GateNumber implements SceneValue {
    protected int gateNumber;
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public Comparable getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {

    }

    @Override
    public void apply(Scene scene) {
        for (int i = 4;i > gateNumber;i--){
            scene.getStaticEntities().remove(scene.getStaticEntities().findBottom("gate"));
        }
    }

    @Override
    public int compareTo(SceneValue o) {
        return 0;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;
import org.socialforce.app.StaticSceneValue;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.SafetyRegion;

/**
 * Created by Whatever on 2016/9/16.
 */
public class SVSR_SafetyReigion implements SceneValue<SafetyRegion>{
    protected SafetyRegion safetyRegion;
    protected String name;
    @Override
    public String getEntityName() {
        return name;
    }

    @Override
    public void setEntityName(String name) {
        this.name = name;
    }

    @Override
    public SafetyRegion getValue() {
        return safetyRegion;
    }

    @Override
    public void setValue(SafetyRegion value) {
        safetyRegion = value;
    }

    @Override
    public void apply(Scene scene) {
        safetyRegion.setName("SafetyRegion");
        scene.getStaticEntities().add(safetyRegion);
    }

    @Override
    public int compareTo(SceneValue<SafetyRegion> o) {
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

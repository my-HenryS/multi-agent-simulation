package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;
import org.socialforce.model.impl.SafetyRegion;

/**
 * Created by Whatever on 2016/9/16.
 */
public class SVSR_SafetyRegion extends SVSR_Base<SafetyRegion> {
    protected SafetyRegion safetyRegion;

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

}

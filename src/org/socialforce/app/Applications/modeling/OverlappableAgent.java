package org.socialforce.app.Applications.modeling;

import org.socialforce.geom.DistancePhysicalEntity;
import org.socialforce.geom.Velocity;
import org.socialforce.model.impl.BaseAgent;
import org.socialforce.scene.Scene;

/**
 * Created by Ledenel on 2018/1/4.
 */
public class OverlappableAgent extends BaseAgent {
    @Override
    public boolean onAdded(Scene scene) {
        return true;
    }



    public OverlappableAgent(DistancePhysicalEntity shape, Velocity velocity) {
        super(shape, velocity);
    }
}

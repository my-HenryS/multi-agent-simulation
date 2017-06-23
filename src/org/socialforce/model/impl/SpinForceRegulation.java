package org.socialforce.model.impl;

import org.socialforce.geom.Affection;
import org.socialforce.geom.Moment;
import org.socialforce.geom.impl.Moment2D;
import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class SpinForceRegulation implements ForceRegulation {
    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return false;
    }

    @Override
    public Affection getForce(InteractiveEntity interactiveEntity, InteractiveEntity interactiveEntity2) {
        return new Moment2D();
    }
}

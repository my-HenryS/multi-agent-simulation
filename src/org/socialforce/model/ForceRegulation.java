package org.socialforce.model;

import org.socialforce.geom.Force;

/**
 * Created by Ledenel on 2016/8/18.
 */
public interface ForceRegulation<Source extends InteractiveEntity,Target extends InteractiveEntity> {
    boolean hasForce(InteractiveEntity source,InteractiveEntity target);
    Force getForce(Source source, Target target);
}

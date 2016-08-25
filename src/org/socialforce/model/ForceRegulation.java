package org.socialforce.model;

import org.socialforce.geom.Force;

/**
 * Created by Ledenel on 2016/8/18.
 */

/**
 *regulate the force between the Source entity and the target entity
 * @param <Source>
 * @param <Target>
 */
public interface ForceRegulation<Source extends InteractiveEntity,Target extends InteractiveEntity> {
    /**
     *judge if there has force between the source entity and the target entity
     * @param source
     * @param target
     * @return true if there has force between the source entity and the target entity
     */
    boolean hasForce(InteractiveEntity source,InteractiveEntity target);

    /**
     *get the force between the source entity and the target entity
     * @param source
     * @param target
     * @return force
     */
    Force getForce(Source source, Target target);
}

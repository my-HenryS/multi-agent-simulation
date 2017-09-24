package org.socialforce.model;

import org.socialforce.geom.Affection;
import org.socialforce.geom.Force;

/**
 *控制（调节）源实体和目标实体之间的作用力。
 * @param <Source>
 * @param <Target>
 */
public interface ForceRegulation<Source extends InteractiveEntity,Target extends InteractiveEntity> {
    /**
     *判断源实体和目标实体之间是否有作用力。
     * @param source
     * @param target
     * @return true 当源实体和目标实体之间是有作用力时返回真。
     */
    boolean hasForce(InteractiveEntity source,InteractiveEntity target);

    /**
     *获取源实体和目标实体之间的作用力。
     * @param source
     * @param target
     * @return force
     */
    Affection getForce(Source source, Target target);

    /**
     * 指明是哪一种force
     * @return
     */
    Class forceType();
}

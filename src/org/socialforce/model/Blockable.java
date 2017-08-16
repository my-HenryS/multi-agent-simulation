package org.socialforce.model;

import org.socialforce.geom.PhysicalEntity;

/**
 * 表示是否会被PathFinder判定为可阻挡Entity的实体
 * @author Ledenel sunjh(edit)
 */
public interface Blockable extends InteractiveEntity {
    PhysicalEntity blockSize();
}

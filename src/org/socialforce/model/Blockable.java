package org.socialforce.model;

import org.socialforce.geom.Shape;

/**
 * 表示是否会被PathFinder判定为可阻挡Entity的实体
 * @author Ledenel sunjh(edit)
 */
public interface Blockable {
    Shape blockSize();
}

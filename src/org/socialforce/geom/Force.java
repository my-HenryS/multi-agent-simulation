package org.socialforce.geom;

import org.socialforce.geom.impl.Force2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;

/**
 * 代表相互作用的实体之间的力.
 * 该力将用社会力模型计算.
 * @see InteractiveEntity
 * @see Model
 * @author Ledenel
 * Created by Ledenel on 2016/7/28.
 */
public interface Force  extends Vector,Affection {
    /**
     * 累积的力和获取速度的增量.
     * @param mass 该实体的质量.
     * @param time 累积的时间.
     * @return 速度的增量.
     */
    Velocity deltaVelocity(double mass, double time);

    /**
     * 创建并返回这个力的副本.
     * 它是一个向量.
     * @return 这个力的副本.
     */
    Force clone();

    /**
     * 获取参考的向量
     * 与原向量同向但是长度为1.
     * 零向量没有参考向量
     * @return 参考向量，即单位向量
     */
    @Override
    Force2D getRefVector();
}

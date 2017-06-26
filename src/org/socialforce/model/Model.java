package org.socialforce.model;

import org.socialforce.scene.Scene;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.geom.*;

/**
 * the social force model.
 * a social force model is used to manage constraints,
 * calculating forces between entities,
 * and create interactive entities compatible with this model.
 * the unit of these constraints and forces are standard units.
 *
 * @author Ledenel
 * @see SocialForceApplication
 * @see Scene
 * Created by Ledenel on 2016/7/30.
 */
public interface Model{

    /**
     * 计算在该模型下模型中场对实体的作用力。
     *
     * @param target 受到作用力的实体。
     * @return the force. 返回力的大小，其单位是牛。
     */
    Force fieldForce(InteractiveEntity target);

    /**
     * 计算在该模型下实体之间的作用力。
     *
     * @param source 产生作用力的实体。
     * @param target 受力的作用的实体。
     * @return the force. 返回力的大小，其单位是牛。
     */
    Force interactionForce(InteractiveEntity source, InteractiveEntity target);

    Moment interactionMoment(InteractiveEntity source, InteractiveEntity target);
    /**
     * 创建并返回零向量。
     *
     * @return 返回零向量。
     */
    Vector zeroVector();

    /**
     * 创建并返回大小为零的速度。
     *
     * @return 大小为零的速度。
     */
    Velocity zeroVelocity();

    /**
     * 创建并返回大小为零的作用力。
     *
     * @return 大小为零的作用力。
     */
    Force zeroForce();
    /**
     * 创建并返回模型的仿真步长。
     * @return 仿真步长。
     */
    double getTimePerStep();

    Model clone();
}

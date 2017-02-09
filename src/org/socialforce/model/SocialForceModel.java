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
public interface SocialForceModel {
    /**
     * 获取每一步的时间。
     *
     * @return TIME_PER_STEP 每一步的时间，单位是s.
     */
    double getTimePerStep();

    void setTimePerStep(double t);

    /**
     * 获取agent的视域范围。
     *
     * @return circle2D 返回一个圆形区域。
     */
    ModelShape getAgentView();           //// TODO: 2016/11/21  model不应该控制agent的视野

    /**
     * 计算源实体对目标实体的作用力。
     *
     * @param source 产生作用力的实体。
     * @param target 受力的作用的实体。
     * @return the force. 返回力的大小，其单位是牛。
     */
    Force calculate(InteractiveEntity source, InteractiveEntity target);

    /**
     * 当力作用于实体本身时，计算该社会力。
     *
     * @param source  产生作用力的实体。
     * @return the force. 返回力的大小，其单位是牛。
     */
    Force getPower(InteractiveEntity source);


    /**
     * 根据默认的设置来创建静态物体。
     * 静态物体可以是墙，门，障碍物等。
     *
     * @return 静态物体。
     */
    InteractiveEntity createStatic();

    /**
     * 根据具体的形状参数来创建静态物体。
     * 静态物体可以是墙，门，障碍物等。
     * @param modelShape the static object's modelShape.
     * @return 静态物体。
     */
    InteractiveEntity createStatic(ModelShape modelShape);

    /**
     * 根据具体的形状，类型参数来创建静态物体。
     * 静态物体可以是墙，门，障碍物等。
     * @param modelShape 静态物体的形状。
     * @param type  静态物体的类型。
     * @return 静态物体。
     */
    InteractiveEntity createStatic(ModelShape modelShape, int type);

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
     * 获取一个agent的驱动（期望）速度。
     *
     * @param current agent 所在的位置。
     * @param goal    agent 的目标点。
     * @return expected.驱动的速度。
     */
    Velocity getAgentMotivation(Point current, Point goal);

    /**
     * 获取当前model的行人期望速度值
     */
    double getExpectedSpeed();

    void setExpectedSpeed(double speed);

    double getREACT_TIME();

    SocialForceModel clone();
}

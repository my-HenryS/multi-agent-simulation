package org.socialforce.model.impl;

import org.socialforce.geom.*;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.*;

import java.util.LinkedList;
import java.util.List;

/**定义SimpleSocialForceModel类，其实现了接口SocialForceModel的方法
 * Created by Ledenel on 2016/8/17.
 */
public class SimpleSocialForceModel implements SocialForceModel {
    double TIME_PER_STEP = 0.05;
    public static final double AGENT_VIEW_RADIUS = 6;
    public static final double EXPECTED_SPEED = 1.4;
    public static final double REACT_TIME = 0.4;
    public static final int STATIC_TYPE_WALL = 0;
    public static final int STATIC_TYPE_GATE = 1;


    protected List<ForceRegulation> regulations;

    public SimpleSocialForceModel() {
        regulations = new LinkedList<>();
        regulations.add(new PsychologicalForceRegulation(InteractiveEntity.class, Agent.class, this));
        regulations.add(new BodyForce());
        regulations.add(new WallForce());
    }

    /**
     * 获取每一步的时间。
     *
     * @return TIME_PER_STEP 每一步的时间，单位是s.
     */
    @Override
    public double getTimePerStep() {
        return TIME_PER_STEP;
    }

    public void setTimePerStep(double t) {
        TIME_PER_STEP = t;
    }

    /**
     * 获取agent的视域范围。
     *
     * @return circle2D 返回一个圆形区域。
     */
    @Override
    public Shape getAgentView() {
        Circle2D circle2D = new Circle2D();
        circle2D.setRadius(AGENT_VIEW_RADIUS);
        return circle2D;
    }

    /**
     * 计算源实体对目标实体的作用力。
     *
     * @param source 产生作用力的实体。
     * @param target 受力的作用的实体。
     * @return the force. 返回力的大小，其单位是牛。
     */
    @Override
    public Force calcualte(InteractiveEntity source, InteractiveEntity target) {
//        return zeroForce(); // added calculation implements.
        Force force = this.zeroForce();
        for (ForceRegulation regulation : regulations) {
            if (regulation.hasForce(source, target)) {
                force.add(regulation.getForce(source, target));
            }
        }
        return force;
    }

    /**
     * 当力作用于实体本身时，计算该社会力。
     *
     * @param source  产生作用力的实体。
     * @return the force. 返回力的大小，其单位是牛。
     */
    @Override
    public Force getPower(InteractiveEntity source) {
        if (source instanceof Agent) {
            Agent agent = (Agent) source;
            Force force = this.zeroForce();
            force.add(agent.expect());
            force.scale(agent.getMass() / REACT_TIME);
            return force;
        }
        return zeroForce();
    }

    private <T extends InteractiveEntity> T reg(T entity) {
        entity.setModel(this);
        return entity;
    }

    /**
     * 根据默认的设置来创建静态物体。
     * 静态物体可以是墙，门，障碍物等。
     *
     * @return 静态物体。
     */
    @Override
    public InteractiveEntity createStatic() {
        return reg(new Wall(null));
    }

    /**
     * 根据具体的形状参数来创建静态物体。
     * 静态物体可以是墙，门，障碍物等。
     * @param shape the static object's shape.
     * @return 静态物体。
     */
    @Override
    public InteractiveEntity createStatic(Shape shape) {
        return reg(new Wall(shape));
    }

    /**
     * 根据具体的形状，类型参数来创建静态物体。
     * 静态物体可以是墙，门，障碍物等。
     * @param shape 静态物体的形状。
     * @param type  静态物体的类型。
     * @return 静态物体。
     */
    @Override
    public InteractiveEntity createStatic(Shape shape, int type) {
        if (type == STATIC_TYPE_WALL) {
            return reg(new Wall(shape));
        } else if (type == STATIC_TYPE_GATE) {
            return reg(new SafetyRegion(shape));
        }
        return reg(createStatic(shape));
    }

    /**
     * 创建并返回零向量。
     *
     * @return 返回零向量。
     */
    @Override
    public Vector zeroVector() {
        return new Vector2D();
    }

    /**
     * 创建并返回大小为零的速度。
     *
     * @return 大小为零的速度。
     */
    @Override
    public Velocity zeroVelocity() {
        return new Velocity2D();
    }

    /**
     * 创建并返回大小为零的作用力。
     *
     * @return 大小为零的作用力。
     */
    @Override
    public Force zeroForce() {
        return new Force2D();
    }

    /**
     * 获取一个agent的驱动（期望）速度。
     *
     * @param current agent 所在的位置。
     * @param goal    agent 的目标点。
     * @return expected.驱动的速度。
     */
    @Override
    public Velocity getAgentMotivation(Point current, Point goal) {
        Velocity expected = this.zeroVelocity();
        expected.sub(current);
        expected.add(goal);
        expected.scale(EXPECTED_SPEED / expected.length());
        return expected;
    }
}

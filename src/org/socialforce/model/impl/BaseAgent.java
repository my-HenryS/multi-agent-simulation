package org.socialforce.model.impl;

import org.socialforce.app.*;
import org.socialforce.geom.*;
import org.socialforce.model.*;

/**
 * Created by Ledenel on 2016/8/15.
 * 定义BaseAgent类，其继承于父类Entity，并实现了接口Agent 的方法。
 */
public class BaseAgent extends Entity implements Agent {
    Velocity currVelocity;
    Path path;
    double mass;
    int currTimestamp;
    Shape view;
    Force pushed;
    Velocity deltaV;
    Vector deltaS;
    boolean escaped = false;
    DistanceShape shape;

    public BaseAgent(DistanceShape shape) {
        super(shape);
        this.shape = shape;
    }

    /**
     * 获取一个实体的形状
     * 如线，矩形，圆等。
     *
     * @return 实体的形状.
     */
    @Override
    public DistanceShape getShape() {
        return shape;
    }

    /**
     * 获取当前移动实体的速度
     *
     * @return 速度.
     */
    @Override
    public Velocity getVelocity() {
        return currVelocity;
    }

    /**
     * 将实体以一定大小的力推向目标点。
     *
     * @param force 推时力的大小
     * (TODO 为什么该方法也是@Override)
     */
    @Override
    public void push(Force force) {
        this.pushed.add(force);
    }

    /**
     * 用大小为force的力推位于特定位置上的点。
     * 该方法还可以使实体旋转。
     *
     * @param force      推力大小
     * @param startPoint 力作用的位置。
     */
    @Override
    public void push(Force force, Point startPoint) {
        push(force);
    }

    /**
     * 获取一个agent的视域范围。
     * 该agent只和位于该视域范围内的agent进行交互
     * @return 一个表示该视域范围的形状
     * @see Shape
     */
    @Override
    public Shape getView() {
        return view;
    }



    /**
     * 获取一个agent的期望速度。
     * 该agent的速度通常来说取决于其自身及其目标。
     * @return 期望速度
     * @see Velocity
     */
    @Override
    public Velocity expect() {
        Point point = this.shape.getReferencePoint();
        return model.getAgentMotivation(point, path.getCurrentGoal(point));
    }

    /**
     * 决定下一步，agent要走向的目标点。
     * 同时，agent也会被社会力驱动。
     * 最终的结果会被act() 方法使用。
     * 如果当前的时间步长和该agent不同步，那么该agnet 会试着跟上
     * (或者忽略当agent的时间落后于真正的时间)  TODO?
     * @param currSteps 当前的时间
     * @return 代表要移动的距离和方向的向量。
     */
    @Override
    public Vector determineNext(int currSteps) {
        if(currSteps >= this.currTimestamp) {
            this.pushed = model.getPower(this);
            int dt = currSteps - this.currTimestamp + 1;
            Iterable<InteractiveEntity> statics = scene.getStaticEntities().select(view);
            Iterable<Agent> neighbors = scene.getAllAgents().select(view);
            for(InteractiveEntity entity : statics) {
                entity.affect(this);
            }
            for(Agent agent : neighbors) {
                agent.affect(this);
            }
            deltaV = this.pushed.deltaVelocity(mass,dt * model.getTimePerStep());
            deltaS = deltaV.deltaDistance(dt * model.getTimePerStep());
            this.currTimestamp = currSteps;
            return deltaS;
        }
        else {
            return null;
        }
    }

    /**
     * 决定下一步，agent要走向的目标点。
     * 同时，agent也会被社会力驱动。
     * 最终的结果会被act() 方法使用。
     * @return 代表要移动的距离和方向的向量。
     */
    @Override
    public Vector determineNext() {
        return determineNext(currTimestamp);
    }


    /**
     * 获取当前时刻，agent的timestep （TODO这里的这个timestep翻译成时刻？）
     * timestep从0开始（仿真开始）
     * @return 当前的timestep
     */
    @Override
    public int getCurrentSteps() {
        return currTimestamp;
    }

    /**
     * 使用determineNext()方法计算出的结果。
     * 该方法会将时间往前推进一步。
     * 当act()成功执行，其还会将之前determineNext()方法计算出的结果清零。
     * 当无法获得该agent通过determineNext()方法计算所得的结果时，不会有移动。
     * 当agnet到达目标（或者逃出）时，不会有移动。
     */
    @Override
    public void act() {
        this.currTimestamp++;
        this.currVelocity.add(deltaV);
        Point point = shape.getReferencePoint();
        point.add(deltaS);
        this.shape.moveTo(point);

        deltaS = model.zeroVector();
        deltaV = model.zeroVelocity();
        pushed = model.zeroForce();
    }

    /**
     * 获取agent的路径
     *
     * @return 路径对象
     */
    @Override
    public Path getPath() {
        return path;
    }

    /**
     *为agent设置路径
     *
     * @param path 要设置的路径
     */
    @Override
    public void setPath(Path path) {
        this.path = path;
    }

    protected Scene scene;

    /**
     * 获取当前场景的具体内容
     * @return 场景
     */
    @Override
    public Scene getScene() {
        return scene;
    }

    /**
     * 设置agent所处的场景
     * 
     * @param scene 被设置的场景
     */
    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * 标明已经逃离的agent
     */
    @Override
    public void escape() {
         
        escaped = true;
        scene.onAgentEscape(this);
    }

    /**
     * 当前this所影响的实体
     * 例如，墙会影响agent(反作用，反推)
     * @param affectedEntity 被影响的实体
     * @see Agent
     * @see SocialForceModel
     */
    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if (affectedEntity instanceof Agent) {
            Agent agent = (Agent) affectedEntity;
            agent.push(model.calcualte(this, affectedEntity));
        }
    }

    /**
     * 获取实体的质量。
     * 通常质量位于形状的参考点上（或者是位于质心上）TODO
     *
     * @return the mass.
     */
    @Override
    public double getMass() {
        return mass;
    }
}

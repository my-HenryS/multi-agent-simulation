package org.socialforce.model.impl;

import org.socialforce.geom.*;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.*;
import org.socialforce.strategy.Path;

/**
 * 定义BaseAgent类，其继承于父类Entity，并实现了接口Agent 的方法。
 * Created by Ledenel on 2016/8/15.
 */
public class BaseAgent extends Entity implements Agent {
    Velocity currVelocity, currAcceleration = new Velocity2D(0,0);
    Path path;
    double mass;
    DistanceShape view;
    Force pushed;
    boolean escaped = false;
    DistanceShape shape;
    boolean stoped = false;
    private static final double forceUpbound = Double.POSITIVE_INFINITY;

    public BaseAgent(DistanceShape shape, Velocity velocity) {
        super(shape);
        this.shape = shape;
        this.currVelocity = velocity;
        this.mass = 1000;
        Circle2D circle = new Circle2D(shape.getReferencePoint(),15);
        this.view = circle;
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
    @Deprecated
    public void push(Force force, Point startPoint) {
        push(force);
    }

    /**
     * 获取一个agent的视域范围。
     * 该agent只和位于该视域范围内的agent进行交互
     *
     * @return 一个表示该视域范围的形状
     * @see Shape
     */
    @Override
    // FIXME: 2016/9/3 it should be DistanceShape to support pool selection.
    public DistanceShape getView() {
        return view;
    }



    /**
     * 按照当前TimePerStep内受到的作用力的和移动Agent
     * 当act()成功执行，其还会将当前受力。
     * 当无法获得该agent被affect而计算所得的结果时，不会有移动。
     * 当agent到达目标（或者逃出）时，不会有移动。
     */
    @Override
    public void act() {
        if(stoped){
            pushed = model.zeroForce();
            stoped = false;
            return;
        }
        if(pushed.length() > forceUpbound){
            this.pushed = pushed.getRefVector();
            pushed.scale(forceUpbound);
        }
        Velocity next_v = new Velocity2D(0,0), deltaV = this.pushed.deltaVelocity(mass, model.getTimePerStep());
        currAcceleration = deltaV.clone();
        currAcceleration.scale(1/model.getTimePerStep());
        Vector deltaS;
        next_v.add(currVelocity);
        next_v.add(deltaV);
        deltaS = next_v.deltaDistance(model.getTimePerStep());
        this.currVelocity.add(deltaV);
        if(currVelocity.dot(new Vector2D(0,1)) < 0){
            currVelocity = new Velocity2D(currVelocity.dot(new Vector2D(1,0)),0);
        }
        Point point = shape.getReferencePoint();
        point.add(deltaS);
        this.shape.moveTo(point);
        this.view.moveTo(point);                      //改变视野
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
     * 为agent设置路径
     *
     * @param path 要设置的路径
     */
    @Override
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * 设置社会力模型，并依照模型维度初始化零向量。
     *
     * @param model 模型
     */
    @Override
    public void setModel(Model model) {
        this.model = model;
        this.pushed = model.zeroForce();
    }


    /**
     * 标明这个Agent已经逃离，稍后将被移除。
     */
    @Override
    public void escape() {
        escaped = true;
    }

    /**
     * 检查这个Agent是否已经逃离。
     *
     * @return 是否已经逃离。
     */
    @Override
    public boolean isEscaped() {
        return escaped;
    }

    /**
     * 当前this所影响的实体
     * 例如，墙会影响agent(反作用，反推)
     *
     * @param target 被影响的实体
     * @see Agent
     * @see Model
     */
    @Override
    public void affect(Agent target) {
        if (this.equals(target)) {
            this.selfAffect();
        }
        else {
            target.push(model.interactionForce(this,target));
        }
    }

    /**
     * BaseAgent以模型场力的形式影响自己
     * @see Agent
     * @see Model
     */
    public void selfAffect(){
        this.push(model.fieldForce(this));
    }

    @Override
    public Velocity getAcceleration() {
        return currAcceleration;
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

    @Override
    public BaseAgent standardclone() {
        return new BaseAgent(shape.clone(), currVelocity.clone());
    }


    public String toString(){
        return "Shape:" + this.shape.toString() + "\tVelocity:" + this.currVelocity.toString() + "\tForce:" + this.pushed.toString();
    }

    /**
     * 一个Blockable可阻挡的面积
     * @return 代表可阻挡范围的Shape
     */
    @Override
    public Shape blockSize() {
        return this.getShape().clone();
    }

    public void stopWhatEver(){
        stoped = true;
    }

}

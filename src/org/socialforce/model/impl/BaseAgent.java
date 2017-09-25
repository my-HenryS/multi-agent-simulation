package org.socialforce.model.impl;

import org.socialforce.geom.*;
import org.socialforce.geom.impl.*;
import org.socialforce.model.*;
import org.socialforce.scene.Scene;
import org.socialforce.strategy.Path;

import java.util.Iterator;

/**
 * 定义BaseAgent类，其继承于父类Entity，并实现了接口Agent 的方法。
 * Created by Ledenel on 2016/8/15.
 */
public class BaseAgent extends Entity implements Agent {
    Palstance currPal=new Palstance2D(0),currAccPal = new Palstance2D(0);
    Path path;
    double mass,intertia;
    DistancePhysicalEntity view;
    boolean escaped = false;
    DistancePhysicalEntity physicalEntity;

    public BaseAgent(DistancePhysicalEntity shape, Velocity velocity) {
        super(shape);
        this.physicalEntity = shape;
        this.physicalEntity.setVelocity(velocity);
        this.physicalEntity.setMass(80);
        if (this.physicalEntity instanceof RotatablePhysicalEntity) {
            ((RotatablePhysicalEntity) this.physicalEntity).setInertia(20);
        }
        Circle2D circle = new Circle2D(shape.getReferencePoint(),5);
        this.view = circle;
    }

    /**
     * 获取一个实体的形状
     * 如线，矩形，圆等。
     *
     * @return 实体的形状.
     */
    @Override
    public DistancePhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    /**
     * 获取当前移动实体的速度
     *
     * @return 速度.
     */
    @Override
    public Velocity getVelocity() {
        return physicalEntity.getVelocity();
    }

    /**
     * 将实体以一定大小的力推向目标点。
     *
     * @param force 推时力的大小
     */
    @Override
    public void push(Force force) {
        this.physicalEntity.push(force);
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
     * @see PhysicalEntity
     */
    @Override
    // FIXME: 2016/9/3 it should be DistancePhysicalEntity to support pool selection.
    public DistancePhysicalEntity getView() {
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
        physicalEntity.act(model.getTimePerStep());
        view.moveTo(physicalEntity.getReferencePoint());
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
            target.rotate(model.interactionMoment(this,target));
        }
    }

    @Override
    public void affectAll(Iterable<Agent> affectableAgents) {
        for(Agent agent:affectableAgents){
            affect(agent);
        }
    }

    @Override
    public Palstance getPalstance() {
        if(physicalEntity instanceof RotatablePhysicalEntity){
        return ((RotatablePhysicalEntity) physicalEntity).getPalstance();}
        else return new Palstance2D(0);
    }

    @Override
    public double getIntetia() {
        if(physicalEntity instanceof RotatablePhysicalEntity){
            return ((RotatablePhysicalEntity) physicalEntity).getInertia();}
        else return 0;
    }

    @Override
    public void rotate(Moment moment){
        physicalEntity.push(moment);
    }

    /**
     * BaseAgent以模型场力的形式影响自己
     * @see Agent
     * @see Model
     */
    public void selfAffect(){
        if (physicalEntity instanceof RotatablePhysicalEntity){
            double angle = ((Ellipse2D) physicalEntity).getAngle();
            Vector2D face = new Vector2D(-Math.sin(angle),Math.cos(angle));
            Velocity2D expected = new Velocity2D(0,0);
            expected.sub(physicalEntity.getReferencePoint());
            expected.add(path.nextStep(physicalEntity.getReferencePoint()));
            double size = Vector2D.getRotateAngle(expected , face);
            physicalEntity.push(new Moment2D(size*200));
        }
    }

    @Override
    public Velocity getAcceleration() {
        return physicalEntity.getAcceleration();
    }

    /**
     * 获取实体的质量。
     * 通常质量位于形状的参考点上（或者是位于质心上）TODO
     *
     * @return the mass.
     */
    @Override
    public double getMass() {
        return physicalEntity.getMass();
    }

    @Override
    public BaseAgent clone() {
        return new BaseAgent(physicalEntity.clone(), getVelocity().clone());
    }


    public String toString(){
        return "PhysicalEntity:" + this.physicalEntity.toString() + "\tVelocity:" + getVelocity().toString();
    }

    /**
     * 一个Blockable可阻挡的面积
     * @return 代表可阻挡范围的Shape
     */
    @Override
    public PhysicalEntity blockSize() {
        return this.getPhysicalEntity().clone();
    }

    /**
     * Agent加入scene会判断是否与场景中现有entity重叠
     * @param scene 触发的场景。
     * @return 返回是否加入成功
     */
    @Override
    public boolean onAdded(Scene scene) {
        for(Iterator<InteractiveEntity> iterator = scene.getAllEntitiesStream().iterator();iterator.hasNext();){
            InteractiveEntity entity = iterator.next();
            if(physicalEntity.intersects(entity.getPhysicalEntity())){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onStep(Scene scene) {

    }

    public void setVelocity(Velocity2D velocity){
        this.physicalEntity.setVelocity(velocity);
    }
}

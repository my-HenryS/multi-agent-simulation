package org.socialforce.model.impl;

import org.socialforce.geom.*;
import org.socialforce.geom.impl.*;
import org.socialforce.model.*;
import org.socialforce.scene.Scene;

/**
 * Created by Whatever on 2017/3/1.
 */
public class Door extends Entity implements Moveable, Influential {

    public Door(Rectangle2D rectangle2D, Point2D ankor, double[] anglerange,int rotationFlag) {
        super(rectangle2D);
        this.rectangle2D = rectangle2D;
        this.ankor = ankor;
        this.Anglerange = anglerange;
        this.rotationFlag = rotationFlag;
    }

    protected int rotationFlag;

    /**
     * 设置实体的名称
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public PhysicalEntity getView() {
        return this.getPhysicalEntity();
    }

    /**
     * 当前this所影响的实体
     * 例如，墙会影响agent(反作用，反推)
     * @param target
     */
    @Override
    public void affect(Agent target) {
        target.push(model.interactionForce(this, target));
        this.push(model.interactionForce(target, this));
    }

    @Override
    public void affectAll(Iterable<Agent> affectableAgents) {
        for(Agent agent:affectableAgents){
            affect(agent);
        }
    }


    /**
     * 获取一个实体的形状
     * 如线，矩形，圆等。
     *
     * @return 实体的形状.
     */
    @Override
    public PhysicalEntity getPhysicalEntity() {
        return rectangle2D;
    }

    protected Rectangle2D rectangle2D;
    protected Point2D ankor;
    protected double[] Anglerange = new double[2];
    /**
     * 获取实体的质量。
     * 通常质量位于形状的参考点上（或者是位于质心上）TODO
     *
     * @return the mass.
     */
    @Override
    public double getMass() {
        double[] scale = rectangle2D.getScale();
        return scale[0]*scale[1]*100;
    }

    /**
     * 获取社会模型中使用的实体模型
     *
     * @return model 模型
     */
    @Override
    public Model getModel() {
        return model;
    }

    protected Model model;
    /**
     * 设置社会力模型。
     *
     * @param model 模型
     */
    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * 将该实体放置在一个特殊的点上。
     * TODO the physicalEntity will {@code moveTo} that point.
     *
     * @param point 目标点。
     */
    @Override
    public void placeOn(Point point) {
        rectangle2D.moveTo(point);
    }

    @Override
    public Door clone() {
        return new Door(rectangle2D.clone(), ankor.clone(), Anglerange.clone(),rotationFlag);
    }

    /**
     * 获取移动实体的速度。.
     *
     * @return currVelocity。 当前速度
     */
    @Override
    public Velocity getVelocity() {
        return new Velocity2D(0,0);
    }

    /**
     * 将实体以一定大小的力推向目标点。
     *
     * @param force 推时力的大小
     */
    @Override
    public void push(Force force) {
        Force temp = force.clone();
        temp.dot(new Vector2D(1,0));
        pushed += (int) temp.length();
    }
    int pushed = 0;

    /**
     * 用大小为force的力推位于特定位置上的点。
     * 该方法还可以使实体旋转。
     *
     * @param force      推力大小
     * @param startPoint 力作用的位置。
     */
    @Override
    public void push(Force force, Point startPoint) {
        pushed++;
    }


    public void act(){
        double angle = rectangle2D.getAngle();
        //System.out.println(Anglerange[0]+","+Anglerange[1]);
        if (angle>=Anglerange[0]&&angle<=Anglerange[1]){
            rectangle2D.spin(ankor,model.getTimePerStep()*200*pushed*rotationFlag/getMass());
        }
        pushed = 0;
    }

    @Override
    public boolean onAdded(Scene scene) {
        return true;
    }

    @Override
    public void onStep(Scene scene) {
    }
}

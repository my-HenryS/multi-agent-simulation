package org.socialforce.model.impl;

import org.socialforce.geom.Force;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Velocity;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Rectangle2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Moveable;
import org.socialforce.model.SocialForceModel;
import org.socialforce.scene.Scene;

/**
 * Created by Whatever on 2017/3/1.
 */
public class Door implements InteractiveEntity,Moveable {

    public Door(Rectangle2D rectangle2D, Point2D ankor, double[] anglerange) {
        this.rectangle2D = rectangle2D;
        this.ankor = ankor;
        if(anglerange[0]<anglerange[1]){
        Anglerange = anglerange;}
        else Anglerange[0] = anglerange[1];Anglerange[1] = anglerange[0];
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    protected Scene scene;
    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * 获取实体的名称
     *
     * @return name 名称
     */
    @Override
    public String getName() {
        return name;
    }

    protected String name;

    /**
     * 设置实体的名称
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 当前this所影响的实体
     * 例如，墙会影响agent(反作用，反推)
     */
    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if (affectedEntity instanceof Agent) {
            Agent agent = (Agent)affectedEntity;
            agent.push(model.calculate(this,affectedEntity));
        }
    }

    /**
     * 获取一个实体的形状
     * 如线，矩形，圆等。
     *
     * @return 实体的形状.
     */
    @Override
    public Shape getShape() {
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
    public SocialForceModel getModel() {
        return model;
    }

    protected SocialForceModel model;
    /**
     * 设置社会力模型。
     *
     * @param model 模型
     */
    @Override
    public void setModel(SocialForceModel model) {
        this.model = model;
    }

    /**
     * 将该实体放置在一个特殊的点上。
     * TODO the shape will {@code moveTo} that point.
     *
     * @param point 目标点。
     */
    @Override
    public void placeOn(Point point) {
        rectangle2D.moveTo(point);
    }

    @Override
    public InteractiveEntity standardclone() {
        return new Door(rectangle2D.clone(), ankor.clone(), Anglerange.clone());
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
        pushed++;
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


    public void determinNext(){
        for (Agent agent : scene.getAllAgents()) {
            agent.affect(this);
        }
    }


    public void act(){
        if (rectangle2D.getAngle()>=Anglerange[0]&&rectangle2D.getAngle()<=Anglerange[1]){
            rectangle2D.spin(ankor,model.getTimePerStep()*100*pushed/getMass());
            System.out.println(pushed);
        }
        pushed = 0;
    }
}

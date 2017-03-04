package org.socialforce.model.impl;

import org.socialforce.geom.*;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Moveable;

/**
 * Created by Whatever on 2017/3/2.
 */
public class Star_Planet extends Entity implements Moveable{
    /**
     * @param shape
     */
    public Star_Planet(Shape shape) {
        super(shape);
    }

    /**
     * 当前this所影响的实体
     * 例如，墙会影响agent(反作用，反推)
     */
    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if(affectedEntity instanceof Star_Planet){
            ((Star_Planet)affectedEntity).push(model.calculate(this,affectedEntity));
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
        double d = shape.getBounds().getSize().length();
        return d*d*d;
    }

    @Override
    public InteractiveEntity standardclone() {
        return new Star_Planet(shape.clone());
    }

    /**
     * 获取移动实体的速度。.
     *
     * @return currVelocity。 当前速度
     */
    @Override
    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity){
        this.velocity = velocity;
    }

    protected Velocity velocity;

    /**
     * 将实体以一定大小的力推向目标点。
     *
     * @param force 推时力的大小
     */
    @Override
    public void push(Force force) {
        Velocity dv = force.deltaVelocity(getMass(),getModel().getTimePerStep());
        velocity.add(dv);
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

    public void determinNext(){
        Iterable<InteractiveEntity> statics = scene.getStaticEntities();
        for (InteractiveEntity entity : statics){
            if (entity instanceof Star_Planet && !entity.equals(this)){
                entity.affect(this);
            }
        }
    }
    public void act(){
        Point2D point2D = (Point2D) shape.getReferencePoint();
        Velocity v= velocity.clone();
        Vector vv = v.deltaDistance(model.getTimePerStep());
        double[] cp = new double[2];
        vv.get(cp);
        point2D.moveBy(cp[0],cp[1]);
        shape.moveTo(point2D);
    }
}

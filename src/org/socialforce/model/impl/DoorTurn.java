package org.socialforce.model.impl;


import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.impl.*;
import org.socialforce.model.Agent;
import org.socialforce.model.Influential;

/**
 * Created by Administrator on 2017/9/15 0015.
 */
public class DoorTurn extends Exit implements Influential {

    protected Box2D door;
    protected double angle;  //朝门内侧的向量对应的转角(此处根据下面getView的定义，恒为-90度)

    public DoorTurn(Box2D box2D,double angle) {
        super(box2D);
        door = box2D;
        this.angle = angle;
    }

    @Override
    public PhysicalEntity getView(){
        return this.getPhysicalEntity();
        /*Point2D center = new Point2D((((Box2D)this.getPhysicalEntity()).getXmin()+((Box2D)this.getPhysicalEntity()).getXmax())/2,((Box2D)this.getPhysicalEntity()).getYmin());
        Circle2D circle2D = new Circle2D(center,((Vector2D)((Box2D)this.getPhysicalEntity()).getSize()).getX()/2);
        return new DoubleShape2D(door,circle2D);*/
    }

    @Override
    public void affect(Agent target) {
        target.push(model.interactAffection(this,target));
    }

    @Override
    public void affectAll(Iterable<Agent> affectableAgents) {
        for(Agent agent:affectableAgents){
            affect(agent);
        }
    }


    @Override
    public DoorTurn clone(){ return new DoorTurn((Box2D)door.clone(),angle); }

   public double getAngle(){
        return angle;
    }
}
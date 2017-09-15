package org.socialforce.model.impl;

import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Semicircle2D;
import org.socialforce.model.*;
import org.socialforce.scene.Scene;

/**
 * Created by Administrator on 2017/9/15 0015.
 */
public class DoorTurn extends Entity implements Influential{

    protected Box2D box2D;

    public DoorTurn(Box2D box2D) {
        super(box2D);
        this.box2D = box2D;
    }

    @Override
    public Semicircle2D getView() {
        Point2D center = new Point2D((((Box2D) this.getPhysicalEntity()).getXmin()+((Box2D) this.getPhysicalEntity()).getXmax())/2,((Box2D) this.getPhysicalEntity()).getYmin());
        return new Semicircle2D(center,center.getX(),(Math.PI)/2);
    }

    @Override
    public void affect(Agent target) {
        target.rotate(model.interactionMoment(this,target));
    }

    @Override
    public PhysicalEntity getPhysicalEntity(){
        return box2D;
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public boolean onAdded(Scene scene) {
        return true;
    }

    @Override
    public void onStep(Scene scene) {

    }
}

package org.socialforce.model.impl;

import org.socialforce.geom.Affection;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Ellipse2D;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.geom.impl.Moment2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.Blockable;
import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public class SpinBodyForceRegulation implements ForceRegulation {
    public SpinBodyForceRegulation(SimpleSocialForceModel model){
        this.model = model;
    }
    private SimpleSocialForceModel model;
    private ForceRegulation forceRegulation = new BodyForce(Blockable.class,Agent.class,model);

    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return forceRegulation.hasForce(source,target) && target.getShape() instanceof Ellipse2D;
    }

    @Override
    public Affection getForce(InteractiveEntity interactiveEntity, InteractiveEntity interactiveEntity2) {
        Force2D force2D = (Force2D) forceRegulation.getForce(interactiveEntity,interactiveEntity2);
        Ellipse2D e2= (Ellipse2D) interactiveEntity2.getShape();
        Shape e1 = interactiveEntity.getShape();
        double[][] temp = e2.closePoint(e1);
        Point2D p2 = new Point2D(temp[1][0],temp[1][1]);
        Moment2D moment2D = (Moment2D) force2D.CalculateMoment(p2,e2.center);
        return moment2D;
    }
}

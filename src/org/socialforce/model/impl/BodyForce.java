package org.socialforce.model.impl;

import org.socialforce.geom.Force;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.model.Agent;
import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;

/**
 * apply Fij in the social force model
 * contains the 'body force' and  the 'sliding force'
 * Created by Whatever on 2016/8/26.
 */
public class BodyForce implements ForceRegulation{
    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        if (source instanceof Agent && target instanceof Agent &&
                ((Agent) source).getView().hits(target.getShape().getBounds())){
            return true;
        }
        else
        return false;
    }

    @Override
    public Force getForce(InteractiveEntity interactiveEntity, InteractiveEntity interactiveEntity2) {
        double A,B,e,k1,k2,g,v,fij,bodyForce,slidingForce,distance;
        distance = interactiveEntity.getShape().getReferencePoint().distanceTo(interactiveEntity2.getShape().getReferencePoint())
                - ((Circle2D) interactiveEntity.getShape()).getRadius()-((Circle2D) interactiveEntity2.getShape()).getRadius();
        bodyForce = A*Math.exp(distance/B) + k1*g*distance;
        slidingForce = k2*g*distance;
        return null;
    }
}

package org.socialforce.model.impl;

import org.socialforce.geom.Force;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.model.Agent;
import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;

/**
 * 在社会力模型中应用 Fij。
 *其中包括“body force”和“sliding force”。
 * 定义了BodyForce类，其实现了接口ForceRegulation 的方法
 * Created by Whatever on 2016/8/26.
 *
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
        double A,B,k1,k2,g,bodyForce,slidingForce,distance,argumentX;
        Vector2D t,n,tempVector;
        A = 2000;
        B = 0.1;
        k1 = 1.2 * 100;
        k2 = 2.4 * 100;
        g = 0;
        argumentX = 1;
        double temp[] = new double[2];
        tempVector = (Vector2D) ((Agent)interactiveEntity).getVelocity().clone();
        tempVector.sub(((Agent)interactiveEntity2).getVelocity());
        n = new Vector2D(interactiveEntity.getShape().getReferencePoint().getX() -  interactiveEntity2.getShape().getReferencePoint().getX(),
                interactiveEntity.getShape().getReferencePoint().getY() -  interactiveEntity2.getShape().getReferencePoint().getY());
        n.set(n.getRefVector());
        n.get(temp);
        t = new Vector2D(-temp[1],temp[0]);
        distance = 0;
        if (distance < 0){g = argumentX;}
        bodyForce = A*Math.exp(-distance/B) + k1*g*distance;
        slidingForce = k2*g*distance*t.dot(tempVector);
        n.scale(bodyForce);
        t.scale(slidingForce);
        n.add(t);
        n.get(temp);
        return new Force2D(temp[0],temp[1]) ;
    }
}

package org.socialforce.model.impl;

import org.socialforce.geom.Force;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.model.*;

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
        if ((source instanceof Agent && target instanceof Agent || source instanceof Wall && target instanceof Agent ) &&
                ((Agent) target).getShape().intersects(source.getShape())){
            return true;
        }
        else
        return false;
    }

    @Override
    public Force getForce(InteractiveEntity source, InteractiveEntity target) {
        double k1,k2,g,bodyForce,slidingForce,distance,argumentX;
        Vector2D t,n,tempVector;
        k1 = 0;
        k2 = 0;
        g = 0;
        argumentX = 1;
        double temp[] = new double[2];
        if (source instanceof Moveable){
        tempVector = (Vector2D) ((Moveable)source).getVelocity().clone();}
        else tempVector = new Vector2D(0,0);
        tempVector.sub(((Agent)target).getVelocity());
        n = (Vector2D) ((Circle2D)target.getShape()).directionTo(source.getShape());
        n.get(temp);
        t = new Vector2D(-temp[1],temp[0]);
        distance = ((Circle2D)target.getShape()).distanceTo(source.getShape());
        if (distance < 0){g = argumentX;}
        bodyForce =  k1*g*Math.abs(distance);
        slidingForce = k2*g*Math.abs(distance)*t.dot(tempVector);
        n.scale(bodyForce);
        t.scale(slidingForce);
        n.add(t);
        n.get(temp);
        return new Force2D(temp[0],temp[1]);
    }
}

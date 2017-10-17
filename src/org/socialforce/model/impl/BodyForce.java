package org.socialforce.model.impl;

import org.socialforce.geom.Affection;
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
public class BodyForce extends TypeMatchRegulation<Blockable, Agent>{
    public BodyForce(Class<Blockable> blockableClass, Class<Agent> agentClass, Model model) {
        super(blockableClass, agentClass, model);
    }

    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return super.hasForce(source,target) && ((Agent)target).getPhysicalEntity().distanceTo(source.getPhysicalEntity()) <=0;
    }

    @Override
    public Affection getForce(Blockable source, Agent target) {
        double k1,k2,g,bodyForce,slidingForce,distance,argumentX;
        Vector2D t,n,tempVector;
        k1 = 1.2 * 100000;
        k2 = 2.4 * 100000;
        g = 1;
        double temp[] = new double[2];
        if (source instanceof Moveable){
            tempVector = (Vector2D)((Moveable)source).getVelocity().clone();
        }
        else
            tempVector = new Vector2D(0,0);
        tempVector.sub(target.getVelocity());
        n = (Vector2D) target.getPhysicalEntity().directionTo(source.getPhysicalEntity());
        n.get(temp);
        t = new Vector2D(-temp[1],temp[0]);
        distance = (target.getPhysicalEntity().distanceTo(source.getPhysicalEntity()));
        if (distance > 0){g = 0;}
        bodyForce =  k1*g*Math.abs(distance);
        slidingForce = k2*g*Math.abs(distance)*t.dot(tempVector);
        n.scale(bodyForce);
        t.scale(slidingForce);
        n.add(t);
        n.get(temp);
        return new Force2D(temp[0],temp[1]);
    }

}

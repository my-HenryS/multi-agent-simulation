package org.socialforce.model.impl;

import org.socialforce.geom.Affection;
import org.socialforce.geom.RotatablePhysicalEntity;
import org.socialforce.geom.Velocity;
import org.socialforce.geom.impl.Ellipse2D;
import org.socialforce.geom.impl.Moment2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.*;

/**
 * Created by Administrator on 2017/9/15 0015.
 */
public class DoorTurnRegulation extends TypeMatchRegulation<DoorTurn,Agent>{


    public DoorTurnRegulation(Class<DoorTurn> doorTurnClass, Class<Agent> agentClass, SimpleForceModel model) {
        super(doorTurnClass, agentClass, model);
    }


    @Override
    public Affection getForce(DoorTurn doorTurn, Agent agent) {
        if ((agent.getPhysicalEntity() instanceof RotatablePhysicalEntity)&&(agent.getPhysicalEntity().intersects(doorTurn.getView()))){
            Vector2D direction = (((Ellipse2D)agent.getPhysicalEntity()).getBasicDirection());
            double angle = Vector2D.getRotateAngle(doorTurn.getViewDirection(),direction);
            if(Math.cos(angle)<1.0e-7) {
                doorTurn.getViewDirection().clone().scale(-1);
                angle = Vector2D.getRotateAngle(direction,doorTurn.getViewDirection());
            }
            return new Moment2D(angle*400);
            //return new Moment2D( -5.15*angle*angle+9.78*angle-3.09);
        }
        return new Moment2D(0);

    }
}

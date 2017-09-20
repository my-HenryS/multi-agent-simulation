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
        /*if ((agent.getPhysicalEntity() instanceof Rotateable)&&(doorTurn.getView().hits(agent.getPhysicalEntity().getBounds()))){
            double angle = ((Ellipse2D) agent.getPhysicalEntity()).getAngle();
            double angleAcceleration = 11.45 * angle * angle - 22.38 * angle + 7.10;
            return new Moment2D(agent.getIntetia()*angleAcceleration);
        }*/
        if ((agent.getPhysicalEntity() instanceof RotatablePhysicalEntity)&&(agent.getPhysicalEntity().intersects(doorTurn.getView()))){
            double angle = ((Ellipse2D)agent.getPhysicalEntity()).getAngle();
            double size = Math.PI/2 - angle;
            return new Moment2D(size*400);
        }
        return new Moment2D(0);

    }
}

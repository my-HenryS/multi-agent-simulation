package org.socialforce.model.impl;

import org.socialforce.geom.Affection;
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
        if ((agent.getPhysicalEntity() instanceof Rotateable)&&(doorTurn.getView().hits(agent.getPhysicalEntity().getBounds()))){
            double angle = ((Ellipse2D)agent.getPhysicalEntity()).getAngle();
            Vector2D side = new Vector2D(Math.cos(angle),Math.sin(angle));
            if (Math.sin(angle) < 0)
                side.scale(-1);
            //Velocity2D expected = (Velocity2D)agent.getVelocity();
            Velocity2D expected = new Velocity2D(0,0);
            expected.sub(agent.getPhysicalEntity().getReferencePoint());
            expected.add(agent.getPath().nextStep(agent.getPhysicalEntity().getReferencePoint()));
            double size = Vector2D.getRotateAngle(expected,side);
            return new Moment2D(size*200);
        }
        return new Moment2D(0);

    }
}

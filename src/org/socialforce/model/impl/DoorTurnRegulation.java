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
        if(agent.getPhysicalEntity() instanceof RotatablePhysicalEntity){
            //double angle = ((BaseAgent)agent).getExpectedAngle()-((Ellipse2D)agent.getPhysicalEntity()).getAngle();
            double angle=((Ellipse2D)agent.getPhysicalEntity()).getAngle(), e_angle=((BaseAgent)agent).getExpectedAngle();
            System.out.println(angle+e_angle);
            double a=0.47,b=43.5,c=13.3,d=25.8,result;
            result=angle*c*c*(1-angle/(a*e_angle+b))*(1-2*angle/(a*e_angle+b));
            if(Math.abs(angle) > Math.abs(((BaseAgent)agent).getExpectedAngle()-(((Ellipse2D)agent.getPhysicalEntity()).getAngle()+Math.PI)))
                angle = angle-Math.PI;
            if(angle > Math.abs((Math.PI-((BaseAgent)agent).getExpectedAngle())-((Ellipse2D)agent.getPhysicalEntity()).getAngle()))
                angle = (Math.PI-((BaseAgent)agent).getExpectedAngle())-((Ellipse2D)agent.getPhysicalEntity()).getAngle();
            if(angle > Math.abs((Math.PI-((BaseAgent)agent).getExpectedAngle())-(((Ellipse2D)agent.getPhysicalEntity()).getAngle()+Math.PI)))
                angle = angle-Math.PI;
            return new Moment2D(result*agent.getIntetia());  //FIXME 主动侧身转矩的计算公式（修改“400”）
            //return new Moment2D(agent.getIntetia()*(13.71*angle*angle-2.74*angle-2.03));
        }
        return new Moment2D(0);
    }

}

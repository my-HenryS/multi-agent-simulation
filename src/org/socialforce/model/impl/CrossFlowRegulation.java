package org.socialforce.model.impl;

import org.socialforce.geom.Affection;
import org.socialforce.geom.RotatablePhysicalEntity;
import org.socialforce.geom.impl.Ellipse2D;
import org.socialforce.geom.impl.Moment2D;
import org.socialforce.model.Agent;

/**
 * Created by Administrator on 2017/12/4 0004.
 */
public class CrossFlowRegulation extends TypeMatchRegulation<CrossFlow,Agent> {
    public CrossFlowRegulation(Class<CrossFlow> crossFlowClass, Class<Agent> agentClass, SimpleForceModel model) {
        super(crossFlowClass, agentClass, model);
    }


    @Override
    public Affection getForce(CrossFlow crossFlow, Agent agent) {
        if(agent.getPhysicalEntity() instanceof RotatablePhysicalEntity){
            double angle = ((Ellipse2D)agent.getPhysicalEntity()).getAngle();
            if((angle < -1*Math.PI)||(angle > Math.PI)){
                if((Math.PI < angle%(2*Math.PI)) && (angle%(2*Math.PI) < 2*Math.PI))
                    angle = angle%(2*Math.PI)-2*Math.PI;
                else if((-2*Math.PI < angle%(2*Math.PI)) && (angle%(2*Math.PI) < -1*Math.PI))
                    angle = angle%(2*Math.PI)+2*Math.PI;
                else
                    angle = angle%(2*Math.PI);
            }
            angle = 0-angle;
            if(Math.abs(angle) > Math.abs(Math.PI-((Ellipse2D)agent.getPhysicalEntity()).getAngle()))
                angle = Math.PI-((Ellipse2D)agent.getPhysicalEntity()).getAngle();
            if(Math.abs(angle) > Math.abs(Math.PI+((Ellipse2D)agent.getPhysicalEntity()).getAngle()))
                angle = -Math.PI-((Ellipse2D)agent.getPhysicalEntity()).getAngle();
            return new Moment2D(angle*400);  //FIXME 主动侧身转矩的计算公式（修改“400”）
            //return new Moment2D(agent.getIntetia()*(13.71*angle*angle-2.74*angle-2.03));
        }
        return new Moment2D(0);
    }
}

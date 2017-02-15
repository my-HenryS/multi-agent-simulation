package org.socialforce.model.impl;

import org.socialforce.container.impl.LinkListAgentPool;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;

import java.util.Iterator;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class Monitor extends Entity {
    double volume = 0, velocity = 0,  timePerStep = 0;
    int vNum = 0;
    LinkListAgentPool agents = new LinkListAgentPool();
    public Monitor(Shape shape) {
        super(shape);
    }

    public void setTimePerStep(double timePerStep){
        this.timePerStep = timePerStep;
    }

    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if(affectedEntity instanceof Agent) {
            if(((Agent)affectedEntity).getShape().intersects(shape)){
                velocity += ((BaseAgent) affectedEntity).getLastAcc()/affectedEntity.getMass();
                vNum += 1;
                //if(!agents.contains(affectedEntity)){   //流量计数不复用Agent
                    //agents.addLast((Agent)affectedEntity);
                    volume += 1;
                //}

            }
        }
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public InteractiveEntity standardclone() {
        return new Monitor(shape.clone());
    }

    public double sayVolume(){
       return volume/scene.getCurrentSteps();
    }

    public double sayVelocity(){
        return vNum > 0 ? velocity/vNum : 0;
    }
}

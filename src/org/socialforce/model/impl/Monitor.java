package org.socialforce.model.impl;

import org.socialforce.container.impl.LinkListAgentPool;
import org.socialforce.geom.ModelShape;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class Monitor extends Entity {
    double volume = 0, velocity = 0,  timePerStep = 0;
    int vNum = 0;
    LinkListAgentPool agents = new LinkListAgentPool();
    public Monitor(ModelShape modelShape) {
        super(modelShape);
    }

    public void setTimePerStep(double timePerStep){
        this.timePerStep = timePerStep;
    }

    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if(affectedEntity instanceof Agent) {
            if(((Agent)affectedEntity).getModelShape().intersects(modelShape)){
                velocity += ((Agent) affectedEntity).getVelocity().length();
                vNum += 1;
                if(!agents.contains(affectedEntity)){   //流量计数不复用Agent
                    agents.addLast((Agent)affectedEntity);
                    volume += 1;
                }

            }
        }
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public InteractiveEntity standardclone() {
        return new Monitor(modelShape.clone());
    }

    public double sayVolume(){
       return volume/(scene.getCurrentSteps() * timePerStep);
    }

    public double sayVelocity(){
        return vNum > 0 ? velocity/vNum : 0;
    }
}

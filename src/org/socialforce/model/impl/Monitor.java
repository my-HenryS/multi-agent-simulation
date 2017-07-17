package org.socialforce.model.impl;

import org.socialforce.container.impl.LinkListAgentPool;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.model.Agent;
import org.socialforce.model.Influential;
import org.socialforce.model.InteractiveEntity;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class Monitor extends Entity implements Influential {
    double volume = 0, velocity = 0,  timePerStep = 0;
    int vNum = 0, EC;
    int firstT = -1, lastT;
    LinkListAgentPool agents = new LinkListAgentPool();
    public Monitor(Shape shape) {
        super(shape);
    }

    public void setTimePerStep(double timePerStep){
        this.timePerStep = timePerStep;
    }

    @Override
    public Shape getView() {
        return this.getShape();
    }

    public void affect(Agent target) {
        velocity += ((BaseAgent) target).currVelocity.dot(new Vector2D(0,1));
        vNum += 1;
        if(!agents.contains(target)){   //EC计数不复用Agent
            agents.addLast(target);
            EC += 1;
            if(firstT == -1) firstT = scene.getCurrentSteps();
            lastT = scene.getCurrentSteps();
        }
        volume += 1;

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

    public double sayEC(){
        return EC/(lastT - firstT);
    }
}

package org.socialforce.model.impl;

import com.sun.xml.internal.bind.v2.TODO;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.socialforce.container.Pool;
import org.socialforce.geom.*;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/6/17.
 */
public class NeuralForceModel implements Model{
    String parentPath = System.getProperty("user.dir")+"/resource/";
    String locationToSave = "neuralNet/robust.net";

    double timePerStep = 0.05;
    double min_div = 0.5;
    double p = 0.3;
    MultiLayerNetwork model = null;

    public NeuralForceModel(){

        try {
            model = ModelSerializer.restoreMultiLayerNetwork(parentPath+locationToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void fieldForce(Pool targets) {
        for(Object target:targets) {
            Agent agent = (Agent)target;
            Velocity2D expected = (Velocity2D) this.zeroVelocity(), velocity = (Velocity2D) agent.getVelocity();
            Point current = agent.getShape().getReferencePoint(), goal = agent.getPath().nextStep(current);
            expected.sub(current);
            expected.add(goal);
            double angle = Vector2D.getRotateAngle(new Vector2D(1,0), velocity);
            velocity.rotate(angle);
            expected.rotate(angle);
            boolean rotated = false;
            if(expected.getY() < 0){
                expected = new Velocity2D(expected.getX(),-expected.getY());
                rotated = true;
            }
            double a_angle = Vector2D.getRotateAngle(expected, new Vector2D(1,0));
            /* env matrix TODO replace by matrix instead of n^2 iteration*/
            int n= 0;
            for(Object t_arget:targets){
                Agent co_Agent = (Agent)t_arget;
                if(!co_Agent.equals(agent) && co_Agent.getShape().distanceTo(agent.getShape()) < min_div){
                   n++;
                }
            }
            /* end env matrix*/
            INDArray input = Nd4j.create(new double[]{a_angle, velocity.getX(), 0, 0, 0, n*p, 0, 0, 0, 0, 0});
            INDArray output = model.output(input);
            Velocity2D newV = new Velocity2D(output.getDouble(0),output.getDouble(1));
            if(rotated) newV = new Velocity2D(newV.getX(),-newV.getY());
            newV.rotate(-angle);
            ((BaseAgent)agent).setCurrVelocity(newV);
        }

    }

    @Override
    public Force interactionForce(InteractiveEntity source, InteractiveEntity target) {
        return zeroForce();
    }

    @Override
    public Moment interactionMoment(InteractiveEntity source, InteractiveEntity target) {
        return null;
    }

    @Override
    public Vector zeroVector() {
        return new Vector2D(0,0);
    }

    @Override
    public Velocity zeroVelocity() {
        return new Velocity2D(0,0);
    }

    @Override
    public Force zeroForce() {
        return new Force2D(0,0);
    }

    @Override
    public double getTimePerStep() {
        return timePerStep;
    }

    @Override
    public Moment zeroMoment() {
        return null;
    }

    @Override
    public Model clone() {
        return new NeuralForceModel();
    }
}

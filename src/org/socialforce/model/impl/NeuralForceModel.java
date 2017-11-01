package org.socialforce.model.impl;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.socialforce.container.Pool;
import org.socialforce.geom.*;
import org.socialforce.geom.impl.*;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sunjh1999 on 2017/6/17.
 */
public class NeuralForceModel implements Model{
    String parentPath = System.getProperty("user.dir")+"/resource/";
    String locationToSave = "neuralNet/robust.net";

    double timePerStep = 2.0/30;
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
            Velocity2D velocity = (Velocity2D) agent.getVelocity();
            Vector2D expected;
            Point position = agent.getPhysicalEntity().getReferencePoint();
            expected =(Vector2D) agent.getPath().nextStep(position).sub(position);

            LinkedList<Vector2D> results = new LinkedList<>();
            results = getNearest5(targets, agent);

            double angle = Vector2D.getRotateAngle(new Vector2D(1,0), velocity);
            velocity.rotate(angle);
            expected.rotate(angle);
            for(Vector2D vector2D:results){
                vector2D.rotate(angle);
            }

            /*boolean rotated = false;
            if(expected.getY() < 0){
                expected = new Velocity2D(expected.getX(),-expected.getY());
                for(Vector2D vector2D:results){
                    vector2D.get(new double[]{vector2D.getX(), - vector2D.getY()});
                }
                rotated = true;
            }*/
            double a_angle = Vector2D.getRotateAngle(expected, new Vector2D(1,0));
            LinkedList<Double> tempA = new LinkedList<>();
            tempA.add(a_angle);
            tempA.add(velocity.getX());
            for(Vector2D vector2D:results){
                tempA.add(vector2D.getX());
                tempA.add(vector2D.getY());
            }
            double[] result = new double[22];
            for(int i =0; i < 22;i++){
                result[i] = tempA.get(i);
            }

            INDArray input = Nd4j.create(result);
            INDArray output = model.output(input);
            Velocity2D newV = new Velocity2D(output.getDouble(0),output.getDouble(1));
            //if(rotated) newV = new Velocity2D(newV.getX(),-newV.getY());
            newV.rotate(-angle);
            ((BaseAgent)agent).setVelocity(newV);
            Force force = new Force2D(newV.getX(),newV.getY());
            force.scale(agent.getMass());
            agent.push(force);
        }

    }

    @Override
    public Affection interactAffection(InteractiveEntity source, InteractiveEntity target) {
        return new Affection2D();
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
        return new Moment2D(0);
    }

    @Override
    public Model clone() {
        return new NeuralForceModel();
    }

    private LinkedList<Vector2D> getNearest5(Pool targets, Agent affected_agent){

        LinkedList<Vector2D> velocities = new LinkedList<>();
        LinkedList<Vector2D> positions = new LinkedList<>();
        for(Object target:targets) {
            Agent agent = (Agent)target;
            if(agent.equals(affected_agent)) continue;
            velocities.add((Vector2D) agent.getVelocity().clone().sub(affected_agent.getVelocity()));
            positions.add((Vector2D) agent.getPhysicalEntity().getReferencePoint().clone().sub(affected_agent.getPhysicalEntity().getReferencePoint()));
        }
        for(int i = 0; i < positions.size()-1; i++){
            if(positions.get(i).length() > positions.get(i+1).length()){
                switch_vector(positions,i,i+1);
                switch_vector(velocities,i,i+1);
                if(i >= 1) i-=2;
            }
        }

        LinkedList<Vector2D> result = new LinkedList<>();
        for(int i = 0; i < 5; i++){
            Vector2D delpoint = positions.get(i);
            if(delpoint.getX()>=0) {
                if(delpoint.getX()>3){
                    delpoint.setX(0);
                }else{
                    delpoint.setX(1-delpoint.getX()/3);
                }
            }else{
                if(delpoint.getX()<-3){
                    delpoint.setX(0);
                }else{
                    delpoint.setX(-(1+delpoint.getX()/3));
                }
            }

            if(delpoint.getY()>=0) {
                if(delpoint.getY()>3){
                    delpoint.setY(0);
                }else{
                    delpoint.setY(1-delpoint.getY()/3);
                }
            }else{
                if(delpoint.getY()<-3){
                    delpoint.setY(0);
                }else{
                    delpoint.setY(-(1+delpoint.getY()/3));
                }
            }
            result.add(delpoint);
            result.add((delpoint.getX()==0)||(delpoint.getY()==0)?new Vector2D(0,0):velocities.get(i));
        }
        return result;

    }
    private <T> void switch_vector(List<T> list, int i, int j){
        T temp;
        temp = list.get(i);
        list.set(i,list.get(j));
        list.set(j, temp);
    }
}

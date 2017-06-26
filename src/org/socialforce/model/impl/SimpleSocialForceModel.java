package org.socialforce.model.impl;

import org.socialforce.geom.*;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.geom.impl.Moment2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.*;

import java.util.LinkedList;
import java.util.List;

/**定义SimpleSocialForceModel类，其实现了接口SocialForceModel的方法
 * Created by Ledenel on 2016/8/17.
 */
public class SimpleSocialForceModel implements Model {
    double TIME_PER_STEP = 0.008;
    double EXPECTED_SPEED = 3;
    double REACT_TIME = 0.5;

    long psyT = 0, bodyT = 0, flT = 0;
    int psyN = 0, bodyN = 0, flN = 0;

    protected List<ForceRegulation> regulations;

    public SimpleSocialForceModel() {
        regulations = new LinkedList<>();
        regulations.add(new PsychologicalForceRegulation(Blockable.class, Agent.class, this));
        regulations.add(new BodyForce(Blockable.class, Agent.class, this));
        //regulations.add(new WallForce());
        regulations.add(new DoorForce(Agent.class, Door.class, this));
        regulations.add(new GravityRegulation(Star_Planet.class, Star_Planet.class, this));
        regulations.add(new SpinForceRegulation());
    }

    /**
     * 计算源实体对目标实体的作用力。
     *
     * @param source 产生作用力的实体。
     * @param target 受力的作用的实体。
     * @return the force. 返回力的大小，其单位是牛。
     */
    @Override
    public Force interactionForce(InteractiveEntity source, InteractiveEntity target) {
//        return zeroForce(); // added calculation implements.
        long startT = System.currentTimeMillis();
        Force force = this.zeroForce();
        for (ForceRegulation regulation : regulations) {
            if (regulation.hasForce(source, target)) {
                if (regulation.getForce(source, target) instanceof Force){
                force.add((Vector) regulation.getForce(source, target));}
            }
            long span = System.currentTimeMillis() - startT;
            startT = System.currentTimeMillis();
            if(regulation instanceof PsychologicalForceRegulation){
                psyT += span;
                psyN += 1;
            }
            else if(regulation instanceof BodyForce){
                bodyT += span;
                bodyN += 1;
            }
        }
        return force;
    }

    @Override
    public Moment interactionMoment(InteractiveEntity source, InteractiveEntity target) {
        Moment moment = this.zeroMoment();
        for (ForceRegulation regulation : regulations) {
                if (regulation.getForce(source, target) instanceof Moment){
                    moment.add((Moment) regulation.getForce(source, target));
                }
            }
        return moment;
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

    public Moment zeroMoment(){return new Moment2D(0);}

    @Override
    public double getTimePerStep() {
        return TIME_PER_STEP;
    }

    public double getExpectedSpeed() {
        return EXPECTED_SPEED;
    }

    public void setExpectedSpeed(double Expect) {
        EXPECTED_SPEED = Expect;
    }

    /**
     * 生成模型的场力--即驱动力。
     *
     * @param source  产生作用力的实体。
     * @return the force. 返回力的大小，其单位是牛。
     */
    public Force fieldForce(InteractiveEntity source) {
        if (!(source instanceof Agent)) {
            return zeroForce();
        }
        Agent agent = (Agent) source;
        Velocity expected = this.zeroVelocity();
        Force force = this.zeroForce();
        Point current = agent.getShape().getReferencePoint(), goal = agent.getPath().nextStep(current);
        expected.sub(current);
        expected.add(goal);
        expected.scale(EXPECTED_SPEED / expected.length());
        force.add(expected);
        force.sub(agent.getVelocity());
        force.scale(agent.getMass() / REACT_TIME);
        return force;
    }

    private <T extends InteractiveEntity> T reg(T entity) {
        entity.setModel(this);
        return entity;
    }

    public Model clone(){
        return new SimpleSocialForceModel();
    }
}

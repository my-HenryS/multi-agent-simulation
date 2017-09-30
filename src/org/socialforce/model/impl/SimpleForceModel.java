package org.socialforce.model.impl;

import org.socialforce.container.Pool;
import org.socialforce.geom.*;
import org.socialforce.geom.impl.*;
import org.socialforce.model.*;
import org.socialforce.settings.LoadFrom;

import java.util.LinkedList;
import java.util.List;

/**定义SimpleSocialForceModel类，其实现了接口SocialForceModel的方法
 * Created by Ledenel on 2016/8/17.
 */
public class SimpleForceModel implements Model {
    @LoadFrom("time_per_step")
    public static double TIME_PER_STEP = 0.002;  //仿真步长
    double EXPECTED_SPEED = 1.5;   //FIXME 期望速度
    double EXPECTED_PALSTANCE = 0;
    double REACT_TIME = 0.5;

    long psyT = 0, bodyT = 0, flT = 0;
    int psyN = 0, bodyN = 0, flN = 0;

    protected List<ForceRegulation> regulations;

    public SimpleForceModel() {
        regulations = new LinkedList<>();
        regulations.add(new PsychologicalForceRegulation(Blockable.class, Agent.class, this));  //FIXME 施加心理力：行人规避障碍物的力
        regulations.add(new BodyForce(Blockable.class, Agent.class, this));                     //FIXME 施加接触力
        regulations.add(new DoorForce(Agent.class, Door.class, this));                          //FIXME 施加人推门的力
        regulations.add(new GravityRegulation(Star_Planet.class, Star_Planet.class, this));     //FIXME 施加重力
        regulations.add(new SpinBodyForceRegulation(this));                                     //FIXME 施加由于接触力产生的转矩
        regulations.add(new SpinPsyForceRegulation(this));                                      //FIXME 施加由于心理力产生的转矩
        regulations.add(new DoorTurnRegulation(DoorTurn.class,Agent.class,this));               //FIXME 人过门的时候施加主动侧身的转矩
    }

    public SimpleForceModel(double timePerStep){
        this();
        TIME_PER_STEP = timePerStep;
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
        Force force = this.zeroForce();
        for (ForceRegulation regulation : regulations) {
            if (Force.class.isAssignableFrom(regulation.forceType()) && regulation.hasForce(source, target)) {
                Affection temp = regulation.getForce(source, target);
                if (temp instanceof Force){
                force.add((Vector) temp);}
            }
        }
        return force;
    }

    @Override
    public Moment interactionMoment(InteractiveEntity source, InteractiveEntity target) {
        Moment moment = this.zeroMoment();
        for (ForceRegulation regulation : regulations) {
            if (Moment.class.isAssignableFrom(regulation.forceType()) && regulation.hasForce(source,target)) {
                Affection temp = regulation.getForce(source, target);
                if (temp instanceof Moment) {
                    moment.add((Moment) temp);
                }
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

    @Override
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
     * @param sources  获有作用力的实体们。
     * @return the force. 返回力的大小，其单位是牛。
     */
    public void fieldForce(Pool sources) {
        for(Object source : sources){
            if (source instanceof Star_Planet || !(source instanceof Agent)) {
                return;
            }
            Agent agent = (Agent) source;
            Velocity expected = this.zeroVelocity();
            Force force = this.zeroForce();
            Point current = agent.getPhysicalEntity().getReferencePoint(), goal = agent.getPath().nextStep(current);
            expected.sub(current);
            expected.add(goal);
            expected.scale(EXPECTED_SPEED / expected.length());
            force.add(expected);
            force.sub(agent.getVelocity());
            force.scale(agent.getMass() / REACT_TIME);
            agent.push(force);  //FIXME 施加驱动力：使行人的速度趋向于期望速度

            if(agent.getPhysicalEntity() instanceof Ellipse2D){
                Moment moment;
                Palstance p = agent.getPalstance().clone(), expectP = new Palstance2D(EXPECTED_PALSTANCE);
                p.scale(-1);
                expectP.add(p);
                moment = new Moment2D(5*(expectP.getOmega() * agent.getIntetia()) / REACT_TIME);  //FIXME 驱动转矩的计算公式（修改“5”）
                agent.rotate(moment);  //FIXME 施加驱动转矩：使行人的角速度趋向于0
            }
        }
    }

    private <T extends InteractiveEntity> T reg(T entity) {
        entity.setModel(this);
        return entity;
    }

    public Model clone(){
        return new SimpleForceModel();
    }
}

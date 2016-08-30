package org.socialforce.model.impl;

import org.socialforce.geom.*;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ledenel on 2016/8/17.
 */
public class SimpleSocialForceModel implements SocialForceModel {
    public static final double TIME_PER_STEP = 0.1;
    public static final double AGENT_VIEW_RADIUS = 6;
    public static final double EXPECTED_SPEED = 1.4;
    public static final double REACT_TIME = 0.4;

    public static final double AGENT_SIZE = 0.486;

    public static final int STATIC_TYPE_WALL = 0;
    public static final int STATIC_TYPE_GATE = 1;


    protected List<ForceRegulation> regulations;

    public SimpleSocialForceModel() {
        regulations = new LinkedList<>();
        regulations.add(new PsychologicalForceRegulation(InteractiveEntity.class, Agent.class, this));
    }

    /**
     * get the constraint that how much time is a step.
     *
     * @return time per step. the unit is second.
     */
    @Override
    public double getTimePerStep() {
        return TIME_PER_STEP;
    }

    /**
     * get the agent view.
     *
     * @return the agent view.
     */
    @Override
    public Shape getAgentView() {
        Circle2D circle2D = new Circle2D();
        circle2D.setRadius(AGENT_VIEW_RADIUS);
        return circle2D;
    }

    /**
     * calculate social force from source to target.
     *
     * @param source the entity which generates the force.
     * @param target the entity which the force applies on.
     * @return the force. the unit is Newton.
     */
    @Override
    public Force calcualte(InteractiveEntity source, InteractiveEntity target) {
//        return zeroForce(); // added calculation implements.
        Force force = this.zeroForce();
        for (ForceRegulation regulation : regulations) {
            if (regulation.hasForce(source, target)) {
                force.add(regulation.getForce(source, target));
            }
        }
        return force;
    }

    /**
     * calculate social force if the force is applied on itself.
     *
     * @param source the entity which generates the force.
     * @return the force. the unit is Newton.
     */
    @Override
    public Force getPower(InteractiveEntity source) {
        if (source instanceof Agent) {
            Agent agent = (Agent) source;
            Force force = this.zeroForce();
            force.add(agent.expect());
            force.sub(agent.getVelocity());
            force.scale(agent.getMass() / REACT_TIME);
            return force;
        }
        return zeroForce();
    }

    /**
     * create a agent using default presets.
     *
     * @return the created agent.
     */
    @Override
    public Agent createAgent() {
        Circle2D size = new Circle2D();
        size.setRadius(AGENT_SIZE);
        return new BaseAgent(size);
    }

    /**
     * create a agent with specific arguments.
     *
     * @param shape @return the created agent.
     */
    @Override
    public Agent createAgent(DistanceShape shape) {
        return createAgent();
    }

    /**
     * create a agent with specific shape and type.
     *
     * @param shape the agent's shape.
     * @param type  the type of the agent.
     * @return the created agent.
     */
    @Override
    public Agent createAgent(DistanceShape shape, int type) {
        return createAgent();
    }

    /**
     * create a static object with default presets.
     * the static object can be walls, gates, obstacles, etc.
     *
     * @return the static object.
     */
    @Override
    public InteractiveEntity createStatic() {
        return new Wall(null);
    }

    /**
     * create a static object with specific arguments.
     * the static object can be walls, gates, obstacles, etc.
     *
     * @param shape@return the static object.
     */
    @Override
    public InteractiveEntity createStatic(Shape shape) {
        return new Wall(shape);
    }

    /**
     * create a static object with specific arguments.
     * the static object can be walls, gates, obstacles, etc.
     *
     * @param shape the static object's shape.
     * @param type  the static object's type.
     * @return the static object.
     */
    @Override
    public InteractiveEntity createStatic(Shape shape, int type) {
        if (type == STATIC_TYPE_WALL) {
            return new Wall(shape);
        } else if (type == STATIC_TYPE_GATE) {
            return new SafetyRegion(shape);
        }
        return createStatic(shape);
    }

    /**
     * creates and returns a zero vector.
     *
     * @return the zero vector.
     */
    @Override
    public Vector zeroVector() {
        return new Vector2D();
    }

    /**
     * creates and returns a zero velocity.
     *
     * @return the zero veloctiy.
     */
    @Override
    public Velocity zeroVelocity() {
        return new Velocity2D();
    }

    /**
     * creates and returns a zero force.
     *
     * @return the zero force.
     */
    @Override
    public Force zeroForce() {
        return new Force2D();
    }

    /**
     * get the motivate speed of an agent.
     *
     * @param current the point which agent is on.
     * @param goal    the point agent is going.
     * @return the speed of the motivation.
     */
    @Override
    public Velocity getAgentMotivation(Point current, Point goal) {
        Velocity expected = this.zeroVelocity();
        expected.sub(current);
        expected.add(goal);
        expected.scale(EXPECTED_SPEED / expected.length());
        return expected;
    }
}

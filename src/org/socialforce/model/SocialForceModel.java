package org.socialforce.model;

import org.socialforce.app.Scene;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.geom.*;

/**
 * the social force model.
 * a social force model is used to manage constraints,
 * calculating forces between entities,
 * and create interactive entities compatible with this model.
 * the unit of these constraints and forces are standard units.
 *
 * @author Ledenel
 * @see SocialForceApplication
 * @see Scene
 * Created by Ledenel on 2016/7/30.
 */
public interface SocialForceModel {
    /**
     * get the constraint that how much time is a step.
     *
     * @return time per step. the unit is second.
     */
    double getTimePerStep();

    /**
     * get the agent view.
     *
     * @return the agent view.
     */
    Shape getAgentView();

    /**
     * calculate social force from source to target.
     *
     * @param source the entity which generates the force.
     * @param target the entity which the force applies on.
     * @return the force. the unit is Newton.
     */
    Force calcualte(InteractiveEntity source, InteractiveEntity target);

    /**
     * calculate social force if the force is applied on itself.
     *
     * @param source the entity which generates the force.
     * @return the force. the unit is Newton.
     */
    Force getPower(InteractiveEntity source);

    /**
     * create a agent using default presets.
     *
     * @return the created agent.
     */
    Agent createAgent();

    /**
     * create a agent with specific shape.
     *
     * @param shape the agent's shape.
     * @return the created agent.
     */
    Agent createAgent(DistanceShape shape);

    /**
     * create a agent with specific shape and type.
     *
     * @param shape the agent's shape.
     * @param type  the type of the agent.
     * @return the created agent.
     */
    Agent createAgent(DistanceShape shape, int type);

    /**
     * create a static object with default presets.
     * the static object can be walls, gates, obstacles, etc.
     *
     * @return the static object.
     */
    InteractiveEntity createStatic();

    /**
     * create a static object with specific arguments.
     * the static object can be walls, gates, obstacles, etc.
     *
     * @param shape the static object's shape.
     * @return the static object.
     */
    InteractiveEntity createStatic(Shape shape);

    /**
     * create a static object with specific arguments.
     * the static object can be walls, gates, obstacles, etc.
     *
     * @param shape the static object's shape.
     * @param type  the static object's type.
     * @return the static object.
     */
    InteractiveEntity createStatic(Shape shape, int type);

    /**
     * creates and returns a zero vector.
     *
     * @return the zero vector.
     */
    Vector zeroVector();

    /**
     * creates and returns a zero velocity.
     *
     * @return the zero veloctiy.
     */
    Velocity zeroVelocity();

    /**
     * creates and returns a zero force.
     *
     * @return the zero force.
     */
    Force zeroForce();

    /**
     * get the motivate speed of an agent.
     *
     * @param current the point which agent is on.
     * @param goal    the point agent is going.
     * @return the speed of the motivation.
     */
    Velocity getAgentMotivation(Point current, Point goal);
}

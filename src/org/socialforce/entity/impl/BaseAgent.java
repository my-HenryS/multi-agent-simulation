package org.socialforce.entity.impl;

import org.socialforce.entity.*;

/**
 * Created by Ledenel on 2016/8/15.
 */
public class BaseAgent extends Entity implements Agent {
    Velocity currVelocity;
    Path path;
    double mass;
    int currTimestamp;
    Shape view;
    Force pushed;

    public BaseAgent(Shape shape) {
        super(shape);
    }

    /**
     * get the velocity of this moving entity.
     *
     * @return the velocity.
     */
    @Override
    public Velocity getVelocity() {
        return currVelocity;
    }

    /**
     * push the entity with a force on the reference point.
     * the force will move this entity.
     *
     * @param force the force to push.
     */
    @Override
    public void push(Force force) {
        this.pushed.add(force);
    }

    /**
     * push the entity on a specific point.
     * this method can also cause entity to rotate.
     *
     * @param force      the force to push.
     * @param startPoint the point which the force is push on.
     */
    @Override
    public void push(Force force, Point startPoint) {
        push(force);
    }

    /**
     * get the view of a agent.
     * agent only interact with other entities inside its view.
     *
     * @return a shape represent the view area.
     * @see Shape
     */
    @Override
    public Shape getView() {
        return view;
    }

    /**
     * get the expecting moving velocity of agent.
     * the velocity usually depends on the agent itself and its goal.
     *
     * @return the expected velocity
     * @see Velocity
     */
    @Override
    public Velocity expect() {
        return null; //TODO: encapsulate expect with this and target in social force model.
    }

    /**
     * determine the next point to move.
     * the agent will also be pushed by social force in determination.
     * the determined result will be applied in act() method.
     * if the current time step is not synchronized with the agent,
     * the agent will try to catch up with that time
     * (or ignore it if current time step is fall behind).
     *
     * @param currSteps the current timestep.
     * @return the vector represent the direction and distance to move.
     */
    @Override
    public Vector determineNext(int currSteps) {
        return null;
    }

    /**
     * determine the next point to move.
     * the agent will also be pushed by social force in determination.
     * the determined result will be applied in act() method.
     *
     * @return the vector represent the direction and distance to move.
     */
    @Override
    public Vector determineNext() {
        return null;
    }

    /**
     * get the current timestep of this agent.
     * the timestep begin at 0(start of the simulation)
     *
     * @return the current timestep.
     */
    @Override
    public int getCurrentSteps() {
        return currTimestamp;
    }

    /**
     * apply the determination made by determineNext() method.
     * this method will also push the time forward 1 step.
     * when act() succeed, the previous determinations will be cleared.
     * nothing happened if there are not available determinations in this agent.
     * nothing happened if the agent reach its goal.
     */
    @Override
    public void act() {

    }

    /**
     * get the path of the agent.
     *
     * @return the path object.
     */
    @Override
    public Path getPath() {
        return path;
    }

    /**
     * set the path for the agent.
     *
     * @param path the path to be set.
     */
    @Override
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * affect the target entity with this.
     * for example, walls can affect an agent (push them).
     *
     * @param affectedEntity the entity to be affected.
     * @see Agent
     * @see SocialForceModel
     */
    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if (affectedEntity instanceof Agent) {
            Agent agent = (Agent)affectedEntity;
            agent.push(model.calcualte(this,affectedEntity));
        }
    }

    /**
     * get the total mass of the entity.
     * usually the mass is all on the shape's reference point.
     *
     * @return the mass.
     */
    @Override
    public double getMass() {
        return mass;
    }
}

package org.socialforce.model.impl;

import org.socialforce.app.*;
import org.socialforce.geom.*;
import org.socialforce.model.*;

/**
 * Created by Ledenel on 2016/8/15.
 */
public class BaseAgent extends Entity implements Agent {
    Velocity currVelocity;
    Path path;
    double mass;
    int currTimestamp;
    DistanceShape view;
    Force pushed;
    Velocity deltaV;
    Vector deltaS;
    boolean escaped = false;
    DistanceShape shape;

    public BaseAgent(DistanceShape shape) {
        super(shape);
        this.shape = shape;
    }

    /**
     * get the Shape of this entity.
     * line, square, circle etc.
     *
     * @return the shape.
     */
    @Override
    public DistanceShape getShape() {
        return shape;
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
    // FIXME: 2016/9/3 it should be DistanceShape to support pool selection.
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
        Point point = this.shape.getReferencePoint();
        return model.getAgentMotivation(point, path.getCurrentGoal(point));
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
        if(currSteps >= this.currTimestamp) {
            this.pushed = model.getPower(this);
            int dt = currSteps - this.currTimestamp + 1;
            Iterable<InteractiveEntity> statics = scene.getStaticEntities().select(view);
            Iterable<Agent> neighbors = scene.getAllAgents().select(view);
            for(InteractiveEntity entity : statics) {
                entity.affect(this);
            }
            for(Agent agent : neighbors) {
                agent.affect(this);
            }
            deltaV = this.pushed.deltaVelocity(mass,dt * model.getTimePerStep());
            deltaS = deltaV.deltaDistance(dt * model.getTimePerStep());
            this.currTimestamp = currSteps;
            return deltaS;
        }
        else {
            return null;
        }
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
        return determineNext(currTimestamp);
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
     * nothing happened if the agent reach its goal (escaped).
     */
    @Override
    public void act() {
        this.currTimestamp++;
        this.currVelocity.add(deltaV);
        Point point = shape.getReferencePoint();
        point.add(deltaS);
        this.shape.moveTo(point);

        deltaS = model.zeroVector();
        deltaV = model.zeroVelocity();
        pushed = model.zeroForce();
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

    protected Scene scene;

    /**
     * get the context the scene is in.
     *
     * @return the scene.
     */
    @Override
    public Scene getScene() {
        return scene;
    }

    /**
     * set the scene for the agent.
     *
     * @param scene the scene to be set.
     */
    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * notify the agent which is escaped.
     */
    @Override
    public void escape() {
        escaped = true;
        scene.onAgentEscape(this);
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
            Agent agent = (Agent) affectedEntity;
            agent.push(model.calcualte(this, affectedEntity));
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

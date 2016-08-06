package org.socialforce.entity;

/**
 * represent a scene in social force simulations.
 * it could be traditional scene,
 * a cloned scene in Cloning strategy,
 * a bucket-self-managed scene in Bucket Clone strategy.
 * @see SocialForceApplication
 * @see Agent
 * @author Ledenel
 * Created by Ledenel on 2016/7/31.
 */
public interface Scene {
    /**
     * calculate the next time step of the scene.
     * the time step will also forward 1 unit.
     */
    void stepNext();

    /**
     * get a set of agents the scene is managing.
     * @return all agents.
     */
    AgentPool getAllAgents();

    /**
     * get a set of static entities the scene is managing.
     * @return all static entitites.
     */
    EntityPool getStaticEntities();

    /**
     * get the bound of the scene.
     * @return the bounds.
     */
    Box getBounds();

    /**
     * get current time step in this scene.
     * @return the current step.
     */
    int getCurrentSteps();

    /**
     * get the path finder for this scene.
     * @return the path finder.
     */
    PathFinder getPathFinder();

    /**
     * set a path finder for this scene.
     * @param finder the path finder for this scene.
     */
    void setPathFinder(PathFinder finder);
}

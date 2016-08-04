package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/7/28.
 */
public interface Agent extends MoveableEntity {
    
    /**
     * get the view of a agent
     * agent only interact with other entities inside its view
     * @return an area in the Shape class
     * @see Shape
     */
    Shape getView();
    
    /**
     * get the expecting moving velocity of agent
     * the velocity depends on the agent itself and its goal
     * @return the expected velocity
     * @see Velocity
     */
    Velocity expect();
    
    /**
     * at the beginning of a timestep, the agent have to exam the goal and its current place
     * so it can have a expect route
     * some parameter of the agent may change over time
     * such as the expect velocity or other plausible goals
     * @param the current timestep
     * @see determineNext
     */
    void determine(int currSteps);
    
    /**
     * the agent exam all entities in its sight and calculate the social force
     * then the agent determine where to go in the next timestep
     * @see determine
     * @see act
     */
    void determineNext();
    
    /**
     * get the current timestep of this agent
     * the timestep begin at 0(start of the simulation)
     * @return the current timestep
     * @see determine
     */
    int getCurrentSteps();
    
    /**
     * after decided the velocity of moving, the agent move to the expecting direction
     * time will moveforward 1 timestep
     * then the process return to determine step
     * loops till the agent reach its goal
     * @see determine
     */
    void act();
}

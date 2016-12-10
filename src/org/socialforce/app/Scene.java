package org.socialforce.app;

import org.socialforce.container.AgentPool;
import org.socialforce.container.EntityPool;
import org.socialforce.drawer.Drawable;
import org.socialforce.geom.Box;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.PathFinder;

import java.util.stream.Stream;

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
public interface Scene extends Drawable {
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
     * 向场景中添加一个Agent。
     * @param agent 要添加的Agent。
     * @return 若确实添加了Agent，返回true。
     */
    default boolean addAgent(Agent agent) {
        return getAllAgents().add(agent);
    }

    /**
     * 向场景中添加一个静态的实体，
     * 例如墙，安全区等物体。
     * @param entity 要添加的静态实体。
     * @return 若确实添加了该实体，返回true。
     */
    default boolean addStaticEntity(InteractiveEntity entity) {
        return getStaticEntities().add(entity);
    }

    /**
     * get a stream with all entities in the scene (including agents, walls, gates, etc.).
     * the default implementation just concat agents and static entities(ordered).
     * @return the stream.
     */
    // TODO: 2016/8/28 add parallel stream support.
    default Stream<InteractiveEntity> getAllEntitiesStream() {
        return Stream.concat(getAllAgents().stream(),getStaticEntities().stream());
    }
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

    /**
     * triggers while an agent is escaped.
     * @param agent the escaped agent.
     */
    void onAgentEscape(Agent agent);

    SocialForceApplication getApplication();
    void setApplication(SocialForceApplication application);
    boolean isVisible();
    void setVisible(boolean visible);
    ValueSet getValueSet();
    SceneListener getSceneListener();
    void setSceneListener(SceneListener listener);
    // TODO: 2016/9/14 add scene listener support.

    boolean isFreeze();
}

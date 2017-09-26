package org.socialforce.scene;

import org.socialforce.app.Application;
import org.socialforce.container.AgentPool;
import org.socialforce.container.EntityPool;
import org.socialforce.drawer.Drawable;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.geom.Box;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;

import java.util.stream.Stream;

/**
 * represent a scene in social force simulations.
 * it could be traditional scene,
 * a cloned scene in Cloning strategy,
 * a bucket-self-managed scene in Bucket Clone strategy.
 * TODO 加入clone()方法
 * @see Application
 * @see Agent
 * @author Ledenel
 * Created by Ledenel on 2016/7/31.
 */
public interface Scene extends Drawable {
    /**
     * interactionForce the next time step of the scene.
     * the time step will also forward 1 unit.
     */
    void stepNext();

    /**
     * init the scene (1)prepare agent threads (2)pre-select Movable, Captor, Influential, etc.
     * @return true if success
     */
    boolean init();

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
     * 从场景中删除一个Entity。
     * @param entity 要删除的Entity。
     * @return 若确实删除了Entity，返回true。
     */
    default boolean removeEntity(InteractiveEntity entity) {
        boolean flag = removeSceneListener(entity);
        if(!flag) return false;
        if(entity instanceof Agent)
            return getAllAgents().remove((Agent) entity);
        else
            return getStaticEntities().remove(entity);

    }

    /**
     * 向场景中添加一个Entity。
     * @param entity 要添加的Entity。
     * @return 若确实添加Entity，返回true。
     */
    default boolean addEntity(InteractiveEntity entity) {
        boolean flag = addSceneListener(entity);
        if(!flag) return false;
        if(entity instanceof Agent)
            return getAllAgents().add((Agent) entity);
        else
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
     * triggers while an agent is escaped.
     * @param agent the escaped agent.
     */
    void onAgentEscape(Agent agent);

    Application getApplication();
    void setApplication(Application application);
    boolean isVisible();
    void setVisible(boolean visible);

    /**
     * 添加sceneLister并调用Listener的onAdded()方法
     * @see SceneListener
     * @param listener 要加入的listener
     * @return 是否加入成功
     */
    boolean addSceneListener(SceneListener listener);

    /**
     * 删除sceneListener
     * @param listener 要删除的listener
     * @return 是否删除成功
     */
    boolean removeSceneListener(SceneListener listener);
    // TODO: 2016/9/14 add scene listener support.

    /**
     * 返回一个和当前scene一样bound的新scene
     * @return 新的scene
     */
    Scene cloneWithBounds();

    /**
     * 返回一个和scene一样静态场景的新的scene
     * @return 新的scene
     */
    Scene cloneWithStatics();

    /**
     * 设置scene中实体应用的model
     * @param model 所应用的model
     */
    Scene setModel(Model model);

    /**
     * 返回scene中实体应用的model
     */
    Model getModel();

    void pack();

    @Override
    SceneDrawer getDrawer();

}

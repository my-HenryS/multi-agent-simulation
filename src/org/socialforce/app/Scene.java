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
 * 代表社会力仿真的一个场景（scene）
 * 可以代表一个传统场景，被常规克隆的场景或者使用bucket策略克隆的场景
 * @see SocialForceApplication
 * @see Agent
 * @author Ledenel
 * Created by Ledenel on 2016/7/31.
 */
public interface Scene extends Drawable {
    /**
     * 计算场景的下一时步
     * 时间会向前推移1个单位
     */
    void stepNext();

    /**
     * 获得本场景中包含的所有agent
     * @return 所有的agent
     */
    AgentPool getAllAgents();

    /**
     * 获得一系列本场景中包含的实体
     * @return 所有的静态实体
     */
    EntityPool getStaticEntities();

    /**
     * 获得一个本场景中的实体流 (包括agent，墙，出口etc.).
     * 默认场景为一系列有序的实体和agent
     * @return 数据流
     */
    // TODO: 2016/8/28 add parallel stream support.
    default Stream<InteractiveEntity> getAllEntitiesStream() {
        return Stream.concat(getAllAgents().stream(),getStaticEntities().stream());
    }
    /**
     * 获得场景的边界
     * @return 场景的边界
     */
    Box getBounds();

    /**
     * 获得场景的当前时间
     * @return 当前的时间
     */
    int getCurrentSteps();

    /**
     * 获得本场景使用的寻路器
     * @return 需使用的寻路器
     */
    PathFinder getPathFinder();

    /**
     * 为本场景设置一个寻路器
     * @param finder 当前场景所需的寻路器
     */
    void setPathFinder(PathFinder finder);

    /**
     * 当一个agent逃离场景时被触发
     * @param agent 逃离场景的agent
     */
    void onAgentEscape(Agent agent);

    SocialForceApplication getApplication();
    void setApplication(SocialForceApplication application);
    boolean isVisible();
    void setVisible(boolean visible);
    ValueSet getValueSet();
}

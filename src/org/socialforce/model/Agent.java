package org.socialforce.model;

import org.socialforce.geom.DistanceModelShape;
import org.socialforce.scene.Scene;
import org.socialforce.geom.ModelShape;
import org.socialforce.geom.Vector;
import org.socialforce.geom.Velocity;
import org.socialforce.strategy.Path;

/**
 * 定义了社会力仿真模型中的agent接口，其继承于父类 InteractiveEntity ,Moveable, Blockable
 * @author Ledenel
 * Created by Ledenel on 2016/7/28.
 */
public interface Agent extends InteractiveEntity, Moveable, Blockable {

    /**
     * 获取一个实体的形状
     * 如线，矩形，圆等。
     *
     * @return 实体的形状.
     */
    DistanceModelShape getModelShape();

    /**
     * 获取一个agent的视域范围。
     * 该agent只和位于该视域范围内的agent进行交互
     * @return 一个表示该视域范围的形状
     * @see ModelShape
     */
    DistanceModelShape getView();
    
    /**
     * 获取一个agent的期望速度。
     * 该agent的速度通常来说取决于其自身及其目标。
     * @return 期望速度
     * @see Velocity
     */
    Velocity expect();

    /**
     * 决定下一步，agent要走向的目标点。
     * 同时，agent也会被社会力驱动。
     * 最终的结果会被act() 方法使用。
     * 如果当前的时间步长和该agent不同步，那么该agnet 会试着跟上
     * (或者忽略当agent的时间落后于真正的时间)  TODO?
     * @param currSteps 当前的时间
     * @return 代表要移动的距离和方向的向量。
     */
    Vector determineNext(int currSteps);
    
    /**
     * 决定下一步，agent要走向的目标点。
     * 同时，agent也会被社会力驱动。
     * 最终的结果会被act() 方法使用。
     * @return 代表要移动的距离和方向的向量。
     */
    Vector determineNext();

    /**
     * 获取当前时刻，agent的timestep （TODO这里的这个timestep翻译成时刻？）
     * timestep从0开始（仿真开始）
     * @return 当前的timestep
     */
    int getCurrentSteps();
    
    /**
     * 使用determineNext()方法计算出的结果。
     * 该方法会将时间往前推进一步。
     * 当act()成功执行，其还会将之前determineNext()方法计算出的结果清零。
     * 当无法获得该agent通过determineNext()方法计算所得的结果时，不会有移动。
     * 当agnet到达目标（或者逃出）时，不会有移动。
     */
    void act();

    /**
     * 获取agent的路径
     *
     * @return 路径对象
     */
    Path getPath();

    /**
     *为agent设置路径
     *
     * @param path 要设置的路径
     */
    void setPath(Path path);

    /**
     * 获取当前场景的具体内容
     * @return 场景
     */
    Scene getScene();

    /**
     * 设置agent所处的场景
     *
     * @param scene 被设置的场景
     */
    void setScene(Scene scene);

    /**
     * 标明已经逃离的agent
     */
    void escape();

    /**
     * 检查这个Agent是否已经逃离。
     * @return 是否已经逃离。
     */
    boolean isEscaped();

    double averageSpeed(int span);
}

package org.socialforce.model.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.impl.EntityDrawer;
import org.socialforce.scene.Scene;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
import org.socialforce.model.SocialForceModel;

/**
 * 定义了抽象类Entity，其实现了接口InteractiveEntity的方法。
 *  * Created by Ledenel on 2016/8/14.
 */
public abstract class Entity implements InteractiveEntity {
    protected SocialForceModel model;
    protected Shape shape;

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    protected Scene scene;

    /**
     * 获取实体的名称
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 设置实体的名称
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    protected String name;

    /**
     *
     * @param shape
     */
    public Entity(Shape shape) {
        this.shape = shape;
    }

    /**
     *
     * @param model
     * @param shape
     */
    public Entity(SocialForceModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    /**
     * 设置一个实体的形状
     * 如线，矩形，圆等。
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * 获取一个实体的形状
     * 如线，矩形，圆等。
     *
     * @return shape 实体的形状.
     */
    @Override
    public Shape getShape() {
        return shape;
    }

    /**
     * 获取社会模型中使用的实体模型
     *
     * @return model 模型
     */
    @Override
    public SocialForceModel getModel() {
        return model;
    }

    /**
     * 设置社会力模型。
     *
     * @param model 模型
     */
    @Override
    public void setModel(SocialForceModel model) {
        this.model = model;
    }

    /**
     * 将该实体放置在一个特殊的点上。
     * TODO the shape will {@code moveTo} that point.
     * @param point 目标点。
     */
    @Override
    public void placeOn(Point point) {
        shape.moveTo(point);
    }
}

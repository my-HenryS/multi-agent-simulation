package org.socialforce.model.impl;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.SocialForceModel;

/**
 * Created by Whatever on 2016/9/5.
 */
public class Air implements InteractiveEntity {
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

    @Override
    public void affect(InteractiveEntity affectedEntity) {
        //do nothing
        //throw new IllegalArgumentException("空气并不能干任何事情");
    }

    protected String name;

    /**
     *
     * @param shape
     */
    public Air(Shape shape) {
        this.shape = shape;
    }

    /**
     *
     * @param model
     * @param shape
     */
    public Air(SocialForceModel model, Shape shape) {
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

    @Override
    public double getMass() {
        return 0;
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

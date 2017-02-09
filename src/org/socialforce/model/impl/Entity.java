package org.socialforce.model.impl;

import org.socialforce.geom.ModelShape;
import org.socialforce.scene.Scene;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.geom.Point;
import org.socialforce.model.SocialForceModel;

/**
 * 定义了抽象类Entity，其实现了接口InteractiveEntity的方法。
 *  * Created by Ledenel on 2016/8/14.
 */
public abstract class Entity implements InteractiveEntity {
    protected SocialForceModel model;
    protected ModelShape modelShape;

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
     * @param modelShape
     */
    public Entity(ModelShape modelShape) {
        this.modelShape = modelShape;
    }

    /**
     *
     * @param model
     * @param modelShape
     */
    public Entity(SocialForceModel model, ModelShape modelShape) {
        this.model = model;
        this.modelShape = modelShape;
    }

    /**
     * 设置一个实体的形状
     * 如线，矩形，圆等。
     */
    public void setModelShape(ModelShape modelShape) {
        this.modelShape = modelShape;
    }

    /**
     * 获取一个实体的形状
     * 如线，矩形，圆等。
     *
     * @return modelShape 实体的形状.
     */
    public ModelShape getModelShape() {
        return modelShape;
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
     * TODO the modelShape will {@code moveTo} that point.
     * @param point 目标点。
     */
    @Override
    public void placeOn(Point point) {
        modelShape.moveTo(point);
    }
}

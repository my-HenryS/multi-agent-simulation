package org.socialforce.model.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.PhysicalEntity;
import org.socialforce.scene.Scene;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.geom.Point;
import org.socialforce.model.Model;

/**
 * 定义了抽象类Entity，其实现了接口InteractiveEntity的方法。
 *  * Created by Ledenel on 2016/8/14.
 */
public abstract class Entity implements InteractiveEntity {
    protected Model model;
    protected PhysicalEntity physicalEntity;

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    protected Drawer drawer;

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
     * @param physicalEntity
     */
    public Entity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

    /**
     *
     * @param model
     * @param physicalEntity
     */
    public Entity(Model model, PhysicalEntity physicalEntity) {
        this.model = model;
        this.physicalEntity = physicalEntity;
    }

    /**
     * 设置一个实体的形状
     * 如线，矩形，圆等。
     */
    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

    /**
     * 获取一个实体的形状
     * 如线，矩形，圆等。
     *
     * @return physicalEntity 实体的形状.
     */
    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    /**
     * 获取社会模型中使用的实体模型
     *
     * @return model 模型
     */
    @Override
    public Model getModel() {
        return model;
    }

    /**
     * 设置社会力模型。
     *
     * @param model 模型
     */
    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * 将该实体放置在一个特殊的点上。
     * TODO the physicalEntity will {@code moveTo} that point.
     * @param point 目标点。
     */
    @Override
    public void placeOn(Point point) {
        physicalEntity.moveTo(point);
    }

    @Override
    public Entity clone(){
        return null;
    }

}

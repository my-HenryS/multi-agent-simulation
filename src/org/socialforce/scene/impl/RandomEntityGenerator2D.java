package org.socialforce.scene.impl;

import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.impl.*;
import org.socialforce.model.*;
import org.socialforce.scene.EntityGenerator;
import org.socialforce.scene.Scene;

import java.util.Random;

/**
 * Created by sunjh1999 on 2016/12/10.
 */
public class RandomEntityGenerator2D extends EntityGenerator<InteractiveEntity>{

    private PhysicalEntity Area;
    private int entityNum;
    public RandomEntityGenerator2D(int entityNum, PhysicalEntity Area){
        this.Area = Area;
        this.entityNum = entityNum;
    }


    /**
     * 注意生成agent使用的是SocialForceModel里的createAgent方法
     * @param scene 要被更改的场景。
     */
    @Override
    public void apply(Scene scene) {
        Agent new_agent;
        Random rand = new Random();
        int cur_num = 0;
        int is_able_flag = 0;
        if (Area instanceof Box2D || Area instanceof Semicircle2D || Area instanceof Circle2D){
            while (cur_num < entityNum) {
                double rand_x = Area.getBounds().getStartPoint().getX() + rand.nextDouble() * (Area.getBounds().getEndPoint().getX() - Area.getBounds().getStartPoint().getX());
                double rand_y = Area.getBounds().getStartPoint().getY() + rand.nextDouble() * (Area.getBounds().getEndPoint().getY() - Area.getBounds().getStartPoint().getY());
                Point2D point = new Point2D(rand_x, rand_y);
                if(Area.contains(point)){
                    InteractiveEntity newEntity = value.clone();
                    newEntity.getPhysicalEntity().moveTo(point);
                    if(commonName != null) {
                        newEntity.setName(commonName);
                    }
                    if(EntityDecorator.place(newEntity, scene, model))
                        cur_num ++;
                }
            }
        }

        else throw new IllegalArgumentException("暂未实现非二维的生成区块");
    }

    public int get_Entitynum(){
        return entityNum;
    }

    protected InteractiveEntity value;
    public InteractiveEntity getValue() {
        return value;
    }

    public RandomEntityGenerator2D setValue(InteractiveEntity value) {
        this.value = value;
        return this;
    }
}


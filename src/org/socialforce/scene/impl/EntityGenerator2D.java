package org.socialforce.scene.impl;
import org.socialforce.container.EntityPool;
import org.socialforce.geom.DistanceShape;
import org.socialforce.model.*;
import org.socialforce.scene.EntityGenerator;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Semicircle2D;

/**
 * Created by Whatever on 2016/9/16.
 */
public class EntityGenerator2D extends EntityGenerator<InteractiveEntity>{

    protected double X_distance,Y_distance;
    protected Shape Area;

    //point-offset定义方式
    public EntityGenerator2D(Point2D origin, double X_distance, double Y_distance, int x_num, int y_num){
        this.X_distance = X_distance;
        this.Y_distance = Y_distance;
        this.Area = new Box2D(origin.getX(),origin.getY(),X_distance*x_num,Y_distance*y_num);
    }
    public EntityGenerator2D(double X_distance, double Y_distance, Shape Area){
        this.X_distance = X_distance;
        this.Y_distance = Y_distance;
        this.Area = Area;
    }
    /**
     * 注意生成agent使用的是SocialForceModel里的createAgent方法
     * @param scene 要被更改的场景。
     */
    @Override
    public void apply(Scene scene) {
        if (Area instanceof Box2D || Area instanceof Semicircle2D || Area instanceof Circle2D) {
            for (int i = 0; i < (Area.getBounds().getEndPoint().getX() - Area.getBounds().getStartPoint().getX()) / X_distance; i++) {
                for (int j = 0; j < (Area.getBounds().getEndPoint().getY() - Area.getBounds().getStartPoint().getY()) / Y_distance; j++) {
                    Point2D point = new Point2D(Area.getBounds().getStartPoint().getX()+i*X_distance,Area.getBounds().getStartPoint().getY()+j*Y_distance);
                    if(Area.contains(point)){
                        InteractiveEntity newEntity = value.clone();
                        newEntity.getShape().moveTo(point);
                        if(commonName != null) {
                            newEntity.setName(commonName);
                        }
                        EntityDecorator.place(newEntity, scene, model);
                    }
                }
            }
        }
        else throw new IllegalArgumentException("暂未实现非二维的生成区块");
    }

    protected InteractiveEntity value;
    public InteractiveEntity getValue() {
        return value;
    }

    public EntityGenerator2D setValue(InteractiveEntity value) {
        this.value = value;
        return this;
    }

}
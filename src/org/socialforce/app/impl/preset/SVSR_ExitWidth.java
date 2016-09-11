package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;
import org.socialforce.geom.ClippableShape;
import org.socialforce.geom.Expandable;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.impl.Air;

/**
 * Created by Whatever on 2016/8/31.
 */
public class SVSR_ExitWidth implements SceneValue {
    protected double width[];
    protected String name;
    public void setWidth(double width,int i){
        this.width[i] = width;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Comparable getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {

    }

    @Override
    public void apply(Scene scene) {
    for (int i = 0;i < width.length;i++){
        Shape gate = scene.getStaticEntities().findBottom("gate").getShape();
        ((Expandable)scene.getStaticEntities().findBottom("gate").getShape()).expandTo(width[i]);
        scene.getStaticEntities().remove(gate);
        /*@TODO 由于返回的是一个EntityPool，而这里面没有实现set啊之类的功能，所以没法绕开。
            尚且不明确设置Pool或Pool中变量名字的方法。
         */
    }
    }

    @Override
    public int compareTo(SceneValue o) {
        return 0;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

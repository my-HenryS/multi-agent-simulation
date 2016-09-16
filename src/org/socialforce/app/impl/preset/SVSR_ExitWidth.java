package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Expandable;

/**
 * TODO 这是一个目前无用的类!!!!!
 * 仔细想了想，这个设计目前卵用。
 * 因为Exit是在apply同时就切了，在apply之前shape不存在于场景的Pool中根本找不到
 * 姑且先写着放在这里
 * 我是想的在设定宽度的时候再切，设定出口位置的时候不切。
 * 或者干脆就是在一堆场景初始化的时候不是要读一堆循环进来吗？这个用来初始化那个。
 * 然后这个设计还有一个注意点是这个方法必须和exit相间执行，不然findTop也好selectTop也好都是不对的。
 * Created by Whatever on 2016/9/16.
 */
public class SVSR_ExitWidth implements SceneValue<Double>{
    protected String name;
    protected double width;
    @Override
    public String getEntityName() {
        return name;
    }

    @Override
    public void setEntityName(String name) {
        this.name = name;
    }

    @Override
    public Double getValue() {
        return width;
    }

    @Override
    public void setValue(Double value) {
        width = value;
    }

    @Override
    public void apply(Scene scene) {
        ((Expandable)scene.getStaticEntities().selectTop((DistanceShape) scene.getBounds()).getShape()).expandTo(width);
    }

    @Override
    public int compareTo(SceneValue<Double> o) {
        return o.getPriority() - this.getPriority();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
    private int priority;
}

package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;
import org.socialforce.geom.ClippableShape;
import org.socialforce.geom.Expandable;
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
        ((Expandable)scene.getStaticEntities().findBottom("gate").getShape()).expandTo(width[i]);
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

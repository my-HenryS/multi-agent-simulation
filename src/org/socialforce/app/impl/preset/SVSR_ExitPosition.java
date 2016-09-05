package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;
import org.socialforce.app.SimpleScene;

/**
 * Created by Whatever on 2016/8/31.
 */
public class SVSR_ExitPosition implements SceneValue{
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName() {

    }

    @Override
    public Comparable getValue() {
        return null;
    }

    @Override
    public void apply(Scene scene) {
        if (scene instanceof SimpleScene){

        }
        else throw new IllegalArgumentException("此方法只能用于设置矩形房间");
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

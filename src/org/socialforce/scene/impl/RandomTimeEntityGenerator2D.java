package org.socialforce.scene.impl;

import org.socialforce.geom.PhysicalEntity;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.scene.SceneValue;

/**
 * Created by sunjh1999 on 2017/1/21.
 * TODO 设置为监听Scene的Listener，当经过xxx步时，生成新的Agent
 */
public class RandomTimeEntityGenerator2D extends RandomEntityGenerator2D implements SceneValue<InteractiveEntity> {


    public RandomTimeEntityGenerator2D(int entityNum, PhysicalEntity Area) {
        super(entityNum, Area);
    }

    public double P_rand(double Lamda){ // 泊松分布
        double x=0,b=1,c=Math.exp(-Lamda),u;
        do {
            u=Math.random();
            b *=u;
            if(b>=c)
                x++;
        }while(b>=c);
        return x;
    }

    protected InteractiveEntity value;
    public InteractiveEntity getValue() {
        return value;
    }
    @Override
    public RandomTimeEntityGenerator2D setValue(InteractiveEntity value) {
        super.setValue(value);
        return this;
    }
}

package org.socialforce.scene.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.scene.EntityGenerator;
import org.socialforce.scene.Scene;

/**
 * Created by sunjh1999 on 2017/5/2.
 */
public class SimpleEntityGenerator extends EntityGenerator<InteractiveEntity> {

    public SimpleEntityGenerator(){
    }

    @Override
    public void apply(Scene scene) {
        InteractiveEntity newEntity = value.clone();
        if(commonName != null) {
            newEntity.setName(commonName);
        }
        EntityDecorator.place(newEntity, scene, model);
    }

    protected InteractiveEntity value;
    public InteractiveEntity getValue() {
        return value;
    }
    public SimpleEntityGenerator setValue(InteractiveEntity value){
        this.value = value;
        return this;
    }
}

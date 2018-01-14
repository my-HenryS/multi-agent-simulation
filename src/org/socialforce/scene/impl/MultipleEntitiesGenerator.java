package org.socialforce.scene.impl;

import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;
import org.socialforce.scene.EntityGenerator;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;

import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/5/2.
 */
public class MultipleEntitiesGenerator extends EntityGenerator<InteractiveEntity> {
    int num = 0;
    protected LinkedList<InteractiveEntity> values = new LinkedList<>();

    @Override
    public void apply(Scene scene) {
        for(InteractiveEntity value : values){
            InteractiveEntity newEntity = value.clone();
            if(commonName != null) {
                newEntity.setName(commonName+String.valueOf(num++));
            }
            EntityDecorator.place(newEntity, scene, model);
        }
    }

    @Override
    public int compareTo(SceneValue<InteractiveEntity> o) {
        return o.getPriority() - this.getPriority();
    }


    public LinkedList<InteractiveEntity> getValues() {
        return values;
    }

    public MultipleEntitiesGenerator addValue(InteractiveEntity value) {
        values.add(value);
        return this;
    }
}

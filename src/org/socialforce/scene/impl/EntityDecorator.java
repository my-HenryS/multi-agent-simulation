package org.socialforce.scene.impl;

import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;
import org.socialforce.scene.Scene;

/**
 * Created by sunjh1999 on 2017/5/2.
 * 定义了Entity、Scene、Model的关系
 */
public class EntityDecorator {
    public static boolean place(InteractiveEntity newEntity, Scene scene, Model model){
        newEntity.setModel(model);
        newEntity.setScene(scene);
        scene.setModel(model);
        boolean flag = scene.addEntity(newEntity);
        if(!flag)
            scene.removeEntity(newEntity);
        return flag;
    }
}

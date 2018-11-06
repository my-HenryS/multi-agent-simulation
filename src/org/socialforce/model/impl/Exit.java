package org.socialforce.model.impl;

import org.socialforce.geom.ClipperPhysicalEntity;
import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.scene.Scene;
import org.socialforce.scene.impl.EntityDecorator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sunjh1999 on 2017/5/2.
 */
public class Exit extends Entity {
    ClipperPhysicalEntity shape;
    public Exit(ClipperPhysicalEntity shape) {
        super(shape);
        this.shape = shape;
    }

    @Override
    public boolean onAdded(Scene scene) {
        InteractiveEntity wall;
        Wall temp;
        if (shape.getBounds().getStartPoint().getX() != shape.getBounds().getEndPoint().getX()
                && shape.getBounds().getStartPoint().getY() != shape.getBounds().getEndPoint().getY()) {
            List<PhysicalEntity> boxes = new LinkedList<>();
            while(true) {
                wall = scene.getStaticEntities().selectTopByClass((Box2D) shape, Wall.class);
                if (wall == null) break;
                Box2D wallShape = (Box2D) wall.getPhysicalEntity();
                boxes.addAll(Arrays.asList(shape.clip(wallShape)));
                scene.removeEntity(wall);
            }
            if(boxes.size() == 0) return false;
            for (int j = 0; j < boxes.size(); j++) {
                temp = new Wall(boxes.get(j));
                EntityDecorator.place(temp, scene, model);
            }

        } else /*do nothing*/;
        return true;
    }

    @Override
    public void onStep(Scene scene) {

    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public Exit clone() {
        return new Exit((ClipperPhysicalEntity)shape.clone());
    }
}

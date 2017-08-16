package org.socialforce.model.impl;

import org.socialforce.geom.ClipperPhysicalEntity;
import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.scene.Scene;
import org.socialforce.scene.impl.EntityDecorator;

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
            wall = scene.getStaticEntities().selectTopByClass(shape.getReferencePoint(), Wall.class);
            if(wall == null) return false;
            Box2D wallShape = (Box2D) wall.getPhysicalEntity();
            PhysicalEntity[] boxes =  shape.clip(wallShape);
            for (int j=0;j<boxes.length;j++){
                temp = new Wall(boxes[j]);
                EntityDecorator.place(temp,scene,model);
            }
            scene.removeEntity(wall);
        } else /*do nothing*/;
        return false;
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

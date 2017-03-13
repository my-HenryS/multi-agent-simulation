package org.socialforce.container.impl;

import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Shape;
import org.socialforce.model.Agent;
import org.socialforce.container.AgentPool;

import java.util.Iterator;

/**
 * Created by Ledenel on 2016/8/21.
 */
public class LinkListAgentPool extends LinkListPool<Agent> implements AgentPool {

    @Override
    public Iterable<Agent> select(Shape shape) {
        return new PoolShapeSelectIterable(shape);
    }


    protected boolean shapeContains(Agent entity, Shape shape) {
        return entity.getShape().intersects(shape);
    }

    public class PoolShapeSelectIterable implements Iterable<Agent>{
        private Shape shape;

        public PoolShapeSelectIterable(Shape shape) {
            this.shape = shape;
        }

        @Override
        public Iterator<Agent> iterator() {
            return LinkListAgentPool.this.stream()
                    .filter(entity -> shapeContains(entity, shape))
                    .iterator();        }
    }

}

package org.socialforce.container.impl;

import org.socialforce.geom.PhysicalEntity;
import org.socialforce.model.Agent;
import org.socialforce.container.AgentPool;

import java.util.Iterator;

/**
 * Created by Ledenel on 2016/8/21.
 */
public class LinkListAgentPool extends LinkListPool<Agent> implements AgentPool {

    @Override
    public Iterable<Agent> select(PhysicalEntity physicalEntity) {
        return new PoolShapeSelectIterable(physicalEntity);
    }


    protected boolean shapeContains(Agent entity, PhysicalEntity physicalEntity) {
        return entity.getPhysicalEntity().intersects(physicalEntity);
    }

    public class PoolShapeSelectIterable implements Iterable<Agent>{
        private PhysicalEntity physicalEntity;

        public PoolShapeSelectIterable(PhysicalEntity physicalEntity) {
            this.physicalEntity = physicalEntity;
        }

        @Override
        public Iterator<Agent> iterator() {
            return LinkListAgentPool.this.stream()
                    .filter(entity -> shapeContains(entity, physicalEntity))
                    .iterator();        }
    }

}

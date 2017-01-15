package org.socialforce.container.impl;

import org.socialforce.container.EntityPool;
import org.socialforce.model.InteractiveEntity;

/**
 * Created by Ledenel on 2016/8/21.
 */
public class LinkListEntityPool extends LinkListPool<InteractiveEntity> implements EntityPool {
    public LinkListEntityPool clone(){
        LinkListEntityPool newpool = new LinkListEntityPool();
        for(InteractiveEntity t: this){
            newpool.addLast(t.standardclone());
        }
        return newpool;
    }

}

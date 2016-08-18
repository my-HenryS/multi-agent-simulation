package org.socialforce.entity.impl;

import org.socialforce.entity.ForceRegulation;
import org.socialforce.entity.InteractiveEntity;

/**
 * Created by Ledenel on 2016/8/19.
 */
public abstract class TypeMatchRegulation<Source extends InteractiveEntity, Target extends InteractiveEntity> implements ForceRegulation<Source,Target> {
    public TypeMatchRegulation(Class<Source> sourceClass, Class<Target> targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    protected Class<Source> sourceClass;
    protected Class<Target> targetClass;

    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return sourceClass.isInstance(source) && targetClass.isInstance(target);
    }
}

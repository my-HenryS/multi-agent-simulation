package org.socialforce.model.impl;

import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.SocialForceModel;

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

    /**
     *
     * @param sourceClass
     * @param targetClass
     * @param model
     */
    public TypeMatchRegulation(Class<Source> sourceClass, Class<Target> targetClass, SocialForceModel model) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
        this.model = model;
    }

    protected SocialForceModel model;

    /**
     * judge if there has force between source and target
     * @param source
     * @param target
     * @return true if there has force between source and target
     */
    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return sourceClass.isInstance(source) && targetClass.isInstance(target);
    }
}

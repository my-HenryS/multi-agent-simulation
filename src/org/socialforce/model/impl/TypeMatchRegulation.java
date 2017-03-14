package org.socialforce.model.impl;

import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;

/**
 * 定义了TypeMatchRegulation类，其实现了接口ForceRegulation的方法。
 * TODO 这个类是类型匹配？
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
    public TypeMatchRegulation(Class<Source> sourceClass, Class<Target> targetClass, Model model) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
        this.model = model;
    }

    protected Model model;

    /**
     * 判断两个agent之间是否有心理作用力。
     * @param source
     * @param target
     * @return tree  如果两个agent之间是有心理作用力则返回真。
     */
    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return sourceClass.isInstance(source) && targetClass.isInstance(target);
    }
}

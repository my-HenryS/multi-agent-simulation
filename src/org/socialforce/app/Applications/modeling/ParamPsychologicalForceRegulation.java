package org.socialforce.app.Applications.modeling;

import org.socialforce.geom.Affection;
import org.socialforce.geom.Force;
import org.socialforce.model.Agent;
import org.socialforce.model.Blockable;
import org.socialforce.model.Model;
import org.socialforce.model.impl.TypeMatchRegulation;

/**行人规避障碍物的心理力
 * 定义了PsychologicalForceRegulation类，其继承于父类TypeMatchRegulation
 * Created by Ledenel on 2016/8/19.
 */

public class ParamPsychologicalForceRegulation extends TypeMatchRegulation<Blockable, Agent> {
    private double A = 500+250;

    public double getA() {
        return A;
    }

    public void setA(double a) {
        A = a;
    }

    public double getB() {
        return B;
    }

    public void setB(double b) {
        B = b;
    }

    private double B = 0.08;

    /**
     *TODO regulate 是翻译成调节还是控制，还是计算的意思？
     *  计算两个agent之间的心理作用力。
     * @param agentClass
     * @param agentClass2
     * @param model
     */
    public ParamPsychologicalForceRegulation(Class<Blockable> agentClass, Class<Agent> agentClass2, Model model) {
        super(agentClass, agentClass2, model);
    }


    /**
     *获取源实体和目标实体之间的作用力。
     * @param source
     * @param target
     * @return force
     */
    @Override
    public Affection getForce(Blockable source, Agent target) {
        Force force = model.zeroForce();
        force.add((target.getPhysicalEntity()).directionTo(source.getPhysicalEntity()));
        double scale = A * Math.exp(- (target.getPhysicalEntity().distanceTo(source.getPhysicalEntity()) + 0.05)/ B);  //FIXME 心理力的计算公式（修改“0.05”）
        force.scale(scale / force.length());
        return force;
    }

}

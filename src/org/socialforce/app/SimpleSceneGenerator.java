package org.socialforce.app;

import org.socialforce.app.impl.preset.SVSR_AgentGenerator;
import org.socialforce.app.impl.preset.SVSR_Exit;
import org.socialforce.app.impl.preset.SVSR_SafetyRegion;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.impl.SafetyRegion;

/**
 * 这个是为了中期检查所建的临时枚举器
 * ParameterSet是假的，是特技
 * 内部所有数据都是硬编码的
 * Created by Whatever on 2016/10/22.
 */
public class SimpleSceneGenerator implements SceneGenerator {
    Scene scene = new SimpleScene(new Box2D(-50,-50,100,100));
    @Override
    public Iterable<Scene> generate(Scene template, ParameterSet params) {
        SVSR_Exit exit = new SVSR_Exit();
        SVSR_AgentGenerator agentGenerator = new SVSR_AgentGenerator();
        SVSR_SafetyRegion safetyRegion = new SVSR_SafetyRegion();
        exit.setValue((new Box2D[]{new Box2D(-1,5,4,2),new Box2D(10,-1,2,4),new Box2D(10,14,2,4),new Box2D(24,10,4,2)}));
        exit.apply(template);
        agentGenerator.apply(template);
        safetyRegion.setValue(new SafetyRegion(new Box2D(-1,5,-4,2)));
        safetyRegion.apply(template);
        safetyRegion.setValue(new SafetyRegion(new Box2D(10,-1,2,-4)));
        safetyRegion.apply(template);
        safetyRegion.setValue(new SafetyRegion(new Box2D(10,14,2,-4)));
        safetyRegion.apply(template);
        safetyRegion.setValue(new SafetyRegion(new Box2D(24,10,-4,2)));
        safetyRegion.apply(template);
        return (Iterable<Scene>) template;
    }

    @Override
    public Iterable<Scene> generate(SceneLoader loader) {
        scene = loader.readScene();
        return (Iterable<Scene>) scene;
    }
}

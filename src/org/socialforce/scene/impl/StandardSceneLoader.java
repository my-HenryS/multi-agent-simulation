package org.socialforce.scene.impl;

import org.socialforce.model.Model;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.*;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;

import static org.socialforce.scene.SceneGenerator.generate;

/**
 * Created by sunjh1999 on 2017/1/14.
 */
public class StandardSceneLoader implements SceneLoader {
    protected ParameterPool parameterSet;
    protected Wall[] walls = new Wall[]{};
    Scene scene;
    Model model;
    public StandardSceneLoader(Scene scene, Wall[] walls){
        this.scene = scene;
        this.walls = walls;
        this.parameterSet = new SimpleParameterPool();
    }

    public StandardSceneLoader setModel(Model model){
        this.model = model;
        return this;
    }

    @Override
    public void setSource(InputStream stream) {

    }

    @Override
    public void setSource(File file) {

    }

    private Scene staticScene(Model model) {
        Scene new_scene = scene.cloneWithBounds();
        Wall[] new_walls = new Wall[walls.length];
        for(int i = 0; i < walls.length; i++){
            new_walls[i] = walls[i].clone();
        }
        for (int i = 0;i < new_walls.length;i++) {
            new_walls[i].setName("wall" + i);
            EntityDecorator.place(new_walls[i], new_scene, model);
        }
        return new_scene;
    }

    @Override
    public LinkedList<Scene> readScene() {
        LinkedList<Scene> scenes = new LinkedList<>();
        int total_num = 1;
        //计算所有可能情况
        for (Iterator<SceneParameter> iterator = parameterSet.iterator(); iterator.hasNext();){
            SimpleSceneParameter parameter = (SimpleSceneParameter) iterator.next();
            total_num = total_num * parameter.getParameter().size();
        }
        //遍历 -- 全组合问题
        for(int i = 0; i < total_num; i++){
            ValueSet values = new SimpleValueSet();
            Model newModel = model.clone();
            for (Iterator<SceneParameter> iterator = parameterSet.iterator(); iterator.hasNext();){
                SimpleSceneParameter parameter = (SimpleSceneParameter) iterator.next();
                SceneValue value = parameter.getParameter().get(i%parameter.getParameter().size());   //根据相对周长的偏移量计算当前应添加的SceneValue
                if(value instanceof EntityGenerator) ((EntityGenerator) value).setModel(newModel);
                values.add(value);
            }
            Scene scene = staticScene(newModel);
            scene = generate(scene,values);
            scene.pack();
            scenes.addLast(scene);
        }
        return scenes;
    }

    @Override
    public ParameterPool readParameterSet(ParameterPool parameterSet) {
        this.parameterSet = parameterSet;
        return parameterSet;
    }

    @Override
    public ParameterPool readValueSet(ValueSet values) {
        for(Iterator<SceneValue> iterator = values.iterator(); iterator.hasNext();){
            SceneValue sceneValue = iterator.next();
            SceneParameter parameter = new SimpleSceneParameter();
            parameter.addValue(sceneValue);
            parameterSet.addLast(parameter);
        }
        return parameterSet;
    }

}


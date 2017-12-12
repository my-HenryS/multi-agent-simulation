package org.socialforce.app.impl;

import com.opencsv.CSVWriter;
import org.socialforce.model.Agent;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneListener;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by sunjh1999 on 2017/5/8.
 */
public class AgentStepCSVWriter implements SceneListener {
    Map<String, List<String>> agentStepMap=new HashMap<>();
    double time_per_step = 1.0/15;

    @Override
    public boolean onAdded(Scene scene) {
        return true;
    }

    @Override
    public void onStep(Scene scene) {
        if(scene.getCurrentSteps() % (int)(time_per_step / scene.getModel().getTimePerStep()) != 0) return;
        for(Iterator<Agent> iter = scene.getAllAgents().iterator(); iter.hasNext();){
            Agent agent = iter.next();
            if(!agentStepMap.containsKey(agent.getName())){
                List<String> newList = new ArrayList<>();
                newList.add(agent.getPhysicalEntity().getReferencePoint().toString());
                agentStepMap.put(agent.getName(), newList);
            }
            else{
                agentStepMap.get(agent.getName()).add(agent.getPhysicalEntity().getReferencePoint().toString());
            }
        }
    }

    public void writeCSV(String path){
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(System.getProperty("user.dir")+"/resource/"+path),',');
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String[]> finalList = new ArrayList<>();
        for(String key: agentStepMap.keySet()){
            finalList.add(agentStepMap.get(key).toArray(new String[agentStepMap.get(key).size()]));
        }
        writer.writeAll(finalList);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

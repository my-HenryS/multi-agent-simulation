package org.socialforce.model.impl;

import com.opencsv.CSVReader;
import org.socialforce.container.Pool;
import org.socialforce.geom.*;
import org.socialforce.geom.impl.*;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sunjh1999 on 2017/9/21.
 */
public class CSVReaderModel implements Model {
    String parentPath = System.getProperty("user.dir")+"/resource/";

    double timePerStep = 0;
    ArrayList<LinkedList<Point2D>> matrix = new ArrayList<>();
    int timeStamp = 0;
    String filename;

    public CSVReaderModel(String filename, double timePerStep){
        this.timePerStep = timePerStep;
        this.filename = filename;

        String directory = parentPath+filename;
        String axis[];
        LinkedList<Point2D> tempR;
        File file = new File(directory);
        FileReader fReader = null;
        List<String[]> lines = null;
        try {
            fReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fReader);
            lines = csvReader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String[] line : lines){
            tempR = new LinkedList<>();
            for(int i = 0 ; i < line.length ; i++){
                if(line[i] != null && line[i].length() > 0){
                    String templine = line[i].substring(0,line[i].length());
                    axis = templine.split(",");
                    tempR.add(new Point2D(Double.parseDouble(axis[0]),0.935*Double.parseDouble(axis[1]))); //12332456
                }
                else{
                    tempR.add(null);
                }
            }
            matrix.add(tempR);
        }
    }
    @Override
    public void fieldForce(Pool targets) {
        int i = 0;
        for(Object target : targets){
            Agent agent = (Agent)target;
            if(matrix.get(i).get(timeStamp) != null) {
                Point position = matrix.get(i).get(timeStamp), currPosition = agent.getPosition();
                Velocity velocity = new Velocity2D((position.getX() - currPosition.getX())/timePerStep,(position.getY() - currPosition.getY())/timePerStep);
                agent.getPhysicalEntity().setVelocity(velocity);
            }
            else{
                agent.getPhysicalEntity().moveTo(new Point2D(0,0));
            }
            i++;
        }
        timeStamp++;
    }

    @Override
    public Affection interactAffection(InteractiveEntity source, InteractiveEntity target) {
        return new Affection2D();
    }

    @Override
    public Vector zeroVector() {
        return new Vector2D(0,0);
    }

    @Override
    public Velocity zeroVelocity() {
        return new Velocity2D(0,0);
    }

    @Override
    public Force zeroForce() {
        return new Force2D(0,0);
    }

    @Override
    public double getTimePerStep() {
        return timePerStep;
    }

    @Override
    public Moment zeroMoment() {
        return new Moment2D(0);
    }

    @Override
    public Model clone() {
        return new CSVReaderModel(filename, timePerStep);
    }
}

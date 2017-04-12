package org.socialforce.neural.impl;

import org.socialforce.neural.Coordinates;
import org.socialforce.neural.DataSetGenerator;

import java.io.*;
import java.util.*;

public class PsyForceGenerator extends ForceGenerator{
    private int nearN = 5; //周围人数
    private double height = 6.78;  //场景宽

    public PsyForceGenerator(double timestep, int intercept) {
        super(timestep, intercept);
    }


    /*
    此处x,y为人在matrix中的坐标 而非位置坐标 TODO 待重构
     */
    public double calcDis(Coordinates x, Coordinates y){
        Coordinates v1 = matrix.get((int)x.X()).get((int)x.Y());
        Coordinates v2 = matrix.get((int)y.X()).get((int)y.Y());
        return Math.sqrt(Math.pow((v1.X() - v2.X()),2) + Math.pow((v1.Y() - v2.Y()),2));
    }

    public Coordinates calAcc(Coordinates a, Coordinates b){
        return new Coordinates((a.X()-b.X())/timestep,(a.Y()-b.Y())/timestep);
    }

    public ArrayList<Coordinates> nearNeighbor(int x, int y){
        ArrayList<Coordinates> neighbor = new ArrayList<>();
        for(int i = 0 ; i < matrix.size() ; i++){
            if(i == x || matrix.get(i).get(y) == null){
                continue;
            }
            else{
                neighbor.add(new Coordinates(i, y));
            }
        }
        for(int i = 0 ; i < neighbor.size() ; i++){
            if(i > 0 && (calcDis(new Coordinates(x,y),neighbor.get(i-1))>calcDis(new Coordinates(x,y),neighbor.get(i)))){
                Coordinates temp = neighbor.get(i);
                neighbor.set(i,neighbor.get(i-1));
                neighbor.set(i-1,temp);
                if(i > 1){
                    i -= 2;
                }
            }
        }
        ArrayList<Coordinates> resultlist = new ArrayList<>();
        for(int i = 0 ; i < nearN ; i++){
            if(i == neighbor.size()){
                break;
            }
            resultlist.add(neighbor.get(i));
        }
        return resultlist;
    }
    public Coordinates deltaVelocity(int x1, int y1, int x2, int y2){
        Coordinates v1 = velocity.get(x1).get(y1);
        Coordinates v2 = velocity.get(x2).get(y2);
        if(v2.X() == 0 && v2.Y() == 0){
            v2 = velocity.get(x2).get(y2+1);
        }
        Coordinates resulttuple = new Coordinates(v1.X() - v2.X(), v1.Y() - v2.Y());
        return resulttuple;
    }
    public Coordinates deltaDistance(int x1, int y1, int x2, int y2){
        Coordinates d1 = matrix.get(x1).get(y1);
        Coordinates d2 = matrix.get(x2).get(y2);
        Coordinates resulttuple = new Coordinates(d2.X() - d1.X(), d2.Y() - d1.Y());
        return resulttuple;
    }

    public void genOutput() {
        for(int i = 0 ; i < matrix.size() ; i++){
            for(int j = 0 ; j < matrix.get(i).size() ; j++){
                if(available(i, j)){
                    LinkedList<Coordinates> temp = new LinkedList<>();
                    ArrayList<Coordinates> neigb = nearNeighbor(i, j);
                    temp.add(calAcc(velocity.get(i).get(j + 1), velocity.get(i).get(j)));
                    for (Coordinates n : neigb) {
                        temp.add(deltaDistance(i, j, (int) n.X(), (int) n.Y()));
                    }
                    while (temp.size() < 6) {
                        temp.add(new Coordinates(0, 0));
                    }
                    /*
                    for(Coordinates n : neigb){
                        temp.add(deltaVelocity(i, j, (int)n.X(), (int)n.Y()));
                    }
                    while(temp.size() < 11){
                        temp.add(new Coordinates(0, 0));
                    }
                    */

                    temp.add(new Coordinates(height - matrix.get(i).get(j).Y(), matrix.get(i).get(j).Y()));
                    temp.add(velocity.get(i).get(j));
                    double [] tempA = new double[temp.size()*2];
                    int tA = 0;
                    for(Coordinates t:temp){
                        tempA[tA++] = t.X();
                        tempA[tA++] = t.Y();
                    }
                    outputs.add(tempA);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        PsyForceGenerator dataSet = new PsyForceGenerator(0.5,4);
        String baseDirect = "/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/anylogicdata/";
        for(int i = 1; i <=12 ;i++){
            dataSet.readFile(baseDirect+"result"+String.valueOf(i)+".csv", 1);
        }
        dataSet.genOutput();
        dataSet.toFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/trainset4.csv");
    }
}

package org.socialforce.neural;

import com.opencsv.CSVReader;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class PsyForceGenerator extends ForceGenerator {
    private int nearN = 5; //周围人数
    private double timestep = 0.5;  //步长、决定：位置差-速度关系、速度差-加速度关系
    private double height = 6.78;  //场景宽
    private int intercept = 4; //小数位数保留
    DecimalFormat formater = new DecimalFormat();
    private ArrayList<LinkedList<coordinates>> matrix;
    private ArrayList<ArrayList<coordinates>> velocity;
    LinkedList<double[]> outputs = new LinkedList<>();
    public PsyForceGenerator(){
        formater.setMaximumFractionDigits(intercept);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
    }
    public boolean available(int i, int j){
        return (matrix.get(i).get(j) != null && velocity.get(i).get(j).X() != 0 && velocity.get(i).get(j).Y() != 0 && velocity.get(i).get(j+1).X() != 0 && velocity.get(i).get(j+1).Y() != 0 && j+1 < velocity.get(i).size());
    }

    /*
    此处x,y为人在matrix中的坐标 而非位置坐标 TODO 待重构
     */
    public double calcDis(coordinates x, coordinates y){
        coordinates v1 = matrix.get((int)x.X()).get((int)x.Y());
        coordinates v2 = matrix.get((int)y.X()).get((int)y.Y());
        return Math.sqrt(Math.pow((v1.X() - v2.X()),2) + Math.pow((v1.Y() - v2.Y()),2));
    }

    public coordinates calAcc(coordinates a, coordinates b){
        return new coordinates((a.X()-b.X())/timestep,(a.Y()-b.Y())/timestep);
    }

    public ArrayList<coordinates> nearNeighbor(int x, int y){
        ArrayList<coordinates> neighbor = new ArrayList<>();
        for(int i = 0 ; i < matrix.size() ; i++){
            if(i == x || matrix.get(i).get(y) == null){
                continue;
            }
            else{
                neighbor.add(new coordinates(i, y));
            }
        }
        for(int i = 0 ; i < neighbor.size() ; i++){
            if(i > 0 && (calcDis(new coordinates(x,y),neighbor.get(i-1))>calcDis(new coordinates(x,y),neighbor.get(i)))){
                coordinates temp = neighbor.get(i);
                neighbor.set(i,neighbor.get(i-1));
                neighbor.set(i-1,temp);
                if(i > 1){
                    i -= 2;
                }
            }
        }
        ArrayList<coordinates> resultlist = new ArrayList<>();
        for(int i = 0 ; i < nearN ; i++){
            if(i == neighbor.size()){
                break;
            }
            resultlist.add(neighbor.get(i));
        }
        return resultlist;
    }
    public coordinates deltaVelocity(int x1, int y1, int x2, int y2){
        coordinates v1 = velocity.get(x1).get(y1);
        coordinates v2 = velocity.get(x2).get(y2);
        if(v2.X() == 0 && v2.Y() == 0){
            v2 = velocity.get(x2).get(y2+1);
        }
        coordinates resulttuple = new coordinates(v1.X() - v2.X(), v1.Y() - v2.Y());
        return resulttuple;
    }
    public coordinates deltaDistance(int x1, int y1, int x2, int y2){
        coordinates d1 = matrix.get(x1).get(y1);
        coordinates d2 = matrix.get(x2).get(y2);
        coordinates resulttuple = new coordinates(d2.X() - d1.X(), d2.Y() - d1.Y());
        return resulttuple;
    }
    public void genOutput() {
        for(int i = 0 ; i < matrix.size() ; i++){
            for(int j = 0 ; j < matrix.get(i).size() ; j++){
                if(available(i, j)){
                    LinkedList<coordinates> temp = new LinkedList<>();
                    ArrayList<coordinates> neigb = nearNeighbor(i, j);
                    temp.add(calAcc(velocity.get(i).get(j + 1), velocity.get(i).get(j)));
                    for (coordinates n : neigb) {
                        temp.add(deltaDistance(i, j, (int) n.X(), (int) n.Y()));
                    }
                    while (temp.size() < 6) {
                        temp.add(new coordinates(0, 0));
                    }
                    /*
                    for(coordinates n : neigb){
                        temp.add(deltaVelocity(i, j, (int)n.X(), (int)n.Y()));
                    }
                    while(temp.size() < 11){
                        temp.add(new coordinates(0, 0));
                    }
                    */

                    temp.add(new coordinates(height - matrix.get(i).get(j).Y(), matrix.get(i).get(j).Y()));
                    temp.add(velocity.get(i).get(j));
                    double [] tempA = new double[temp.size()*2];
                    int tA = 0;
                    for(coordinates t:temp){
                        tempA[tA++] = t.X();
                        tempA[tA++] = t.Y();
                    }
                    outputs.add(tempA);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        PsyForceGenerator dataSet = new PsyForceGenerator();
        String baseDirect = "/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/anylogicdata/";
        for(int i = 1; i <=12 ;i++){
            dataSet.readFile(baseDirect+"result"+String.valueOf(i)+".csv");
        }
        dataSet.toFile("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/trainset4.csv");
    }
}

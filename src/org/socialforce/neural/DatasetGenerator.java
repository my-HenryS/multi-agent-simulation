package org.socialforce.neural;

import com.opencsv.CSVReader;
import com.sun.rowset.internal.Row;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class DatasetGenerator {
    private int nearN = 5;
    private double timestep = 0.5;
    private double height = 6.78;
    private ArrayList<LinkedList<tuple>> matrix;
    private ArrayList<ArrayList<tuple>> velocity;
    private class tuple {
        private double a;
        private double b;
        public tuple(double aIn, double bIn) {
            a = aIn;
            b = bIn;
        }
        public double A(){
            return a;
        }
        public double B(){
            return b;
        }
    }
    public DatasetGenerator(){
        try{
            matrix = new ArrayList<LinkedList<tuple>>();
            velocity = new ArrayList<ArrayList<tuple>>();
            csv2matrix("D:result4.csv");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void csv2matrix(String directory)throws IOException {
        String axis[];
        LinkedList<tuple> tempR = new LinkedList<tuple>();
        File file = new File(directory);
        FileReader fReader = new FileReader(file);
        CSVReader csvReader = new CSVReader(fReader);
        List<String[]> lines = csvReader.readAll();
        for(String[] line : lines){
            tempR = new LinkedList<>();
            for(int i = 0 ; i < line.length ; i++){
                if(line[i] != null && line[i].length() > 0){
                    String templine = line[i].substring(1,line[i].length()-1);
                    axis = templine.split(",");
                    tempR.add(new tuple(Double.parseDouble(axis[0]),Double.parseDouble(axis[1])));
                }
                else{
                    tempR.add(null);
                }
            }
            matrix.add(tempR);
        }
    }
    public void calcVelocity(){
        for(LinkedList<tuple> tempR : matrix){
            ArrayList<tuple> tempV = new ArrayList<>();
            for(tuple tempT : tempR){
                if(tempT != null && tempR.indexOf(tempT)>=1 && tempR.get(tempR.indexOf(tempT)-1)!=null){
                    tuple preT = tempR.get(tempR.indexOf(tempT)-1);
                    tempV.add(new tuple((tempT.A()-preT.A())/timestep,(tempT.B()-preT.B())/timestep));
                }
                else{
                    tempV.add(new tuple(0,0));
                }
            }
            velocity.add(tempV);
        }
    }
    public boolean available(int i, int j){
        return (matrix.get(i).get(j) != null && velocity.get(i).get(j).A() != 0 && velocity.get(i).get(j).B() != 0 && velocity.get(i).get(j+1).A() != 0 && velocity.get(i).get(j+1).B() != 0 && j+1 < velocity.get(i).size());
    }
    public double calcDis(tuple x, tuple y){
        return Math.sqrt(Math.pow((x.A() - y.A()),2) + Math.pow((x.B() - y.B()),2));
    }
    public ArrayList<tuple> nearNeighbor(int x, int y){
        ArrayList<tuple> neighbor = new ArrayList<>();
        for(int i = 0 ; i < matrix.size() ; i++){
            if(i == x || matrix.get(i).get(y) == null){
                continue;
            }
            else{
                neighbor.add(new tuple(i, y));
            }
        }
        for(int i = 0 ; i < neighbor.size() ; i++){
            if(i > 0 && (calcDis(matrix.get(x).get(y),neighbor.get(i-1))>calcDis(matrix.get(x).get(y),neighbor.get(i)))){
                tuple temp = neighbor.get(i);
                neighbor.set(i,neighbor.get(i-1));
                neighbor.set(i-1,temp);
                if(i > 1){
                    i -= 2;
                }
            }
        }
        ArrayList<tuple> resultlist = new ArrayList<>();
        for(int i = 0 ; i <= nearN ; i++){
            if(i >= neighbor.size()){
                break;
            }
            resultlist.add(neighbor.get(i));
        }
        return resultlist;
    }
    public tuple deltaVelocity(int x1, int y1, int x2, int y2){
        tuple v1 = velocity.get(x1).get(y1);
        tuple v2 = velocity.get(x2).get(y2);
        if(v2.A() == 0 && v2.B() == 0){
            v2 = velocity.get(x2).get(y2+1);
        }
        tuple resulttuple = new tuple(v1.A() - v2.A(), v1.B() - v2.B());
        return resulttuple;
    }
    public tuple deltaDistance(int x1, int y1, int x2, int y2){
        tuple d1 = matrix.get(x1).get(y1);
        tuple d2 = matrix.get(x2).get(y2);
        tuple resulttuple = new tuple(d1.A() - d2.A(), d1.B() - d2.B());
        return resulttuple;
    }
    public String output(int x, int y){
          ArrayList<tuple> outputs = new ArrayList<>();
          String output_print = new String();
          ArrayList<tuple> neigb = nearNeighbor(x, y);
          outputs.add(velocity.get(x).get(y+1));
          for(tuple n : neigb){
              outputs.add(deltaDistance(x, y, (int)n.A(), (int)n.B()));
          }
          while(outputs.size() < 6){
              outputs.add(new tuple(0, 0));
          }
          for(tuple n : neigb){
              outputs.add(deltaVelocity(x, y, (int)n.A(), (int)n.B()));
          }
          while(outputs.size() < 11){
              outputs.add(new tuple(0, 0));
          }
          outputs.add(new tuple(height - matrix.get(x).get(y).B(),matrix.get(x).get(y).B()));
          outputs.add(velocity.get(x).get(y));
          for(tuple out : outputs){
              output_print += String.valueOf(out.A()) + ",";
              output_print += String.valueOf(out.B()) + ",";
          }
          String resultStr = output_print.substring(0,output_print.length()-1) + "\n";
          return resultStr;
    }
    public static void main(String[] args) throws IOException {
        DatasetGenerator x = new DatasetGenerator();
        x.calcVelocity();
        File output_file = new File("D:out.csv");
        FileWriter fw=new FileWriter(output_file);
        BufferedWriter bf=new BufferedWriter(fw);
        for(int i = 0 ; i < x.matrix.size() ; i++){
            for(int j = 0 ; j < x.matrix.get(i).size() ; j++){
                if(x.available(i, j)){
                    bf.write(x.output(i, j));
                }
            }
        }
        bf.close();
    }
}

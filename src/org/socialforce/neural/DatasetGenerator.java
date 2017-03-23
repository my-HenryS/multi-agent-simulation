package org.socialforce.neural;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.*;

public class DatasetGenerator {
    private int nearN = 5;
    private double timestep = 0.5;
    private double height = 6.78;
    private ArrayList<LinkedList<coordinates>> matrix;
    private ArrayList<ArrayList<coordinates>> velocity;
    private class coordinates {
        private double a;
        private double b;
        public coordinates(double aIn, double bIn) {
            a = aIn;
            b = bIn;
        }
        public double X(){
            return a;
        }
        public double Y(){
            return b;
        }
    }
    public DatasetGenerator(){
        try{
            matrix = new ArrayList<LinkedList<coordinates>>();
            velocity = new ArrayList<ArrayList<coordinates>>();
            csv2matrix("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/result4.csv");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void csv2matrix(String directory)throws IOException {
        String axis[];
        LinkedList<coordinates> tempR = new LinkedList<coordinates>();
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
                    tempR.add(new coordinates(Double.parseDouble(axis[0])/10,Double.parseDouble(axis[1])/10));
                }
                else{
                    tempR.add(null);
                }
            }
            matrix.add(tempR);
        }
    }
    public void calcVelocity(){
        for(LinkedList<coordinates> tempR : matrix){
            ArrayList<coordinates> tempV = new ArrayList<>();
            for(coordinates tempT : tempR){
                if(tempT != null && tempR.indexOf(tempT)>=1 && tempR.get(tempR.indexOf(tempT)-1)!=null){
                    coordinates preT = tempR.get(tempR.indexOf(tempT)-1);
                    tempV.add(new coordinates((tempT.X()-preT.X())/timestep,(tempT.Y()-preT.Y())/timestep));
                }
                else{
                    tempV.add(new coordinates(0,0));
                }
            }
            velocity.add(tempV);
        }
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
    public String output(int x, int y){
          ArrayList<coordinates> outputs = new ArrayList<>();
          String output_print = new String();
          ArrayList<coordinates> neigb = nearNeighbor(x, y);
          outputs.add(velocity.get(x).get(y+1));
          for(coordinates n : neigb){
              outputs.add(deltaDistance(x, y, (int)n.X(), (int)n.Y()));
          }
          while(outputs.size() < 6){
              outputs.add(new coordinates(0, 0));
          }
          for(coordinates n : neigb){
              outputs.add(deltaVelocity(x, y, (int)n.X(), (int)n.Y()));
          }
          while(outputs.size() < 11){
              outputs.add(new coordinates(0, 0));
          }
          outputs.add(new coordinates(height - matrix.get(x).get(y).Y(),matrix.get(x).get(y).Y()));
          outputs.add(velocity.get(x).get(y));
          for(coordinates out : outputs){
              output_print += String.valueOf(out.X()) + ",";
              output_print += String.valueOf(out.Y()) + ",";
          }
          String resultStr = output_print.substring(0,output_print.length()-1) + "\n";
          return resultStr;
    }
    public ArrayList<Double> outputData(int x, int y){
        ArrayList<coordinates> outputs = new ArrayList<>();
        ArrayList<coordinates> neigb = nearNeighbor(x, y);
        outputs.add(velocity.get(x).get(y+1));
        for(coordinates n : neigb){
            outputs.add(deltaDistance(x, y, (int)n.X(), (int)n.Y()));
        }
        while(outputs.size() < 6){
            outputs.add(new coordinates(0, 0));
        }
        for(coordinates n : neigb){
            outputs.add(deltaVelocity(x, y, (int)n.X(), (int)n.Y()));
        }
        while(outputs.size() < 11){
            outputs.add(new coordinates(0, 0));
        }
        outputs.add(new coordinates(height - matrix.get(x).get(y).Y(),matrix.get(x).get(y).Y()));
        outputs.add(velocity.get(x).get(y));
        ArrayList<Double> outList = new ArrayList<>();
        for(coordinates out : outputs){
            outList.add(out.X());
            outList.add(out.Y());
        }
        return outList;
    }
    public void toFile(DatasetGenerator dataSet) throws IOException{
        dataSet.calcVelocity();
        File output_file = new File("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/trainset-4.csv");
        FileWriter fw=new FileWriter(output_file);
        BufferedWriter bf=new BufferedWriter(fw);
        for(int i = 0 ; i < dataSet.matrix.size() ; i++){
            for(int j = 0 ; j < dataSet.matrix.get(i).size() ; j++){
                if(dataSet.available(i, j)){
                    bf.write(dataSet.output(i, j));
                }
            }
        }
        bf.close();
    }
    public double[][] toNetwork(DatasetGenerator dataSet){
        double data[][];
        dataSet.calcVelocity();
        ArrayList<ArrayList<Double>> DATAList = new ArrayList<>();
        for(int i = 0 ; i < dataSet.matrix.size() ; i++){
            for(int j = 0 ; j < dataSet.matrix.get(i).size() ; j++){
                if(dataSet.available(i, j)){
                    ArrayList<Double> temp = outputData(i,j);
                    DATAList.add(temp);
                }
            }
        }
        data = new double[DATAList.size()][DATAList.get(0).size()];
        for(int i = 0 ; i < DATAList.size() ; i++){
            for(int j = 0 ; j < DATAList.get(i).size() ; j++){
                data[i][j] = DATAList.get(i).get(j);
            }
        }
        return data;
    }
    public static void main(String[] args) throws IOException {
        DatasetGenerator dataSet = new DatasetGenerator();
        dataSet.toFile(dataSet);
    }
}

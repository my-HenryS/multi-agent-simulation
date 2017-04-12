package org.socialforce.neural.impl;

import com.opencsv.CSVReader;
import org.socialforce.neural.Coordinates;
import org.socialforce.neural.DataSetGenerator;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by micha on 2017/3/27.
 */
public abstract class ForceGenerator implements DataSetGenerator {
    protected double timestep;  //步长、决定：位置差-速度关系、速度差-加速度关系
    DecimalFormat formater = new DecimalFormat();
    protected ArrayList<LinkedList<Coordinates>> matrix = new ArrayList<LinkedList<Coordinates>>();
    protected ArrayList<ArrayList<Coordinates>> velocity = new ArrayList<ArrayList<Coordinates>>();
    LinkedList<double[]> outputs = new LinkedList<>();


    public ForceGenerator(double timestep, int intercept){
        this.timestep = timestep;
        formater.setMaximumFractionDigits(intercept);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
    }

    public void readFile(String directory, int timeInterval){
        matrix = new ArrayList<LinkedList<Coordinates>>();
        velocity = new ArrayList<ArrayList<Coordinates>>();
        try{
            csv2matrix(directory, timeInterval);
            calcVelocity();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void csv2matrix(String directory, int timeInterval)throws IOException {
        String axis[];
        LinkedList<Coordinates> tempR = new LinkedList<Coordinates>();
        File file = new File(directory);
        FileReader fReader = new FileReader(file);
        CSVReader csvReader = new CSVReader(fReader);
        List<String[]> lines = csvReader.readAll();
        for(String[] line : lines){
            tempR = new LinkedList<>();
            for(int i = 0 ; i < line.length ; i++){
                if(i % timeInterval != 0) continue;
                if(line[i] != null && line[i].length() > 0){
                    String templine = line[i].substring(1,line[i].length()-1);
                    axis = templine.split(",");
                    tempR.add(new Coordinates(Double.parseDouble(axis[0])/10,Double.parseDouble(axis[1])/10));
                }
                else{
                    tempR.add(null);
                }
            }
            matrix.add(tempR);
        }
    }

    public void calcVelocity(){
        for(LinkedList<Coordinates> tempR : matrix){
            ArrayList<Coordinates> tempV = new ArrayList<>();
            for(Coordinates tempT : tempR){
                if(tempT != null && tempR.indexOf(tempT)>=1 && tempR.get(tempR.indexOf(tempT)-1)!=null){
                    Coordinates preT = tempR.get(tempR.indexOf(tempT)-1);
                    tempV.add(new Coordinates((tempT.X()-preT.X())/timestep,(tempT.Y()-preT.Y())/timestep));
                }
                else{
                    tempV.add(new Coordinates(0,0));
                }
            }
            velocity.add(tempV);
        }
    }

    public boolean available(int i, int j){
        return matrix.get(i).get(j) != null && !velocity.get(i).get(j).isZero() && j+1 < velocity.get(i).size() && !velocity.get(i).get(j+1).isZero() ;
    }

    public void toFile(String outDirect){
        File output_file = new File(outDirect);
        FileWriter fw= null;
        try {

            fw = new FileWriter(output_file);
            BufferedWriter bf=new BufferedWriter(fw);
            for(double [] output:outputs){
                String s = "";
                for(double element:output){
                    s += String.valueOf(formater.format(element))+",";
                }
                bf.write(s.substring(0,s.length()-1)+"\n");
            }
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toFile(String outDirect, int interval){
        File output_file = new File(outDirect);
        FileWriter fw= null;
        int count = 0;
        try {

            fw = new FileWriter(output_file);
            BufferedWriter bf=new BufferedWriter(fw);
            for(double [] output:outputs){
                count ++;
                if(count % interval != 0)
                    continue;
                String s = "";
                for(double element:output){
                    s += String.valueOf(formater.format(element))+",";
                }
                bf.write(s.substring(0,s.length()-1)+"\n");
            }
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double[][] toMatrix() {
        double[][] matrix = new double[outputs.size()][outputs.get(0).length];
        for(int i = 0; i < outputs.size(); i++){
            double[] output = outputs.get(i);
            for(int j = 0; j < output.length; j++){
                matrix[i][j] = Double.parseDouble(formater.format(output[j]));
            }
        }
        return matrix;
    }

    public void addOutput(double[] output, int times){
        for(; times > 0; times --){
            outputs.add(output);
        }
    }
}

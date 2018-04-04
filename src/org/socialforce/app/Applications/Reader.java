package org.socialforce.app.Applications;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.*;

public class Reader {
    public static void main(String[] args) {
        try {
            String[] container = new String[30];
            File f = new File(System.getProperty("user.dir") + "/resource/output/Ellipse.csv");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                String item[] = line.split("\"");
                String container_of_ped[] = new String[item.length / 2];
                for (int i = 0; i < item.length; i++) {
                    if (i % 2 == 0) {
                        continue;
                    }
                    container_of_ped[i / 2] = item[i];
                }
                for (int i = 0; i < container_of_ped.length; i++) {
                    container_of_ped[i] = container_of_ped[i].substring(1, container_of_ped[i].length() - 1);
                    String subSrring[] = container_of_ped[i].split(",");
                    container_of_ped[i] = subSrring[1] + "," + subSrring[0];
                }
                String container_of_everyone[] = new String[30];
                for (int i = 0; i < container_of_everyone.length; i++) {
                    container_of_everyone[i] = "";
                }
                for (int i = 0; i < container_of_ped.length; i++) {
                    int m = i % 30;
//                    container_of_ped[i] = container_of_ped[i].substring(1, container_of_ped[i].length() - 1);
                    container_of_everyone[m] += "\"" + container_of_ped[i] + "\",";
                }
                for (int i = 0; i < container_of_everyone.length; i++) {
                    container_of_everyone[i] = container_of_everyone[i].substring(0, container_of_everyone[i].length() - 1);
                    System.out.println(container_of_everyone[i]);
                }
                try {
                    String FileName = "transEllipse.csv";
                    String FilePath = System.getProperty("user.dir") + "/resour ce/output/";
                    File csv = null;
                    csv = new File(FilePath + FileName);
                    csv.createNewFile();
                    File ff = new File(FilePath + FileName);
                    FileWriter fffw = new FileWriter(ff);
                    BufferedWriter ffbw = new BufferedWriter(fffw);
                    for (int i = 0; i < container_of_everyone.length; i++) {
                        ffbw.write(container_of_everyone[i]);
                        ffbw.newLine();
                    }
                    ffbw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

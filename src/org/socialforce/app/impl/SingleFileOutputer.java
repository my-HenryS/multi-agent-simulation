package org.socialforce.app.impl;

import org.socialforce.app.DataListener;

import java.io.*;

/**
 * Created by Ledenel on 2017/1/29.
 */
public class SingleFileOutputer implements DataListener<String> {
    @Override
    public void update(String data) {
        try {
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String filename;

    BufferedWriter bw;
    public SingleFileOutputer(String filename) throws IOException {
        this.filename = filename;
        bw = new BufferedWriter(new FileWriter(filename));
    }

    public void close() {
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package org.socialforce.app.gui;

import javax.swing.*;

/**
 * Created by Ledenel on 2016/8/23.
 */
public class SimulationPanelMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("SimulationPanelMain");
        frame.setContentPane(new SimulationPanelMain().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel root;
}

package org.socialforce.app.gui;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ledenel on 2016/8/23.
 */
public class SimulationPanelMain {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            //  e.printStackTrace();
        } catch (InstantiationException e) {
            //  e.printStackTrace();
        } catch (IllegalAccessException e) {
            //  e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            //  e.printStackTrace();
        } finally {
            //UIManager.look
        }
        try {
            JFrame frame = new JFrame("SimulationPanelMain");
            frame.setContentPane(new SimulationPanelMain().root);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (HeadlessException e) {
            System.out.println("GUI Not Supported on this machine.");
        }

    }

    private JPanel root;
    private JTextField currentScnenTextField;
    private JButton importFromFileButton;
    private JComboBox agentPathFindingComboBox;
    private JButton runButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JPanel sceneContainer;
    private JPanel scene1;
    private JPanel scene2;
    private JPanel scene3;
    private JPanel scene4;
    private JLabel timeUsedLabel;
    private JTextArea logTextArea;
    private JTextField timePerStepTextField;
    private JLabel fpsLabel;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        scene1 = new SceneShower("Scene 1").getRoot();
        scene2 = new SceneShower("Scene 2").getRoot();
        scene3 = new SceneShower("Scene 3").getRoot();
        scene4 = new SceneShower("Scene 4").getRoot();
    }
}

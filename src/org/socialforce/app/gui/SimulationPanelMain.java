package org.socialforce.app.gui;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;

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
        JFrame frame = new JFrame("SimulationPanelMain");
        frame.setContentPane(new SimulationPanelMain().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel root;
    private JTextField currentScnenTextField;
    private JButton importFromFileButton;
    private JComboBox agentPathFindingComboBox;
    private JButton runButton;
    private JButton pauseButton;
    private JButton stopButton;
}

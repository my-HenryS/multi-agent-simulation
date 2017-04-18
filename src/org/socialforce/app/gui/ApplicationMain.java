package org.socialforce.app.gui;

import org.socialforce.app.ApplicationListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by micha on 2017/4/18.
 */
public class ApplicationMain{
    private JButton simulationModeButton;
    private JButton editModeButton;
    public JPanel demoP;
    private JLabel demoL;

    public ApplicationMain(){
        simulationModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    JFrame frameS = new JFrame("SimulationPanelMain");
                    SimulationPanelMain mainPanel = new SimulationPanelMain();
                    mainPanel.frame.dispose();
                    /*SocialForceApplication application = new ApplicationForDoorTest();//应用在这里！
                     application.setApplicationListener(mainPanel);*/
                    //mainPanel.setLoader(new ApplicationLoader(mainPanel));
                    frameS.setContentPane(mainPanel.root);
                    frameS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //frame.setResizable(false);
                    frameS.pack();
                    frameS.setVisible(true);
                    //application.start();
                } catch (HeadlessException x) {
                    System.out.println("GUI Not Supported on this machine.");
                }
            }
        });
    }
}

package org.socialforce.app.gui;

import org.socialforce.app.Scene;
import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.drawer.impl.SceneDrawerInstaller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Ledenel on 2016/8/23.
 */
public class SceneShower {
    private JCheckBox visibleCheckBox;
    private JPanel showPanel;
    private JPanel root;
    private JButton changeButton;
    private JLabel totalPeopleLabel;
    private JLabel remainPeopleLabel;
    private JLabel timeLabel;
    private JLabel trappedPeopleLabel;
    private String title;

    private DrawerInstaller drawerInstaller;

    /**
     * show the title of the scene
     * @param title
     */
    public SceneShower(String title) {
        this.title = title;
    }

    /**
     * //////??????
     * @return the root
     */
    public JPanel getRoot() {
        root.setBorder(BorderFactory.createTitledBorder(title));
        return root;

    }

    /**
     *
     */
    private void createUIComponents() {
        root = new JPanel();

        //root.setBorder(BorderFactory.createTitledBorder(title));
        // TODO: place custom component creation code here
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        drawerInstaller = new SceneDrawerInstaller((Graphics2D) showPanel.getGraphics(), this.showPanel.getWidth(), this.showPanel.getHeight());
        drawerInstaller.addDrawerSupport(scene);
    }

    Scene scene;

}

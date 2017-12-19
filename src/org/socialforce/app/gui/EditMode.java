package org.socialforce.app.gui;

import org.socialforce.app.Application;
import org.socialforce.app.ApplicationListener;
import org.socialforce.app.Applications.ApplicationForCanteen;
import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.drawer.impl.SceneDrawerInstaller;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.Agent;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneListener;
import org.socialforce.scene.impl.SimpleParameterPool;
import org.socialforce.scene.impl.SimpleScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 *   Copied by RespawningChar on 2017/11/26
 */
public class EditMode {

    private JPanel rootPanel;
    private JButton rootButton;
    private JPanel drawBoard;
    private JButton wallSelector;
    private JCheckBox visibleCheckBox;
    private SceneProcessor sceneProcessor;
    private SimpleScene mainScene;

    private String title;
    private SceneProcessor board;


    public EditMode() {
        this.title = title;
        setUpscene();
        mainScene = new SimpleScene(new Box2D(0,0,20,20));
        //mainScene.setDrawer();
        board.setScene(mainScene);
        visibleCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawBoard.setVisible(visibleCheckBox.isSelected());

                }
                                          });
    }

    /**
     * Establish a certain scene that only contains walls, doors and some other entities which pedenstrains are not included.
     * @throws EditMode
     */
    public void setUpscene()
    {
        //this.mainScene.setStaticEntities();
    }

    public JPanel getRoot() {
        rootPanel.setBorder(BorderFactory.createTitledBorder(title));
        return rootPanel;

    }

    private void createUIComponents() {
        rootPanel = new JPanel();

        //root.setBorder(BorderFactory.createTitledBorder(title));
        // TODO: place custom component creation code here

        board = new SceneProcessor();
        drawBoard = board;

    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("EditMode");
        frame.setContentPane(new EditMode().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
    private DrawerInstaller drawerInstaller = new SceneDrawerInstaller((Graphics2D) image.getGraphics(), image.getWidth(), image.getHeight());

}

package org.socialforce.app.gui;

import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.drawer.impl.SceneDrawerInstaller;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneListener;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by Ledenel on 2016/8/23.
 */
public class SceneShower implements SceneListener {
    private JCheckBox visibleCheckBox;
    private JPanel showPanel1;
    private JPanel showPanel2;
    private JTabbedPane tabbedPane;
    private JPanel root;
    private JButton changeButton;
    private JLabel totalPeopleLabel;
    private JLabel remainPeopleLabel;
    private JLabel timeLabel;
    private JLabel trappedPeopleLabel;
    private JLabel positionLabel;
    private JPanel showPanel3;
    private String title;

    private DrawerInstaller drawerInstaller;

    private boolean drawable = false;

    private Timer timer = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(visibleCheckBox.isSelected() && scene.getDrawer() != null && scene.getAllAgents().size() != 0)
                scene.getDrawer().draw(scene);
        }
    });

    private Timer repaintTimer = new Timer(3, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(visibleCheckBox.isSelected()) getBoard().repaint();
        }
    });


    /**
     * show the title of the scene
     *
     * @param title
     */
    public SceneShower(String title) {
        this.title = title;
        visibleCheckBox.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                SceneShower.this.getBoard().setVisible(visibleCheckBox.isSelected());
                SceneShower.this.positionLabel.setVisible(visibleCheckBox.isSelected());
            }
        });
        showPanel1.addMouseMotionListener(new MouseMotionAdapter() {
            /**
             * Invoked when the mouse button has been moved on a component
             * (with no buttons no down).
             *
             * @param e
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                if (SceneShower.this.scene != null) {
                    SceneDrawer sc = (SceneDrawer) SceneShower.this.scene.getDrawer();
                    double[] scr = sc.screenToScene(e.getX(), e.getY());
                    SceneShower.this.positionLabel.setText(String.format("当前坐标：(%.3f,%.3f)", scr[0], scr[1]));
                }
//                super.mouseMoved(e);
            }
        });
    }

    /**
     * //////??????
     *
     * @return the root
     */
    public JPanel getRoot() {
        root.setBorder(BorderFactory.createTitledBorder(title));
        return root;

    }

    private SceneBoard board;

    public SceneBoard getBoard() {
        return board;
    }

    public void setBoard(SceneBoard board) {
        this.board = board;
    }

    /**
     *
     */
    private void createUIComponents() {
        root = new JPanel();

        //root.setBorder(BorderFactory.createTitledBorder(title));
        // TODO: place custom component creation code here
        board = new SceneBoard();
        showPanel1 = board;
    }

    BufferedImage image;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        scene.addSceneListener(this);
        // FIXME: 2017/1/2 change this image to dynamic-sized(with components.) or using swing's own double-buffered system.
        image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
        drawerInstaller = new SceneDrawerInstaller((Graphics2D) image.getGraphics(), image.getWidth(), image.getHeight());
        drawerInstaller.addDrawerSupport(scene);
        board.setImage(image);
        board.setScene(scene);
        totalPeopleLabel.setText("" + scene.getAllAgents().size());
        board.getResizeListener().componentResized(null);
        timer.restart();
        repaintTimer.restart();
    }

    Scene scene;

    public DrawerInstaller getDrawerInstaller() {
        return drawerInstaller;
    }

    /**
     * 场景全部加载完毕时触发的事件。
     *
     * @param scene 触发的场景。
     */
    @Override
    public void onLoaded(Scene scene) {

    }

    /**
     * 场景进行一步时触发的事件。
     *
     * @param scene 触发的场景。
     */
    @Override
    public void onStep(Scene scene) {
        this.remainPeopleLabel.setText("" + scene.getAllAgents().size());
        this.timeLabel.setText(String.format("%.3f", scene.getCurrentSteps() * scene.getApplication().getModel().getTimePerStep()));

    }


}

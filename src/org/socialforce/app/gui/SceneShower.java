package org.socialforce.app.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.*;
import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.drawer.impl.SceneDrawerInstaller;
import org.socialforce.geom.*;
import org.socialforce.geom.Box;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.Agent;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneListener;
import org.tc33.jheatchart.HeatChart;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.*;
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
    private JLabel avgVLabel;
    private JLabel positionLabel;
    private JPanel showPanel3;
    private String title;

    private JTextArea textArea;

    protected void setLog(JTextArea area){
        textArea = area;
    }

    private DrawerInstaller drawerInstaller;

    private Timer timer = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(visibleCheckBox.isSelected() && scene.getDrawer() != null && scene.getAllAgents().size() != 0) {
                scene.getDrawer().draw(scene);
                chartPanel.loadPhoto(heatMapListener.getImage());
                chartPanel2.loadPhoto(dynamicsListener.getImage());
            }
        }
    });

    private Timer repaintTimer = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(visibleCheckBox.isSelected()){
                getBoard().repaint();
                chartPanel.repaint();
                chartPanel2.repaint();
            }
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
                chartPanel.setVisible(visibleCheckBox.isSelected());
                chartPanel2.setVisible(visibleCheckBox.isSelected());
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
                    if(sc != null) {
                        double[] scr = sc.screenToScene(e.getX(), e.getY());
                        SceneShower.this.positionLabel.setText(String.format("Mouse Position：(%.3f,%.3f)", scr[0], scr[1]));
                    }
                }
//                super.mouseMoved(e);
            }
        });

        board.addMouseMotionListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                textArea.append("GOOD");
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
    private ImagePanel chartPanel, chartPanel2;

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
        chartPanel = new ImagePanel();
        showPanel2 = chartPanel;
        chartPanel2 = new ImagePanel();
        showPanel3 = chartPanel2;

    }

    BufferedImage image;

    public Scene getScene() {
        return scene;
    }

    HeatMapListener heatMapListener;
    DynamicsListener dynamicsListener;

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
        heatMapListener = new HeatMapListener();
        scene.addSceneListener(heatMapListener);
        dynamicsListener = new DynamicsListener();
        scene.addSceneListener(dynamicsListener);
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
    public void onAdded(Scene scene) {

    }

    /**
     * 场景进行一步时触发的事件。
     *
     * @param scene 触发的场景。
     */
    @Override
    public void onStep(Scene scene) {
        if(scene.getAllAgents().isEmpty()) return;
        this.remainPeopleLabel.setText("" + scene.getAllAgents().size());
        this.timeLabel.setText(String.format("%.3f", scene.getCurrentSteps() * scene.getApplication().getModel().getTimePerStep()));
        this.avgVLabel.setText(String.format("%.3f", scene.getAllAgents().stream().mapToDouble(value -> value.getVelocity().length()).average().getAsDouble()));
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        //boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
           /* if (hasAlpha) {
             transparency = Transparency.BITMASK;
             }*/

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
            /*if (hasAlpha) {
             type = BufferedImage.TYPE_INT_ARGB;
             }*/
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }

    private class HeatMapListener implements SceneListener{
        double X,Y;
        double xMin, yMin, xMax, yMax;
        @Override
        public void onAdded(Scene scene) {
            Box bounds = scene.getBounds();
            this.X = bounds.getEndPoint().getX() - bounds.getStartPoint().getX();
            this.Y = bounds.getEndPoint().getY() - bounds.getStartPoint().getY();
            this.xMin = bounds.getStartPoint().getX();
            this.yMin = bounds.getStartPoint().getY();
            this.xMax = bounds.getEndPoint().getX();
            this.yMax = bounds.getEndPoint().getY();
            HeatMap = new double[(int)Y+1][(int)X+1];
            aggrHeatMap = new double[(int)Y+1][(int)X+1];

        }
        public double[][] HeatMap, aggrHeatMap;

        public double[][] getHeatMap(){ return HeatMap; }

        public Image getImage(){
            HeatChart map = new HeatChart(getHeatMap());
            map.setCellSize(new Dimension(10,10));
            map.setTitle("HeatMap");
            map.setXAxisLabel("X Axis");
            map.setYAxisLabel("Y Axis");
            map.setShowXAxisValues(false);
            map.setShowYAxisValues(false);
            map.setHighValueColour(Color.ORANGE);
            map.setLowValueColour(Color.BLUE);
            return map.getChartImage();
        }

        @Override
        public void onStep(Scene scene) {
            for(Agent agent: scene.getAllAgents()){
                org.socialforce.geom.Point position = agent.getShape().getReferencePoint();
                aggrHeatMap[(int)(position.getY() - yMin)][(int)(position.getX() - xMin)] += 1;
            }
            for(int i = 0; i < aggrHeatMap.length; i++){
                for(int j = 0; j < aggrHeatMap[i].length; j++){
                    HeatMap[i][j] = aggrHeatMap[aggrHeatMap.length - i - 1][j] / scene.getCurrentSteps();
                }
            }
        }

    }

    private class DynamicsListener implements SceneListener{
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String series1 = "平均时间";
        Integer timeCount = 0, dataCount = 0;
        int interval = 10;
        @Override
        public void onAdded(Scene scene) {

        }

        public Image getImage(){
            JFreeChart chart = ChartFactory.createLineChart("场景平均速度变化", "时间", "平均速度", dataset,
                    PlotOrientation.VERTICAL,
                    true, true, true
            );
            chart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(false);
            return chart.createBufferedImage(660,550);
        }

        @Override
        public void onStep(Scene scene) {
            if(scene.getAllAgents().isEmpty() || timeCount++ % interval != 0) return;
            dataset.addValue(
                    Double.parseDouble(
                            String.format("%.3f",
                                    scene.getAllAgents().stream().mapToDouble(value -> value.getVelocity().length()).average().getAsDouble()
                            )
                    ),
                    series1,
                    dataCount++
            );
        }
    }

}

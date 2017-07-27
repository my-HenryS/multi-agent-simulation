package org.socialforce.app.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.drawer.impl.SceneDrawerInstaller;
import org.socialforce.geom.Box;
import org.socialforce.geom.impl.Tuple2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneListener;
import org.tc33.jheatchart.HeatChart;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

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
    private JComboBox comboBox1;
    private JPanel Properties;
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
                if(tabbedPane.getSelectedIndex() == 0){
                    scene.getDrawer().draw(scene);
                    getBoard().repaint();
                }
                else if(tabbedPane.getSelectedIndex() == 1) {
                    chartPanel.loadPhoto(heatMapListener.getImage());
                    chartPanel.repaint();
                }
                else{
                    if (comboBox1.getSelectedIndex() == 0)
                        chartPanel2.loadPhoto(avgVListener.getImage());
                    else
                        chartPanel2.loadPhoto(ecListener.getImage());
                    chartPanel2.repaint();
                }
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
        chartPanel.setOrigin(20,20);
        showPanel2 = chartPanel;
        chartPanel2 = new ImagePanel();
        chartPanel2.setOrigin(0,20);
        showPanel3 = chartPanel2;

    }

    BufferedImage image;

    public Scene getScene() {
        return scene;
    }

    HeatMapListener heatMapListener;
    AvgVelocityListener avgVListener;
    ExitCapacityListener ecListener;

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
        avgVListener = new AvgVelocityListener();
        scene.addSceneListener(avgVListener);
        ecListener = new ExitCapacityListener();
        scene.addSceneListener(ecListener);
        //repaintTimer.restart();
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
    public boolean onAdded(Scene scene) {
        return true;
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
        this.timeLabel.setText(String.format("%.3f", scene.getCurrentSteps() * scene.getModel().getTimePerStep()));
        this.avgVLabel.setText(String.format("%.3f", scene.getAllAgents().stream().mapToDouble(value -> value.getVelocity().length()).average().getAsDouble()));
    }

    private class HeatMapListener implements SceneListener{
        double X,Y;
        double xMin, yMin, xMax, yMax;
        @Override
        public boolean onAdded(Scene scene) {
            Box bounds = scene.getBounds();
            this.X = bounds.getEndPoint().getX() - bounds.getStartPoint().getX();
            this.Y = bounds.getEndPoint().getY() - bounds.getStartPoint().getY();
            this.xMin = bounds.getStartPoint().getX();
            this.yMin = bounds.getStartPoint().getY();
            this.xMax = bounds.getEndPoint().getX();
            this.yMax = bounds.getEndPoint().getY();
            HeatMap = new double[(int)Y+1][(int)X+1];
            aggrHeatMap = new double[(int)Y+1][(int)X+1];
            return true;
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

    private abstract class DynamicsListener implements SceneListener{
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        LinkedBlockingQueue<Tuple2D<Double, String>> series = new LinkedBlockingQueue<>();   //储存新进入的数据缓存
        String [] labels;   //所有序列的labels
        Integer timeCount = 0, dataCount = 0;  //时间数，数据数
        int interval = 10;  //间隔0.08秒采样
        int maxDataPerSeries = 100;  //每序列最多数据 （8秒）
        String title = "", xAxis = "", yAxis = "";

        //Consumer
        public Image getImage(){
            int temp = 0;
            for(Tuple2D<Double, String> data: series){
                dataset.addValue(data.getFirst(), data.getSecond(), dataCount);
                temp++;
                if(temp % labels.length == 0){
                    temp = 0;
                    dataCount++;
                }
            }
            series.clear();
            while(dataset.getColumnCount() >= maxDataPerSeries){
                dataset.removeColumn(0);    //当超过8秒的数据时，删除前面的数据
            }
            JFreeChart chart = ChartFactory.createLineChart(title, xAxis, yAxis, dataset,
                    PlotOrientation.VERTICAL,
                    true, true, true
            );
            chart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(false);
            chart.getCategoryPlot().getDomainAxis().setTickMarksVisible(false);
            //chart.getCategoryPlot().getRangeAxis().setRange(0,); 设定y轴坐标上下限
            Image image = chart.createBufferedImage(660,500);
            return image;
        }

    }

    private class AvgVelocityListener extends DynamicsListener{

        /**
         * 初始化绘图坐标，序列标签等
         * @param scene 触发的场景。
         */

        @Override
        public boolean onAdded(Scene scene) {
            title = "最近8秒行人平均速度变化";
            xAxis = "时间";
            yAxis = "平均速度";
            labels = new String[]{"所有行人"};
            return true;
        }

        //Producer
        @Override
        public void onStep(Scene scene) {
            if(scene.getAllAgents().isEmpty() || timeCount++ % interval != 0) return;
            series.add(
                    new Tuple2D<>(
                            Double.parseDouble(String.format("%.3f",
                                    scene.getAllAgents().stream().mapToDouble(value -> value.getVelocity().length()).average().getAsDouble()
                            )),
                            labels[0]
                    )
            );
        }
    }

    private class ExitCapacityListener extends DynamicsListener{

        /**
         * 初始化绘图坐标，序列标签等
         * @param scene 触发的场景。
         */

        @Override
        public boolean onAdded(Scene scene) {
            interval = 40;  //间隔0.32秒采样
            maxDataPerSeries = 200;  //每序列最多数据 （64秒）
            title = "各出口逃生速率变化";
            xAxis = "时间";
            yAxis = "行人平均逃生速率";
            int labelNum = 0;
            for(InteractiveEntity sR : scene.getStaticEntities().selectClass(SafetyRegion.class)){
                labelNum++;   //计算sR个数
            }
            labels = new String[labelNum];
            String baseLabel = "出口";
            for(int i = 0; i < labelNum; i++){
                labels[i] = baseLabel + String.valueOf(i);
            }
            return true;
        }

        //Producer
        @Override
        public void onStep(Scene scene) {
            if(scene.getAllAgents().isEmpty() || timeCount++ % interval != 0) return;
            int i = 0;
            for(InteractiveEntity sR : scene.getStaticEntities().selectClass(SafetyRegion.class)){
                series.add(
                        new Tuple2D<>((double)((SafetyRegion) sR).getLastSecondEscapedAgents(), labels[i++])
                );
            }
        }
    }

}

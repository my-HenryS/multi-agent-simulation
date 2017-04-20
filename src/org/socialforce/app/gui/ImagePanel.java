package org.socialforce.app.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * 用于显示图片的JPanel, 放在frame中
 * Created by sunjh1999 on 2017/4/20.
 */
class ImagePanel extends JPanel {
    private Image image;

    //初始化时，加载的图片1.jpg
    public ImagePanel()
    {

    }
    //实现图片的更新
    public void loadPhoto(Image img)
    {
        image=img;
        repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if(image==null) return;

        int imageWidth=image.getWidth(this);
        int imageHeight=image.getHeight(this);

//将图片画在左上角
        g.drawImage(image, 0, 0, null);

    }
}
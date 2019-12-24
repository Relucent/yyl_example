package yyl.example.exercise.awt.movesc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.JFrame;

import yyl.example.basic.awt.img.DoubleBufferCanvas;

/**
 * 移动的SCV (Canvas用法演示)<br>
 * 使用 DoubleBufferCanvas可以防止图案刷新闪烁<br>
 */
public class CanvasBoard extends DoubleBufferCanvas implements Runnable {

    private static final long serialVersionUID = 1L;

    private Point starPoint = new Point(50, 50);

    private Point targetPoint = new Point(50, 50);

    Image[] images = new Image[10];

    public CanvasBoard() {
        Image image = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("scv_red.gif"));
        for (int i = 0; i < 5; i++)
            images[i] = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), new CropImageFilter(0, 47 * i, 45, 45)));

        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent event) {
                targetPoint.x = event.getX();
                targetPoint.y = event.getY();
            }
        });
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void paint(Graphics gc) {
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setColor(new Color(0, 0, 0));

        if (state < 0) {
            gc.drawImage(images[-state], starPoint.x - 25, starPoint.y - 25, starPoint.x + 20, starPoint.y + 20, 50, 0, 0, 50, this);
        } else {
            gc.drawImage(images[state], starPoint.x - 25, starPoint.y - 25, starPoint.x + 20, starPoint.y + 20, 0, 0, 50, 50, this);
        }
    }

    int state = 0;

    private void gogo() {

        if (targetPoint.x < starPoint.x) {
            if (targetPoint.y > starPoint.y) {
                state = -3;
            } else if (targetPoint.y < starPoint.y) {
                state = -1;
            } else {
                state = -2;
            }
        } else if (targetPoint.x > starPoint.x) {
            if (targetPoint.y > starPoint.y) {
                state = 3;
            } else if (targetPoint.y < starPoint.y) {
                state = 1;
            } else {
                state = 2;
            }
        } else {
            if (targetPoint.y > starPoint.y) {
                state = 4;
            } else if (targetPoint.y < starPoint.y) {
                state = 0;
            }
        }

        if (targetPoint.x > starPoint.x) {
            starPoint.x = starPoint.x + 1;
        }
        if (targetPoint.x < starPoint.x) {
            starPoint.x = starPoint.x - 1;
        }
        if (targetPoint.y > starPoint.y) {
            starPoint.y = starPoint.y + 1;
        }
        if (targetPoint.y < starPoint.y) {
            starPoint.y = starPoint.y - 1;
        }
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
                gogo();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            repaint();
        }
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new CanvasBoard());
        frame.setBounds(100, 100, 500, 375);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.validate();
    }

}

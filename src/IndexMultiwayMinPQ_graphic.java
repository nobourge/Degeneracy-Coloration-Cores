/// IndexMultiwayMinPQ graphical representation

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.IndexMultiwayMinPQ;

public class IndexMultiwayMinPQ_graphic extends JFrame {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_N = 10;
    private static final int DEFAULT_K = 3;
    private static final int DEFAULT_D = 2;
    private static final int DEFAULT_W = 10;
    private static final int DEFAULT_H = 10;
    private static final int DEFAULT_R = 10;
    private static final int DEFAULT_G = 10;
    private static final int DEFAULT_B = 10;
    private static final int DEFAULT_X = 0;
    private static final int DEFAULT_Y = 0;
    private static final int DEFAULT_DELAY = 100;
    private static final int DEFAULT_DELAY_INCREMENT = 100;
    private static final int DEFAULT_DELAY_DECREMENT = 100;
    private static final int DEFAULT_DELAY_MAX = 1000;
    private static final int DEFAULT_DELAY_MIN = 0;
    private static final int DEFAULT_DELAY_STEP = 100;
    private static final int DEFAULT_DELAY_INIT = 0;
    private static final int DEFAULT_DELAY_LABEL = 10;
    private static final int DEFAULT_DELAY_LABEL_X = 10;
    private static final int DEFAULT_DELAY_LABEL_Y = 10;
    private static final int DEFAULT_DELAY_LABEL_W = 100;
    private static final int DEFAULT_DELAY_LABEL_H = 20;
    private static final int DEFAULT_DELAY_LABEL_FONT_SIZE = 16;

    public IndexMultiwayMinPQ_graphic(IndexMultiwayMinPQ<Integer> pq) {
        setTitle("IndexMultiwayMinPQ");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
        setLocation(DEFAULT_X, DEFAULT_Y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new IndexMultiwayMinPQ_graphic_panel());
    }

    //IndexMultiwayMinPQ_graphic_panel constructor
    private static class IndexMultiwayMinPQ_graphic_panel extends JPanel {
        private static final int DEFAULT_WIDTH = 600;
        private static final int DEFAULT_HEIGHT = 600;
        private static final int DEFAULT_N = 10;
        private static final int DEFAULT_K = 3;
        private static final int DEFAULT_D = 2;
        private static final int DEFAULT_W = 10;
        private static final int DEFAULT_H = 10;
        private static final int DEFAULT_R = 10;
        private static final int DEFAULT_G = 10;
        private static final int DEFAULT_B = 10;
        private static final int DEFAULT_X = 0;
        private static final int DEFAULT_Y = 0;
        private static final int DEFAULT_DELAY = 100;
        private static final int DEFAULT_DELAY_INCREMENT = 100;
        private static final int DEFAULT_DELAY_DECREMENT = 100;
        private static final int DEFAULT_DELAY_MAX = 1000;
        private static final int DEFAULT_DELAY_MIN = 0;
        private static final int DEFAULT_DELAY_STEP = 100;
        private static final int DEFAULT_DELAY_INIT = 0;
        private static final int DEFAULT_DELAY_LABEL = 10;
        private static final int DEFAULT_DELAY_LABEL_X = 10;
        private static final int DEFAULT_DELAY_LABEL_Y = 10;
        private static final int DEFAULT_DELAY_LABEL_W = 100;
        private static final int DEFAULT_DELAY_LABEL_H = 20;
        private static final int DEFAULT_DELAY_LABEL_FONT_SIZE = 16;

        private IndexMultiwayMinPQ<Integer> pq;
        private int n;
        private int k;
        private int d;
        private int w;
        private int h;
        private int r;
        private int g;
        private int b;
        private int delay;
        private int delay_increment;
    }

    //function shows a graphical representation of an IndexMultiwayMinPQ
    public static void show(IndexMultiwayMinPQ<Integer> pq) {
        JFrame frame = new IndexMultiwayMinPQ_graphic(pq);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }




}

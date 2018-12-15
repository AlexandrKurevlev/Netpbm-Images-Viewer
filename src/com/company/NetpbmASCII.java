package com.company;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

public class NetpbmASCII extends JFrame{
    private int w;
    private int h;
    private Color[] image;

    public NetpbmASCII(String absolutePath){
        try {
            Scanner scanner = new Scanner(new File(absolutePath));
            String mn = scanner.next();
            System.out.println("Magic Number: " + mn);

            //Skip comment
            if (scanner.hasNext("#")) {
                scanner.nextLine();
                scanner.nextLine();
            }

            w = scanner.nextInt();
            h = scanner.nextInt();
            System.out.println("Width: " + w);
            System.out.println("Height: " + h);

            int numOfPixels = w*h;
            image = new Color[numOfPixels];

            switch (mn) {
                case "P1":
                    processP1(scanner);
                    break;
                case "P2":
                    processP2(scanner);
                    break;
                case "P3":
                    processP3(scanner);
                    break;
            }

            DrawingPanel dp = new DrawingPanel(image, w, h);
            this.add(dp);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(w,h);
            this.setVisible(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void processP1(Scanner scanner) {
        for (int i = 0; i < w * h; i++) {
            image[i] = scanner.nextInt() == 0 ? Color.WHITE : Color.BLACK;
        }
    }

    private void processP2(Scanner scanner) {
        int maxValue = scanner.nextInt();

        for (int i = 0; i < w * h; i++) {
            int grayScale = scanner.nextInt();
            image[i] = new Color(grayScale, grayScale, grayScale);
        }
    }

    private void processP3(Scanner scanner) {
        int maxValue = scanner.nextInt();

        for (int i = 0; i < w * h; i++) {
            int red = scanner.nextInt();
            int green = scanner.nextInt();
            int blue = scanner.nextInt();
            image[i] = new Color(red, green, blue);
        }
    }
}

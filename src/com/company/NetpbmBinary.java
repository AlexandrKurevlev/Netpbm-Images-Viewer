package com.company;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

public class NetpbmBinary extends JFrame{
    private int ws;
    private int w;
    private int h;
    private Color[] image;
    private FileInputStream fis;

    NetpbmBinary(String absolutePath){
        try {
            fis = new FileInputStream(absolutePath);

            String mn = getMagicNumber(fis);
            System.out.println("Magic Number: " + mn);

            skipWhitespace();

            w = readNumber();
            System.out.println("Width: " + w);

            skipWhitespace();

            h = readNumber();
            System.out.println("Height: " + h);

            int numOfPixels = w*h;
            image = new Color[numOfPixels];

            switch (mn) {
                case "P4":
                    processP4();
                    break;
                case "P5":
                    processP5();
                    break;
                case "P6":
                    processP6();
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DrawingPanel dp = new DrawingPanel(image, w, h);
        this.add(dp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(w,h);
        this.setVisible(true);
    }

    public static String getMagicNumber(FileInputStream fis) {
        byte [] magicNum = new byte[2];
        try {
            fis.read(magicNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(magicNum);
    }

    private void skipWhitespace() {
        try {
            ws = fis.read();
            while(Character.isWhitespace(ws))
                ws = fis.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int readNumber() {
        String wstr = "";
        try {
            while (!Character.isWhitespace(ws)) {
                wstr = wstr + (ws - '0');
                ws = fis.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(wstr);
    }

    private void processP4() {
        int pixelIndex = 0;
        int lineIndex = 0;

        while (pixelIndex < w * h) {
            int b = 0;
            try {
                b = fis.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < 8 && pixelIndex < w * h; j++) {

                //End of the row is reached, skip all bites that left
                if (lineIndex >= w) {
                    lineIndex = 0;
                    break;
                } else {
                    lineIndex++;
                }
                image[pixelIndex] = ((b >> (7 - j)) & 1) == 0 ? Color.WHITE : Color.BLACK;
                pixelIndex++;
            }
        }
    }

    private void processP5() {
        skipWhitespace();
        try {
            int maxValue = fis.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        skipWhitespace();

        for (int i = 0; i < w * h; i++) {
            try {
                int grayScale = fis.read();
                image[i] = new Color(grayScale, grayScale, grayScale);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processP6() {
        skipWhitespace();

        for (int i = 0; i < w * h; i++) {
            try {
                int red = fis.read();
                int green = fis.read();
                int blue = fis.read();
                image[i] = new Color(red, green, blue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {

    private final Color[] image;
    private final int width;
    private final int height;
    private final BufferedImage imageBI;
    private final IterationInformation iterationInformation;

    public DrawingPanel(Color[] image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;

        imageBI = new BufferedImage(width, height, Image.SCALE_DEFAULT);
        iterationInformation = new IterationInformation(1, height / 5);

        Timer timer = new Timer(1000, new LoadImagePartiallyListener());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageBI, 0, 0, this);
    }

    private void drawIteration(BufferedImage imageBI, IterationInformation iterationInformation) {
        for (int y = height / 5 * (iterationInformation.step - 1); y < iterationInformation.nextHeight; y++) {
            for (int x = 0; x < width; x++) {
                imageBI.setRGB(x, y, image[y * width + x].getRGB());
            }
        }
        iterationInformation.step++;

        if (iterationInformation.step == 5) {
            iterationInformation.nextHeight = height;
        } else {
            iterationInformation.nextHeight = height / 5 * iterationInformation.step;
        }

        repaint();
    }

    private class LoadImagePartiallyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawIteration(imageBI, iterationInformation);

            if (iterationInformation.step > 5) {
                Timer timer = (Timer)e.getSource();
                timer.stop();
            }
        }
    }

    private class IterationInformation {
        int step;
        int nextHeight;

        IterationInformation(int step, int nextHeight) {
            this.step = step;
            this.nextHeight = nextHeight;
        }
    }
}

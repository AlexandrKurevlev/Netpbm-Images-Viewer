package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setFileFilter(new FileNameExtensionFilter("Netpbm files", "pgm", "ppm", "pbm"));

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            String absolutePath = selectedFile.getAbsolutePath();

            System.out.println("Selected file: " + absolutePath);
            FileInputStream fis = new FileInputStream(absolutePath);
            String magicNumber = NetpbmBinary.getMagicNumber(fis);
            if (magicNumber.equals("P1") || magicNumber.equals("P2") || magicNumber.equals("P3")) {
                new NetpbmASCII(absolutePath);
            } else if (magicNumber.equals("P4") || magicNumber.equals("P5") || magicNumber.equals("P6")) {
                new NetpbmBinary(absolutePath);
            }

        }
    }
}

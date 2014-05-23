package ru.nevsky_company.coder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PixelArray {

    public PixelArray(String nameImage) throws IOException {
        loadImage(nameImage);
    }


    public double[][][] getyCbCrArray() {
        return yCbCrArray;
    }


    public int getHeight() {
        return height;
    }


    public int getWidth() {
        return width;
    }


    /**
     *
     * @param nameImage . The method load image by nameImage
     * @throws IOException
     * PixelArray.class.getResource(nameImage + ".bmp")
     */
    private void loadImage(String nameImage) throws IOException {
        img = ImageIO.read(new File(nameImage));
        width = img.getWidth();
        height = img.getHeight();
        convertPixelToRGB();
    }

    /**
     * In this method we convert each pixel value to RGB
     */
    private void convertPixelToRGB() {
        rgbArray = new int[height][width][3];
        int rgb;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                rgb = img.getRGB(col, row);
                rgbArray[row][col][0] = (rgb >> 16) & 0xff; //red
                rgbArray[row][col][1] = (rgb >> 8) & 0xff; // green
                rgbArray[row][col][2] = rgb & 0xff; // blue
            }
        }
        convertRGBtoYCbCr();
    }

    /**
     * The method convert RGB values to YCbCr values
     */
    private void convertRGBtoYCbCr() {
        int R;
        int G;
        int B;
        yCbCrArray = new double[height][width][3];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                R = rgbArray[row][col][0];
                G = rgbArray[row][col][1];
                B = rgbArray[row][col][2];
                yCbCrArray[row][col][0] = (0.299 * R + 0.587 * G + 0.114 * B); // Y channel
                yCbCrArray[row][col][1] = (128 - (0.168736 * R) - (0.331264 * G) + 0.5 * B); // Cb channel
                yCbCrArray[row][col][2] = (128 + (0.5 * R) - (0.418688 * G) - 0.081312 * B); // Cr channel
            }
        }
    }

    private BufferedImage img;
    private int rgbArray[][][];
    private double yCbCrArray[][][];
    private int height;
    private int width;
}

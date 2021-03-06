package ru.nevsky_company.decode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

public class DePixelArray {

    public DePixelArray(int yCbCrArray[][][], int height) {
        this.yCbCrArray = yCbCrArray;
        this.height = height;
        width = height;
        rgbArray = new int[height][height];
    }

    public void runConversion() throws IOException {
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                convertYCBCRtoRGB(yCbCrArray[row][col][0],
                        yCbCrArray[row][col][1],
                        yCbCrArray[row][col][2]);
                rgbArray[row][col] = getPixelValue(R, G, B);
            }
        }
        try {
            writeImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected int[][] getRgbArray() {
        return rgbArray;
    }

    private void convertYCBCRtoRGB(double Y, double Cb, double Cr) {
        R = round(Y + (Cr - 128) * 1.402);
        G = round(Y - (Cb - 128) * 0.34414 - (Cr - 128) * 0.71414);
        B = round(Y + 1.772 * (Cb - 128));
        R = R > 255 ? 255 : (R < 0 ? 0 : R);
        G = G > 255 ? 255 : (G < 0 ? 0 : G);
        B = B > 255 ? 255 : (B < 0 ? 0 : B);
    }

    private int round(double value) {
        return (int)(value);
    }

    private void writeImage() throws IOException {
        int pImage[] = new int[height * height];
        for (int row = 0, count = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pImage[count] = rgbArray[row][col];
                count++;
            }
        }
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, width, height, pImage, 0, width);
        ImageIO.write(img, "bmp", new FileOutputStream("RESULT.bmp"));  // save image
    }

    private int getPixelValue(int r, int g, int b) {
        int argb = 0;
        argb += (b & 0xff); // blue channel
        argb += ((g & 0xff) << 8); // green channel
        argb += ((r & 0xff) << 16); // red channel
        return (0xff000000 | r << 16 | g << 8 | b);
    }

    private BufferedImage img;
    private int rgbArray[][];
    private int yCbCrArray[][][];
    private int height;
    private int width;
    private int R;
    private int G;
    private int B;
}

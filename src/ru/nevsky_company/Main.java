package ru.nevsky_company;

import ru.nevsky_company.coder.EncoderJPEG;
import ru.nevsky_company.decode.DecoderJPEG;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("CODE JPEG:");
        EncoderJPEG encoderJPEG = new EncoderJPEG("imageEarth.bmp");
        encoderJPEG.runAlgorithm();
        double array[][][] = encoderJPEG.getYCbCr();
        int SIZE = encoderJPEG.getSizePicture();

        System.out.println("DECODE JPEG:");
        DecoderJPEG decoderJPEG = new DecoderJPEG(array, SIZE);
        decoderJPEG.runAlgrithm();
        /*
        File file = new File("imageEarth.bmp");
        BufferedImage img = ImageIO.read(file);
        final int WIDTH = img.getWidth();
        final int HEIGHT = img.getHeight();
        System.out.println("WIDTH = " + WIDTH);
        System.out.println("HEIGHT = " + HEIGHT);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int pixel = img.getRGB(i, j);
                System.out.printf("%6d ", pixel);
            }
            System.out.println();
        }
        */
    }
}

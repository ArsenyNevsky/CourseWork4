package ru.nevsky_company;

import ru.nevsky_company.coder.EncoderJPEG;
import ru.nevsky_company.decode.DecoderJPEG;

import java.io.IOException;

public class Main {

    public static long fibb(long n) {
        long b = 0;
        long secondValue = 1;
        long thirdValue;
        long a = 0;
        for (int i = 2; i <= n; i++) {
            a = b;
            thirdValue = b + secondValue;
            b = secondValue;
            secondValue = thirdValue;
        }
        System.out.println("3 last " + a);
        System.out.println("2 last " + b);
        return (a + b) % 10;
    }

    public static void main(String[] args) throws IOException {

        System.out.println("CODE JPEG:");
        EncoderJPEG encoderJPEG = new EncoderJPEG("cotee.jpg");
        encoderJPEG.runAlgorithm();
        double[] array = encoderJPEG.getArrayForZigZag();
        //double array[][][] = encoderJPEG.getYCbCr();
        int SIZE = encoderJPEG.getSizePicture();

        System.gc();
        System.out.println();

        //DecoderJPEG decoderJPEG = new DecoderJPEG(array, SIZE);
        DecoderJPEG decoderJPEG = new DecoderJPEG(array, SIZE);
        decoderJPEG.runAlgrithm();
    }
}

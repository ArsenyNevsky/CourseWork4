package ru.nevsky_company;

import ru.nevsky_company.HuffmanZip.Huffman;
import ru.nevsky_company.coder.EncoderJPEG;
import ru.nevsky_company.decode.DecoderJPEG;

import java.io.IOException;


public class Main {


    public static void main(String[] args) throws IOException {

        EncoderJPEG encoderJPEG = new EncoderJPEG("forest.bmp");
        encoderJPEG.run();
        int[] array = encoderJPEG.getArrayForZigZag();
        int SIZE = encoderJPEG.getSizePicture();

        double array1[][][] = encoderJPEG.getyCbCr();

        Huffman huffman = new Huffman();
        huffman.compress(array);

        DecoderJPEG decoderJPEG = new DecoderJPEG(SIZE);
        decoderJPEG.run();
        int array2[][][] = decoderJPEG.getYCbCr();

        final int N = SIZE / 3;
        System.out.println("\nRESULT");
        double summ = 0;
        double result = 0;
        double koeff = 0;
        double temp = 0;
        final int HEIGHT = (int)(N / Math.sqrt(N));

        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    temp = Math.pow((array2[i][j][k] - array1[i][j][k]), 2);
                    summ += temp;
                }
            }
            koeff = (Math.sqrt(summ / (N * N)));
            result += koeff;
            System.out.println("Error coefficient for" + k + " component = " + koeff);
        }
        System.out.printf("Error coefficient for image = %.2f", result);
    }
}

package ru.nevsky_company;

import ru.nevsky_company.coder.EncoderJPEG;
import ru.nevsky_company.decode.DecoderJPEG;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("CODE JPEG:");
        EncoderJPEG encoderJPEG = new EncoderJPEG();
        encoderJPEG.runAlgorithm();
        //double[][][] yCbCr = encoderJPEG.getYCbCr();
        //final int SIZE = encoderJPEG.getSizePicture();
        double array[] = encoderJPEG.getArrayForHuffman();
        int SIZE = array.length;

        /*
        for (int i = 0; i < result.length; i++) {
            System.out.printf("%2d ", result[i]);
        } */
        System.out.println();
        System.out.println("\n--------------------------------------------------------------------------------\n" +
                "--------------------------------------------------------------------------------" +
                "\n--------------------------------------------------------------------------------\n" +
                "--------------------------------------------------------------------------------");

        System.out.println("DECODE JPEG:");
        //System.out.println("Length of ycbcr = " + yCbCr.length);
        //DecoderJPEG decoderJPEG = new DecoderJPEG(result, result.length);
        //DecoderJPEG decoderJPEG = new DecoderJPEG(yCbCr, SIZE);
        DecoderJPEG decoderJPEG = new DecoderJPEG(array, SIZE);
        decoderJPEG.runAlgrithm();
    }
}

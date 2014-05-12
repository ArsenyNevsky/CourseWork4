package ru.nevsky_company;

import ru.nevsky_company.coder.EncoderJPEG;
import ru.nevsky_company.decode.DecoderJPEG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Main {

    private static void sort(int[] array) {
        ArrayList a = new ArrayList();
        for (int i = 0; i < array.length - 1; i++) {
            a.add(array[i]);
        }
        Collections.sort(a);
        System.out.println("Min = " + a.get(0));
        System.out.println("Max = " + a.get(a.size() - 1));
    }


    public static void main(String[] args) throws IOException {

        System.out.println("CODE JPEG:");

        EncoderJPEG encoderJPEG = new EncoderJPEG("imageEarth.bmp");
        encoderJPEG.run();
        int[] array = encoderJPEG.getArrayForZigZag();

        int SIZE = encoderJPEG.getSizePicture();

        System.gc();

        DecoderJPEG decoderJPEG = new DecoderJPEG(array, SIZE);
        decoderJPEG.run();

    }
}

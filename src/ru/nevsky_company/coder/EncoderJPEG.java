package ru.nevsky_company.coder;

import java.io.IOException;

public class EncoderJPEG {

    public EncoderJPEG(String nameImage) throws IOException {
        System.out.println("ENCODE JPEG");
        pixelArray = new PixelArray(nameImage);
        codecDCT = new DCT();
        quant = new Quant();
        zigZag = new ZigZag();
    }

    /**
     * Проходим трижды по матрице YCbCr. Сначала всю работу от начала до конца проводим
     * с компонентой Y. Затем проводим аналогичную работу с компонентой Cb, а после с Cr.
     * В результате записываем все получившиеся данные в один массив
     */
    public void runAlgorithm() {
        System.out.println("RUN CODE JPEG:");
        yCbCr = pixelArray.getyCbCrArray();
        HEIGHT = pixelArray.getHeight();
        WIDTH = pixelArray.getWidth();
        arrayForZigZag = new double[3 * HEIGHT * WIDTH];
        double arrayPix[][] = new double[SIZE][SIZE];

        int row = 0;
        int col = 0;
        int x = 0;
        int y = 0;
        final int COUNT_LAYER = 3;
        for (int element = 0; element < COUNT_LAYER; element++) {
            for (int i = 0; i < HEIGHT; i += STEP) {
                row = i + STEP;
                col = STEP;
                for (int j = 0; j < WIDTH; j += STEP) {

                    for (int k = i; k < row; k++) {
                        for (int s = j; s < col; s++) {
                            arrayPix[x][y] = yCbCr[k][s][element];
                            y++;
                        }
                        y = 0;
                        ++x;
                    }
                    x = 0;
                    y = 0;
                    arrayPix = runWavelet(arrayPix);
                    arrayPix = runQuant(arrayPix);
                    runHideZigZag(arrayPix);

                    for (int k = i; k < row; k++) {
                        for (int s = j; s < col; s++) {
                            yCbCr[k][s][element] = arrayPix[x][y];
                            y++;
                        }
                        y = 0;
                        ++x;
                    }
                    x = 0;
                    y = 0;
                    col += STEP;
                }
            }
        }
        System.out.println("END CODE JPEG:\n");
    }

    public double[][][] getYCbCr() {
        return yCbCr;
    }

    public int getSizePicture() {
        return HEIGHT * HEIGHT * 3;
    }

    public double[] getArrayForZigZag() {
        return arrayForZigZag;
    }

    private double[][] runWavelet(double array[][]) {
        return codecDCT.directHoaar(array);
    }

    private double[][] runQuant(double f[][]) {
        resultArray = quant.quant(f);
        return resultArray;
    }

    private void runHideZigZag(double array[][]) {
        convolution = zigZag.getHideZigZagArray(array, SIZE);
        for (int i = 0; i < STEP * STEP; i++) {
            arrayForZigZag[position++] = convolution[i];
        }
    }

    private int position = 0;
    private int HEIGHT;
    private int WIDTH;
    private final int SIZE = 8;
    private final int STEP = 8;
    private double yCbCr[][][];
    private double resultArray[][];
    private double convolution[];
    private double arrayForZigZag[];
    private PixelArray pixelArray;
    private DCT codecDCT;
    private Quant quant;
    private ZigZag zigZag;
}

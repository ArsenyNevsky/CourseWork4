package ru.nevsky_company.coder;

import java.io.IOException;


public class EncoderJPEG {

    public EncoderJPEG(String nameImage) throws IOException {
        System.out.println("\tENCODER JPEG");
        pixelArray = new PixelArray(nameImage);
        enWavelet = new EnWavelet();
        quant = new Quant();
        zigZag = new ZigZag();
        convolution = new int[SIZE * SIZE];
    }

    public void run() {
        yCbCr = pixelArray.getyCbCrArray();
        HEIGHT = pixelArray.getHeight();
        WIDTH = pixelArray.getWidth();

        double hideLay1[] = hideLay(getLay(0)); // свернули слои
        double hideLay2[] = hideLay(getLay(1));
        double hideLay3[] = hideLay(getLay(2));

        hideLay1 = runWavelet(hideLay1); // к каждому слою применили вейвлеты
        hideLay2 = runWavelet(hideLay2);
        hideLay3 = runWavelet(hideLay3);

        expand(hideLay1, 0);
        expand(hideLay2, 1);
        expand(hideLay3, 2);

        QUANT();
        System.out.println("\tEND ENCODER");
    }

    public int getSizePicture() {
        return HEIGHT * HEIGHT * 3;
    }

    public int[] getArrayForZigZag() {
        return arrayForZigZag;
    }

    private double[][] getLay(int numberLay) {
        double lay[][] = new double[HEIGHT][HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                lay[i][j] = yCbCr[i][j][numberLay];
            }
        }
        return lay;
    }

    private double[] hideLay(double[][] lay) {
        double hiddenLay[] = new double[HEIGHT * HEIGHT];
        int k = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                hiddenLay[k++] = lay[i][j];
            }
        }
        return hiddenLay;
    }

    private void expand(double[] array, int lay) {
        int k = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                yCbCr[i][j][lay] = array[k++];
            }
        }
    }

    private void QUANT() {
        position = 0;
        double arrayPix[][] = new double[SIZE][SIZE];
        arrayForZigZag = new int[3 * HEIGHT * WIDTH];
        int quant[][];

        int row;
        int col;
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
                    quant = runQuant(arrayPix);
                    runHideZigZag(quant);
                    col += STEP;
                }
            }
        }
        System.out.println("END QUANT JPEG:\n");
    }

    private double[] runWavelet(double array[]) {
        return enWavelet.directHoaar(array, HEIGHT);
    }

    private int[][] runQuant(double f[][]) {
        resultArray = quant.quant(f);
        return resultArray;
    }

    private void runHideZigZag(int array[][]) {
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
    private int resultArray[][];
    private int convolution[];
    private int arrayForZigZag[];
    private PixelArray pixelArray;
    private EnWavelet enWavelet;
    private Quant quant;
    private ZigZag zigZag;
}

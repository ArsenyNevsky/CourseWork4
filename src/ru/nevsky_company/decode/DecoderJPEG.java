package ru.nevsky_company.decode;

import java.io.IOException;

import static java.lang.System.*;

public class DecoderJPEG {

    public DecoderJPEG(double arrayForHuffman[], int size) {
        out.println("DECODE JPEG:");
        this.arrayForHuffman = arrayForHuffman;
        SIZE_INPUT_ARRAY = size;
        SIZE_COLOR_BLOCK = SIZE_INPUT_ARRAY / 3;
        arrayAfterZigZag = new double[SIZE_BLOCK][SIZE_BLOCK];
        HEIGHT = (int)Math.sqrt(SIZE_COLOR_BLOCK);
        deZigZag = new DeZigZag();
        deQuant = new DeQuant();
        deIDCT = new DeIDCT();
    }

    public DecoderJPEG(double[][][] yCbCr, int size) {
        out.println("DECODE JPEG:");
        this.yCbCr = yCbCr;
        SIZE_INPUT_ARRAY = size;
        SIZE_COLOR_BLOCK = SIZE_INPUT_ARRAY / 3;
        arrayAfterZigZag = new double[SIZE_BLOCK][SIZE_BLOCK];
        HEIGHT = (int)Math.sqrt(SIZE_COLOR_BLOCK);
        deIDCT = new DeIDCT();
        deQuant = new DeQuant();
    }

    public void runAlgrithm() throws IOException {
        fillYCbCr();

        int row;
        int col;
        int x = 0;
        int y = 0;
        final int COUNT_LAYER = 3;
        double arrayPix[][] = new double[SIZE_BLOCK][SIZE_BLOCK];

        for (int element = 0; element < COUNT_LAYER; element++) {
            out.println("\n\nPRINT VALUES OF YCbCr FOR CHANNEL " + element + "\n\n");
            for (int i = 0; i < HEIGHT; i += STEP) {
                row = i + STEP;
                col = 0;
                for (int j = 0; j < HEIGHT; j += STEP) {
                    col += STEP;
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
                }
            }
        }
        dePixelArray = new DePixelArray(yCbCr, HEIGHT);
        try {
            dePixelArray.runConversion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillYCbCr() throws IOException {
        yCbCr = new double[HEIGHT][HEIGHT][3];
        out.println("START ALGORITHM:");
        int positionY = 0;
        int positionCb = SIZE_COLOR_BLOCK;
        int positionCr = 2 * positionCb;

        out.println("\t\tRUN CONVERSION");
        doConversion(positionY, 0);
        doConversion(positionCb, 1);
        doConversion(positionCr, 2);
    }

    private void doConversion(int startPosition, int stepAlgorithm) {
        out.println("\t\tStep conversion = " + stepAlgorithm);
        double block[] = new double[BLOCK];
        int col = 0;
        int row;
        int x = 0;
        int y = 0;
        int position = startPosition;
        for (int i = 0; i < HEIGHT; i += SIZE_BLOCK) {
            row = i + STEP;
            for (int j = 0; j < HEIGHT; j += SIZE_BLOCK) {
                col += STEP;

                int index = 0;
                for (int k = position; k < position + BLOCK; k++) {
                    block[index++] = arrayForHuffman[k];
                }
                position += BLOCK;
                arrayAfterZigZag = deZigZag.getArray(block);
                arrayAfterQuant = runQuant(arrayAfterZigZag);

                for (int k = i; k < row; k++) {
                    for (int s = j; s < col; s++) {
                        yCbCr[k][s][stepAlgorithm] = arrayAfterQuant[x][y];
                        y++;
                    }
                    y = 0;
                    ++x;
                }
                x = 0;
                y = 0;
            }
            col = 0;
        }
        out.println("END FILL LAYOUT " + STEP);
    }

    private double[][] runWavelet(double F[][]) {
        return deIDCT.directHoaar(F);
    }

    private double[][] runQuant(double[][] arrayAfterWavelet) {
        return deQuant.quant(arrayAfterWavelet);
    }


    private double yCbCr[][][];
    private double arrayForHuffman[];
    private final int SIZE_INPUT_ARRAY;
    private int SIZE_COLOR_BLOCK;
    private int HEIGHT;
    private final int STEP = 8;
    private final int SIZE_BLOCK = 8;
    private final int BLOCK = SIZE_BLOCK * SIZE_BLOCK;
    private double arrayAfterZigZag[][];
    private double arrayAfterQuant[][];
    private DeZigZag deZigZag;
    private DeQuant deQuant;
    private DeIDCT deIDCT;
    private DePixelArray dePixelArray;
}
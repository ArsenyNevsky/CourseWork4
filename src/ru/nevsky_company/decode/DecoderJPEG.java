package ru.nevsky_company.decode;

import java.io.IOException;

public class DecoderJPEG {

    public DecoderJPEG(double arrayForHuffman[], int size) {
        System.out.println("\tSTART INITIALIZATION DECODER'S OBJECT:");
        this.arrayForHuffman = arrayForHuffman;
        SIZE_INPUT_ARRAY = size;
        SIZE_COLOR_BLOCK = SIZE_INPUT_ARRAY / 3;
        arrayAfterZigZag = new double[SIZE_BLOCK][SIZE_BLOCK];
        HEIGHT = (int)Math.sqrt(SIZE_COLOR_BLOCK);
        WIDTH = HEIGHT;
        deIDCT = new DeIDCT();
        deZigZag = new DeZigZag();
        deQuant = new DeQuant();
        System.out.println("\tEND INITIALIZATION DECODER'S OBJECT");
    }

    public DecoderJPEG(double[][][] yCbCr, int length) {
        this.yCbCr = yCbCr;
        SIZE_INPUT_ARRAY = length;
        SIZE_COLOR_BLOCK = SIZE_INPUT_ARRAY / 3;  // one of layers
        HEIGHT = (int)Math.sqrt(SIZE_COLOR_BLOCK); // height of image
        arrayAfterZigZag = new double[SIZE_BLOCK][SIZE_BLOCK];
        WIDTH = HEIGHT; // width of image
        deIDCT = new DeIDCT();
    }

    public void runAlgrithm() throws IOException {
        //fillYCbCr();
        int row = 0;
        int col = 0;
        int x = 0;
        int y = 0;
        final int COUNT_LAYER = 3;
        double arrayPix[][] = new double[SIZE_BLOCK][SIZE_BLOCK];
        for (int element = 0; element < COUNT_LAYER; element++) {
            System.out.println("\n\nPRINT VALUES OF YCbCr FOR CHANNEL " + element + "\n\n");
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
                            //System.out.printf("%3f ", yCbCr[k][s][element]);
                            y++;
                        }
                        //System.out.println();
                        y = 0;
                        ++x;
                    }
                    //System.out.println();
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
        System.out.println("\tSTART ALGORITHM:");
        int positionY = 0;
        int positionCb = SIZE_COLOR_BLOCK;
        int positionCr = 2 * positionCb;
        yCbCr = new double[SIZE_COLOR_BLOCK][SIZE_COLOR_BLOCK][3];

        double block[] = new double[BLOCK];

        System.out.println("\t\tRUN CONVERSION");
        doConversion(positionY, positionCb, block, 0);
        doConversion(positionCb, positionCr, block, 1);
        doConversion(positionCr, SIZE_INPUT_ARRAY, block, 2);
    }

    private void doConversion(int startPosition, int endPosition,
                              double block[], int stepAlgorithm) {
        System.out.println("\t\tStep conversion = " + stepAlgorithm);
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        int position = startPosition;
        for (int i = 0; i < HEIGHT; i += STEP) {
            row = i + STEP;
            col = 0;
            for (int j = 0; j < HEIGHT; j += STEP) {
                col += STEP;

                int index = 0;
                for (int k = position; k < position + BLOCK; k++) {
                    block[index++] = arrayForHuffman[k];
                }
                position += BLOCK;
                //arrayAfterZigZag = deZigZag.getArray(block);
                //arrayAfterQuant = deQuant.quant(arrayAfterZigZag);

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
        }
    }

    private double[][] runWavelet(double F[][]) {
        //return deIDCT.idct(F);
        return deIDCT.directHoaar(F);
    }


    private double yCbCr[][][];
    private double arrayForHuffman[];
    private final int SIZE_INPUT_ARRAY;
    private int SIZE_COLOR_BLOCK;
    private int WIDTH;
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
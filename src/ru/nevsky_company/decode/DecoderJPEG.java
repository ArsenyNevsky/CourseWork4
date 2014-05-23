package ru.nevsky_company.decode;

import ru.nevsky_company.HuffmanZip.Huffman;

import java.io.IOException;

public class DecoderJPEG {

    public DecoderJPEG(int size) {
        huffman = new Huffman();
        huffman.decompress();
        this.arrayForHuffman = getArrayForHuffman();
        SIZE_INPUT_ARRAY = size;
        SIZE_COLOR_BLOCK = SIZE_INPUT_ARRAY / 3;
        arrayAfterZigZag = new int[SIZE_BLOCK][SIZE_BLOCK];
        HEIGHT = (int)Math.sqrt(SIZE_COLOR_BLOCK);
        deZigZag = new DeZigZag();
        deQuant = new DeQuant();
        deWavelet = new DeWavelet();
    }


    public void run() throws IOException {
        fillYCbCr();

        int hideLay1[] = hideLay(getLay(0));
        int hideLay2[] = hideLay(getLay(1));
        int hideLay3[] = hideLay(getLay(2));

        hideLay1 = runWavelet(hideLay1);
        hideLay2 = runWavelet(hideLay2);
        hideLay3 = runWavelet(hideLay3);

        expand(hideLay1, 0);
        expand(hideLay2, 1);
        expand(hideLay3, 2);
        dePixelArray = new DePixelArray(yCbCr, HEIGHT);
        try {
            dePixelArray.runConversion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int[][][] getYCbCr() {
        return yCbCr;
    }


    private int[][] getLay(int numberLay) {
        int lay[][] = new int[HEIGHT][HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                lay[i][j] = yCbCr[i][j][numberLay];
            }
        }
        return lay;
    }


    private int[] hideLay(int[][] lay) {
        int hiddenLay[] = new int[HEIGHT * HEIGHT];
        int k = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                hiddenLay[k++] = lay[i][j];
            }
        }
        return hiddenLay;
    }


    private void expand(int[] array, int lay) {
        int k = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                yCbCr[i][j][lay] = array[k++];
            }
        }
    }


    private void fillYCbCr() throws IOException {
        yCbCr = new int[HEIGHT][HEIGHT][3];
        int positionY = 0;
        int positionCb = SIZE_COLOR_BLOCK;
        int positionCr = 2 * positionCb;

        doConversion(positionY, 0);
        doConversion(positionCb, 1);
        doConversion(positionCr, 2);
    }


    private void doConversion(int startPosition, int stepAlgorithm) {
        int block[] = new int[BLOCK];
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
                arrayAfterZigZag = deZigZag.getIntegerArray(block);
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
    }


    private int[] runWavelet(int F[]) {
        return deWavelet.directWavelet(F, HEIGHT);
    }


    private int[][] runQuant(int[][] arrayAfterWavelet) {
        return deQuant.quant(arrayAfterWavelet);
    }


    private int[] getArrayForHuffman() {
        String resultDecompression = huffman.getResultDecompress();
        char array[] = resultDecompression.toCharArray();
        arrayForHuffman = new int[array.length];
        int i = 0;
        int temp = 0;
        for (char symbol : array) {
            temp = (int)symbol - 150;
            arrayForHuffman[i++] = temp;
        }
        return arrayForHuffman;
    }


    private int yCbCr[][][];
    private int arrayForHuffman[];
    private final int SIZE_INPUT_ARRAY;
    private int SIZE_COLOR_BLOCK;
    private int HEIGHT;
    private final int STEP = 8;
    private final int SIZE_BLOCK = 8;
    private final int BLOCK = SIZE_BLOCK * SIZE_BLOCK;
    private int arrayAfterZigZag[][];
    private int arrayAfterQuant[][];
    private DeZigZag deZigZag;
    private DeQuant deQuant;
    private DeWavelet deWavelet;
    private DePixelArray dePixelArray;
    private Huffman huffman;
}
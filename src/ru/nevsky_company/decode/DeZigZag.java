package ru.nevsky_company.decode;

public class DeZigZag {

    public double[][] getArray(double f[]) {
        int i = 1;
        int j = 1;
        int k = 0;
        double arrayCoeff[][] = new double[SIZE_BLOCK][SIZE_BLOCK];
        for (int element = 0; element < SIZE_BLOCK * SIZE_BLOCK; element++) {
            arrayCoeff[i - 1][j - 1] = f[k];
            k++;
            if ((i + j) % 2 == 0) {
                // Even stripes
                if (j < SIZE_BLOCK) {
                    j++;
                } else {
                    i += 2;
                }
                if (i > 1)
                    i--;
            } else {
                // Odd stripes
                if (i < SIZE_BLOCK)
                    i++;
                else
                    j += 2;
                if (j > 1)
                    j--;
            }
        }
        //arrayCoeff[SIZE * SIZE - 1] = f[SIZE - 1][SIZE - 1];
        return arrayCoeff;
    }
    private final int SIZE_BLOCK = 8;
}

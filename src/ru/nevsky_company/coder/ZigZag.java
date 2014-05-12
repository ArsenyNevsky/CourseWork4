package ru.nevsky_company.coder;

public class ZigZag {

    public int[] getHideZigZagArray(int[][] f, final int SIZE) {
        int i = 1;
        int j = 1;
        int k = 0;
        int arrayCoeff[] = new int[SIZE * SIZE];
        for (int element = 0; element < SIZE * SIZE; element++) {
            arrayCoeff[k] = f[i - 1][j - 1];
            k++;
            if ((i + j) % 2 == 0) {
                // Even stripes
                if (j < SIZE) {
                    j++;
                } else {
                    i += 2;
                }
                if (i > 1)
                    i--;
            } else {
                // Odd stripes
                if (i < SIZE)
                    i++;
                else
                    j += 2;
                if (j > 1)
                    j--;
            }
        }
        arrayCoeff[SIZE * SIZE - 1] = f[SIZE - 1][SIZE - 1];
        return arrayCoeff;
    }

    public double[] getHideZigZagArray(double[][] f, final int SIZE) {
        int i = 1;
        int j = 1;
        int k = 0;
        double arrayCoeff[] = new double[SIZE * SIZE];
        for (int element = 0; element < SIZE * SIZE; element++) {
            arrayCoeff[k] = f[i - 1][j - 1];
            k++;
            if ((i + j) % 2 == 0) {
                // Even stripes
                if (j < SIZE) {
                    j++;
                } else {
                    i += 2;
                }
                if (i > 1)
                    i--;
            } else {
                // Odd stripes
                if (i < SIZE)
                    i++;
                else
                    j += 2;
                if (j > 1)
                    j--;
            }
        }
        arrayCoeff[SIZE * SIZE - 1] = f[SIZE - 1][SIZE - 1];
        return arrayCoeff;
    }

    public int[][] fillArray(final int size) {
        int[][] data = new int[size][size];
        int i = 1;
        int j = 1;
        for (int element = 0; element < size * size; element++) {
            data[i - 1][j - 1] = element;
            if ((i + j) % 2 == 0) {
                // Even stripes
                if (j < size)
                    j++;
                else
                    i+= 2;
                if (i > 1)
                    i--;
            } else {
                // Odd stripes
                if (i < size)
                    i++;
                else
                    j+= 2;
                if (j > 1)
                    j--;
            }
        }
        return data;
    }
}

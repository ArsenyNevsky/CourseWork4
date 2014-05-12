package ru.nevsky_company.decode;

class DeQuant {

    public int[][] quant(int array[][]) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                array[i][j] = round((array[i][j] * tableQuant[i][j]));
            }
        }
        return array;
    }

    private int round(int value) {
        return (value);
    }

    private final int[][] tableQuant = {
            {3, 5, 7, 9, 11, 13, 15, 17},
            {5, 7, 9, 11, 13, 15, 17, 19},
            {7, 9, 11, 13, 15, 17, 19, 21},
            {9, 11, 13, 15, 17, 19, 21, 23},
            {11, 13, 15, 17, 19, 21, 23, 25},
            {13, 15, 17, 19, 21, 23, 25, 27},
            {15, 17, 19, 21, 23, 25, 27, 29},
            {17, 19, 21, 23, 25, 27, 29, 31}
    };

    private final int SIZE = 8;
}

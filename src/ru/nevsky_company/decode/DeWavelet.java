package ru.nevsky_company.decode;

import ru.nevsky_company.coder.ZigZag;

class DeWavelet {

    public DeWavelet(int F[][], final int N) {
        this.N = N;
        zigZag = new ZigZag();
        zigZag1 = new DeZigZag();
    }

    public DeWavelet() {
        N = 8;
        zigZag = new ZigZag();
        zigZag1 = new DeZigZag();
    }

    public int[][] directHoaar(int f[][]) {
        int c[] = zigZag.getHideZigZagArray(f, N);
        int result[] = new int[N * N];
        for (int i = 0; i < N * N / 2; i++) {
            result[2 * i] = ((c[2 * i] + c[2 * i + 1]));
            result[2 * i + 1] = ((c[2 * i] - c[2 * i + 1]));
        }
        int array[][] = zigZag1.getIntegerArray(result); // there was c
        return array;
    }

    public int[] directWavelet(int c[], int h) {
        int result[] = new int[h * h];
        for (int i = 0; i < (h * h) / 2; i++) {
            result[2 * i] = ((c[2 * i] + c[2 * i + 1]));
            result[2 * i + 1] = ((c[2 * i] - c[2 * i + 1]));
        }
        return result;
    }

    public int[][] idct(int F[][]) {
        double tempX[][] = new double[N][N];
        double tempY[][] = new double[N][N];
        int f[][] = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tempY[i][j] = (double)F[i][j];
            }
        }

        idct_direct(tempY, tempX);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                f[i][j] = (int)(Math.floor(tempX[i][j] + 0.5));
            }
        }
        return f;
    }

    private void idct_direct(double F[][], double f[][]) {

        double a[] = new double[N];
        a[0] = sqrt(1. / N);
        for (int i = 1; i < N; i++) {
            a[i] = sqrt(2. / N);
        }

        double summ = 0;
        double koeff = 0;
        for (short i = 0; i < N; i++) {
            for (short j = 0; j < N; j++) {
                summ = 0.;
                for (short u = 0; u < N; u++) {
                    for (short v = 0; v < N; v++) {
                        koeff = cos((2 * j + 1) * v * PI / (2 * N)) *
                                cos((2 * i + 1) * u * PI / (2 * N));
                        summ += a[u] * a[v] * F[u][v] * koeff;
                    }
                    f[i][j] = summ;
                }
            }
        }
    }

    private double cos(double x) {
        return Math.cos(x);
    }

    private double sqrt(double x) {
        return Math.sqrt(x);
    }

    private int N = 8;
    private final double PI = Math.PI;
    private ZigZag zigZag;
    private DeZigZag zigZag1;
}

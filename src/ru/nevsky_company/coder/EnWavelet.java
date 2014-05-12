package ru.nevsky_company.coder;

import ru.nevsky_company.decode.DeZigZag;

class EnWavelet {

    public EnWavelet(int f[][], int N) {
        this.N = N;
    }

    public EnWavelet() {
        N = 8;
        zigZag = new ZigZag();
        deZigZag = new DeZigZag();
    }

    public double[][] directHoaar(double f[][]) {
        double c[] = zigZag.getHideZigZagArray(f, N);
        double result[] = new double[N * N];
        for (int i = 0; i < N * N / 2; i++) {
            result[2 * i] = ((c[2 * i] + c[2 * i + 1]) / 2.0);
            result[2 * i + 1] = ((c[2 * i] - c[2 * i + 1]) / 2.0);
        }
        double array[][] = deZigZag.getDoubleArray(result);
        return array;
    }

    public double[] directHoaar(double c[], int h) {
        double result[] = new double[h * h];
        for (int i = 0; i < (h * h) / 2; i++) {
            result[2 * i] = ((c[2 * i] + c[2 * i + 1]) / 2.0);
            result[2 * i + 1] = ((c[2 * i] - c[2 * i + 1]) / 2.0);
        }
        return result;
    }

    public int[][] dct(int f[][]) {
        double tempX[][] = new double[N][N];
        double tempY[][] = new double[N][N];
        int F[][] = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tempX[i][j] = (double)f[i][j];
            }
        }

        dct_direct(tempX, tempY);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                F[i][j] = (int)(Math.floor(tempY[i][j] + 0.5));
            }
        }
        return F;
    }

    private void dct_direct(double f[][], double F[][]) {
        double a[] = new double[N];
        a[0] = sqrt(1. / N);
        for (int i = 1; i < N; i++) {
            a[i] = sqrt(2. / N);
        }

        double summ = 0;
        double koeff = 0;
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                summ = 0.;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        koeff = cos((2 * i + 1) * u * PI / (2 * N)) *
                                cos((2 * j + 1) * v * PI / (2 * N));
                        summ += f[i][j] * koeff;
                    }
                    F[u][v] = a[u] * a[v] * summ;
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


    private final int N;
    private final double PI = Math.PI;
    private ZigZag zigZag;
    DeZigZag deZigZag;

}

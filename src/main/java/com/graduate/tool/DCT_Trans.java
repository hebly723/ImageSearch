package com.graduate.tool;

public class DCT_Trans {
    private class Keys{
        int x;
        int y;
        public Keys( int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    /**
     * 要进行DCT变换的图片的宽或高
     */
    private int N;
    public DCT_Trans( int N)
    {
        this.N = N;
    }
//    public static final int N = 256;

    /**
     * 离散余弦变换
     * @param pix 原图像的数据矩阵(一维)
     * @param n 原图像(n*n)的高或宽
     * @return 变换后的矩阵数组
     */
    public double[] DCT(double[] pix, int n) {
        double[][] iMatrix = new double[n][n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                iMatrix[i][j] = pix[i*n + j];
            }
        }
        double[][] quotient = coefficient(n);   //求系数矩阵
        double[][] quotientT = transposingMatrix(quotient, n);  //转置系数矩阵

        double[][] temp = new double[n][n];
        temp = matrixMultiply(quotient, iMatrix, n);
        iMatrix =  matrixMultiply(temp, quotientT, n);

        double newpix[] = new double[n*n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                newpix[i*n + j] = iMatrix[i][j];
            }
        }
        return newpix;
    }
    /**
     * 离散余弦变换(二维)
     * @param iMatrix 原图像的数据矩阵
     * @return 变换后的矩阵数组
     */
    public double[][] DCT(double[][] iMatrix) {
        int n = iMatrix[0].length;
        double[][] quotient = coefficient(n);   //求系数矩阵
        double[][] quotientT = transposingMatrix(quotient, n);  //转置系数矩阵
        double[][] temp = new double[n][n];
        temp = matrixMultiply(quotient, iMatrix, n);
        iMatrix =  matrixMultiply(temp, quotientT, n);

//        double newpix[] = new double[n*n];
//        for(int i=0; i<n; i++) {
//            for(int j=0; j<n; j++) {
//                newpix[i*n + j] = iMatrix[i][j];
//            }
//        }
        return iMatrix;
    }

    /**
     * 将二维数组中指定区域作离散余弦变换
     * @param pix
     * @param x0 起始点x坐标
     * @param y0 起始点y坐标
     * @param n  方形区域边长
     * @return
     */
    public double[][] DCT(double[][] pix,
                          int x0, int y0, int n) {
        double[][] iMatrix = new double[n][n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                iMatrix[i][j] = pix[x0+i][y0+j];
            }
        }
        double[][] quotient = coefficient(n);   //求系数矩阵
        double[][] quotientT = transposingMatrix(quotient, n);  //转置系数矩阵
        double[][] temp = new double[n][n];
        temp = matrixMultiply(quotient, iMatrix, n);
        iMatrix =  matrixMultiply(temp, quotientT, n);
        return iMatrix;
    }
    /**
     * 矩阵转置
     * @param matrix 原矩阵
     * @param n 矩阵(n*n)的高或宽
     * @return 转置后的矩阵
     */
    private double[][]  transposingMatrix(double[][] matrix, int n) {
        double nMatrix[][] = new double[n][n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                nMatrix[i][j] = matrix[j][i];
            }
        }
        return nMatrix;
    }
    /**
     * 求离散余弦变换的系数矩阵
     * @param n n*n矩阵的大小
     * @return 系数矩阵
     */
    private double[][] coefficient(int n) {
        double[][] coeff = new double[n][n];
        double sqrt = 1.0/Math.sqrt(n);
        for(int i=0; i<n; i++) {
            coeff[0][i] = sqrt;
        }
        for(int i=1; i<n; i++) {
            for(int j=0; j<n; j++) {
                coeff[i][j] = Math.sqrt(2.0/n) * Math.cos(i*Math.PI*(j+0.5)/(double)n);
            }
        }
        return coeff;
    }
    /**
     * 矩阵相乘
     * @param A 矩阵A
     * @param B 矩阵B
     * @param n 矩阵的大小n*n
     * @return 结果矩阵
     */
    private double[][] matrixMultiply(double[][] A, double[][] B, int n) {
        double nMatrix[][] = new double[n][n];
        double t = 0.0;
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                t = 0;
                for(int k=0; k<n; k++) {
                    t += A[i][k]*B[k][j];
                }
                nMatrix[i][j] = t;
            }
        }
        return nMatrix;
    }

    /**
     * Z字扫描
     * @param originArray 原二维数组
     * @return Z字扫描之后的数组
     */
    public double[] zScan(double [][] originArray)
    {
        double[] zResult = new double[originArray[0].length*originArray[0].length];
        boolean[][] zBoolean = new boolean[originArray[0].length][originArray[0].length];
        Keys keys = new Keys(0,0);
        int h = 0, zi = 0;
        zBoolean[0][0] = true;
        zResult[zi] = originArray[0][0];
        while (zi<(originArray[0].length*originArray[0].length-1))
        {
            if (keys.y > keys.x - originArray[0].length)
            {
                switch (h) {
                    case 0:
                    case 2:
                        Keys neKeys = zMove(keys, h, originArray[0].length);
                        if (neKeys.x >= 0 &&
                                neKeys.x < originArray[0].length &&
                                neKeys.y >= 0 &&
                                neKeys.y < originArray[0].length&&
                                !zBoolean[neKeys.x][neKeys.y]) {
                            keys = neKeys;
                            h = (h + 1) % 4;
                            zBoolean[keys.x][keys.y] = true;
                            zi++;
                            zResult[zi] = originArray[keys.x][keys.y];
                        } else
                            h = (h + 1) % 4;
                        break;
                    case 1:
                    case 3:
                        Keys newKeys = zMove(keys, h, originArray[0].length);
                        if (newKeys.x >= 0 &&
                                newKeys.x < originArray[0].length &&
                                newKeys.y >= 0 &&
                                newKeys.y < originArray[0].length&&
                                !zBoolean[newKeys.x][newKeys.y]) {
                            keys = newKeys;
                            zi++;
                            zBoolean[keys.x][keys.y] = true;
                            zResult[zi] = originArray[keys.x][keys.y];
                        } else
                            h = (h + 1) % 4;
                        break;
                }
            }else
            {
                switch (h) {
                    case 0:
                    case 2:
                        Keys neKeys = zMove(keys, h, originArray[0].length);
                        if (neKeys.x >= 0 &&
                                neKeys.x < originArray[0].length &&
                                neKeys.y >= 0 &&
                                neKeys.y < originArray[0].length&&
                                !zBoolean[neKeys.x][neKeys.y]) {
                            zResult[zi] = originArray[keys.x][keys.y];
                            keys = neKeys;
                            h = (h + 3) % 4;
                            zi++;
                            zBoolean[keys.x][keys.y] = true;
                            zResult[zi] = originArray[keys.x][keys.y];
                        } else
                            h = (h + 3) % 4;
                        break;
                    case 1:
                    case 3:


                        Keys newKeys = zMove(keys, h, originArray[0].length);
                        if (newKeys.x >= 0 &&
                                newKeys.x < originArray[0].length &&
                                newKeys.y >= 0 &&
                                newKeys.y < originArray[0].length&&
                                !zBoolean[newKeys.x][newKeys.y]) {
                            keys = newKeys;
                            zi++;
                            zBoolean[keys.x][keys.y] = true;
                            zResult[zi] = originArray[keys.x][keys.y];
                        } else
                            h = (h + 1) % 4;
                        break;
                }
            }
        }
        return zResult;
    }

    /**
     * Z字移动
     * @param key0 初始坐标
     * @param flag 移动方向
     * @return 移动之后的坐标
     */
    private Keys zMove( Keys key0, int flag, int bound)
    {
        Keys answer;
        switch (flag){
            case 0: //向右移动
                answer = new Keys(key0.x+1, key0.y);
                break;
            case 1: //左下角移动
                answer = new Keys(key0.x-1, key0.y+1);
                break;
            case 2: //向下移动
                answer = new Keys(key0.x, key0.y+1);
                break;
            case 3: //右上角移动
                answer = new Keys(key0.x+1, key0.y-1);
                break;
            default:
                answer = null;
        }
        return answer;
    }

    /**
     *
     * @param matrix 原矩阵
     * @param m      截取后的矩阵边长
     * @return       截取之后的矩阵
     */
    public double[] cutArray( double[] matrix, int m )
    {
        double[] newMatrix = new double[m];
        for (int i = 0; i < m; i++) {
            newMatrix[i] = matrix[i];
        }
        return newMatrix;
    }

    /**
     * CVA中单块DCT，Z字扫描，生成前（边长-1）/边长的二维数组操作的封装
     * @param array
     * @return
     */
    public double[] CVADCTProgress(double[][] array){
        /**
         *DCT变换
         */
        double[][] dctArray = DCT(array);
        /**
         * Z字扫描
         */
        double[] zArray = zScan(dctArray);
        /**
         * 截取一部分，这边设置为（边长-1）/边长
         */
        return cutArray(zArray, (int)(zArray.length*(((double)(N-1))/
                ((double)N))));
    }

    /**
     * CVA中取二维数组中单块DCT，Z字扫描，生成前（边长-1）/边长的二维数组操作的封装
     * @param array
     * @param x0
     * @param y0
     * @param n
     * @return
     */
    public double[] CVADCTProgressD(double[][] array, int x0, int y0, int n){
        double[][] newArray = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
            {
                newArray[i][j] = array[x0+i][y0+j];
            }
        }
        this.N = n;
        return CVADCTProgress(newArray);
    }


}













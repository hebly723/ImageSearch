package com.graduate.tool;

import com.graduate.entity.Matrix;
import org.opencv.core.Mat;

/**
 * LLE降维相关算法封装
 */
public class LLEArg {
    private int dimention;
    private int neighborhoodNumber;
    public LLEArg(){
        dimention = 2;
        neighborhoodNumber = 2;
    }
    /**
     * 求与给定列向量相邻的列向量
     * @param matrix
     * @param i 该列向量在列向量数组中的下标
     * @return
     */
    public Matrix[] neighborhood( Matrix matrix, int i){
        Matrix[] matrices = matrix.divideCol();
        Matrix[] answer;
        answer = new Matrix[neighborhoodNumber];
        int begin = (i-neighborhoodNumber+matrices.length)%matrices.length;
        int k=0;
        while (k<answer.length)
        {
            if (begin!=i)
            {
                answer[k] = matrices[begin];
                k++;
            }
            begin=(begin+1)%matrices.length;
        }
        return answer;
    }

    /**
     * 求协方差矩阵
     * @param matrix
     * @param index
     * @return
     */
    public Matrix getZ(Matrix matrix, int index){
        Matrix[] neighbor = neighborhood(matrix, index).clone();

        Matrix[] answer = new Matrix[neighbor.length];

        for (int i=0; i<neighbor.length;i++)
        {
            answer[i] = matrix.divideCol()[index].plus(neighbor[i].multi(-1));
            neighbor[i].multi(-1);
        }

        Matrix oz = Matrix.merge(answer);
        Matrix result = oz.reverse().multi(oz);
//        System.out.println("getZ\n"+result);
        return result.round();
    }

    /**
     * 求权重系数
     * @param matrix
     * @param index
     */
    public Matrix getWI(Matrix matrix, int index){
        Matrix z = getZ(matrix, index);
        Matrix wU = (z.getInverse().multi(Matrix.initCol(neighborhoodNumber)));

        Matrix wD = (Matrix.initCol(neighborhoodNumber).reverse().multi(
                        z.getInverse().multi(Matrix.initCol(neighborhoodNumber))));

//        System.out.println("getWI"+wD);
        return wU.multi(1/wD.getMatrix().get(0,0)).round();
    }

    /**
     * 求权重系数矩阵
     * @param matrix
     */
    public Matrix getW(Matrix matrix){
        Matrix[] matrix1 = new Matrix[matrix.getMatrix().getColumnDimension()];
        for (int i=0; i<matrix.getMatrix().getColumnDimension();i++)
        {
            matrix1[i] = getWI(matrix, i);
        }
//        System.out.println("getW");
        return Matrix.merge(matrix1).round();
    }

    /**
     * 计算过渡矩阵
     * @param matrix
     * @return
     */
    public Matrix getM(Matrix matrix){
        Matrix w = getW(matrix);
        Matrix i = Matrix.initI(w.getMatrix().getRowDimension(),
                w.getMatrix().getColumnDimension());
//        w.multi(-1);
        Matrix m = i.plus(w.multi(-1));
//        System.out.println(m);
        Matrix matrix1 = (m.reverse()).multi(m);
//        System.out.println("N\n"+matrix1);
//        System.out.println("getM");
        return matrix1.round();
    }

    public Matrix getAnswer(Matrix matrix){
        Matrix matrix1 = getM(matrix);
//        System.out.println(matrix1.getV());
        Matrix matrix3 = matrix1.getV();
        matrix3 = matrix3.sort();
//        System.out.println(matrix3);
        Jama.Matrix matrix2 = matrix3.getMatrix().getMatrix(0,matrix3.getMatrix().getRowDimension()-1,
                1, dimention).copy();

//        System.out.println("getAnswer");
        return new Matrix(matrix2).round().reverse();
    }

    public int getDimention() {
        return dimention;
    }

    public void setDimention(int dimention) {
        this.dimention = dimention;
    }

    public int getNeighborhoodNumber() {
        return neighborhoodNumber;
    }

    public void setNeighborhoodNumber(int neighborhoodNumber) {
        this.neighborhoodNumber = neighborhoodNumber;
    }
}

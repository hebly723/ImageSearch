package com.graduate.tool;

import com.graduate.entity.Matrix;
import org.opencv.core.Mat;

/**
 * LLE降维相关算法封装
 */
public class LLEArg {
    /**
     * 求与给定列向量相邻的列向量
     * @param matrix
     * @param i 该列向量在列向量数组中的下标
     * @return
     */
    public Matrix[] neighborhood( Matrix matrix, int i){
        Matrix[] matrices = matrix.divideCol();
        Matrix[] answer;
            answer = new Matrix[2];
            answer[0] = new Matrix(matrices[(i+2)%3].getMatrix().copy());
            answer[1] = new Matrix(matrices[(i+1)%3].getMatrix().copy());
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

        return oz.reverse().multi(oz).round();
    }

    /**
     * 求权重系数
     * @param matrix
     * @param index
     */
    public Matrix getWI(Matrix matrix, int index){
        Matrix z = getZ(matrix, index);
        Matrix wU = (z.getInverse().multi(Matrix.initCol(2)));

        Matrix wD = (Matrix.initCol(2).reverse().multi(
                        z.getInverse().multi(Matrix.initCol(2))));

        return wU.multi(1/wD.getValue());
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
        return Matrix.merge(matrix1);
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
        System.out.println(m);
        Matrix matrix1 = (m.reverse()).multi(m);
//        System.out.println("N\n"+matrix1);
        return matrix1;
    }

    public Matrix getAnswer(Matrix matrix){
        Matrix matrix1 = getM(matrix);
//        System.out.println(matrix1.getV());
        Matrix matrix3 = matrix1.getV();
        matrix3 = matrix3.sort();
        return matrix3.removeCol(0).round().reverse();
    }


}

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
        if (i>0&&i<matrices.length-1)
        {
            answer = new Matrix[2];
            answer[0] = matrices[i-1];
            answer[1] = matrices[i+1];
            return answer;
        }
        else if (i == 0&&i<matrices.length-1)
        {
            answer = new Matrix[1];
            answer[0] = matrices[i+1];
            return answer;
        }
        else if (i > 0&&i==matrices.length-1)
        {
            answer = new Matrix[1];
            answer[0] = matrices[i-1];
            return answer;
        }
        return null;
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
            answer[i] = matrix.divideCol()[index].plus(neighbor[i].mulNumber(-1));
            neighbor[i].mulNumber(-1);
        }

        Matrix oz = Matrix.merge(answer, true);

        return oz.reverse().multi(oz);
    }

    /**
     * 求权重系数
     * @param matrix
     * @param index
     */
    public Matrix getWI(Matrix matrix, int index){
        Matrix z = getZ(matrix, index);
        Matrix wU = (z.getInverse().multi(Matrix.initCol(z.getWidth())));

        Matrix wD = (Matrix.initCol(z.getWidth()).reverse().multi(
                        z.getInverse().multi(Matrix.initCol(z.getWidth()))));

        return wU.mulNumber(1/wD.getValue());
    }

    /**
     * 求权重系数矩阵
     * @param matrix
     */
    public Matrix getW(Matrix matrix){
        Matrix[] matrix1 = new Matrix[matrix.getWidth()];
        for (int i=0; i<matrix.getWidth();i++)
        {
            matrix1[i] = getWI(matrix, i);
        }
        return Matrix.merge(matrix1, true);
    }

    /**
     * 计算过渡矩阵
     * @param matrix
     * @return
     */
    public Matrix getM(Matrix matrix){
        Matrix w = getW(matrix);
        Matrix i = Matrix.initI(w.getWidth(), w.getHeight());
        w.mulNumber(-1);
        return (i.plus(w).reverse()).multi(i.plus(w));
    }

    public Matrix getAnswer(Matrix matrix){
        Matrix matrix1 = getM(matrix);
        System.out.println(matrix1.getV());
        Matrix matrix3 = matrix1.getV();
        matrix3 = matrix3.sort();
        return matrix3.removeCol(0);
    }


}

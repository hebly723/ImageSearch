package com.graduate.test;

import com.graduate.entity.Matrix;
import org.junit.Before;
import org.junit.Test;

/**
 * 矩阵代码测试类
 */
public class MatrixTest {
    private Matrix matrix;
    double[][] doubles = {
            {1, 2, 3},
            {2, 2, 1},
            {3, 4, 3}
    };
    @Before
    public void init(){
        matrix = new Matrix(doubles);

        System.out.println(matrix);
    }

    /**
     * 测试逆生成
     */
    @Test
    public void testInverse(){
        System.out.println(matrix.getValue()+"\n"+matrix.getInverse());
    }

    /**
     * 测试数乘
     */
    @Test
    public void TestMulNumber(){
        System.out.println(matrix.multi(2));
    }

    /**
     * 测试行列式值生成
     */
    @Test
    public void testValue(){
        System.out.println(matrix.getValue());
    }

    /**
     * 测试数组初始化
     */
    @Test
    public void testArrayInit() {
        double[][] doubles = {
                {1, 3, 5, 7},
                {2, 4, 6, 8}
        };
        Matrix matrix2 = new Matrix(doubles);

        System.out.println(matrix2 + "" +
                matrix2.getMatrix().getRowDimension());
    }
    /**
     * 测试形成K维全一列向量
     */
    @Test
    public void testInitCol(){
        System.out.println(Matrix.initCol(3));
    }
    /**
     * 测试按列分割列向量
     */
    @Test
    public void testDivideCol(){
        Matrix[] matrices = matrix.divideCol();
        for (int i=0; i< matrices.length; i++)
        System.out.println(matrices[i]);
    }
    /**
     * 测试按行分割行向量
     */
    @Test
    public void testDivideRow(){
        Matrix[] matrices = matrix.divideRow();
        for (int i=0; i< matrices.length; i++)
            System.out.println(matrices[i]);
    }

    @Test
    public void testMulti(){
        double[][] doubles1 = {
                {2},
                {1},
                {3},
        };
        double[][] doubles2 = {
                {-1,2}
        };
        Matrix matrix1 = new Matrix(doubles1);

        Matrix matrix2 = new Matrix(doubles2);
        System.out.println(matrix1+"\n"+matrix2);
        System.out.println(matrix1.multi(matrix2));
    }
    @Test
    public void testMerge(){
        Matrix[] matrices = matrix.divideRow();
        for (int i=0; i< matrices.length; i++)
            System.out.println(matrices[i]);
        System.out.println(Matrix.merge(matrices));
    }
}

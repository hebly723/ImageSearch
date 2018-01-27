package com.graduate.test;

import com.graduate.entity.Matrix;
import org.junit.Before;
import org.junit.Test;

/**
 * 矩阵代码测试类
 */
public class MatrixTest {
    double[] doubles = {
            1, 2, 3,
            2, 2, 1,
            3, 4, 3
    };
    @Before
    public void init(){
        Matrix matrix = new Matrix(doubles, 3);

        System.out.println(matrix);
    }

    /**
     * 测试逆生成
     */
    @Test
    public void testInverse(){
        Matrix matrix = new Matrix(doubles, 3);

        System.out.println(matrix.getValue()+"\n"+matrix.getInverse());
    }

    /**
     * 测试伴随矩阵生成
     */
    @Test
    public void testAdjoin() {

        Matrix matrix = new Matrix(doubles, 3);

        System.out.println(matrix.getAdjoint());
    }

    /**
     * 测试余子式生成
     */
    @Test
    public void testConfactor(){
        Matrix matrix = new Matrix(doubles, 3);
        for (int i=0; i<3;i++)
            for (int j=0; j<3; j++)
            {
                System.out.println("A"+(i+1)+""+(j+1)+":\n"+matrix.getConfactor(i,j));
            }
    }

    /**
     * 测试数乘
     */
    @Test
    public void TestMulNumber(){
        Matrix matrix = new Matrix(doubles, 3);
        matrix.mulNumber(2);
        System.out.println(matrix);
    }

    /**
     * 测试行列式值生成
     */
    @Test
    public void testValue(){
        Matrix matrix = new Matrix(doubles, 3);
        for (int i=0; i<3;i++)
            for (int j=0; j<3; j++)
            {
                System.out.println("A"+(i+1)+""+(j+1)+":\n"+matrix.getConfactor(i,j)+
                        " = " + matrix.getConfactor(i,j).getValue());
            }
    }

    /**
     * 测试数组初始化
     */
    @Test
    public void testArrayInit() {
        double[] doubles = {
                1, 3, 5, 7,
                2, 4, 6, 8,
        };
        Matrix matrix = new Matrix(doubles, 4);
        System.out.println(matrix);
    }
}

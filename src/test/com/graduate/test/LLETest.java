package com.graduate.test;

import com.graduate.entity.Matrix;
import com.graduate.tool.LLEEmbed;
import org.junit.Before;
import org.junit.Test;

public class LLETest {
    private Matrix matrix;
    double[] doubles = {
            1, 2, 3,
            2, 2, 1,
            3, 4, 3
    };
    private LLEEmbed lleEmbed;
    @Before
    public void init(){
        matrix = new Matrix(doubles, 3);
        lleEmbed = new LLEEmbed();
        System.out.println(matrix);
    }
    @Test
    public void testGetZ(){
        System.out.println(lleEmbed.getZ(matrix,1));
    }
    @Test
    public void testNeighbor(){
        Matrix[] matrices = (lleEmbed.neighborhood(matrix,1));
        for (int i=0; i< matrices.length;i++)
            System.out.println(matrices[i]);
    }
    @Test
    public void testGetW(){
//        System.out.println(lleEmbed.getWI(matrix,1));
        System.out.println(lleEmbed.getW(matrix));
    }
    @Test
    public void testInitI(){
//        System.out.println(lleEmbed.getWI(matrix,1));
        System.out.println(Matrix.initI(3, 4));
    }
    @Test
    public void testGetM(){
        System.out.println(lleEmbed.getM(matrix));
    }
}
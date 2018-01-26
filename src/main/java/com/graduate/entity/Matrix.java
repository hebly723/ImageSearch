package com.graduate.entity;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

/**
 * 矩阵及其运算的封装
 */
public class Matrix {
    private int width;
    private int height;
    private List<List<Double>> matrixList;
    public Matrix(){}

    /**
     * 使用二维数列初始化矩阵,是这么初始的，#是数列头，0是数列尾
     * width
     * # # # h
     * × × × e
     * × × × i
     * × × × g
     * × × × h
     * × × × t
     * 0 0 0
     * @param mlist 二维数列，元素为double
     */
    public Matrix(List<List<Double>> mlist){
        setMatrixList(mlist);
        width = getMatrixList().size();
        height = getMatrixList().get(0).size();
    }

    /**
     * 求矩阵的转置
     */
    public void reverse(){
        List<List<Double>> mlist = new ArrayList<>();
        for (int i=0; i<height; i++)
        {
            mlist.add(new ArrayList<Double>());
            for (int j=0; j<width; j++)
            {
                mlist.get(i).add(matrixList.get(j).get(i));
            }
        }
        setMatrixList(mlist);
        width = getMatrixList().size();
        height = getMatrixList().get(0).size();
    }

    /**
     * 将矩阵所有元素取负
     */
    public void negative(){
        for (int i = 0; i< matrixList.size();i++)
        {
            for (int j = 0; j < matrixList.get(i).size(); j++)
            {
                matrixList.get(i).set(j, -matrixList.get(i).get(j));
            }
        }
    }

    /**
     * 矩阵相加
     * @param matrix
     */
    public void plus(Matrix matrix){
        List<List<Double>> otherList = matrix.getMatrixList();
        for (int i = 0; i<width; i++)
        {
            for (int j = 0; j<height; j++)
            {
                matrixList.get(i).set(j, matrixList.get(i).get(j)+otherList.get(i).get(j));
            }
        }
    }

    /**
     * 矩阵分割为一个个列向量
     * @return 矩阵数组
     */
    public Matrix[] divideCol(){
        Matrix[] matrices = new Matrix[width];
        for (int i=0;i<width;i++)
        {
            List<List<Double>> mlist = new ArrayList<>();
            mlist.add(matrixList.get(i));
            Matrix matrix = new Matrix(mlist);
            matrices[i] = matrix;
        }
        return matrices;
    }

    /**
     * 矩阵分割为一个个行向量
     * @return 矩阵数组
     */
    public Matrix[] divideRow(){
        this.reverse();
        Matrix[] matrices = this.divideCol();
        this.reverse();
        for (int i=0; i<matrices.length; i++)
        {
            matrices[i].reverse();
        }
        return matrices;
    }

    /**
     * 列向量数组合并为一个矩阵
     * @param matrices
     * @param flag true为列向量，false为行向量
     * @return
     */
    public static Matrix merge(Matrix[] matrices, boolean flag){
        if (flag) {
            List<List<Double>> mlist = new ArrayList<>();
            for (int i = 0; i < matrices.length; i++) {
                mlist.add(matrices[i].getMatrixList().get(0));
            }
            return new Matrix(mlist);
        }
        else
        {
            List<List<Double>> mlist = new ArrayList<>();
            for (int i = 0; i< matrices[0].getWidth();i++)
            {
                List<Double> list = new ArrayList<>();
                for (int j=0; j<matrices.length;j++){
                    double r = matrices[j].getMatrixList().get(i).get(0);
                    list.add(r);
                }
                mlist.add(list);
            }
            return new Matrix(mlist);
        }
    }

    public int getWidth() {
        return width;
    }

    /**
     * 矩阵相乘
     * @param matrix 乘矩阵
     */
    public void multi(Matrix matrix){
        List<List<Double>> mlist = new ArrayList<>();
        Matrix[] rows = this.divideRow();
        Matrix[] cols = matrix.divideCol();
        for (int i = 0; i<matrix.getWidth(); i++)
        {
            List<Double> list = new ArrayList<>();
            for (int j = 0; j<this.getHeight();j++)
            {
                list.add(Matrix.singleMulti(rows[j],cols[i]));
            }
            mlist.add(list);
        }
        matrixList = mlist;
        width = getMatrixList().size();
        height = getMatrixList().get(0).size();
    }

    /**
     * 行向量与列向量相乘
     * @param matrix1 行向量
     * @param matrix2 列向量
     * @return
     */
    public static double singleMulti(Matrix matrix1, Matrix matrix2){
        double sum = 0;
        for (int i = 0; i< matrix1.width; i++)
        {
            sum += matrix1.getMatrixList().get(i).get(0) *
                    matrix2.getMatrixList().get(0).get(i);
        }
        return sum;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<List<Double>> getMatrixList() {
        return matrixList;
    }

    public void setMatrixList(List<List<Double>> matrixList) {
        this.matrixList = matrixList;
    }

    @Override
    public String toString() {
        String str =  "Matrix{" +
                "width=" + width +
                ", height=" + height +
                ", matrixList="+ System.lineSeparator();
        for (int i=0; i<width; i++)
        {
            for (int j=0; j<height; j++)
            {
                str = str + matrixList.get(i).get(j) + "\t";
            }
            str = str + System.lineSeparator();
        }
        char end = '}';
        str = str + end;
        return str;
    }

    /**
     * 测试入口
     * @param args
     */
    public static void main(String args[]){
        List<List<Double>> mlist = new ArrayList<>();
        for (int i=0; i<4; i++)
        {
            List<Double> list = new ArrayList<>();
            for (int j=0; j<8; j++)
            {
                list.add(j+0.0);

//                System.out.print(j+"\t");
            }
//            System.out.print(System.lineSeparator());
            mlist.add(list);
        }
        Matrix matrix = new Matrix(mlist);
//        Matrix matrix1 = new Matrix(mlist);
//        System.out.println(matrix1);
//        matrix1.reverse();
//        System.out.println(matrix1);
//        matrix.reverse();
//        matrix.negative();
//        System.out.println(matrix1);
//        matrix.plus(matrix1);
//        System.out.println(matrix);
//        System.out.println(matrix);
        Matrix[] matrices = matrix.divideRow();
        for (int i=0; i<matrices.length; i++)
            System.out.println(matrices[i]);
        Matrix[] matrices2 = matrix.divideCol();
        for (int i=0; i<matrices2.length; i++)
            System.out.println(matrices2[i]);
        Matrix[] matrixes = new Matrix[2];
        matrixes[0] = matrices[0];
        matrixes[1] = matrices[1];
        Matrix reverMat = Matrix.merge(matrices2, true);
        Matrix newMat = Matrix.merge(matrices, false);
        System.out.println(newMat);
        newMat.reverse();
        System.out.println("newMat"+newMat);

        System.out.println("reverMat"+reverMat);
        newMat.multi(reverMat);
        System.out.println(newMat);
//        matrices[2].reverse();
//        System.out.println(singleMulti(matrices[2], matrices[1]));
//        System.out.println(Matrix.merge(matrices));
    }
}

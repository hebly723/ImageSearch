package com.graduate.entity;

import Jama.SingularValueDecomposition;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import java.math.BigDecimal;
import java.util.*;

import static org.opencv.core.CvType.CV_32S;

/**
 * 矩阵及其运算的封装
 */
public class Matrix implements Cloneable {
//    private int width;
//    private int height;
//    private List<List<Double>> matrixList;
    private Jama.Matrix matrix;

    public Matrix(Jama.Matrix matrix){
        this.matrix = matrix;
    }

    public Matrix(int w ,int h)
    {
        Jama.Matrix matrix = new Jama.Matrix( w, h);
        this.matrix = matrix;
    }

    public Matrix(ArrayList<double[]> arrayList){
        Jama.Matrix matrix = new Jama.Matrix(arrayList.get(0).length, arrayList.size());
        for (int i=0; i<arrayList.size();i++)
        {
            for (int j=0; j<arrayList.get(0).length;j++)
            {
                matrix.set(j,i,arrayList.get(i)[j]);
            }
        }
        this.matrix = matrix;
    }

    public Matrix(double[][] matrices)
    {
        this.matrix = new Jama.Matrix(matrices);
    }

    public Matrix(List<List<Double>> lists){
        matrix = new Jama.Matrix(lists.get(0).size(), lists.size());
        for (int i=0; i<lists.get(0).size();i++)
        {
            for (int j=0; j<lists.size(); j++)
            {
                matrix.set(i,j,lists.get(j).get(i));
            }
        }
    }

    /**
     * 求矩阵的转置
     */
    public Matrix reverse(){
        return new Matrix(this.matrix.transpose());
    }

    /**
     * 将矩阵所有元素取负
     */
    public Matrix multi(double k){
        return new Matrix(this.matrix.times(k));
    }


    /**
     * 矩阵相乘
     * @param matrix 乘矩阵
     */
    public Matrix multi(Matrix matrix){
        return new Matrix(this.matrix.times(matrix.getMatrix()));
    }

    /**
     * 矩阵相加
     * @param matrix
     */
    public Matrix plus(Matrix matrix){
        return new Matrix(this.matrix.plus(matrix.getMatrix()));
    }

    /**
     * 矩阵分割为一个个列向量
     * @return 矩阵数组
     */
    public Matrix[] divideCol(){
        Matrix[] matrices = new Matrix[this.getMatrix().getColumnDimension()];
        for (int i=0; i<matrices.length;i++)
        {
            Jama.Matrix matrix1 = this.getMatrix().
                    getMatrix(0,this.getMatrix().getRowDimension()-1,i,i).copy();
            matrices[i] = new Matrix(matrix1);
        }
        return matrices;
    }

    /**
     * 矩阵分割为一个个行向量
     * @return 矩阵数组
     */
    public Matrix[] divideRow(){
        Matrix[] matrices = new Matrix[this.getMatrix().getRowDimension()];
        for (int i=0; i<matrices.length;i++)
        {
            matrices[i] = new Matrix(this.getMatrix().
                    getMatrix( i, i, 0, this.getMatrix().getColumnDimension()-1).copy());
        }
        return matrices;
    }

    /**
     * 列向量数组合并为一个矩阵
     * @param matrices
     * @return
     */
    public static Matrix merge(Matrix[] matrices){
        Jama.Matrix matrix;
        if (matrices.length == 0)
            return null;
        if (matrices[0].getMatrix().getRowDimension()>1)
        {
            matrix = new Jama.Matrix(matrices[0].getMatrix().getRowDimension(), matrices.length);
            for (int i=0; i<matrices.length; i++)
            {
                for (int j=0;j<matrices[0].getMatrix().getRowDimension();j++)
                {
                    matrix.set(j,i,matrices[i].getMatrix().get(j,0));
                }
            }
        }
        else
        {
            matrix = new Jama.Matrix(matrices.length,matrices[0].getMatrix().getColumnDimension());
            for (int i=0; i<matrices.length; i++)
            {
                for (int j=0;j<matrices[0].getMatrix().getColumnDimension();j++)
                {
                    matrix.set(i,j,matrices[i].getMatrix().get(0,j));
                }
            }
        }
        return new Matrix(matrix);
    }

    /**
     * 矩阵求逆
     * @return
     */
    public Matrix getInverse(){
//        System.out.println(matrix.det());
        if (matrix.det()!=0) {
            return new Matrix(matrix.inverse());
        }else{
            return Matrix.initI(matrix.getRowDimension(), matrix.getColumnDimension());
        }
    }

    /**
     * 矩阵行列式值计算
     * @return
     */
    public double getValue()//计算n阶行列式（N=n-1）
    {
        return Matrix.roundValue(matrix.det(),3);
    }

    /**
     * 形成k维全一列向量
     * @param k
     * @return
     */
    public static Matrix initCol(int k)
    {
        Jama.Matrix matrix = new Jama.Matrix(k,1);
        for (int i=0; i<k; i++)
        {
            matrix.set(i,0, 1.0);
        }
        return new Matrix(matrix);
    }

    /**
     * 求矩阵特征值特征向量
     * @return
     */
    public Jama.EigenvalueDecomposition getSVD(){

        Jama.Matrix matrix = this.getMatrix();
        SingularValueDecomposition singularValueDecomposition =  matrix.svd();
        return matrix.eig();

    }

    public double[] getD(){
        Jama.Matrix matrix = this.getSVD().getD();
        double[] doubles = new double[matrix.getColumnDimension()];
        for (int i=0; i<doubles.length; i++)
        {
            doubles[i] = matrix.get(i, i);
        }
        return doubles;
    }
//
    public Matrix round(){
        double[][] doubles = matrix.getArray();
        for (int i=0; i<doubles.length;i++)
        {
            for (int j=0; j<doubles[i].length;j++)
            {
//                if (Double.isNaN(doubles[i][j])){
//                    doubles[i][j] = 1.0;
//                }else
                    doubles[i][j] = roundValue(doubles[i][j],2);
            }
        }
        return this;
    }

    public static double roundValue(double f, int newScale){
//        try {
            BigDecimal b = new BigDecimal(new Double(f).toString());
            double f1 = b.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
            return f1;
//        }catch (Exception e)
//        {
//            System.out.println("出错"+f);
//        }
//        return 0;
    }

    /**
     * 按特征值绝对值排序
     * @Return
     */
    public Matrix sort(){
        double[] doubles = this.getD();
        Matrix[] matrices = this.getV().divideCol();
//        System.out.println(Matrix.merge(matrices));
//        for (int i=0;i<doubles.length;i++)
//            System.out.println(doubles[i]);
        Map<Double, Matrix> map = new HashMap();
        DV[] dvs = new DV[doubles.length];
        for (int i=0; i<doubles.length;i++)
        {
            dvs[i] = new DV(matrices[i],doubles[i]);
        }
        Arrays.sort(dvs, new DVComparator());

        return Matrix.merge(DV.getMatrix(dvs));
    }

    public Matrix getV(){
        Jama.Matrix matrix = this.getSVD().getV();
        Jama.Matrix matrix1 = new Jama.Matrix(matrix.getRowDimension(),
                matrix.getColumnDimension());
        for (int j=0; j<matrix.getColumnDimension();j++){
            for (int i=0; i<matrix.getRowDimension(); i++)
            {
                double d = matrix.get(i, j);
                matrix1.set(i,j,d);
            }
        }
        return new Matrix(matrix1);
    }

    public Matrix removeRow(int index){
        Jama.Matrix matrix = new Jama.Matrix(this.getRowDimension()-1,this.getColumnDimension());
        int k = 0;
        for (int i=0; i<this.getRowDimension();i++)
        {
            if (i!=index) {
                matrix.setMatrix(k, k, 0, this.getColumnDimension() - 1,
                        this.matrix.getMatrix(i, i, 0, this.getColumnDimension() - 1).
                                copy());

                k++;
            }
        }
        return new Matrix(matrix);
    }

    public Matrix removeCol(int index){
        Matrix[] matrices = this.divideCol();
        Matrix[] matrices1 = new Matrix[matrices.length-1];
        int j=0;
        for (int i=0; i<matrices.length;i++)
        {
            if (i!=index)
            {
                Matrix matrix1 = new Matrix(matrices[i].getMatrix().copy());
                matrices1[j] = matrix1;
                j++;
            }
        }
        return Matrix.merge(matrices1);
    }

    /**
     * 转换为数组变量
     * @return
     */
    public double[][] toArray(){
        return matrix.getArrayCopy();
    }

    public Jama.Matrix toJama(){
        return matrix.copy();
    }

    /**
     * 形成k维单位矩阵
     * @param w
     * @param h
     * @return
     */
    public static Matrix initI(int w, int h)
    {
        Jama.Matrix matrix = new Jama.Matrix(w, h);

        for (int i=0; i<w; i++)
        {
            for (int j=0; j<h;j++) {
                if (i!=j)
                    matrix.set(i,j,0.0);
                else
                    matrix.set(i,j,1.0);
            }
        }

        return new Matrix(matrix);
    }

    public Jama.Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Jama.Matrix matrix) {
        this.matrix = matrix;
    }

    public int getRowDimension(){
        return this.getMatrix().getRowDimension();
    }

    public int getColumnDimension(){
        return this.getMatrix().getColumnDimension();
    }

    public double get(int i,int j)
    {
        return matrix.get(i,j);
    }

    public void set( int i, int j, double d)
    {
        matrix.set(i, j, d);
    }

    public static boolean isZeroMatrix(Mat matrix)
    {
        boolean f = true;
        for (int i=0; i<matrix.rows();i++)
        {
            for (int j=0; j<matrix.cols();j++)
            {
                if (matrix.get(i,j)[0] < 220||matrix.get(i,j)[1] < 220||matrix.get(i,j)[2] < 220)
                    f = false;
            }
        }
        return f;
    }
    public static boolean isZeroMatrix(Matrix matrix)
    {
        boolean f = true;
        double d = matrix.get(0,0);
        for (int i=0; i<matrix.getRowDimension();i++)
        {
            for (int j=0; j<matrix.getColumnDimension();j++)
            {
                if (matrix.get(i,j)!=0)
                {
                    if (matrix.get(i,j)>0.01&&((matrix.get(i,j)>1.25*d||matrix.get(i,j)<0.75*d)))
                        f = false;
                }

            }
        }
        return f;
    }

    public boolean checkMatrix(){
        return isZeroMatrix(this.divideCol()[0])||
                isZeroMatrix(this.divideCol()[this.getColumnDimension()-1])||
                isZeroMatrix(this.divideRow()[0])||
                isZeroMatrix(this.divideRow()[this.getRowDimension()-1]);
    }

    @Override
    public String toString() {
        String str =  "Matrix{" +
                "width=" + matrix.getColumnDimension() +
                ", height=" + matrix.getRowDimension() +
                ", matrixList="+ System.lineSeparator();
        for (int i=0; i<matrix.getRowDimension(); i++)
        {
            for (int j=0; j<matrix.getColumnDimension(); j++)
            {
                str = str + matrix.get(i,j) + "\t";
            }
            str = str + System.lineSeparator();
        }
        char end = '}';
        str = str + end;
        return str;
    }

    public static Matrix[] MatToMatrix(Mat srcImage){
        Matrix[] matrix = new Matrix[3];
        for (int i=0; i<matrix.length;i++)
            matrix[i] = new Matrix(srcImage.height(), srcImage.width());
        for (int i=0; i<srcImage.height();i++)
        {
            for (int j=0; j<srcImage.width();j++)
            {
                matrix[0].set(i,j,srcImage.get(i, j)[0]);
                matrix[1].set(i,j,srcImage.get(i, j)[1]);
                matrix[2].set(i,j,srcImage.get(i, j)[2]);
            }
        }
        return matrix;
    }

}

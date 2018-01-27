package com.graduate.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 矩阵及其运算的封装
 */
public class Matrix implements Cloneable {
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

    public Matrix(double[] matrices, int N)
    {
        List<List<Double>> mlist = new ArrayList<>();
        for (int i=0; i<matrices.length/N; i++)
        {
            List<Double> list = new ArrayList<>();
            for (int j=0; j<N; j++)
            {
                list.add(matrices[i*N+j]);
            }
            mlist.add(list);
        }
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
    public void mulNumber(double k){

        for (int i = 0; i< matrixList.size();i++)
        {
            for (int j = 0; j < matrixList.get(i).size(); j++)
            {
                matrixList.get(i).set(j, k*matrixList.get(i).get(j));
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

    @Override
    public Object clone() {
        Matrix stu = null;
        try{
            stu = (Matrix) super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }

    /**
     * 矩阵求逆
     * @return
     */
    public Matrix getInverse(){
        Matrix matrix = this.getAdjoint();
        double d = 1/this.getValue();
        matrix.mulNumber(d);
        return matrix;
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


    /**
     * 求伴随矩阵
     * @return
     */
    public Matrix getAdjoint(){
        List<List<Double>> mlist = new ArrayList<>();
        for (int i=0; i<this.getWidth();i++)
        {
            List<Double> list = new ArrayList<>();
            for (int j=0; j<this.getHeight(); j++)
            {
                double d = this.getConfactor(i, j).getValue();
                list.add(d);
            }
            mlist.add(list);
        }
        Matrix matrix = new Matrix(mlist);
        matrix.reverse();
        return matrix;
    }

    /**
     * 求代数余子式
     * @param x
     * @param y
     * @return
     */
    public Matrix getConfactor(int x, int y)
    {
        boolean flag = true;
        if ((x+y)%2==1)
        {
            flag = false;
        }
        List<List<Double>> mlist = new ArrayList<>();
        for (int i=0; i<this.getWidth();i++)
        {
            if (i!=x) {
                List<Double> list = new ArrayList<>();
                for (int j = 0; j < this.get(i).size(); j++) {
                    if (j!=y)
                    {
                        double d = this.get(i).get(j);
                        list.add(d);
                    }
                }
                mlist.add(list);
            }
        }

        Matrix answer = new Matrix(mlist);
        if (!flag)
        {
            for (int i = 0; i<answer.get(0).size(); i ++)
            {
                answer.get(0).set(i, -answer.get(0).get(i));
            }
        }
        return answer;
    }

    /**
     * 矩阵行列式值计算
     * @param matrix 二维数组
     * @param N
     * @return
     */
    public static double Det(double [][]matrix,int N)//计算n阶行列式（N=n-1）
    {
        int T0;
        int T1;
        int T2;
        double Num;
        int Cha;
        double [][] B;
        if(N>0)
        {
            Cha=0;
            B=new double[N][N];
            Num=0;
            if(N==1)
            {
                return matrix[0][0]*matrix[1][1]-matrix[0][1]*matrix[1][0];
            }
            for(T0=0;T0<=N;T0++)//T0循环
            {
                for(T1=1;T1<=N;T1++)//T1循环
                {
                    for(T2=0;T2<=N-1;T2++)//T2循环
                    {
                        if(T2==T0)
                        {
                            Cha=1;

                        }
                        B[T1-1][T2]=matrix[T1][T2+Cha];
                    }//T2循环
                    Cha=0;
                }//T1循环
                Num=Num+matrix[0][T0]*Det(B,N-1)*Math.pow((-1),T0);
            }//T0循环
            return Num;
        }
        else if(N==0)
        {
            return matrix[0][0];
        }

        return 0;
    }
    /**
     * 矩阵行列式值计算
     * @return
     */
    public double getValue()//计算n阶行列式（N=n-1）
    {
        int T0;
        int T1;
        int T2;
        double Num;
        int N = this.getWidth()-1;
        int Cha;
        double [][] B;
        if(N>0)
        {
            Cha=0;
            B=new double[N][N];
            Num=0;
            if(N==1)
            {
                return this.get(0).get(0)*this.get(1).get(1)-
                        this.get(0).get(1)*this.get(1).get(0);
            }
            for(T0=0;T0<=N;T0++)//T0循环
            {
                for(T1=1;T1<=N;T1++)//T1循环
                {
                    for(T2=0;T2<=N-1;T2++)//T2循环
                    {
                        if(T2==T0)
                        {
                            Cha=1;

                        }
                        B[T1-1][T2]=this.get(T1).get(T2+Cha);
                    }//T2循环
                    Cha=0;
                }//T1循环
                Num=Num+this.get(0).get(T0)*Det(B,N-1)*Math.pow((-1),T0);
            }//T0循环
            return Num;
        }
        else if(N==0)
        {
            return this.get(0).get(0);
        }

        return 0;
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

    public List<Double> get(int index){
        return matrixList.get(index);
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
}

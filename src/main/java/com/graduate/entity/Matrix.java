package com.graduate.entity;

import Jama.SingularValueDecomposition;

import java.util.*;

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
    public Matrix reverse(){
        List<List<Double>> mlist = new ArrayList<>();
        for (int i=0; i<height; i++)
        {
            mlist.add(new ArrayList<Double>());
            for (int j=0; j<width; j++)
            {
                double d = matrixList.get(j).get(i);
                mlist.get(i).add(d);
            }
        }
        Matrix matrix = new Matrix(mlist);
        return matrix;
    }

    /**
     * 将矩阵所有元素取负
     */
    public Matrix mulNumber(double k){

        for (int i = 0; i< matrixList.size();i++)
        {
            for (int j = 0; j < matrixList.get(i).size(); j++)
            {
                matrixList.get(i).set(j, k*matrixList.get(i).get(j));
            }
        }
        return this;
    }

    /**
     * 矩阵相加
     * @param matrix
     */
    public Matrix plus(Matrix matrix){
        List<List<Double>> otherList = matrix.getMatrixList();
        List<List<Double>> answer = new ArrayList<>();
        for (int i = 0; i<width; i++)
        {
            List<Double> list = new ArrayList<>();
            for (int j = 0; j<height; j++)
            {
                list.add(matrixList.get(i).get(j)+otherList.get(i).get(j));
            }
            answer.add(list);
        }
        return new Matrix(answer);
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
        Matrix[] matrices = this.reverse().divideCol();
        for (int i=0; i<matrices.length; i++)
        {
            matrices[i] = matrices[i].reverse();
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
        Matrix matrix;
        if (this.getWidth()>1) {
            matrix = this.getAdjoint();
            double d = 1 / this.getValue();
            matrix.mulNumber(d);
        }
        else {
            double[] des = new double[1];
            des[0] = 1/this.get(0).get(0);
            matrix = new Matrix(des, 1);
        }
        return matrix;
    }

    /**
     * 矩阵相乘
     * @param matrix 乘矩阵
     */
    public Matrix multi(Matrix matrix){
        List<List<Double>> mlist = new ArrayList<>();
        Matrix[] rows = this.divideRow();
        Matrix[] cols = matrix.divideCol();
//        System.out.println(rows.length);
        for (int i = 0; i<cols.length; i++)
        {
            List<Double> list = new ArrayList<>();
            for (int j = 0; j<rows.length;j++)
            {
                list.add(Matrix.singleMulti(rows[j],cols[i]));
            }
            mlist.add(list);
        }
        matrixList = mlist;
        width = getMatrixList().size();
        height = getMatrixList().get(0).size();
        return this;
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

        return matrix.reverse();
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

    /**
     * 形成k维全一列向量
     * @param k
     * @return
     */
    public static Matrix initCol(int k)
    {
        List<List<Double>> mlist = new ArrayList<>();
        List<Double> list = new ArrayList<>();
        for (int i=0; i<k; i++)
        {
            list.add(1.0);
        }
        mlist.add(list);
        return new Matrix(mlist);
    }

    /**
     * 求矩阵特征值特征向量
     * @return
     */
    public Jama.EigenvalueDecomposition getSVD(){

        Jama.Matrix matrix = this.toJama();
        SingularValueDecomposition singularValueDecomposition =  matrix.svd();
        return matrix.eig();

    }

    public double[] getD(){
        Jama.Matrix matrix = this.getSVD().getD();
        double[] doubles = new double[this.getWidth()];
        for (int i=0; i<doubles.length; i++)
        {
            doubles[i] = matrix.get(i, i);
        }
        return doubles;
    }

    /**
     * 按特征值绝对值排序
     * @Return
     */
    public Matrix sort(){
        double[] doubles = this.getD();
        Matrix[] matrices = this.getV().divideCol();
        Map<Double, Matrix> map = new HashMap();
        DV[] dvs = new DV[doubles.length];
        for (int i=0; i<doubles.length;i++)
        {
            dvs[i] = new DV(matrices[i],doubles[i]);
        }
        Arrays.sort(dvs, new DVComparator());

        return Matrix.merge(DV.getMatrix(dvs),true);
    }

    public Matrix getV(){
        Jama.Matrix matrix = this.getSVD().getV();
        List<List<Double>> mlist = new ArrayList<>();
        for (int j=0; j<matrix.getColumnDimension();j++){
            List<Double> list = new ArrayList<>();
            for (int i=0; i<matrix.getRowDimension(); i++)
            {
                double d = matrix.get(i, j);
                list.add(d);
            }
            mlist.add(list);
        }
        return new Matrix(mlist);
    }

    public Matrix removeCol(int index){
        Matrix[] matrices = this.divideCol();
        Matrix[] matrices1 = new Matrix[matrices.length-1];
        int j=0;
        for (int i=0; i<matrices.length;i++)
        {
            if (i!=index)
            {
                matrices1[j] = matrices[i];
                j++;
            }
        }
        return Matrix.merge(matrices1, true);
    }

    /**
     * 转换为数组变量
     * @return
     */
    public double[][] toArray(){
        double[][]  matrices = new double[this.getWidth()][this.getHeight()];
        for (int i=0; i< this.getWidth();i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                matrices[i][j] = this.get(i).get(j);
            }
        }
        return matrices;
    }

    public Jama.Matrix toJama(){
        return new Jama.Matrix(this.toArray());
    }

    /**
     * 形成k维单位矩阵
     * @param w
     * @param h
     * @return
     */
    public static Matrix initI(int w, int h)
    {
        List<List<Double>> mlist = new ArrayList<>();

        for (int i=0; i<w; i++)
        {
            List<Double> list = new ArrayList<>();
            for (int j=0; j<h;j++) {
                if (i!=j)
                    list.add(0.0);
                else
                    list.add(1.0);
            }
            mlist.add(list);
        }

        return new Matrix(mlist);
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

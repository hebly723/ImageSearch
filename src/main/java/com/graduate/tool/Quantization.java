package com.graduate.tool;

import com.graduate.entity.Matrix;

import static java.lang.Math.pow;

public class Quantization {
    private Matrix matrix;
    private int dimention;
    private int N;
    public Quantization(Matrix matrix){
        this.matrix = matrix;
        dimention = matrix.getMatrix().getRowDimension();
        N = matrix.getMatrix().getColumnDimension();
    }

    public int[] getHash(){
        double[] doubles = this.variance();
        return getValue(doubles);
    }

    public double[] variance(){
        double[] doubles = getU();
        double[] variances = new double[N];
        for (int i=0; i<N; i++)
        {
            double sum = 0;
            for (int j=0; j<dimention;j++)
            {
                double d = matrix.getMatrix().get(j, i) - doubles[i];
//                System.out.println("d2="+d*d);
                sum += d*d;
            }
//            System.out.println("sum+"+sum);
            variances[i] = sum/(dimention-1);
        }
        return variances;
    }
    public double[] getU(){
        double[] doubles = new double[N];
        for (int i=0; i<N;i++)
        {
            double sum = 0;
            for (int j=0; j<dimention;j++)
            {
//                System.out.println("U"+"("+i+","+j+")"+matrix.getMatrix().get(j, i));
                sum+=matrix.getMatrix().get(j, i);
            }
            doubles[i] = sum/(double)dimention;
//            System.out.println("doubles["+i+"]"+doubles[i]);
        }
        return doubles;
    }

    public int[] getValue(double[] doubles){
        double u = 0;
        for (int i=0; i<doubles.length;i++)
        {
            u += doubles[i];
        }
        u /= doubles.length;
        int[] booleans = new int[doubles.length];
//        System.out.println("u="+u);
        for (int i=0; i<doubles.length;i++)
        {
            booleans[i] = Q(doubles[i], u);
        }
        return booleans;
    }

    public int Q(double i, double mean){
//        System.out.println("i="+i);
        if (i>mean)
            return 1;
        else
            return 0;
    }
}

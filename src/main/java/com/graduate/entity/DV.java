package com.graduate.entity;

import java.util.Comparator;

public class DV {
    private Matrix matrix;
    private double d;
    public DV(Matrix matrix, double d){
        this.matrix = matrix;
        this.d = d;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }
    public static Matrix[] getMatrix(DV[] dvs){
        Matrix[] matrices = new Matrix[dvs.length];
        for (int i=0; i<dvs.length; i++)
        {
            matrices[i] = dvs[i].getMatrix();
        }
        return matrices;
    }

    @Override
    public String toString() {
        return "DV{" +
                "matrix=" + matrix +
                ", d=" + d +
                '}';
    }
}

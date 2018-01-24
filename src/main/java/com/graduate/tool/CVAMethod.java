package com.graduate.tool;

import org.opencv.core.Mat;

/**
 * 实现颜色向量角的相关操作
 */
public class CVAMethod {
    /**
     * 获取图像中的RGB均值
     * @param mat 原图像
     * @return 图像RGB均值
     */
    public static Pixel calAverage(Mat mat)
    {
        Pixel pixel = new Pixel();
        for (int i=0;i<mat.height();i++)
        {
            for (int j=0;j<mat.width();j++)
            {
                pixel.plus(mat.get(i, j));
            }
        }
        pixel.divide(mat.height()*mat.width());
        return pixel;
    }
    public static double[][] getCVA(Mat mat, Pixel ref){
        double[][] array= new double[mat.height()][mat.width()];
        for(int i=0; i<mat.height();i++)
            for (int j=0;j<mat.width();j++)
            {
                Pixel pixel = new Pixel(mat.get(i,j));
                array[i][j] = pixel.getSin(ref);
            }
        return array;
    }

}

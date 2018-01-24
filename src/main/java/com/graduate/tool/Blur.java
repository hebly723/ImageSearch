package com.graduate.tool;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


/**
 * 滤波
 */
public class Blur {
    /**
     * 高斯滤波
     * @param mat 原图像
     * @param modelX 模板宽
     * @param modelY 模板高
     * @param o1 x方向方差
     * @param o2 y方向方差
     * @return 处理后的图像
     */
    public Mat GaussianBlurD(Mat mat, int modelX, int modelY, double o1, double o2)
    {
        Mat result = new Mat();
        Size s = new Size(modelX,modelY);
        Imgproc.GaussianBlur(mat,result,new Size(modelX, modelY), o1, o2);
        return result;
    }
}

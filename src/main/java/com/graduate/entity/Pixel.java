package com.graduate.entity;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * 像素点
 */
public class Pixel {
    public static double zero = 0.0001;
    double r, g, b;
    public Pixel() {
        r = 0;
        g = 0;
        b = 0;
    }
    public Pixel(double [] d)
    {
        r = d[0];
        g = d[1];
        b = d[2];
    }
    /**
     * 加操作
     * @param d RGB数组
     */
    public void plus(double[] d)
    {
        r += d[0];
        g += d[1];
        b += d[2];
    }

    /**
     * 除操作
     * @param d 除数
     */
    public void divide(double d)
    {
        r /= d;
        g /= d;
        b /= d;
    }

    /**
     * 两像素点相乘
     * @param pixel 被乘像素点
     * @param anoPixel 乘像素点
     * @return 积值
     */
    public double multiPixel(Pixel pixel, Pixel anoPixel)
    {
        return pixel.r*anoPixel.r+
                pixel.g*anoPixel.g+
                pixel.b*anoPixel.b;
    }

    /**
     * 获取当前像素点的颜色向量角
     * @param ref 参考像素点
     * @return 颜色向量角值
     */
    public double getSin(Pixel ref)
    {
        double up =  pow(multiPixel(this, ref), 2);
        double down = multiPixel(this, this)*
                multiPixel(ref, ref);
        if (down == 0)
            down = zero;
        double k = up/down;
//        System.out.println(up+"|"+down);
        return sqrt(1-k);
    }

}

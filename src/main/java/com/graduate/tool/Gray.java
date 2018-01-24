package com.graduate.tool;

import static com.graduate.tool.CvMath.cvRound;

/**
 * 感知hash算法中对灰度值的操作
 */
public class Gray {
    /**
     * rgb值转化为灰度值
     * @param pixels 彩色RGB值(Red-Green-Blue 红绿蓝)
     * @return int 灰度值
     */
    public static int rgbToGray(double[] pixels) {
        // int _alpha =(pixels >> 24) & 0xFF;
        switch(pixels.length) {
            case 3:
               int _red = (int) pixels[0];
               int _green = (int) pixels[1];
               int _blue = (int) pixels[2];
               return cvRound (0.3 * _red + 0.59 * _green + 0.11 * _blue);
            case 1:
               return cvRound(pixels[0]);
            default:
               return 0;
        }
    }
}

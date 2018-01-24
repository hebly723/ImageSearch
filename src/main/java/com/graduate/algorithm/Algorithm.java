package com.graduate.algorithm;

import org.opencv.core.Mat;

public interface Algorithm {
    public String hashString(Mat mat);

    /**
     * 比较两张图片的相似度
     * @param src 原图片
     * @param newString 新图片
     * @return 相似度
     */
    public double hashCompare(String src, String newString);
}

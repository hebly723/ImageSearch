package com.graduate.algorithm.impl;

import com.graduate.algorithm.Algorithm;
import com.graduate.tool.DCT_Trans;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import static com.graduate.tool.CvMath.cvRound;
import static com.graduate.tool.Gray.rgbToGray;
import static org.opencv.core.CvType.CV_32S;
import static org.opencv.imgproc.Imgproc.INTER_CUBIC;
import static org.opencv.imgproc.Imgproc.resize;

/**
 * 使用感知hash算法实现hash字符串的生成
 */
public class PHA2 implements Algorithm {

    /**
     * 生成hash字符串
     * @return
     */
    public String hashString(Mat mat)
    {
        /**
         * 第一步，调用opencv自带函数缩小图片的尺寸
         */
        Size dSize = new Size(32, 32);
        Mat thumbImage = new Mat(dSize,CV_32S);
        resize(mat, thumbImage, dSize, 0, 0, INTER_CUBIC);

        /**
         * 第二步，将RGB值转化为灰度
         */
        double[] pixels = new double[64];
        for (int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                pixels[i* 32 + j] = rgbToGray(thumbImage.get(i, j));
            }
        }

        /**
         * 第四步，计算灰度区域平均值
         */
        int avgPixel= 0;
        int m = 0;
        for (int i =0; i < pixels.length; ++i) {
            m +=pixels[i];
        }
        m = cvRound(m /(double)pixels.length);
        avgPixel = m;
        /**
         * 第五步，建立8×8数组，遍历图像所有区域
         * 灰度值在平均值以上的令其在数组中的相应位置取值等于1,在平均值以下的令其等于0
         */
        int[] comps= new int[64];
        for (int i = 0; i < comps.length; i++) {
            if(pixels[i] >= avgPixel) {
                comps[i]= 1;
            }else {
                comps[i]= 0;
            }
        }
        /**
         * 最后，将获得的数组转为十六进制字符串
         */
        StringBuffer hashCode = new StringBuffer();
        for (int i = 0; i < comps.length; i+= 4) {
            int result = comps[i] * (int) Math.pow(2, 3) +
                    comps[i + 1] * (int) Math.pow(2, 2) +
                    comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 2];
            hashCode.append(Integer.toHexString(result));//二进制转为16进制
        }
        String sourceHashCode = hashCode.toString();
        return sourceHashCode;
    }

    @Override
    public double hashCompare(String src, String newString) {
        int difference = 0;
        int len =src.length();
        String bSrc = hexStrToBinaryStr(src);
        String bNew = hexStrToBinaryStr(newString);
        System.out.println(bSrc);
        System.out.println(bNew);
        for (int i = 0; i < len; i++) {
            if(bSrc.charAt(i) != bNew.charAt(i)) {
                difference++;
            }
        }

        return (1 - ((double)difference)/bSrc.length());
    }

    /**
     * 模拟运行流程， 打印相似度
     * @param src 源图像
     * @param newMat 新比对图像
     */
    public static void PHAProgress( Mat src, Mat newMat)
    {
        Algorithm algorithm;
        algorithm = new PHA2();
        System.out.println( "相似度为" + algorithm.hashCompare( algorithm.hashString(src),
                algorithm.hashString(newMat))*100+"%");
    }
    /**
     * 将十六进制的字符串转换成二进制的字符串
     *
     * @param hexString 十六进制字符串
     * @return
     */
    public static String hexStrToBinaryStr(String hexString) {

        if (hexString == null || hexString.equals("")) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        // 将每一个十六进制字符分别转换成一个四位的二进制字符
        for (int i = 0; i < hexString.length(); i++) {
            String indexStr = hexString.substring(i, i + 1);
            String binaryStr = Integer.toBinaryString(Integer.parseInt(indexStr, 16));
            while (binaryStr.length() < 4) {
                binaryStr = "0" + binaryStr;
            }
            sb.append(binaryStr);
        }

        return sb.toString();
    }


}

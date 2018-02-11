package com.graduate.algorithm.impl;

import com.graduate.algorithm.Algorithm;
import com.graduate.algorithm.Reduce;
import com.graduate.entity.Matrix;
import com.graduate.entity.Pixel;
import com.graduate.tool.*;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import java.util.ArrayList;
import java.util.List;

import static com.graduate.algorithm.impl.PHA.hexStrToBinaryStr;
import static com.graduate.entity.Pixel.zero;
import static com.graduate.tool.CvMath.Fisher_Yates;
import static com.graduate.tool.CvMath.getUR;
import static com.graduate.tool.CvMath.password;
import static org.opencv.core.CvType.CV_32S;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.INTER_CUBIC;
import static org.opencv.imgproc.Imgproc.resize;

public class CVA implements Algorithm {
    @Override
    public String hashString(Mat originImage) {

        Mat srcImage = originImage;
        /**
         * 先进行规格化，化成512×512的矩阵
         */
        Size dSize = new Size( 64, 64);
        Mat originCub = new Mat(dSize,CV_32S);
        resize( srcImage, originCub, dSize, 0, 0, INTER_CUBIC);
        /**
         * 通过高斯滤波
         */
        Blur blur = new Blur();
        Mat blurMat = blur.GaussianBlurD(originCub,3,3,16,16);
        /**
         * 提取颜色向量角，生成矩阵
         */
        Pixel ref = CVAMethod.calAverage(blurMat);
        double[][] array = CVAMethod.getCVA(blurMat, ref);

        Matrix matrix1 = new Matrix(array);
//        for (int i=0; i<30; i++)
//        {
//            for (int j=0; j<array[i].length;j++)
//                System.out.print(matrix1.get(i,j)+"\t");
//            System.out.println();
//        }

//        System.out.println("originImage:cols="+originImage.cols()+";rows="+originImage.rows());

        int allowance = 0;
        while (matrix1.checkMatrix()) {

            srcImage = Reduce.cut(matrix1, srcImage);
            dSize = new Size( 64, 64);
            originCub = new Mat(dSize,CV_32S);
            resize( srcImage, originCub, dSize, 0, 0, INTER_CUBIC);
            /**
             * 通过高斯滤波
             */
//            blur = new Blur();
            blurMat = blur.GaussianBlurD(originCub,3,3,16,16);
            /**
             * 提取颜色向量角，生成矩阵
             */
            ref = CVAMethod.calAverage(blurMat);
            array = CVAMethod.getCVA(blurMat, ref);

            matrix1 = new Matrix(array);

//            if (allowance > 50)
//                break;
//            allowance++;
        }

//        System.out.println("srcImage:cols="+srcImage.cols()+";rows="+srcImage.rows());

        originCub = new Mat(dSize,CV_32S);

        imwrite("/home/hebly723/1.jpg", srcImage);

        /**
         * 先进行规格化，化成512×512的矩阵
         */
        dSize = new Size( 512, 512);
        resize( srcImage, originCub, dSize, 0, 0, INTER_CUBIC);
        /**
         * 通过高斯滤波
         */
        blurMat = blur.GaussianBlurD(originCub,3,3,16,16);
        /**
         * 提取颜色向量角，生成矩阵
         */
        ref = CVAMethod.calAverage(blurMat);
        array = CVAMethod.getCVA(blurMat, ref);

        ArrayList<double[]> dctlMat = new ArrayList<double[]>();

        /**
         * 分块
         */
        for (int i = 0; i < blurMat.width()/64; i++)
        {
            for (int j = 0; j < blurMat.width() / 64; j++) {
                /**
                 * 作DCT变换，取前几个作为特征向量，生成特征矩阵
                 */
                DCT_Trans dct_trans = new DCT_Trans(64);
                double[] doubles = dct_trans.CVADCTProgressD(array, i * 64, j * 64, 64);
                    dctlMat.add(doubles);
            }
        }
        Matrix dctMat = new Matrix(dctlMat);
        /**
         * 计算相同余弦位置的方差和标准差
         */
        List<double[]> urList = getUR(dctlMat);
        /**
         * 归一，类似正态
         */
        List<List<Double>> sList = new ArrayList<List<Double>>();
        for (int i = 0; i < dctMat.getColumnDimension(); i++) {
            List<Double> sdlist = new ArrayList<Double>();
            for (int j = 0; j < dctMat.getRowDimension(); j++) {
                double k = urList.get(j)[1];
                if (k==0)
                    k = zero;
                double d = (dctMat.get(j, i) - urList.get(j)[0])/k;
                sdlist.add(d);
            }
            sList.add(sdlist);
        }
        /**
         * 置乱
         */
        Fisher_Yates(sList, password);
        Matrix matrix = new Matrix(sList);

        LLEArg lleArg = new LLEArg();
        lleArg.setDimention(40);
        lleArg.setNeighborhoodNumber(15);
        Matrix lleAnswer =  lleArg.getAnswer(matrix);

        Quantization quantization = new Quantization(lleAnswer);

        int[] comps = quantization.getHash();
        StringBuffer hashCode = new StringBuffer();

        for (int i = 0; i < comps.length; i+= 4) {
            int result = comps[i] * (int) Math.pow(2, 3) +
                    comps[i + 1] * (int) Math.pow(2, 2) +
                    comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 3];
            hashCode.append(Integer.toHexString(result));//二进制转为16进制
        }
        String sourceHashCode = hashCode.toString();

        return sourceHashCode;

//        return null;
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
}

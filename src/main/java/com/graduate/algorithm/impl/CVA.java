package com.graduate.algorithm.impl;

import com.graduate.algorithm.Algorithm;
import com.graduate.entity.Matrix;
import com.graduate.entity.Pixel;
import com.graduate.tool.*;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import java.util.ArrayList;
import java.util.List;

import static com.graduate.algorithm.impl.PHA.hexStrToBinaryStr;
import static com.graduate.tool.CvMath.Fisher_Yates;
import static com.graduate.tool.CvMath.getUR;
import static com.graduate.tool.CvMath.password;
import static org.opencv.core.CvType.CV_32S;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.INTER_CUBIC;
import static org.opencv.imgproc.Imgproc.resize;

public class CVA implements Algorithm {
    @Override
    public String hashString(Mat srcImage) {
//        /**
//         * 获取图片
//         */
//        Mat srcImage = imread("image/山脉.png");
//        ImageViewer srcImageViewer = new ImageViewer(srcImage, "原图");
//        srcImageViewer.imshow();

        /**
         * 先进行规格化，化成512×512的矩阵
         */
        Size dSize = new Size( 512, 512);
        Mat originCub = new Mat(dSize,CV_32S);
        resize( srcImage, originCub, dSize, 0, 0, INTER_CUBIC);
//        ImageViewer originImageView =
//                new ImageViewer( originCub, "opencv自带函数规格化结果");
//        originImageView.imshow();
        /**
         * 通过高斯滤波
         */
        Blur blur = new Blur();
        Mat blurMat = blur.GaussianBlurD(originCub,3,3,16,16);
//        ImageViewer blurImageViewer = new ImageViewer(blurMat, "滤波结果");
//        blurImageViewer.imshow();
        /**
         * 提取颜色向量角，生成矩阵
         */
        Pixel ref = CVAMethod.calAverage(blurMat);
        double[][] array = CVAMethod.getCVA(blurMat, ref);
        ArrayList<double[]> dctMat = new ArrayList<double[]>();
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
                dctMat.add(dct_trans.CVADCTProgressD(array, i * 64, j * 64, 64));
            }
        }
        /**
         * 计算相同余弦位置的方差和标准差
         */
        List<double[]> urList = getUR(dctMat);
        /**
         * 归一，类似正态
         */
        int dctMatLength = dctMat.get(0).length;
        List<List<Double>> sList = new ArrayList<List<Double>>();
        for (int i = 0; i < dctMat.size(); i++) {
            List<Double> sdlist = new ArrayList<Double>();
            for (int j = 0; j < dctMat.get(0).length; j++) {
                sdlist.add((dctMat.get(i)[j] - urList.get(j)[0])/urList.get(j)[1]);
            }
            sList.add(sdlist);
        }
        /**
         * 置乱
         */
//        System.out.println("特征矩阵列向量数"+sList.size());
        Fisher_Yates(sList, password);
//        for (List<Double> dlist: sList) {
//            for (double de: dlist) {
//                //System.out.print(de+"\t");
//            }
//            //System.out.println();
//        }

        Matrix matrix = new Matrix(sList);

        System.out.println(matrix);

        LLEArg lleArg = new LLEArg();
        lleArg.setDimention(40);
        lleArg.setNeighborhoodNumber(15);

        Matrix lleAnswer =  lleArg.getAnswer(matrix);

//        System.out.println(lleAnswer);

        Quantization quantization = new Quantization(lleAnswer);

//        System.out.println();

        int[] comps = quantization.getHash();
//        for (int i=0; i<booleans.length;i++)
//            System.out.print(booleans[i]);

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
}

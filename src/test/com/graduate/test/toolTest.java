package com.graduate.test;
import com.graduate.algorithm.Algorithm;
import com.graduate.algorithm.impl.CVA;
import com.graduate.algorithm.impl.PHA;
import com.graduate.algorithm.impl.PHA2;
import com.graduate.tool.*;
import org.opencv.core.*;

import java.util.ArrayList;
import java.util.List;

import static com.graduate.tool.StringMethod.strictTrim;
import static org.opencv.core.CvType.CV_32S;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.INTER_CUBIC;
import static org.opencv.imgproc.Imgproc.resize;

/**
 * 测试程序
 */
public class toolTest {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
        public static void main(String[] args) {
//            progressCVA();
            progressPHA();
//            testCub();
//            dct_test();
//            dct_run_test();
//            StringTest();
    }

    public static void dct_run_test(){
        double[][] matrix = new double[8][8];
        for (int i=1; i<9; i++)
            for (int j=1; j<9; j++)
                matrix[i-1][j-1] = i*10 + j;
        for (int i=0; i<8; i++)
        {
            for (int j = 0; j < 8; j++)
                System.out.print(j+""+i+"   ");
            System.out.println();
        }
        System.out.println(1);
        DCT_Trans dct_trans = new DCT_Trans(matrix[0].length);
        double[] d = dct_trans.zScan(matrix);
//        for (int i=0; i<d.length;i++)
//            System.out.println(d[i]);
        double[] cArray = dct_trans.cutArray(d, (int)(d.length*((8.0-1.0)/8.0)));
        for (int i = 0; i < cArray.length; i++) {
            System.out.println(cArray[i]);
        }
        System.out.println(cArray.length);
    }
    /**
     * 演练一下颜色向量角hash码大概的提取流程
     */
    public static void progressCVA()
    {
        /**
         * 获取图片
         */
        Mat srcImage = imread("src/main/resources/image/山脉.png");
        ImageViewer srcImageViewer = new ImageViewer(srcImage, "原图");
        srcImageViewer.imshow();
        CVA cva = new CVA();
        cva.hashString(srcImage);
//        DCT_Trans dct_trans = new DCT_Trans();
    }
    /**
     * 演练一下感知hash算法大概的流程
     */
    public static void progressPHA()
    {
        Algorithm algorithm;
        algorithm = new PHA();
        Blur blur = new Blur();

        Mat srcImage = imread("/home/hebly723/Lenovo/graduateWork/image/自然(31)/8.jpg");
        Mat anoImage = imread("/home/hebly723/Lenovo/graduateWork/image/自然(31)/9.jpg");
        ImageViewer srcImageViewer = new ImageViewer(srcImage, "原图");
        srcImageViewer.imshow();
        Mat blurMat = blur.GaussianBlurD(srcImage,3,3,1600,1600);
        ImageViewer blurImageViewer = new ImageViewer(anoImage, "滤波结果");
        blurImageViewer.imshow();
        PHA.PHAProgress(srcImage, anoImage);
//
//        List<Mat> channels = new ArrayList<Mat>();
//        List<Mat> channels2 = new ArrayList<Mat>();
//        Core.split(srcImage,channels);
//        ImageViewer channelImageViewer = new ImageViewer(channels.get(1), "1图");
//        channelImageViewer.imshow();
//        Core.split(blurMat,channels2);
//        ImageViewer channel2ImageViewer = new ImageViewer(channels.get(0), "2图");
//        channel2ImageViewer.imshow();
//        PHA.PHAProgress(channels.get(1), channels.get(2));
    }
    public static void testCub()
    {
        Mat srcImage = imread("image/1.jpg");
        Size dSize = new Size( srcImage.width()*3.1, srcImage.height()*3.1);
        Mat originCub = new Mat(dSize,CV_32S);
        resize( srcImage, originCub, dSize, 0, 0, INTER_CUBIC);
        ImageViewer originImageView =
                new ImageViewer( originCub, "opencv自带函数规格化结果");
        originImageView.imshow();
    }
    public static void dct_test() {
        Mat mat = imread("image/标准.png");
        Size dSize = new Size(mat.width(), mat.width());
        Mat originCub = new Mat(dSize, CV_32S);
        resize(mat, originCub, dSize, 0, 0, INTER_CUBIC);
        /**
         * 分通道
         */
        List<Mat> channels = new ArrayList<Mat>();
        Core.split(mat, channels);
        System.out.println(channels.size());

        ImageViewer imageViewer0 = new ImageViewer(channels.get(0), "0");
        imageViewer0.imshow();
        ImageViewer imageViewer1 = new ImageViewer(channels.get(1), "1");
        imageViewer1.imshow();
        ImageViewer imageViewer2 = new ImageViewer(channels.get(2), "2");
        imageViewer2.imshow();
        DCT_Trans dct_trans = new DCT_Trans(mat.width());
        double[][][] d = new double[3][mat.width()][mat.width()];
        for (int i = 0; i < mat.width(); i++) {
            for (int j = 0; j < mat.width(); j++)
                for (int k = 0; k < 3; k++)
                    d[k][i][j] = (channels.get(k).get(i, j))[0];
        }
        double[][] newD = dct_trans.DCT(d[0]);
        Size daSize = new Size(newD[0].length, newD[0].length);
        Mat newMat = new Mat(daSize, CV_32S);
        for (int i = 0; i < newD[0].length; i++) {
            for (int j = 0; j < newD[0].length; j++) {
                double[] array = new double[1];
                array[0] = newD[i][j];
                newMat.put(i, j, array);
                System.out.print(newD[i][j]+"   ");
            }
            System.out.println();
        }
        System.out.println(newMat.toString());
//        ImageViewer dctimageViewer = new ImageViewer(newMat, "DCT");
//        dctimageViewer.imshow();
    }
    public static void StringTest(){
        String str = "自然12";
        String stl = strictTrim(str);
        System.out.println(stl);
        System.out.println(str.substring(str.length()-stl.length(), str.length()));
    }
}

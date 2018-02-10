package com.graduate.test;

import com.graduate.algorithm.Algorithm;
import com.graduate.algorithm.Reduce;
import com.graduate.algorithm.impl.CVA;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import static org.opencv.imgcodecs.Imgcodecs.imread;

/**
 * 处理多空白图片的测试
 */
public class CvaTest {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    String str;
    Mat mat;
    String ans;
    Algorithm algorithm;
    @Before
    public void init(){
        algorithm = new CVA();
    }

//    @Test
//    public void CutTest()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/1.jpg";
//        mat = imread(str);
//        Reduce.cut(mat);
//    }

//    @Test
//    public void getCvaPeople1()
//    {
//        System.out.println("getCvaPeople1");
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/1.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaPeople2()
//    {
//        System.out.println("getCvaPeople2");
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/2.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
    @Test
    public void getCvaPeople3()
    {
        System.out.println("getCvaPeople3");
        str = "/media/hebly723/Lenovo/graduateWork/image/people/3.jpg";
        mat = imread(str);
        ans = algorithm.hashString(mat);
        System.out.println(ans);
    }
//    @Test
//    public void getCvaPeople4()
//    {
//
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/4.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaPeople5()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/5.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaPeople8()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/8.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaPeople10()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/10.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaPeople11()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/11.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaPeople12()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/12.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
    @Test
    public void getCvaPeople13()
    {
        str = "/media/hebly723/Lenovo/graduateWork/image/people/13.jpg";
        mat = imread(str);
        ans = algorithm.hashString(mat);
        System.out.println(ans);
    }
//    @Test
//    public void getCvaPeople14()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/14.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaPeople16()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/16.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
    @Test
    public void getCvaPeople18()
    {
        str = "/media/hebly723/Lenovo/graduateWork/image/people/18.jpg";
        mat = imread(str);
        ans = algorithm.hashString(mat);
        System.out.println(ans);
    }
//    @Test
//    public void getCvaPeople19()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/19.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaPeople21()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/21.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
    @Test
    public void getCvaPeople22()
    {
        str = "/media/hebly723/Lenovo/graduateWork/image/people/22.jpg";
        mat = imread(str);
        ans = algorithm.hashString(mat);
        System.out.println(ans);
    }
//    @Test
//    public void getCvaPeople23()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/23.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaPeople24()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/people/24.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }

//    /**
//     * 运动图片，这里主要是背景不为白
//     */
//    @Test
//    public void getCvaSports5()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/sports/5.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaSports9()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/sports/9.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
    @Test
    public void getCvaAnimal2()
    {
        str = "/media/hebly723/Lenovo/graduateWork/image/animal/2.jpg";
        mat = imread(str);
        ans = algorithm.hashString(mat);
        System.out.println(ans);
    }
//    @Test
//    public void getCvaAnimal19()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/animal/19.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaAnimal24()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/animal/24.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaAnimal26()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/animal/26.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
//    @Test
//    public void getCvaAnimal29()
//    {
//        str = "/media/hebly723/Lenovo/graduateWork/image/animal/29.jpg";
//        mat = imread(str);
//        ans = algorithm.hashString(mat);
//        System.out.println(ans);
//    }
    @Test
    public void getCvaBird14()
    {
        str = "/media/hebly723/Lenovo/graduateWork/image/bird/14.jpg";
        mat = imread(str);
        ans = algorithm.hashString(mat);
        System.out.println(ans);
    }
    @Test
    public void getCvaBird19()
    {
        str = "/media/hebly723/Lenovo/graduateWork/image/bird/19.jpg";
        mat = imread(str);
        ans = algorithm.hashString(mat);
        System.out.println(ans);
    }
    @Test
    public void getCvaBird26()
    {
        str = "/media/hebly723/Lenovo/graduateWork/image/bird/26.jpg";
        mat = imread(str);
        ans = algorithm.hashString(mat);
        System.out.println(ans);
    }
}

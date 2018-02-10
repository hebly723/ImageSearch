package com.graduate.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class CvMath {
    public static int[] password = {
            52,38,47,57,24,11,19,48,4,19,46,
            35,43,50,34,26,11,3,37,5,6,18,15,
            9,12,17,32,7,11,24,3,26,0,22,10,
            23,8,18,25,13,22,16,15,20,12,15,
            12,14,8,9,6,5,7,3,7,2,2,1,1,3,1,1,1
    };
    /**
     * 四舍五入
     * @param a 双精度数
     * @return 四舍五入之后的整数
     */
    public static int cvRound(double a)
    {
        int n = (int)a;
        if (a-n>=0.5)
            n++;
        return n;
    }

    /**
     * 计算给定数列每一数组元素中相同位置的均值和方差
     * @param dctMat 原数列
     * @return 一个数列，每个元素的0位是对应位置的均值，1位是对应位置的方差
     */
    public static List<double[]> getUR(ArrayList<double[]> dctMat)
    {
        int arrayLength = dctMat.get(0).length;
        List<double[]> urList = new ArrayList<double[]>();
        for (int i = 0; i < arrayLength; i++) {
            double[] ura = new double[2];
            double sum = 0;
            for (int j = 0; j < dctMat.size(); j++)
            {
                sum += dctMat.get(j)[i];
            }

            ura[0] = sum/dctMat.size();
//            System.out.println(sum);
            sum = 0;
            for (int j = 0; j < dctMat.size(); j++)
            {
                sum += pow(dctMat.get(j)[i] - ura[0], 2);
            }
            ura[1] = sqrt(sum/(dctMat.size()-1));
//            System.out.println(sum);
//            System.out.println("dctMat.size()"+dctMat.size()+"\t"+ura[0]+"\t"+ura[1]);
            urList.add(ura);
        }
        return urList;
    }

    /**
     * 费雪耶兹随机置乱算法
     * @param originList 原矩阵
     * @param pass 密钥
     * @return 置乱后的矩阵
     */
    public static List<List<Double>> Fisher_Yates(List<List<Double>> originList, int[] pass)
    {
        for (int i = originList.size() - 1; i > 0; i--) {
            //随机数生成器，范围[0, i]
//            int rand = (new Random()).nextInt(i+1);
            int rand = pass[pass.length - i];
//            System.out.print(" " + rand);
            List temp = originList.get(i);
            originList.set(i, originList.get(rand));
            originList.set(rand,temp);
//            arr[i] = arr[rand];
//            arr[rand] = temp;
        }
        return originList;
    }
    /**
     * 费雪耶兹随机置乱算法
     * @param originList 原矩阵
     * @return 置乱后的矩阵
     */
    public static List<List<Double>> Fisher_Yates(List<List<Double>> originList)
    {
        for (int i = originList.size() - 1; i > 0; i--) {
            //随机数生成器，范围[0, i]
            int rand = (new Random()).nextInt(i+1);
//            int rand = pass[i];
            System.out.print(" " + rand);
            List temp = originList.get(i);
            originList.set(i, originList.get(rand));
            originList.set(rand,temp);
//            arr[i] = arr[rand];
//            arr[rand] = temp;
        }
        return originList;
    }

}

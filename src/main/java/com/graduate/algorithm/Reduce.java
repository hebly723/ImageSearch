package com.graduate.algorithm;

import com.graduate.entity.Matrix;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;

import static org.opencv.core.CvType.CV_16S;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.INTER_CUBIC;
import static org.opencv.imgproc.Imgproc.resize;

public class Reduce {

    public static Mat cut(Mat srcImage){
        /**
         * 先进行规格化，化成16×16的矩阵
         */
        int size = 512;
        Size dSize = new Size( size, size);
        Mat originCub = new Mat(dSize,CV_16S);
        resize( srcImage, originCub, dSize, 0, 0, INTER_CUBIC);

        boolean[] ws = new boolean[size];
        boolean[] hs = new boolean[size];

        for (int i = 0; i<size; i++)
        {
            ws[i] = Matrix.isZeroMatrix(originCub.col(i));
        }
        for (int j = 0; j<size; j++)
        {
            hs[j] = Matrix.isZeroMatrix(originCub.row(j));
        }

        int bw, bh, fw, fh;

        for (bw = 0; bw < size; bw++)
        {
            if (!ws[bw])
                break;
        }

        for (fw = size - 1; fw >= 0; fw--)
        {
            if (!ws[fw])
                break;
        }

        for (bh = 0; bh < size; bh++)
        {
            if (!hs[bh])
                break;
        }

        for (fh = size - 1; fh >= 0; fh--)
        {
            if (!hs[fh])
                break;
        }
        Mat newImage = srcImage.submat((bh*srcImage.height())/size, ((fh+1)*srcImage.height())/size,
                (bw*srcImage.width())/size, ((fw+1)*srcImage.width())/size);

//        imwrite("/home/hebly723/1.jpg", newImage);

        return newImage;
    }
    public static Mat cut(Matrix matrix, Mat srcImage){
        /**
         * 先进行规格化，化成16×16的矩阵
         */

        boolean[] ws = new boolean[matrix.getColumnDimension()];
        boolean[] hs = new boolean[matrix.getRowDimension()];

//        System.out.println(matrix.divideCol()[63]);
        for (int i = 0; i<matrix.getColumnDimension(); i++)
        {
            ws[i] = Matrix.isZeroMatrix(matrix.divideCol()[i]);
        }
        for (int j = 0; j<matrix.getRowDimension(); j++)
        {
            hs[j] = Matrix.isZeroMatrix(matrix.divideRow()[j]);
        }

        int bw, bh, fw, fh;

        for (bw = 0; bw < matrix.getColumnDimension(); bw++)
        {
            if (!ws[bw])
                break;
        }

        for (fw = matrix.getColumnDimension() - 1; fw >= 0; fw--)
        {
            if (!ws[fw])
                break;
        }

        for (bh = 0; bh < matrix.getRowDimension(); bh++)
        {
            if (!hs[bh])
                break;
        }

        for (fh = matrix.getRowDimension() - 1; fh >= 0; fh--)
        {
            if (!hs[fh])
                break;
        }
//        System.out.println("bw"+bw+"\n"+
//                "bh"+bh+"\n"+
//                "fw"+fw+"\n"+
//                "fh"+fh+"\n");
        Mat newImage = srcImage.submat((bh*srcImage.height())/matrix.getRowDimension(),
                ((fh+1)*srcImage.height())/matrix.getRowDimension(),
                (bw*srcImage.width())/matrix.getColumnDimension(),
                ((fw+1)*srcImage.width())/matrix.getColumnDimension());

//        imwrite("/home/hebly723/1.jpg", newImage);

        return newImage;
    }

}

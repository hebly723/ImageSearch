package com.graduate.test;

import com.graduate.algorithm.Algorithm;
import com.graduate.algorithm.Reduce;
import com.graduate.algorithm.impl.CVA;
import com.graduate.algorithm.impl.PHA;
import com.graduate.dao.*;
import com.graduate.entity.*;
import com.graduate.tool.ImageViewer;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.graduate.tool.StringMethod.strictTrim;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class CvaDaoTest extends BaseTest {
  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ImageDao imageDao;
  @Autowired
  private HashcvaDao hashcvaDao;

  @Test
  public void insertImage() throws InterruptedException {
//    ImageExample imageExample = new ImageExample();
//    ImageExample.Criteria criteria = imageExample.createCriteria();
//    criteria.andIdIsNotNull();
//    List<Image> images = imageDao.selectByExample(imageExample);
//    for (Image image:images) {
//      System.out.println(image);
//    }
//    http://og7tyqvd1.bkt.clouddn.com/image/test/animal/1.jpg
//    Size dSize = new Size( 512, 512);
//    Mat mat = new Mat(dSize,CV_32S);
//    Mat mat = imread("/home/hebly723/Lenovo/SSM_BookSystem-master/ImageSearch/src/main/resources/image/山脉.png");
//    Mat mat = imread("src/main/resources/image/山脉.png");
//    ImageViewer imageViewer = new ImageViewer(mat, "山脉");
//    imageViewer.imshow();
//    imshow("huo", mat);
//    waitKey(100);
//    System.out.println(mat.width()+"------------"+mat.height());
    String location = "/media/hebly723/Lenovo/graduateWork/image";
    int dir[] = {24, 20, 33, 36, 31, 31, 34, 43};
    String[] eng = {
            "people",
            "sports",
            "comic",
            "animal",
            "life",
            "nature",
            "city",
            "bird"
    };
    Map<String, Integer> map = new HashMap();
    for (int i = 1; i < eng.length + 1; i++) {
      for (int j = 1; j < dir[i - 1] + 1; j++) {
        ImageExample imageExample = new ImageExample();
        imageExample.createCriteria().andDetailEqualTo(eng[i - 1] + j);
        Hashcva hashcva = new Hashcva();
        hashcva.setId(imageDao.selectByExample(imageExample).get(0).getId());
        Algorithm algorithm = new CVA();
        Mat mat = imread(location + "/" + eng[i - 1] + "/" + j + ".jpg");
        hashcva.setHash(algorithm.hashString(mat));
        hashcvaDao.insertSelective(hashcva);
      }
    }

  }



  @Test
  public void selectDumpPHA() {
//    String location = "/home/hebly723/Lenovo/graduateWork/image";
//    List<Hashpha> list = hashphaDao.selectDump();
//    ImageViewer[] imageViewers = new ImageViewer[list.size()];
//    ImageExample imageExample = new ImageExample();
//    for (Hashpha hashpha:list) {
//        ImageExample.Criteria criteria = imageExample.or();
//        criteria.andIdEqualTo(hashpha.getId());
////      Image image = imageDao.selectByPrimaryKey(hashpha.getId());
////      System.out.println(image.getLocation());
//    }
//    List<Image> list2 = imageDao.selectByExample(imageExample);
//    for (int i = 0; i < list2.size(); i++)
//    {
//        System.out.println(list2.get(i).getLocation());
//    }
  }
}
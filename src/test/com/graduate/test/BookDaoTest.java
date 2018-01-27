package com.graduate.test;

import com.graduate.algorithm.Algorithm;
import com.graduate.algorithm.impl.PHA;
import com.graduate.dao.BookDao;
import com.graduate.dao.HashphaDao;
import com.graduate.dao.ImageDao;
import com.graduate.dao.UserDao;
import com.graduate.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.graduate.tool.ImageViewer;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static com.graduate.tool.StringMethod.strictTrim;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class BookDaoTest extends BaseTest {
  static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private BookDao bookDao;
  @Autowired
  private UserDao userDao;
  @Autowired
  private ImageDao imageDao;
  @Autowired
  private HashphaDao hashphaDao;

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
    int dir[] = {24,20, 33, 36, 31, 31,34,43};
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
    for (int i=1; i<eng.length+1;i++)
    {
      for (int j=1;j<dir[i-1]+1;j++)
      {
        Image image = new Image();
        image.setLocation("http://og7tyqvd1.bkt.clouddn.com/image/test/" + eng[i-1] + "/" + j + ".jpg");
        image.setDetail(eng[i-1] + j);
        imageDao.insertSelective(image);
        ImageExample imageExample = new ImageExample();
        imageExample.createCriteria().andDetailEqualTo(eng[i-1] + j);
        Hashpha hashpha = new Hashpha();
        hashpha.setId(imageDao.selectByExample(imageExample).get(0).getId());
        Algorithm algorithm = new PHA();
        Mat mat = imread(location+"/"+ eng[i-1] + "/" + j + ".jpg");
        hashpha.setHash(algorithm.hashString(mat));
        hashphaDao.insertSelective(hashpha);
      }
    }

  }

  @Test
  public void selectDumpPHA()
  {
    String location = "/home/hebly723/Lenovo/graduateWork/image";
    List<Hashpha> list = hashphaDao.selectDump();
    ImageViewer[] imageViewers = new ImageViewer[list.size()];
    ImageExample imageExample = new ImageExample();
    for (Hashpha hashpha:list) {
        ImageExample.Criteria criteria = imageExample.or();
        criteria.andIdEqualTo(hashpha.getId());
//      Image image = imageDao.selectByPrimaryKey(hashpha.getId());
//      System.out.println(image.getLocation());
    }
    List<Image> list2 = imageDao.selectByExample(imageExample);
    for (int i = 0; i < list2.size(); i++)
    {
        System.out.println(list2.get(i).getLocation());
    }
  }
  @Test
  public void newImage()
  {
    String location = "/media/hebly723/Lenovo/graduateWork/image";
    Image image = new Image();
    image.setLocation(location+"1.jpg");
    image.setDetail("图片一");
    imageDao.insertSelective(image);
  }
  @Test
  public void addHashPHA()
  {
    String url = "http://og7tyqvd1.bkt.clouddn.com/image/test/";
    String location = "/media/hebly723/Lenovo/graduateWork/image";


    ImageExample imageExample = new ImageExample();
    ImageExample.Criteria criteria = imageExample.createCriteria();
    criteria.andIdIsNotNull();
    List<Image> images = imageDao.selectByExample(imageExample);
    for (Image image:images) {
      Algorithm algorithm = new PHA();
      Hashpha hashpha = new Hashpha();
      hashpha.setId(image.getId());
      String str = strictTrim(image.getDetail());
      System.out.println(location+"/"+str+"/"+
              image.getDetail().
                      substring(str.length(),
                              image.getDetail().length())+".jpg");
      Mat mat = imread(location+"/"+str+"/"+
              image.getDetail().
                      substring(str.length(),
                              image.getDetail().length())+".jpg");
      hashpha.setHash(algorithm.hashString(mat));
      hashphaDao.insertSelective(hashpha);
      System.out.println(image);
    }
  }
  @Test
  public void selectUser() {
    User user = userDao.selectByPrimaryKey(1);
    System.out.println(user);
  }

  @Test
  public void addBook() {
    for (int i = 0; i < 10; i++) {
      Book book = new Book();
      book.setDetail("描述" + i);
      book.setName("活着" + i);
      book.setNumber(i + 100);
      int num = bookDao.addBook(book);
    }
  }

  @Test
  public void queryById() {
    Book book = bookDao.queryById(101);
    System.out.println(book);
  }

  @Test
  public void queryAll() {
    List<Book> books = bookDao.queryAll(1, 1000);
    System.out.println(books);
  }

  @Test
  public void updateBook() {
    Book book = new Book();
    book.setBookId(101);
    book.setDetail("描述---修改");
    book.setName("活着---修改");
    book.setNumber(100);
    int num = bookDao.updateBook(book);
  }

  @Test
  public void deleteBookById() {
    bookDao.deleteBookById(100);
  }
}

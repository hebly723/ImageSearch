package com.graduate.service.impl;

import com.graduate.dao.BookDao;
import com.graduate.dao.HashphaDao;
import com.graduate.dao.ImageDao;
import com.graduate.entity.Book;
import com.graduate.entity.Hashpha;
import com.graduate.entity.Image;
import com.graduate.service.BookService;
import com.graduate.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hisen on 17-4-24.
 */
@Service
public class ImageServiceImpl implements ImageService {
  @Autowired
  private ImageDao imageDao;
  @Autowired
  private HashphaDao hashphaDao;


  @Override
  public Image getById(int imageId) {
    return imageDao.selectByPrimaryKey(imageId);
  }

  @Override
  public List<Image> getList(int start, int pageNum) {
    return null;
  }

  @Override
  public List<Image> getDump() {
    List<Hashpha> hashphas = hashphaDao.selectDump();
    List<Image> images = new ArrayList<Image>();
    for (Hashpha hashpha: hashphas) {
      images.add(imageDao.selectByPrimaryKey(hashpha.getId()));
    }
    return images;
  }

}

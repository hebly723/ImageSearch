package com.graduate.service;

import com.graduate.entity.Book;
import com.graduate.entity.Image;

import java.util.List;

/**
 * Created by hisen on 17-4-24.
 */
public interface ImageService {
  Image getById(int imageId);
  List<Image> getList(int start, int pageNum);
  List<Image> getDump();
}

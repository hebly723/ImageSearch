package com.graduate.test;

import com.graduate.dao.HashcvaDao;
import com.graduate.dao.HashphaDao;
import com.graduate.dao.ImageDao;
import com.graduate.entity.HashPack;
import com.graduate.entity.Image;
import org.junit.Test;
import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PhaDaoTest extends BaseTest {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private HashcvaDao hashcvaDao;
    @Autowired
    private HashphaDao hashphaDao;
    @Test
    public void selectHash(){
        HashPack hashPack = new HashPack();
//        hashPack.setHashCva("644dc6b663ded270");
//        hashPack.setHashPha("b000000000000000");
        List<Image> list = hashphaDao.selectHash(hashPack);
        for (int i=0; i<list.size(); i++)
        {
            System.out.println(list.get(i));
        }
    }
}

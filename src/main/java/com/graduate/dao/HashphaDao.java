package com.graduate.dao;

import com.graduate.entity.HashPack;
import com.graduate.entity.Hashpha;
import com.graduate.entity.HashphaExample;
import com.graduate.entity.Image;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HashphaDao {
    int countByExample(HashphaExample example);

    int deleteByExample(HashphaExample example);

    int insert(Hashpha record);

    int insertSelective(Hashpha record);

    List<Hashpha> selectByExample(HashphaExample example);

    int updateByExampleSelective(@Param("record") Hashpha record, @Param("example") HashphaExample example);

    int updateByExample(@Param("record") Hashpha record, @Param("example") HashphaExample example);

    List<Hashpha> selectDump();

    List<Image> selectHash(HashPack hashPack);

}
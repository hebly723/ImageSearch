package com.graduate.dao;

import com.graduate.entity.Hashcva;
import com.graduate.entity.HashcvaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HashcvaDao {
    int countByExample(HashcvaExample example);

    int deleteByExample(HashcvaExample example);

    int insert(Hashcva record);

    int insertSelective(Hashcva record);

    List<Hashcva> selectByExample(HashcvaExample example);

    int updateByExampleSelective(@Param("record") Hashcva record, @Param("example") HashcvaExample example);

    int updateByExample(@Param("record") Hashcva record, @Param("example") HashcvaExample example);
}
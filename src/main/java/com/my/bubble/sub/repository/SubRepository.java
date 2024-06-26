package com.my.bubble.sub.repository;

import com.my.bubble.sub.model.ResponsePubList;
import com.my.bubble.sub.model.ResponseSubList;
import com.my.bubble.sub.model.Subscribe;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubRepository {
    List<ResponsePubList> findPubAll(String id);
    void saveSubFirst(Subscribe subscribe);

    List<ResponseSubList> findSubAll(String id);
}

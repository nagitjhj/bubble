package com.my.bubble.sub.service;

import com.my.bubble.sub.model.ResponsePubList;
import com.my.bubble.sub.model.Subscribe;
import com.my.bubble.sub.repository.SubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubService {
    private final SubRepository subRepository;

    public List<ResponsePubList> findPubList(String id) {
        return subRepository.findAll(id);
    }

    public void subFirst(String userId, String pubId) {
        Subscribe subscribe = new Subscribe(userId, pubId);
        subRepository.saveSubFirst(subscribe);
    }
}

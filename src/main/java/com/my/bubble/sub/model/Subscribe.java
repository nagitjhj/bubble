package com.my.bubble.sub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Subscribe {
    private Long seq;
    private String userId;
    private String pubId;
    private Timestamp startDate;
    private Timestamp endDate;
    private String validYn;
    private Integer times;

    public Subscribe(String userId, String pubId) {
        this.userId = userId;
        this.pubId = pubId;
    }
}

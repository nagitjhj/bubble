package com.my.bubble.rabbitmq;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageDTO {
    private String title;
    private String content;
}

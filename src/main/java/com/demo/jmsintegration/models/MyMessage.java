package com.demo.jmsintegration.models;

import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String data;
    private ZonedDateTime timestamp;

}

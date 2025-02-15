package com.suvey.suvey.global.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class ResponseDTO<T> {

    private String error;
    private List<T> data;
}

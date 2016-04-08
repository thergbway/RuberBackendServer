package com.ruber.controller.support;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionWrapper {
    private Integer code;
    private String message;
}

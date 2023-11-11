package com.pews.brightdreamsfoundation.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponseEntity {
    private Integer code;
    private Object data;
    private String message;

    public static HttpResponseEntity ok() {
        return new HttpResponseEntity(200, null, "OK!");
    }
}

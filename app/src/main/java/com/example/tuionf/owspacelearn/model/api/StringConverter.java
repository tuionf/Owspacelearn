package com.example.tuionf.owspacelearn.model.api;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author tuionf
 * @date 2017/12/23
 * @email 596019286@qq.com
 * @explain
 */

public class StringConverter implements Converter<ResponseBody,String>{

    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }

}

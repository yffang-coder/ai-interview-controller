package com.ruoyi.framework.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class RestTemplateMessageConverter extends MappingJackson2HttpMessageConverter {

    public RestTemplateMessageConverter() {
        List<MediaType>  mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.TEXT_PLAIN);
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        setSupportedMediaTypes(mediaTypeList);
    }

}

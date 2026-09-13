package com.jung.daysum.config;

import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FeignConfig {

    @Bean
    public Decoder feignDecoder() {
        MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter();

        List<MediaType> mediaTypes =
                new ArrayList<>(converter.getSupportedMediaTypes());

        mediaTypes.add(
                MediaType.valueOf("text/javascript")
        );

        converter.setSupportedMediaTypes(mediaTypes);

        ObjectFactory<HttpMessageConverters> messageConverters =
                () -> new HttpMessageConverters(converter);

        return new ResponseEntityDecoder(
                new SpringDecoder(messageConverters)
        );
    }
}
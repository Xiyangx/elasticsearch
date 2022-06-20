package com.sunac.elasticsearch;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import java.io.FileNotFoundException;

/**
 * @Description: 下载请求超时处理
 * @Author xiyang
 * @Date 2022/6/17 12:17 下午
 * @Version 1.0
 */
@ControllerAdvice //所有的Controller都会进入到这个类
public class BaseExceptionAdvice {
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public String handException(AsyncRequestTimeoutException e) {
        return "文件正在加速为您下载，请耐心等待哟～～～";
    }
    @ExceptionHandler(FileNotFoundException.class)
    public String fileNotFoundException(FileNotFoundException e){
        return "您下载的文件不存在，请检查您下载的文件是否正确～ \n  ps:如果您坚持想要下载这个文件，请联系数据中台的同事进行处理～～～";
    }
}


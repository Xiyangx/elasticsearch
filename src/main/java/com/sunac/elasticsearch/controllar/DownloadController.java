package com.sunac.elasticsearch.controllar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/1 12:20 下午
 * @Version 1.0
 */
@RestController
public class DownloadController {

    private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

    @RequestMapping(value = "/download/{year}/{month}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String year, @PathVariable String month) throws IOException {

//        String fileName = "/Users/xiyang/Documents/sunac/文档/项目/序时账/excel/"
//                  + year
//                  + "/"
//                  + month
//                  + ".zip";
        String filePath = "/opt/project/file/"
                + year
                + "/";
        String fileName = month + ".zip";

        logger.info("--------要下载的文件是：{}/{}.zip------",year,month);

        return getInputStreamResourceResponseEntity(filePath, fileName);

    }

    @RequestMapping(value = "/download/{year}/{month}/{company}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String year, @PathVariable String month,@PathVariable String company) throws IOException {

        String filePath = "/Users/xiyang/Documents/sunac/文档/项目/序时账/excel/"
                  + year
                  + "/"
                  + month
                  + "/";
        String fileName = company + ".xlsx";
        logger.info("--------要下载的文件是：{}/{}/{}.xlsx------",year,month,company);


        return getInputStreamResourceResponseEntity(filePath, fileName);

    }

    private ResponseEntity<InputStreamResource> getInputStreamResourceResponseEntity(String filePath, String fileName) throws IOException {
        FileSystemResource file = new FileSystemResource(filePath + fileName);
        logger.info("--------正在下载------");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("content-disposition","attachment;filename="+ URLEncoder.encode(fileName,"utf-8"));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream;charset=utf-8"))
                .body(new InputStreamResource(file.getInputStream()));
    }
}

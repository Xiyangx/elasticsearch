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
//        String filePath = "/Users/xiyang/Documents/sunac/文档/项目/序时账/" + "excel.zip";
        String fileName = "/opt/project/file/"
                + year
                + "/"
                + month
                + ".zip";

        FileSystemResource file = new FileSystemResource(fileName);
        logger.info("--------下载成功------");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

}

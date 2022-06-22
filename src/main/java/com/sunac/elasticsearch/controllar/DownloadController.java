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

    /**
     * @Description: 下载大区的zip
     * @Param: [year, month, area]
     * @Return: org.springframework.http.ResponseEntity<org.springframework.core.io.InputStreamResource>
     * @Author: xiyang
     * @Date 2022/6/22 4:06 下午
     **/
    @RequestMapping(value = "/download/{year}/{month}/{area}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String year, @PathVariable String month, @PathVariable String area) throws IOException {

//        String filePath = "/Users/xiyang/Documents/sunac/文档/项目/序时账/excel/" + year + "/" + month + "/";
        String filePath = "/opt/project/file/" + year + "/" + month + "/";
        String fileName = area + ".zip";

        logger.info("--------要下载的文件是：{}/{}/{}.zip------",year,month,area);

        return getInputStreamResourceResponseEntity(filePath, fileName);

    }
    /**
     * @Description: 下载单个公司的excel
     * @Param: [year, month, area, company]
     * @Return: org.springframework.http.ResponseEntity<org.springframework.core.io.InputStreamResource>
     * @Author: xiyang
     * @Date 2022/6/22 4:07 下午
     **/
    @RequestMapping(value = "/download/{year}/{month}/{area}/{company}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String year, @PathVariable String month,@PathVariable String area, @PathVariable String company) throws IOException {
//        String filePath = "/Users/xiyang/Documents/sunac/文档/项目/序时账/excel/" + year + "/" + month + "/" + area + "/";
        String filePath = "/opt/project/file/" + year + "/" + month + "/" + area + "/";
        String fileName = company + ".xlsx";
        logger.info("--------要下载的文件是：{}/{}/{}.xlsx------",year,month,company);

        return getInputStreamResourceResponseEntity(filePath, fileName);

    }
    /**
     * @Description: 下载集团的zip
     * @Param: [year, month]
     * @Return: org.springframework.http.ResponseEntity<org.springframework.core.io.InputStreamResource>
     * @Author: xiyang
     * @Date 2022/6/22 4:07 下午
     **/
    @RequestMapping(value = "/download/{year}/{month}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String year, @PathVariable String month) throws IOException {

//        String filePath = "/Users/xiyang/Documents/sunac/文档/项目/序时账/excel/" + year + "/";
        String filePath = "/opt/project/file/" + year + "/";
        String fileName = month + ".zip";

        logger.info("--------要下载的文件是：{}/{}.zip------",year,month);

        return getInputStreamResourceResponseEntity(filePath, fileName);

    }
    /**
     * @Description: 下载文件工具类
     * @Param: [filePath, fileName]
     * @Return: org.springframework.http.ResponseEntity<org.springframework.core.io.InputStreamResource>
     * @Author: xiyang
     * @Date 2022/6/22 4:08 下午
     **/
    private ResponseEntity<InputStreamResource> getInputStreamResourceResponseEntity(String filePath, String fileName) throws IOException {
        FileSystemResource file = new FileSystemResource(filePath + fileName);
        logger.info("--------正在下载------");
        String originalFileName = URLEncoder.encode(fileName, "utf-8");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("content-disposition", "attachment;filename*=utf-8''" + originalFileName);
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

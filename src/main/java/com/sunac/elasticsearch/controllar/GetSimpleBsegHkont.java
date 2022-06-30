package com.sunac.elasticsearch.controllar;

import com.sunac.elasticsearch.entity.Company;
import com.sunac.elasticsearch.service.impl.EsServiceImpl;
import com.sunac.elasticsearch.service.impl.HiveSqlServiceImpl;
import com.sunac.elasticsearch.utils.ArgsUtils;
import com.sunac.elasticsearch.utils.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Description: 单个科目的数据下载接口
 * @Author xiyang
 * @Date 2022/6/27 2:47 下午
 * @Version 1.0
 */
@RestController
public class GetSimpleBsegHkont {

    private static final Logger logger = LoggerFactory.getLogger(GetSimpleBsegHkont.class);

    @Autowired
    private EsServiceImpl esServiceImpl;
    @Autowired
    private HiveSqlServiceImpl hiveSqlServiceImpl;
    /**
     * @Description: 单个账目的下载
     * @Param: [year, month] 当前年份，当前月份
     * @Return: void
     * @Author: xiyang
     * @Date 2022/6/17 2:13 下午
     **/
    @RequestMapping(value = "/getZip/{bsegGjahr}/{bsegH2Monat}/{bsegBukrs}/{bsegHkont}/{bsegZzwyfwlx}/{bsegKostl}/{csksKtext}/{bsegPrctr}/{cepcKtext}/{bsegZzlfinr}/{lfa1Name1}/{bsegZzkunnr}/{kna1Name1}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getString(@PathVariable String bsegGjahr, @PathVariable String bsegH2Monat,
                                                         @PathVariable String bsegBukrs, @PathVariable String bsegHkont,
                                                         @PathVariable String bsegZzwyfwlx, @PathVariable String bsegKostl,
                                                         @PathVariable String csksKtext, @PathVariable String bsegPrctr,
                                                         @PathVariable String cepcKtext,@PathVariable String bsegZzlfinr,
                                                         @PathVariable String lfa1Name1,@PathVariable String bsegZzkunnr,
                                                         @PathVariable String kna1Name1) throws IOException {

        //获取各个大区下面公司代码和公司名称的集合
        List<Company> areaList = hiveSqlServiceImpl.getSimpleAreaMap(ArgsUtils.getBsegBukrs(bsegBukrs));

        long l = System.currentTimeMillis();
//        String filePath = "/Users/xiyang/Documents/sunac/文档/项目/序时账/excel/simple/" + l + "/";
//        String zipPath = "/Users/xiyang/Documents/sunac/文档/项目/序时账/excel/simple/";
        String filePath = "/opt/project/file/simple/" + l + "/";
        String zipPath = "/opt/project/file/simple/";
        String fileName = l + ".zip";
        //创建月份的文件夹
        ZipUtils.mkdir(filePath);
        //创建各个大区的文件夹
        logger.info("正在生成excel文件");
        //生成excel
        esServiceImpl.writeExcel(areaList, filePath, bsegGjahr, bsegH2Monat,bsegBukrs,bsegHkont,bsegZzwyfwlx,bsegKostl,csksKtext,bsegPrctr,cepcKtext,bsegZzlfinr,lfa1Name1,bsegZzkunnr,kna1Name1);

        logger.info("生成全部excel文件成功");
        logger.info("正在压缩月份的excel文件");
        //压缩所有的文件
        ZipUtils.zipForGroup(filePath);
        logger.info("压缩月份的excel文件成功");

        logger.info("--------要下载的文件是：simple/{}.zip------",l);

        return getInputStreamResourceResponseEntity(zipPath, fileName);
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

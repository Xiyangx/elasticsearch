package com.sunac.elasticsearch.controllar;

import com.sunac.elasticsearch.entity.InsertLog;
import com.sunac.elasticsearch.service.impl.EsServiceImpl;
import com.sunac.elasticsearch.service.impl.InserLogServiceImpl;
import com.sunac.elasticsearch.utils.ArgsUtils;
import com.sunac.elasticsearch.utils.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/5/14 7:02 下午
 * @Version 1.0
 */
@RestController
public class CreateExcelController {
    private static final Logger logger = LoggerFactory.getLogger(CreateExcelController.class);

    @Autowired
    private EsServiceImpl esServiceImpl;
    @Autowired
    private InserLogServiceImpl inserLogServiceImpl;
    /**
     * @Description: TODO
     * @Param: [year, month] 当前年份，当前月份
     * @Return: void
     * @Author: xiyang
     * @Date 2022/6/17 2:13 下午
     **/
    @RequestMapping(value = "/createExcel/{year}/{month}")
    public void getString(@PathVariable String year, @PathVariable String month) {

//         String filePath = "/Users/xiyang/Documents/sunac/文档/项目/序时账/excel/" + new ArgsUtils().getyear() + "/" + new ArgsUtils().getMonth();
        String filePath = "/opt/project/file/" + year + "/" + ArgsUtils.getBeforeMonth(month).get(ArgsUtils.getBeforeMonth(month).size()-1);

        File file = new File(filePath);
        //判断是否存在
        if(file.exists()) {
            boolean delete = file.delete();


            System.out.println("目录存在并删除" + delete);
        } else {
            boolean mkdirs = file.mkdirs();
            System.out.println("创建目录成功" + mkdirs);
        }

        logger.info("正在生成excel文件");
        esServiceImpl.writeExcel(filePath, year, month);

        logger.info("生成全部excel文件成功");
        logger.info("正在压缩excel文件");
        FileOutputStream fos1 = null;
        String zipFile = filePath + ".zip";
        File zip = new File(zipFile);
        if(file.exists()) {
            boolean delete = zip.delete();
            System.out.println("zip存在并删除" + delete);
        }
        try {
            fos1 = new FileOutputStream(filePath + ".zip");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ZipUtils.toZip(filePath, fos1, true);
        logger.info("压缩excel文件成功");

        boolean save = inserLogServiceImpl.save(new InsertLog(year, ArgsUtils.getBeforeMonth(month).get(ArgsUtils.getBeforeMonth(month).size()-1)));
        logger.info("插入mysql log表数据：{}", save);

        logger.info("生成并压缩excel文件成功");
    }
}

package com.sunac.elasticsearch.controllar;

import com.sunac.elasticsearch.entity.InsertLog;
import com.sunac.elasticsearch.service.impl.EsServiceImpl;
import com.sunac.elasticsearch.service.impl.InserLogServiceImpl;
import com.sunac.elasticsearch.utils.ArgsUtils;
import com.sunac.elasticsearch.utils.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @Param: []
     * @Return: java.lang.String
     * @Author: xiyang
     * @Date 2022/5/14 7:08 下午
     **/
    @RequestMapping("/createExcel")
    public String getString() {

        // String filePath = "/Users/xiyang/Documents/sunac/文档/项目/序时账/excel/" + new ArgsUtils().getyear() + "/" + new ArgsUtils().getMonth();
        String filePath = "/opt/project/file/" + new ArgsUtils().getyear() + "/" + new ArgsUtils().getMonth();

        logger.info("正在生成excel文件");
        esServiceImpl.writeExcel(filePath);

        logger.info("生成全部excel文件成功");
        logger.info("正在压缩excel文件");
        FileOutputStream fos1 = null;
        try {
            fos1 = new FileOutputStream(filePath + ".zip");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ZipUtils.toZip(filePath, fos1, true);
        logger.info("压缩excel文件成功");
        boolean save = inserLogServiceImpl.save(new InsertLog(new ArgsUtils().getyear(), new ArgsUtils().getMonth()));
        logger.info("插入mysql log表数据：{}", save);
        return "生成并压缩excel文件成功～";
    }
}

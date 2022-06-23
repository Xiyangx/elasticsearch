package com.sunac.elasticsearch.controllar;

import com.sunac.elasticsearch.entity.InsertLog;
import com.sunac.elasticsearch.service.impl.EsServiceImpl;
import com.sunac.elasticsearch.service.impl.HiveSqlServiceImpl;
import com.sunac.elasticsearch.service.impl.InserLogServiceImpl;
import com.sunac.elasticsearch.utils.ArgsUtils;
import com.sunac.elasticsearch.utils.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Autowired
    private HiveSqlServiceImpl hiveSqlServiceImpl;
    /**
     * @Description: TODO
     * @Param: [year, month] 当前年份，当前月份
     * @Return: void
     * @Author: xiyang
     * @Date 2022/6/17 2:13 下午
     **/
    @RequestMapping(value = "/createExcel/{year}/{month}")
    public void getString(@PathVariable String year, @PathVariable String month) {

//         String filePath = "/Users/xiyang/Documents/sunac/文档/项目/序时账/excel/" + year + "/" + month + "/";
        String filePath = "/opt/project/file/" + year + "/" + month + "/";
        List<String> companyAreaList = hiveSqlServiceImpl.getCompanyAreaList();
        //创建月份的文件夹
        ZipUtils.mkdir(filePath);
        //创建各个大区的文件夹
        ZipUtils.mkdirCompanyAreaPath(companyAreaList,filePath);
        logger.info("正在生成excel文件");
        //生成excel
        esServiceImpl.writeExcel(filePath, year, month);

        logger.info("生成全部excel文件成功");
        logger.info("正在压缩月份的excel文件");
        //压缩所有的文件
        ZipUtils.zipForGroup(filePath);
        logger.info("压缩月份的excel文件成功");
        logger.info("正在压缩大区的excel文件");
        //压缩大区的文件
        ZipUtils.zipForArea(companyAreaList,filePath);
        logger.info("压缩大区的excel文件成功");
        //在数据库标记文件生成成功
        boolean save = inserLogServiceImpl.save(new InsertLog(year, ArgsUtils.getBeforeMonth(month).get(ArgsUtils.getBeforeMonth(month).size()-1)));
        logger.info("插入mysql log表数据：{}", save);

        logger.info("生成并压缩excel文件成功");
    }
}

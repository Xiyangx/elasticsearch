package com.sunac.elasticsearch.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.ZipOutputStream;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/15 1:40 下午
 * @Version 1.0
 */
public class test {
    public static void main(String[] args) {

        //需要处理的文件夹路径
        String path = "F://tarTest";
        //生成的格式
        String format = "rar";
        //返回的文件夹路径
        String outPath = "F://tarTest";
        try {
            generateFile(path, format,outPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param path   要压缩的文件路径
     * @param format 生成的格式（zip、rar）d
     * @param outPath 输出的文件路径
     */
    public static void generateFile(String path, String format,String outPath) throws Exception {

        File file = new File(path);
        // 压缩文件的路径不存在
        if (!file.exists()) {
            throw new Exception("路径 " + path + " 不存在文件!");
        }
        // 用于存放压缩文件的文件夹
        String generateFile = file.getParent() + File.separator +"CompressFile";
        File compress = new File(generateFile);
        // 如果文件夹不存在则进行创建
        if( !compress.exists() ){
            boolean mkdirs = compress.mkdirs();
            System.out.println("mkdirs = " + mkdirs);
        }
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String dateName = df.format(calendar.getTime());
        // 目的压缩文件
        String generateFileName = outPath + File.separator + dateName + "." + format;

        // 输出流
        FileOutputStream outputStream = new FileOutputStream(generateFileName);

        // 压缩输出流
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));

        generateFile(path,"rar","");

        System.out.println("源文件位置：" + file.getAbsolutePath() + "，目的压缩文件生成位置：" + generateFileName);
        // 关闭 输出流
        zipOutputStream.close();


    }

}

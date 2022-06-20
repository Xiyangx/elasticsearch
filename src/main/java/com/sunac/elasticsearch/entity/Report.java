package com.sunac.elasticsearch.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Description: 报表类
 * @Author xiyang
 * @Date 2022/5/31 5:09 下午
 * @Version 1.0
 */
@Data
public class Report {
    @ExcelProperty(value = "融创凭证号", index = 0)
    private String bkpfXref2Hd;
    //@DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")

    @ExcelProperty(value = "预制凭证号", index = 1)
    private String bkpfZyzid;
    @ExcelProperty(value = "凭证抬头文本", index = 2)
    private String bkpfBktxt;
    @ExcelProperty(value = "会计凭证号码", index = 3)
    private String bsegBelnr;
    @ExcelProperty(value = "附件张数", index = 4)
    private String bkpfNumpg;
    @ExcelProperty(value = "汇率", index = 5)
    private String bkpfKursf;
    @ExcelProperty(value = "行项目数", index = 6)
    private String bsegbuzei;
    @ExcelProperty(value = "公司代码", index = 7)
    private String bsegBukrs;
    @ExcelProperty(value = "财年", index = 8)
    private String bsegGjahr;
    @ExcelProperty(value = "会计期间", index = 9)
    private String bsegH2Monat;
    @ExcelProperty(value = "业务日期", index = 10)
    private String bsegH2Bldat;
    @ExcelProperty(value = "记帐日期", index = 11)
    private String bsegH2Budat;
    @ExcelProperty(value = "凭证类型", index = 12)
    private String bsegH2Bblart;
    @ExcelProperty(value = "摘要", index = 13)
    private String bsegSgtxt;
    @ExcelProperty(value = "总分类帐帐目", index = 14)
    private String bsegHkont;
    @ExcelProperty(value = "总账科目长文本", index = 15)
    private String ska1Txt50;
    @ExcelProperty(value = "记帐代码", index = 16)
    private String bsegBschl;
    @ExcelProperty(value = "本币", index = 17)
    private String bsegH2Hwaer;
    @ExcelProperty(value = "借方金额", index = 18)
    private String bsegS2Wrbtr;
    @ExcelProperty(value = "贷方金额", index = 19)
    private String bsegH2Wrbtr;
    @ExcelProperty(value = "业务类型", index = 20)
    private String bsegGsber;
    @ExcelProperty(value = "借方/贷方标识", index = 21)
    private String bsegShkzg;
    @ExcelProperty(value = "本位币金额", index = 22)
    private String bsegDmbtr;
    @ExcelProperty(value = "成本中心", index = 23)
    private String bsegKostl;
    @ExcelProperty(value = "利润中心", index = 24)
    private String bsegPrctr;
    @ExcelProperty(value = "现金流量码", index = 25)
    private String bsegZzrstgr;
    @ExcelProperty(value = "银行标识", index = 26)
    private String bsegHktid;
    @ExcelProperty(value = "开户银行", index = 27)
    private String bsegHbkid;
    @ExcelProperty(value = "客户编号", index = 28)
    private String bsegKunnr;
    @ExcelProperty(value = "供应商或债权人的帐号", index = 29)
    private String bsegLifnr;
    @ExcelProperty(value = "主资产号", index = 30)
    private String bsegAnln1;
    @ExcelProperty(value = "资产业务类型", index = 31)
    private String bsegAnbwa;
    @ExcelProperty(value = "项目", index = 32)
    private String bsegZzxiangmu;
    @ExcelProperty(value = "合同档案", index = 33)
    private String bsegZzheto;
    @ExcelProperty(value = "销售/购买税代码", index = 34)
    private String bsegMwskz;
    @ExcelProperty(value = "辅助核算项", index = 35)
    private String bsegZzbeiyong1;
    @ExcelProperty(value = "辅助核算类别", index = 36)
    private String bsegZzbeiyong2;
    @ExcelProperty(value = "基准日期", index = 37)
    private String bsegZfbdt;
    @ExcelProperty(value = "往来类型", index = 38)
    private String bsegZzmenge;
    @ExcelProperty(value = "基本计量单位", index = 39)
    private String bsegMeins;
    @ExcelProperty(value = "标识: 反记帐", index = 40)
    private String bsegXnegp;
    @ExcelProperty(value = "银行账号", index = 41)
    private String bsegZzhktid;
    @ExcelProperty(value = "原单据号", index = 42)
    private String bsegBxdnr;
    @ExcelProperty(value = "系统来源", index = 43)
    private String bkpfTcode;
    @ExcelProperty(value = "供应商", index = 44)
    private String bsegZzlfinr;
    @ExcelProperty(value = "面积", index = 45)
    private String bsegZzshulz;
    @ExcelProperty(value = "客户", index = 46)
    private String bsegZzkunnr;
    @ExcelProperty(value = "凭证长文本", index = 47)
    private String bsegZzltext;
    @ExcelProperty(value = "物业服务类型", index = 48)
    private String bsegZzwyfwlx;
    @ExcelProperty(value = "税款性质", index = 49)
    private String bsegZzskxz;
    @ExcelProperty(value = "代收费用类型", index = 50)
    private String bsegZzcollc;
    @ExcelProperty(value = "凭证状态", index = 51)
    private String bkpfXreversal;
    @ExcelProperty(value = "制单人", index = 52)
    private String bkpfTjusr;
    @ExcelProperty(value = "制单日期", index = 53)
    private String bkpfTjdate;
    @ExcelProperty(value = "制单时间", index = 54)
    private String bkpfTjtime;
    @ExcelProperty(value = "过账人", index = 55)
    private String bkpfGzusr;
    @ExcelProperty(value = "过账日期", index = 56)
    private String bkpfGzrdt;
    @ExcelProperty(value = "过账时间", index = 57)
    private String bkpfGztim;
    @ExcelProperty(value = "成本中心名称", index = 58)
    private String csksKtext;
    @ExcelProperty(value = "利润中心描述", index = 59)
    private String cepcKtext;
    @ExcelProperty(value = "现金流量描述", index = 60)
    private String ztfi0258Rstgt;
    @ExcelProperty(value = "客户描述", index = 61)
    private String kna1Name1;
    @ExcelProperty(value = "供应商描述", index = 62)
    private String lfa1Name1;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * IT工单对象 jhcn_itsm_adesk
 *
 * @author Russel
 * @date 2025-10-17
 */
public class JhcnItsmAdesk extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 工单名 */
    @Excel(name = "工单名")
    private String itsmName;

    /** 填写人姓名 */
    @Excel(name = "填写人姓名")
    private String createUsername;

    /** 申请人姓名 */
    @Excel(name = "申请人姓名")
    private String applyUsername;

    /** 申请人工号 */
    @Excel(name = "申请人工号")
    private String applyNo;

    /** 申请人部门 */
    @Excel(name = "申请人部门")
    private Long applyDeptId;

    /** 申请人所属部门名称 */
    @Excel(name = "申请人所属部门")
    private String applyDeptName;

    /** 云桌面01 */
    @Excel(name = "云桌面01")
    private String demandAdesk;

    /** 爱数账号 */
    @Excel(name = "爱数账号")
    private String demandAdeskAnyshare;

    /** 加密系统 */
    @Excel(name = "加密系统")
    private String demandAdeskIpguard;

    /** OA账号申请 */
    @Excel(name = "OA账号申请")
    private String demandOa;

    /** 11位手机号 */
    @Excel(name = "11位手机号")
    private String demandOaPhone;

    /** 邮箱申请 */
    @Excel(name = "邮箱申请")
    private String demandMail;

    /** 岗位 */
    @Excel(name = "岗位")
    private String demandMailJob;

    /** 拟设邮箱 */
    @Excel(name = "拟设邮箱")
    private String demandMailPre;

    /** 打印机安装 */
    @Excel(name = "打印机安装")
    private String demandPrinter;

    /** 技术大办公室Kyocera京瓷 */
    @Excel(name = "技术大办公室Kyocera京瓷")
    private String demandPrinterLarge;

    /** 人事小办公室Kyocera京瓷 */
    @Excel(name = "人事小办公室Kyocera京瓷")
    private String demandPrinterSmall;

    /** 模组办公室彩色Brother打印机 */
    @Excel(name = "模组办公室彩色Brother打印机")
    private String demandPrinterMz;

    /** 其他 */
    @Excel(name = "其他")
    private String demandPrinterOther;

    private String demandPrinterOtherInfo;

    /** sap安装 */
    @Excel(name = "sap安装")
    private String demandSap;

    /** sap账号新开 */
    @Excel(name = "sap账号新开")
    private String demandSapNumber;

    /** 联系it沟通新账号权限 */
    @Excel(name = "联系it沟通新账号权限")
    private String demandSapNumberInfo;

    /** 微信更新 */
    @Excel(name = "微信更新")
    private String demandWechatUpdate;

    /** WPS文件格式关联 */
    @Excel(name = "WPS文件格式关联")
    private String demandWpsSet;

    /** 其他需求 */
    @Excel(name = "其他需求")
    private String demandOther;

    public String getApplyDeptName() {
        return applyDeptName;
    }

    public void setApplyDeptName(String applyDeptName) {
        this.applyDeptName = applyDeptName;
    }

    public String getDemandPrinterOtherInfo() {
        return demandPrinterOtherInfo;
    }

    public void setDemandPrinterOtherInfo(String demandPrinterOtherInfo) {
        this.demandPrinterOtherInfo = demandPrinterOtherInfo;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setItsmName(String itsmName)
    {
        this.itsmName = itsmName;
    }

    public String getItsmName()
    {
        return itsmName;
    }

    public void setCreateUsername(String createUsername)
    {
        this.createUsername = createUsername;
    }

    public String getCreateUsername()
    {
        return createUsername;
    }

    public void setApplyUsername(String applyUsername)
    {
        this.applyUsername = applyUsername;
    }

    public String getApplyUsername()
    {
        return applyUsername;
    }

    public void setApplyNo(String applyNo)
    {
        this.applyNo = applyNo;
    }

    public String getApplyNo()
    {
        return applyNo;
    }

    public void setApplyDeptId(Long applyDeptId)
    {
        this.applyDeptId = applyDeptId;
    }

    public Long getApplyDeptId()
    {
        return applyDeptId;
    }

    public void setDemandAdesk(String demandAdesk)
    {
        this.demandAdesk = demandAdesk;
    }

    public String getDemandAdesk()
    {
        return demandAdesk;
    }

    public void setDemandAdeskAnyshare(String demandAdeskAnyshare)
    {
        this.demandAdeskAnyshare = demandAdeskAnyshare;
    }

    public String getDemandAdeskAnyshare()
    {
        return demandAdeskAnyshare;
    }

    public void setDemandAdeskIpguard(String demandAdeskIpguard)
    {
        this.demandAdeskIpguard = demandAdeskIpguard;
    }

    public String getDemandAdeskIpguard()
    {
        return demandAdeskIpguard;
    }

    public void setDemandOa(String demandOa)
    {
        this.demandOa = demandOa;
    }

    public String getDemandOa()
    {
        return demandOa;
    }

    public void setDemandOaPhone(String demandOaPhone)
    {
        this.demandOaPhone = demandOaPhone;
    }

    public String getDemandOaPhone()
    {
        return demandOaPhone;
    }

    public void setDemandMail(String demandMail)
    {
        this.demandMail = demandMail;
    }

    public String getDemandMail()
    {
        return demandMail;
    }

    public void setDemandMailJob(String demandMailJob)
    {
        this.demandMailJob = demandMailJob;
    }

    public String getDemandMailJob()
    {
        return demandMailJob;
    }

    public void setDemandMailPre(String demandMailPre)
    {
        this.demandMailPre = demandMailPre;
    }

    public String getDemandMailPre()
    {
        return demandMailPre;
    }

    public void setDemandPrinter(String demandPrinter)
    {
        this.demandPrinter = demandPrinter;
    }

    public String getDemandPrinter()
    {
        return demandPrinter;
    }

    public void setDemandPrinterLarge(String demandPrinterLarge)
    {
        this.demandPrinterLarge = demandPrinterLarge;
    }

    public String getDemandPrinterLarge()
    {
        return demandPrinterLarge;
    }

    public void setDemandPrinterSmall(String demandPrinterSmall)
    {
        this.demandPrinterSmall = demandPrinterSmall;
    }

    public String getDemandPrinterSmall()
    {
        return demandPrinterSmall;
    }

    public void setDemandPrinterMz(String demandPrinterMz)
    {
        this.demandPrinterMz = demandPrinterMz;
    }

    public String getDemandPrinterMz()
    {
        return demandPrinterMz;
    }

    public void setDemandPrinterOther(String demandPrinterOther)
    {
        this.demandPrinterOther = demandPrinterOther;
    }

    public String getDemandPrinterOther()
    {
        return demandPrinterOther;
    }

    public void setDemandSap(String demandSap)
    {
        this.demandSap = demandSap;
    }

    public String getDemandSap()
    {
        return demandSap;
    }

    public void setDemandSapNumber(String demandSapNumber)
    {
        this.demandSapNumber = demandSapNumber;
    }

    public String getDemandSapNumber()
    {
        return demandSapNumber;
    }

    public void setDemandSapNumberInfo(String demandSapNumberInfo)
    {
        this.demandSapNumberInfo = demandSapNumberInfo;
    }

    public String getDemandSapNumberInfo()
    {
        return demandSapNumberInfo;
    }

    public void setDemandWechatUpdate(String demandWechatUpdate)
    {
        this.demandWechatUpdate = demandWechatUpdate;
    }

    public String getDemandWechatUpdate()
    {
        return demandWechatUpdate;
    }

    public void setDemandWpsSet(String demandWpsSet)
    {
        this.demandWpsSet = demandWpsSet;
    }

    public String getDemandWpsSet()
    {
        return demandWpsSet;
    }

    public void setDemandOther(String demandOther)
    {
        this.demandOther = demandOther;
    }

    public String getDemandOther()
    {
        return demandOther;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("itsmName", getItsmName())
            .append("createUsername", getCreateUsername())
            .append("createTime", getCreateTime())
            .append("applyUsername", getApplyUsername())
            .append("applyNo", getApplyNo())
            .append("applyDeptId", getApplyDeptId())
            .append("demandAdesk", getDemandAdesk())
            .append("demandAdeskAnyshare", getDemandAdeskAnyshare())
            .append("demandAdeskIpguard", getDemandAdeskIpguard())
            .append("demandOa", getDemandOa())
            .append("demandOaPhone", getDemandOaPhone())
            .append("demandMail", getDemandMail())
            .append("demandMailJob", getDemandMailJob())
            .append("demandMailPre", getDemandMailPre())
            .append("demandPrinter", getDemandPrinter())
            .append("demandPrinterLarge", getDemandPrinterLarge())
            .append("demandPrinterSmall", getDemandPrinterSmall())
            .append("demandPrinterMz", getDemandPrinterMz())
            .append("demandPrinterOther", getDemandPrinterOther())
            .append("demandSap", getDemandSap())
            .append("demandSapNumber", getDemandSapNumber())
            .append("demandSapNumberInfo", getDemandSapNumberInfo())
            .append("demandWechatUpdate", getDemandWechatUpdate())
            .append("demandWpsSet", getDemandWpsSet())
            .append("demandOther", getDemandOther())
            .toString();
    }
}

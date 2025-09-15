package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绑定管理对象 jhcn_module_bind
 *
 * @author ruoyi
 * @date 2025-08-26
 */
public class JhcnModuleBind extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 模组id */
    @Excel(name = "模组id")
    private Long moduleId;



    /** 单体id */
    @Excel(name = "单体id")
    private Long dtId;

    /** 板子编号 */
    @Excel(name = "板子编号")
    private String bzCode;

    /** 绑定时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "绑定时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date bindTime;

    /** 逻辑删除 0 未删除 1 已删除 */
    private String delFlag;

    /* 关联模组条码 */
    private String moduleCode;
    /** 绑定的单体条码 */
    private String dtCode;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getDtCode() {
        return dtCode;
    }

    public void setDtCode(String dtCode) {
        this.dtCode = dtCode;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setModuleId(Long moduleId)
    {
        this.moduleId = moduleId;
    }

    public Long getModuleId()
    {
        return moduleId;
    }

    public void setDtId(Long dtId)
    {
        this.dtId = dtId;
    }

    public Long getDtId()
    {
        return dtId;
    }

    public void setBzCode(String bzCode)
    {
        this.bzCode = bzCode;
    }

    public String getBzCode()
    {
        return bzCode;
    }

    public void setBindTime(Date bindTime)
    {
        this.bindTime = bindTime;
    }

    public Date getBindTime()
    {
        return bindTime;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("moduleId", getModuleId())
            .append("dtId", getDtId())
            .append("bzCode", getBzCode())
            .append("bindTime", getBindTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}

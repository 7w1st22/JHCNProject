package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 单体库对象 jhcn_dt_library
 *
 * @author ruoyi
 * @date 2025-08-25
 */
public class JhcnDtLibrary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 单体编号 */
    @Excel(name = "单体编号")
    private String dtCode;

    @Excel(name = "静电容量(F)")
    private String capacitance;

    /** 直流内阻 */
    @Excel(name = "直流内阻(mΩ)")
    private String resistanc;

    /** 档位 */
    @Excel(name = "档位")
    private String level;

    /** 描述 */
    private String description;

    /** 导入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date importTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bindedTime;

    public Date getBindedTime() {
        return bindedTime;
    }

    public void setBindedTime(Date bindedTime) {
        this.bindedTime = bindedTime;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setDtCode(String dtCode)
    {
        this.dtCode = dtCode;
    }

    public String getDtCode()
    {
        return dtCode;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getLevel()
    {
        return level;
    }

    public void setCapacitance(String capacitance)
    {
        this.capacitance = capacitance;
    }

    public String getCapacitance()
    {
        return capacitance;
    }

    public void setResistanc(String resistanc)
    {
        this.resistanc = resistanc;
    }

    public String getResistanc()
    {
        return resistanc;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setImportTime(Date importTime)
    {
        this.importTime = importTime;
    }

    public Date getImportTime()
    {
        return importTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dtCode", getDtCode())
            .append("level", getLevel())
            .append("capacitance", getCapacitance())
            .append("resistanc", getResistanc())
            .append("description", getDescription())
            .append("importTime", getImportTime())
            .toString();
    }
}

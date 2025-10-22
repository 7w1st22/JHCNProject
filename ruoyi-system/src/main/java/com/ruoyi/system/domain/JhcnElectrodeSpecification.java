package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 电极规格管理对象 jhcn_electrode_specification
 * 
 * @author Russel
 * @date 2025-10-22
 */
public class JhcnElectrodeSpecification extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 规格 */
    @Excel(name = "规格")
    private String specification;

    /** 电极样式 */
    @Excel(name = "电极样式")
    private String type;

    /** 测量点位(个) */
    @Excel(name = "测量点位(个)")
    private Long testNum;

    /** 特殊规格 */
    @Excel(name = "特殊规格")
    private String specialSpecification;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public void setTestNum(Long testNum) 
    {
        this.testNum = testNum;
    }

    public Long getTestNum() 
    {
        return testNum;
    }

    public void setSpecialSpecification(String specialSpecification) 
    {
        this.specialSpecification = specialSpecification;
    }

    public String getSpecialSpecification() 
    {
        return specialSpecification;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("specification", getSpecification())
            .append("type", getType())
            .append("testNum", getTestNum())
            .append("specialSpecification", getSpecialSpecification())
            .toString();
    }
}

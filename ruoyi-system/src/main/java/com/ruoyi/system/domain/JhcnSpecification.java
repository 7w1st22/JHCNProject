package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模组规格对象 jhcn_specification
 * 
 * @author ruoyi
 * @date 2025-08-26
 */
public class JhcnSpecification extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 模组规格 */
    @Excel(name = "模组规格")
    private String specification;

    /** 模组对应数量限制 */
    @Excel(name = "模组对应数量限制")
    private Long num;

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

    public void setNum(Long num) 
    {
        this.num = num;
    }

    public Long getNum() 
    {
        return num;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("specification", getSpecification())
            .append("num", getNum())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

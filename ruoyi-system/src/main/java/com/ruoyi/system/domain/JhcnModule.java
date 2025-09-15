package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模组条码对象 jhcn_module
 *
 * @author ruoyi
 * @date 2025-08-26
 */
public class JhcnModule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 模组条码 */
    private String module;

    /** 规格 */
    private Long specificationId;

    /** 真实数量 */
    private Long num;

    /** 已绑定数量 */
    private Long bindedNum;

    /** 规格 */
    private String specification;

    /** 关联规格数量 **/
    private String specificationNum;

    public Long getBindedNum() {
        return bindedNum;
    }

    public void setBindedNum(Long bindedNum) {
        this.bindedNum = bindedNum;
    }

    public String getSpecificationNum() {
        return specificationNum;
    }

    public void setSpecificationNum(String specificationNum) {
        this.specificationNum = specificationNum;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setModule(String module)
    {
        this.module = module;
    }

    public String getModule()
    {
        return module;
    }

    public void setSpecificationId(Long specificationId)
    {
        this.specificationId = specificationId;
    }

    public Long getSpecificationId()
    {
        return specificationId;
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
            .append("module", getModule())
            .append("specificationId", getSpecificationId())
                .append("specification", getSpecification())
                .append("specificationNum", getSpecificationNum())
            .append("num", getNum())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

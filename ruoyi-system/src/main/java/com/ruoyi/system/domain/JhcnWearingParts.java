package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 易损件对象 jhcn_wearing_parts
 *
 * @author russel
 * @date 2025-09-16
 */
public class JhcnWearingParts extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 品名 */
    @Excel(name = "品名")
    private String partName;

    /** 型号 */
    @Excel(name = "型号")
    private String partType;

    /** 位置 */
    @Excel(name = "位置")
    private String pos;

    /** 安全库存 */
    @Excel(name = "安全库存")
    private Long safetyNum;

    /** 缓冲距离 */
    @Excel(name = "缓冲距离")
    private Long safetyDistance;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Long num;

    /** 使用寿命 */
    @Excel(name = "使用寿命")
    private String serviceLife;

    /** 采购周期 */
    @Excel(name = "采购周期")
    private String leadTime;

    /** 图片链接 */
    private String img;

    /** 一般配件 */
    private String generalAccessories;

    /** 关键备件 */
    private String criticalSpares;

    // 备注
    private String description;

    @Excel(name = "易损件类型(一般配件=1,关键备件=2)")
    // 关键备件 2 or 一般备件 1
    private String importance;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setPartName(String partName)
    {
        this.partName = partName;
    }

    public String getPartName()
    {
        return partName;
    }

    public void setPartType(String partType)
    {
        this.partType = partType;
    }

    public String getPartType()
    {
        return partType;
    }

    public void setPos(String pos)
    {
        this.pos = pos;
    }

    public String getPos()
    {
        return pos;
    }

    public void setSafetyNum(Long safetyNum)
    {
        this.safetyNum = safetyNum;
    }

    public Long getSafetyNum()
    {
        return safetyNum;
    }

    public void setSafetyDistance(Long safetyDistance)
    {
        this.safetyDistance = safetyDistance;
    }

    public Long getSafetyDistance()
    {
        return safetyDistance;
    }

    public void setNum(Long num)
    {
        this.num = num;
    }

    public Long getNum()
    {
        return num;
    }

    public void setServiceLife(String serviceLife)
    {
        this.serviceLife = serviceLife;
    }

    public String getServiceLife()
    {
        return serviceLife;
    }

    public void setLeadTime(String leadTime)
    {
        this.leadTime = leadTime;
    }

    public String getLeadTime()
    {
        return leadTime;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getImg()
    {
        return img;
    }

    public void setGeneralAccessories(String generalAccessories)
    {
        this.generalAccessories = generalAccessories;
    }

    public String getGeneralAccessories()
    {
        return generalAccessories;
    }

    public void setCriticalSpares(String criticalSpares)
    {
        this.criticalSpares = criticalSpares;
    }

    public String getCriticalSpares()
    {
        return criticalSpares;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("partName", getPartName())
            .append("partType", getPartType())
            .append("pos", getPos())
            .append("safetyNum", getSafetyNum())
            .append("safetyDistance", getSafetyDistance())
            .append("num", getNum())
            .append("serviceLife", getServiceLife())
            .append("leadTime", getLeadTime())
            .append("img", getImg())
            .append("generalAccessories", getGeneralAccessories())
            .append("criticalSpares", getCriticalSpares())
            .toString();
    }
}

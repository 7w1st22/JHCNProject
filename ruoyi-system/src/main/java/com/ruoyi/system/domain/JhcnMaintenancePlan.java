package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 维护计划对象 jhcn_maintenance_plan
 *
 * @author russel
 * @date 2025-09-22
 */
public class JhcnMaintenancePlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceNo;

    /** 机台号 */
    @Excel(name = "机台号")
    private String jtNo;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 型号 */
    @Excel(name = "型号")
    private String deviceType;

    /** 厂家 */
    @Excel(name = "厂家")
    private String company;

    /** 计划维护日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划维护日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date maintanceDate;

    /** 工序 */
    @Excel(name = "工序")
    private String process;

    /** 提醒 0 关闭 1 开启 **/
    private String alarm;

    private int reminded;

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public int getReminded() {
        return reminded;
    }

    public void setReminded(int reminded) {
        this.reminded = reminded;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setDeviceNo(String deviceNo)
    {
        this.deviceNo = deviceNo;
    }

    public String getDeviceNo()
    {
        return deviceNo;
    }

    public void setJtNo(String jtNo)
    {
        this.jtNo = jtNo;
    }

    public String getJtNo()
    {
        return jtNo;
    }

    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getCompany()
    {
        return company;
    }

    public void setMaintanceDate(Date maintanceDate)
    {
        this.maintanceDate = maintanceDate;
    }

    public Date getMaintanceDate()
    {
        return maintanceDate;
    }

    public void setProcess(String process)
    {
        this.process = process;
    }

    public String getProcess()
    {
        return process;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceNo", getDeviceNo())
            .append("jtNo", getJtNo())
            .append("deviceName", getDeviceName())
            .append("deviceType", getDeviceType())
            .append("company", getCompany())
            .append("maintanceDate", getMaintanceDate())
            .append("process", getProcess())
            .toString();
    }
}

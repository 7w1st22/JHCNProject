package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * employeeInfo对象 jhcn_employee
 * 
 * @author russel
 * @date 2025-04-16
 */
public class JhcnEmployee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String credentialType;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String credentialNo;

    /** 姓名拼音 */
    @Excel(name = "姓名拼音")
    private String namePy;

    /** 用工形式 */
    @Excel(name = "用工形式")
    private String employeeType;

    /** 合同签署公司全称 */
    @Excel(name = "合同签署公司全称")
    private String companyName;

    /** 入职来源 */
    @Excel(name = "入职来源")
    private String jobSource;

    /** 工作地点 */
    @Excel(name = "工作地点")
    private String workPlace;

    /** 社保缴纳地 */
    @Excel(name = "社保缴纳地")
    private String socialSecurityPaymentLocation;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 出生日期 */
    @Excel(name = "出生日期")
    private String birthday;

    /** 民族 */
    @Excel(name = "民族")
    private String ethn;

    /** 婚姻状况 */
    @Excel(name = "婚姻状况")
    private String marriageStatus;

    /** 国籍 */
    @Excel(name = "国籍")
    private String nation;

    /** 户口性质 */
    @Excel(name = "户口性质")
    private String registeredResidenceNature;

    /** 户口所在地 */
    @Excel(name = "户口所在地")
    private String permanentResidence;

    /** 户口所在地详细地址 */
    @Excel(name = "户口所在地详细地址")
    private String permanentResidenceAddress;

    /** 籍贯 */
    @Excel(name = "籍贯")
    private String nativePlace;

    /** 出生地 */
    @Excel(name = "出生地")
    private String birthPlace;

    /** 家庭地址 */
    @Excel(name = "家庭地址")
    private String homeAddress;

    /** 家庭地址详细信息 */
    @Excel(name = "家庭地址详细信息")
    private String homeAddressInfo;

    /** 工作时间 */
    @Excel(name = "工作时间")
    private String workStart;

    /** 入职时间 */
    @Excel(name = "入职时间")
    private String jobStart;

    /** 个人邮箱 */
    @Excel(name = "个人邮箱")
    private String email;

    /** 常用手机 */
    @Excel(name = "常用手机")
    private String phone;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setCredentialType(String credentialType) 
    {
        this.credentialType = credentialType;
    }

    public String getCredentialType() 
    {
        return credentialType;
    }

    public void setCredentialNo(String credentialNo) 
    {
        this.credentialNo = credentialNo;
    }

    public String getCredentialNo() 
    {
        return credentialNo;
    }

    public void setNamePy(String namePy) 
    {
        this.namePy = namePy;
    }

    public String getNamePy() 
    {
        return namePy;
    }

    public void setEmployeeType(String employeeType) 
    {
        this.employeeType = employeeType;
    }

    public String getEmployeeType() 
    {
        return employeeType;
    }

    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }

    public void setJobSource(String jobSource) 
    {
        this.jobSource = jobSource;
    }

    public String getJobSource() 
    {
        return jobSource;
    }

    public void setWorkPlace(String workPlace) 
    {
        this.workPlace = workPlace;
    }

    public String getWorkPlace() 
    {
        return workPlace;
    }

    public void setSocialSecurityPaymentLocation(String socialSecurityPaymentLocation) 
    {
        this.socialSecurityPaymentLocation = socialSecurityPaymentLocation;
    }

    public String getSocialSecurityPaymentLocation() 
    {
        return socialSecurityPaymentLocation;
    }

    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }

    public void setBirthday(String birthday) 
    {
        this.birthday = birthday;
    }

    public String getBirthday() 
    {
        return birthday;
    }

    public void setEthn(String ethn) 
    {
        this.ethn = ethn;
    }

    public String getEthn() 
    {
        return ethn;
    }

    public void setMarriageStatus(String marriageStatus) 
    {
        this.marriageStatus = marriageStatus;
    }

    public String getMarriageStatus() 
    {
        return marriageStatus;
    }

    public void setNation(String nation) 
    {
        this.nation = nation;
    }

    public String getNation() 
    {
        return nation;
    }

    public void setRegisteredResidenceNature(String registeredResidenceNature) 
    {
        this.registeredResidenceNature = registeredResidenceNature;
    }

    public String getRegisteredResidenceNature() 
    {
        return registeredResidenceNature;
    }

    public void setPermanentResidence(String permanentResidence) 
    {
        this.permanentResidence = permanentResidence;
    }

    public String getPermanentResidence() 
    {
        return permanentResidence;
    }

    public void setPermanentResidenceAddress(String permanentResidenceAddress) 
    {
        this.permanentResidenceAddress = permanentResidenceAddress;
    }

    public String getPermanentResidenceAddress() 
    {
        return permanentResidenceAddress;
    }

    public void setNativePlace(String nativePlace) 
    {
        this.nativePlace = nativePlace;
    }

    public String getNativePlace() 
    {
        return nativePlace;
    }

    public void setBirthPlace(String birthPlace) 
    {
        this.birthPlace = birthPlace;
    }

    public String getBirthPlace() 
    {
        return birthPlace;
    }

    public void setHomeAddress(String homeAddress) 
    {
        this.homeAddress = homeAddress;
    }

    public String getHomeAddress() 
    {
        return homeAddress;
    }

    public void setHomeAddressInfo(String homeAddressInfo) 
    {
        this.homeAddressInfo = homeAddressInfo;
    }

    public String getHomeAddressInfo() 
    {
        return homeAddressInfo;
    }

    public void setWorkStart(String workStart) 
    {
        this.workStart = workStart;
    }

    public String getWorkStart() 
    {
        return workStart;
    }

    public void setJobStart(String jobStart) 
    {
        this.jobStart = jobStart;
    }

    public String getJobStart() 
    {
        return jobStart;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("credentialType", getCredentialType())
            .append("credentialNo", getCredentialNo())
            .append("namePy", getNamePy())
            .append("employeeType", getEmployeeType())
            .append("companyName", getCompanyName())
            .append("jobSource", getJobSource())
            .append("workPlace", getWorkPlace())
            .append("socialSecurityPaymentLocation", getSocialSecurityPaymentLocation())
            .append("sex", getSex())
            .append("birthday", getBirthday())
            .append("ethn", getEthn())
            .append("marriageStatus", getMarriageStatus())
            .append("nation", getNation())
            .append("registeredResidenceNature", getRegisteredResidenceNature())
            .append("permanentResidence", getPermanentResidence())
            .append("permanentResidenceAddress", getPermanentResidenceAddress())
            .append("nativePlace", getNativePlace())
            .append("birthPlace", getBirthPlace())
            .append("homeAddress", getHomeAddress())
            .append("homeAddressInfo", getHomeAddressInfo())
            .append("workStart", getWorkStart())
            .append("jobStart", getJobStart())
            .append("email", getEmail())
            .append("phone", getPhone())
            .toString();
    }
}

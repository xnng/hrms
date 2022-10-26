package com.ruoyi.hrms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class MajorDic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long majorId;

    /** 门类 */
    @Excel(name = "门类")
    private String category;

    /** 专业类 */
    @Excel(name = "专业类")
    private String professional;

    /** 专业代码 */
    @Excel(name = "专业代码")
    private String majorCode;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String majorName;

    /** 学位授予门类 */
    @Excel(name = "学位授予门类")
    private String degreeCategory;

    public void setMajorId(Long majorId)
    {
        this.majorId = majorId;
    }

    public Long getMajorId()
    {
        return majorId;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }
    public void setProfessional(String professional)
    {
        this.professional = professional;
    }

    public String getProfessional()
    {
        return professional;
    }
    public void setMajorCode(String majorCode)
    {
        this.majorCode = majorCode;
    }

    public String getMajorCode()
    {
        return majorCode;
    }
    public void setMajorName(String majorName)
    {
        this.majorName = majorName;
    }

    public String getMajorName()
    {
        return majorName;
    }
    public void setDegreeCategory(String degreeCategory)
    {
        this.degreeCategory = degreeCategory;
    }

    public String getDegreeCategory()
    {
        return degreeCategory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("majorId", getMajorId())
                .append("category", getCategory())
                .append("professional", getProfessional())
                .append("majorCode", getMajorCode())
                .append("majorName", getMajorName())
                .append("degreeCategory", getDegreeCategory())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("remark", getRemark())
                .toString();
    }
}

package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JhcnSpecification;

/**
 * 模组规格Mapper接口
 * 
 * @author ruoyi
 * @date 2025-08-26
 */
public interface JhcnSpecificationMapper 
{
    /**
     * 查询模组规格
     * 
     * @param id 模组规格主键
     * @return 模组规格
     */
    public JhcnSpecification selectJhcnSpecificationById(Long id);

    /**
     * 查询模组规格列表
     * 
     * @param jhcnSpecification 模组规格
     * @return 模组规格集合
     */
    public List<JhcnSpecification> selectJhcnSpecificationList(JhcnSpecification jhcnSpecification);

    /**
     * 新增模组规格
     * 
     * @param jhcnSpecification 模组规格
     * @return 结果
     */
    public int insertJhcnSpecification(JhcnSpecification jhcnSpecification);

    /**
     * 修改模组规格
     * 
     * @param jhcnSpecification 模组规格
     * @return 结果
     */
    public int updateJhcnSpecification(JhcnSpecification jhcnSpecification);

    /**
     * 删除模组规格
     * 
     * @param id 模组规格主键
     * @return 结果
     */
    public int deleteJhcnSpecificationById(Long id);

    /**
     * 批量删除模组规格
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJhcnSpecificationByIds(Long[] ids);
}

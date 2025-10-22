package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.JhcnElectrodeSpecification;

/**
 * 电极规格管理Service接口
 * 
 * @author Russel
 * @date 2025-10-22
 */
public interface IJhcnElectrodeSpecificationService 
{
    /**
     * 查询电极规格管理
     * 
     * @param id 电极规格管理主键
     * @return 电极规格管理
     */
    public JhcnElectrodeSpecification selectJhcnElectrodeSpecificationById(Long id);

    /**
     * 查询电极规格管理列表
     * 
     * @param jhcnElectrodeSpecification 电极规格管理
     * @return 电极规格管理集合
     */
    public List<JhcnElectrodeSpecification> selectJhcnElectrodeSpecificationList(JhcnElectrodeSpecification jhcnElectrodeSpecification);

    /**
     * 新增电极规格管理
     * 
     * @param jhcnElectrodeSpecification 电极规格管理
     * @return 结果
     */
    public int insertJhcnElectrodeSpecification(JhcnElectrodeSpecification jhcnElectrodeSpecification);

    /**
     * 修改电极规格管理
     * 
     * @param jhcnElectrodeSpecification 电极规格管理
     * @return 结果
     */
    public int updateJhcnElectrodeSpecification(JhcnElectrodeSpecification jhcnElectrodeSpecification);

    /**
     * 批量删除电极规格管理
     * 
     * @param ids 需要删除的电极规格管理主键集合
     * @return 结果
     */
    public int deleteJhcnElectrodeSpecificationByIds(Long[] ids);

    /**
     * 删除电极规格管理信息
     * 
     * @param id 电极规格管理主键
     * @return 结果
     */
    public int deleteJhcnElectrodeSpecificationById(Long id);
}

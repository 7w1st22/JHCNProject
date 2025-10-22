package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnElectrodeSpecificationMapper;
import com.ruoyi.system.domain.JhcnElectrodeSpecification;
import com.ruoyi.system.service.IJhcnElectrodeSpecificationService;

/**
 * 电极规格管理Service业务层处理
 * 
 * @author Russel
 * @date 2025-10-22
 */
@Service
public class JhcnElectrodeSpecificationServiceImpl implements IJhcnElectrodeSpecificationService 
{
    @Autowired
    private JhcnElectrodeSpecificationMapper jhcnElectrodeSpecificationMapper;

    /**
     * 查询电极规格管理
     * 
     * @param id 电极规格管理主键
     * @return 电极规格管理
     */
    @Override
    public JhcnElectrodeSpecification selectJhcnElectrodeSpecificationById(Long id)
    {
        return jhcnElectrodeSpecificationMapper.selectJhcnElectrodeSpecificationById(id);
    }

    /**
     * 查询电极规格管理列表
     * 
     * @param jhcnElectrodeSpecification 电极规格管理
     * @return 电极规格管理
     */
    @Override
    public List<JhcnElectrodeSpecification> selectJhcnElectrodeSpecificationList(JhcnElectrodeSpecification jhcnElectrodeSpecification)
    {
        return jhcnElectrodeSpecificationMapper.selectJhcnElectrodeSpecificationList(jhcnElectrodeSpecification);
    }

    /**
     * 新增电极规格管理
     * 
     * @param jhcnElectrodeSpecification 电极规格管理
     * @return 结果
     */
    @Override
    public int insertJhcnElectrodeSpecification(JhcnElectrodeSpecification jhcnElectrodeSpecification)
    {
        return jhcnElectrodeSpecificationMapper.insertJhcnElectrodeSpecification(jhcnElectrodeSpecification);
    }

    /**
     * 修改电极规格管理
     * 
     * @param jhcnElectrodeSpecification 电极规格管理
     * @return 结果
     */
    @Override
    public int updateJhcnElectrodeSpecification(JhcnElectrodeSpecification jhcnElectrodeSpecification)
    {
        return jhcnElectrodeSpecificationMapper.updateJhcnElectrodeSpecification(jhcnElectrodeSpecification);
    }

    /**
     * 批量删除电极规格管理
     * 
     * @param ids 需要删除的电极规格管理主键
     * @return 结果
     */
    @Override
    public int deleteJhcnElectrodeSpecificationByIds(Long[] ids)
    {
        return jhcnElectrodeSpecificationMapper.deleteJhcnElectrodeSpecificationByIds(ids);
    }

    /**
     * 删除电极规格管理信息
     * 
     * @param id 电极规格管理主键
     * @return 结果
     */
    @Override
    public int deleteJhcnElectrodeSpecificationById(Long id)
    {
        return jhcnElectrodeSpecificationMapper.deleteJhcnElectrodeSpecificationById(id);
    }
}

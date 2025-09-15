package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnSpecificationMapper;
import com.ruoyi.system.domain.JhcnSpecification;
import com.ruoyi.system.service.IJhcnSpecificationService;

/**
 * 模组规格Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-26
 */
@Service
public class JhcnSpecificationServiceImpl implements IJhcnSpecificationService 
{
    @Autowired
    private JhcnSpecificationMapper jhcnSpecificationMapper;

    /**
     * 查询模组规格
     * 
     * @param id 模组规格主键
     * @return 模组规格
     */
    @Override
    public JhcnSpecification selectJhcnSpecificationById(Long id)
    {
        return jhcnSpecificationMapper.selectJhcnSpecificationById(id);
    }

    /**
     * 查询模组规格列表
     * 
     * @param jhcnSpecification 模组规格
     * @return 模组规格
     */
    @Override
    public List<JhcnSpecification> selectJhcnSpecificationList(JhcnSpecification jhcnSpecification)
    {
        return jhcnSpecificationMapper.selectJhcnSpecificationList(jhcnSpecification);
    }

    /**
     * 新增模组规格
     * 
     * @param jhcnSpecification 模组规格
     * @return 结果
     */
    @Override
    public int insertJhcnSpecification(JhcnSpecification jhcnSpecification)
    {
        jhcnSpecification.setCreateTime(DateUtils.getNowDate());
        return jhcnSpecificationMapper.insertJhcnSpecification(jhcnSpecification);
    }

    /**
     * 修改模组规格
     * 
     * @param jhcnSpecification 模组规格
     * @return 结果
     */
    @Override
    public int updateJhcnSpecification(JhcnSpecification jhcnSpecification)
    {
        jhcnSpecification.setUpdateTime(DateUtils.getNowDate());
        return jhcnSpecificationMapper.updateJhcnSpecification(jhcnSpecification);
    }

    /**
     * 批量删除模组规格
     * 
     * @param ids 需要删除的模组规格主键
     * @return 结果
     */
    @Override
    public int deleteJhcnSpecificationByIds(Long[] ids)
    {
        return jhcnSpecificationMapper.deleteJhcnSpecificationByIds(ids);
    }

    /**
     * 删除模组规格信息
     * 
     * @param id 模组规格主键
     * @return 结果
     */
    @Override
    public int deleteJhcnSpecificationById(Long id)
    {
        return jhcnSpecificationMapper.deleteJhcnSpecificationById(id);
    }
}

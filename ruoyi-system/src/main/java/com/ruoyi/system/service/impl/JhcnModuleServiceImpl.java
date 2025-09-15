package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Objects;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.JhcnSpecification;
import com.ruoyi.system.mapper.JhcnSpecificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnModuleMapper;
import com.ruoyi.system.domain.JhcnModule;
import com.ruoyi.system.service.IJhcnModuleService;

/**
 * 模组条码Service业务层处理
 *
 * @author ruoyi
 * @date 2025-08-26
 */
@Service
public class JhcnModuleServiceImpl implements IJhcnModuleService
{
    @Autowired
    private JhcnModuleMapper jhcnModuleMapper;

    @Autowired
    private JhcnSpecificationMapper jhcnSpecificationMapper;

    /**
     * 查询模组条码
     *
     * @param id 模组条码主键
     * @return 模组条码
     */
    @Override
    public JhcnModule selectJhcnModuleById(Long id)
    {
        return jhcnModuleMapper.selectJhcnModuleById(id);
    }

    /**
     * 查询模组条码列表
     *
     * @param jhcnModule 模组条码
     * @return 模组条码
     */
    @Override
    public List<JhcnModule> selectJhcnModuleList(JhcnModule jhcnModule)
    {
        return jhcnModuleMapper.selectJhcnModuleList(jhcnModule);
    }

    /**
     * 新增模组条码
     *
     * @param jhcnModule 模组条码
     * @return 结果
     */
    @Override
    public int insertJhcnModule(JhcnModule jhcnModule)
    {
        jhcnModule.setCreateTime(DateUtils.getNowDate());
        return jhcnModuleMapper.insertJhcnModule(jhcnModule);
    }

    /**
     * 修改模组条码
     *
     * @param jhcnModule 模组条码
     * @return 结果
     */
    @Override
    public int updateJhcnModule(JhcnModule jhcnModule)
    {
        jhcnModule.setUpdateTime(DateUtils.getNowDate());
        JhcnModule module = jhcnModuleMapper.selectJhcnModuleById(jhcnModule.getId());
        if (!Objects.equals(module.getSpecificationId(), jhcnModule.getSpecificationId())) {
            // 更换规格
            JhcnSpecification specification = jhcnSpecificationMapper.selectJhcnSpecificationById(jhcnModule.getSpecificationId());
            // 更换规格数量不小于目前已经绑定的数量
            if (specification.getNum() < module.getBindedNum()) {
                throw new RuntimeException("更换规格数量不应小于目前已经绑定的数量");
            }
        }

        return jhcnModuleMapper.updateJhcnModule(jhcnModule);
    }

    /**
     * 批量删除模组条码
     *
     * @param ids 需要删除的模组条码主键
     * @return 结果
     */
    @Override
    public int deleteJhcnModuleByIds(Long[] ids)
    {
        return jhcnModuleMapper.deleteJhcnModuleByIds(ids);
    }

    /**
     * 删除模组条码信息
     *
     * @param id 模组条码主键
     * @return 结果
     */
    @Override
    public int deleteJhcnModuleById(Long id)
    {
        return jhcnModuleMapper.deleteJhcnModuleById(id);
    }
}

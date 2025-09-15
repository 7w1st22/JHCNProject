package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnCheckPlanMapper;
import com.ruoyi.system.domain.JhcnCheckPlan;
import com.ruoyi.system.service.IJhcnCheckPlanService;

/**
 * 检查计划Service业务层处理
 * 
 * @author russel
 * @date 2025-09-12
 */
@Service
public class JhcnCheckPlanServiceImpl implements IJhcnCheckPlanService 
{
    @Autowired
    private JhcnCheckPlanMapper jhcnCheckPlanMapper;

    /**
     * 查询检查计划
     * 
     * @param id 检查计划主键
     * @return 检查计划
     */
    @Override
    public JhcnCheckPlan selectJhcnCheckPlanById(Long id)
    {
        return jhcnCheckPlanMapper.selectJhcnCheckPlanById(id);
    }

    /**
     * 查询检查计划列表
     * 
     * @param jhcnCheckPlan 检查计划
     * @return 检查计划
     */
    @Override
    public List<JhcnCheckPlan> selectJhcnCheckPlanList(JhcnCheckPlan jhcnCheckPlan)
    {
        return jhcnCheckPlanMapper.selectJhcnCheckPlanList(jhcnCheckPlan);
    }

    /**
     * 新增检查计划
     * 
     * @param jhcnCheckPlan 检查计划
     * @return 结果
     */
    @Override
    public int insertJhcnCheckPlan(JhcnCheckPlan jhcnCheckPlan)
    {
        return jhcnCheckPlanMapper.insertJhcnCheckPlan(jhcnCheckPlan);
    }

    /**
     * 修改检查计划
     * 
     * @param jhcnCheckPlan 检查计划
     * @return 结果
     */
    @Override
    public int updateJhcnCheckPlan(JhcnCheckPlan jhcnCheckPlan)
    {
        return jhcnCheckPlanMapper.updateJhcnCheckPlan(jhcnCheckPlan);
    }

    /**
     * 批量删除检查计划
     * 
     * @param ids 需要删除的检查计划主键
     * @return 结果
     */
    @Override
    public int deleteJhcnCheckPlanByIds(Long[] ids)
    {
        return jhcnCheckPlanMapper.deleteJhcnCheckPlanByIds(ids);
    }

    /**
     * 删除检查计划信息
     * 
     * @param id 检查计划主键
     * @return 结果
     */
    @Override
    public int deleteJhcnCheckPlanById(Long id)
    {
        return jhcnCheckPlanMapper.deleteJhcnCheckPlanById(id);
    }
}

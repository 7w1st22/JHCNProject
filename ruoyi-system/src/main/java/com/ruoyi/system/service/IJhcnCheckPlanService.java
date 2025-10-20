package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.JhcnCheckPlan;
import com.ruoyi.system.domain.JhcnMaintenancePlan;

/**
 * 检查计划Service接口
 *
 * @author russel
 * @date 2025-09-12
 */
public interface IJhcnCheckPlanService
{
    /**
     * 查询检查计划
     *
     * @param id 检查计划主键
     * @return 检查计划
     */
    public JhcnCheckPlan selectJhcnCheckPlanById(Long id);

    /**
     * 查询检查计划列表
     *
     * @param jhcnCheckPlan 检查计划
     * @return 检查计划集合
     */
    public List<JhcnCheckPlan> selectJhcnCheckPlanList(JhcnCheckPlan jhcnCheckPlan);

    /**
     * 新增检查计划
     *
     * @param jhcnCheckPlan 检查计划
     * @return 结果
     */
    public int insertJhcnCheckPlan(JhcnCheckPlan jhcnCheckPlan);

    /**
     * 修改检查计划
     *
     * @param jhcnCheckPlan 检查计划
     * @return 结果
     */
    public int updateJhcnCheckPlan(JhcnCheckPlan jhcnCheckPlan);

    /**
     * 批量删除检查计划
     *
     * @param ids 需要删除的检查计划主键集合
     * @return 结果
     */
    public int deleteJhcnCheckPlanByIds(Long[] ids);

    /**
     * 删除检查计划信息
     *
     * @param id 检查计划主键
     * @return 结果
     */
    public int deleteJhcnCheckPlanById(Long id);

    public String importList(List<JhcnCheckPlan> checkList, Boolean updateSupport, String operName);

    /**
     * 改变提醒状态
     *
     * @param jhcnCheckPlan 检查计划
     * @return 结果
     */
    public int changeAlarmStatus(JhcnCheckPlan jhcnCheckPlan);
}

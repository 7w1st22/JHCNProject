package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.system.domain.JhcnMaintenancePlan;
import com.ruoyi.system.domain.JhcnWearingParts;
import org.quartz.SchedulerException;

/**
 * 维护计划Service接口
 *
 * @author russel
 * @date 2025-09-22
 */
public interface IJhcnMaintenancePlanService
{
    /**
     * 查询维护计划
     *
     * @param id 维护计划主键
     * @return 维护计划
     */
    public JhcnMaintenancePlan selectJhcnMaintenancePlanById(Long id);

    /**
     * 查询维护计划列表
     *
     * @param jhcnMaintenancePlan 维护计划
     * @return 维护计划集合
     */
    public List<JhcnMaintenancePlan> selectJhcnMaintenancePlanList(JhcnMaintenancePlan jhcnMaintenancePlan);

    /**
     * 新增维护计划
     *
     * @param jhcnMaintenancePlan 维护计划
     * @return 结果
     */
    public int insertJhcnMaintenancePlan(JhcnMaintenancePlan jhcnMaintenancePlan) throws SchedulerException, TaskException;

    /**
     * 修改维护计划
     *
     * @param jhcnMaintenancePlan 维护计划
     * @return 结果
     */
    public int updateJhcnMaintenancePlan(JhcnMaintenancePlan jhcnMaintenancePlan);

    /**
     * 批量删除维护计划
     *
     * @param ids 需要删除的维护计划主键集合
     * @return 结果
     */
    public int deleteJhcnMaintenancePlanByIds(Long[] ids);

    /**
     * 删除维护计划信息
     *
     * @param id 维护计划主键
     * @return 结果
     */
    public int deleteJhcnMaintenancePlanById(Long id);

    public String importList(List<JhcnMaintenancePlan> partList, Boolean updateSupport, String operName);
}

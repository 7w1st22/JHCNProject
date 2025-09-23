package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JhcnMaintenancePlan;

/**
 * 维护计划Mapper接口
 *
 * @author russel
 * @date 2025-09-22
 */
public interface JhcnMaintenancePlanMapper
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

    public JhcnMaintenancePlan selectJhcnMaintenancePlanByDeviceNo(String deviceNo);

    /**
     * 新增维护计划
     *
     * @param jhcnMaintenancePlan 维护计划
     * @return 结果
     */
    public int insertJhcnMaintenancePlan(JhcnMaintenancePlan jhcnMaintenancePlan);

    /**
     * 修改维护计划
     *
     * @param jhcnMaintenancePlan 维护计划
     * @return 结果
     */
    public int updateJhcnMaintenancePlan(JhcnMaintenancePlan jhcnMaintenancePlan);

    /**
     * 删除维护计划
     *
     * @param id 维护计划主键
     * @return 结果
     */
    public int deleteJhcnMaintenancePlanById(Long id);

    /**
     * 批量删除维护计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJhcnMaintenancePlanByIds(Long[] ids);


}

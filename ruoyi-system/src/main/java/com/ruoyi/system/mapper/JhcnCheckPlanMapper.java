package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JhcnCheckPlan;
import org.apache.ibatis.annotations.Param;

/**
 * 检查计划Mapper接口
 *
 * @author russel
 * @date 2025-09-12
 */
public interface JhcnCheckPlanMapper
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
     * 删除检查计划
     *
     * @param id 检查计划主键
     * @return 结果
     */
    public int deleteJhcnCheckPlanById(Long id);

    /**
     * 批量删除检查计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJhcnCheckPlanByIds(Long[] ids);

    JhcnCheckPlan selectJhcnCheckPlanByDeviceNo(String deviceNo);

    JhcnCheckPlan selectJhcnCheckPlanByDeviceNoAndJtNo(@Param("deviceNo") String deviceNo, @Param("jtNo") String jtNo);
}

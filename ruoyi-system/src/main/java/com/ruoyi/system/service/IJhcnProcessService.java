package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.JhcnProcess;

/**
 * 工序Service接口
 * 
 * @author Russel
 * @date 2025-10-09
 */
public interface IJhcnProcessService 
{
    /**
     * 查询工序
     * 
     * @param id 工序主键
     * @return 工序
     */
    public JhcnProcess selectJhcnProcessById(Long id);

    /**
     * 查询工序列表
     * 
     * @param jhcnProcess 工序
     * @return 工序集合
     */
    public List<JhcnProcess> selectJhcnProcessList(JhcnProcess jhcnProcess);

    /**
     * 新增工序
     * 
     * @param jhcnProcess 工序
     * @return 结果
     */
    public int insertJhcnProcess(JhcnProcess jhcnProcess);

    /**
     * 修改工序
     * 
     * @param jhcnProcess 工序
     * @return 结果
     */
    public int updateJhcnProcess(JhcnProcess jhcnProcess);

    /**
     * 批量删除工序
     * 
     * @param ids 需要删除的工序主键集合
     * @return 结果
     */
    public int deleteJhcnProcessByIds(Long[] ids);

    /**
     * 删除工序信息
     * 
     * @param id 工序主键
     * @return 结果
     */
    public int deleteJhcnProcessById(Long id);

    JhcnProcess selectJhcnProcessByProcess(String process);
}

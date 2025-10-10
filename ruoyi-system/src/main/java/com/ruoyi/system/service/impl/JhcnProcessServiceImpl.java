package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnProcessMapper;
import com.ruoyi.system.domain.JhcnProcess;
import com.ruoyi.system.service.IJhcnProcessService;

/**
 * 工序Service业务层处理
 *
 * @author Russel
 * @date 2025-10-09
 */
@Service
public class JhcnProcessServiceImpl implements IJhcnProcessService
{
    @Autowired
    private JhcnProcessMapper jhcnProcessMapper;

    /**
     * 查询工序
     *
     * @param id 工序主键
     * @return 工序
     */
    @Override
    public JhcnProcess selectJhcnProcessById(Long id)
    {
        return jhcnProcessMapper.selectJhcnProcessById(id);
    }

    /**
     * 查询工序列表
     *
     * @param jhcnProcess 工序
     * @return 工序
     */
    @Override
    public List<JhcnProcess> selectJhcnProcessList(JhcnProcess jhcnProcess)
    {
        return jhcnProcessMapper.selectJhcnProcessList(jhcnProcess);
    }

    /**
     * 新增工序
     *
     * @param jhcnProcess 工序
     * @return 结果
     */
    @Override
    public int insertJhcnProcess(JhcnProcess jhcnProcess)
    {
        return jhcnProcessMapper.insertJhcnProcess(jhcnProcess);
    }

    /**
     * 修改工序
     *
     * @param jhcnProcess 工序
     * @return 结果
     */
    @Override
    public int updateJhcnProcess(JhcnProcess jhcnProcess)
    {
        return jhcnProcessMapper.updateJhcnProcess(jhcnProcess);
    }

    /**
     * 批量删除工序
     *
     * @param ids 需要删除的工序主键
     * @return 结果
     */
    @Override
    public int deleteJhcnProcessByIds(Long[] ids)
    {
        // todo 检查是否有关联子表 包含月度维护计划 计划内容表等
        return jhcnProcessMapper.deleteJhcnProcessByIds(ids);
    }

    /**
     * 删除工序信息
     *
     * @param id 工序主键
     * @return 结果
     */
    @Override
    public int deleteJhcnProcessById(Long id)
    {
        return jhcnProcessMapper.deleteJhcnProcessById(id);
    }

    @Override
    public JhcnProcess selectJhcnProcessByProcess(String process) {
        return jhcnProcessMapper.selectJhcnProcessByProcess(process);
    }
}

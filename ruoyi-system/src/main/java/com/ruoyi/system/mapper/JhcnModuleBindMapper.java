package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JhcnModuleBind;

/**
 * 绑定管理Mapper接口
 *
 * @author ruoyi
 * @date 2025-08-26
 */
public interface JhcnModuleBindMapper
{
    /**
     * 查询绑定管理
     *
     * @param id 绑定管理主键
     * @return 绑定管理
     */
    public JhcnModuleBind selectJhcnModuleBindById(Long id);

    /**
     * 查询绑定管理列表
     *
     * @param jhcnModuleBind 绑定管理
     * @return 绑定管理集合
     */
    public List<JhcnModuleBind> selectJhcnModuleBindList(JhcnModuleBind jhcnModuleBind);

    /**
     * 新增绑定管理
     *
     * @param jhcnModuleBind 绑定管理
     * @return 结果
     */
    public int insertJhcnModuleBind(JhcnModuleBind jhcnModuleBind);

    /**
     * 修改绑定管理
     *
     * @param jhcnModuleBind 绑定管理
     * @return 结果
     */
    public int updateJhcnModuleBind(JhcnModuleBind jhcnModuleBind);

    /**
     * 删除绑定管理
     *
     * @param id 绑定管理主键
     * @return 结果
     */
    public int deleteJhcnModuleBindById(Long id);

    /**
     * 批量删除绑定管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJhcnModuleBindByIds(Long[] ids);

    JhcnModuleBind selectJhcnModuleBindByDtCode(String dtCode);


}

package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JhcnModule;

/**
 * 模组条码Mapper接口
 *
 * @author ruoyi
 * @date 2025-08-26
 */
public interface JhcnModuleMapper
{
    /**
     * 查询模组条码
     *
     * @param id 模组条码主键
     * @return 模组条码
     */
    public JhcnModule selectJhcnModuleById(Long id);

    /**
     * 查询模组条码列表
     *
     * @param jhcnModule 模组条码
     * @return 模组条码集合
     */
    public List<JhcnModule> selectJhcnModuleList(JhcnModule jhcnModule);

    /**
     * 新增模组条码
     *
     * @param jhcnModule 模组条码
     * @return 结果
     */
    public int insertJhcnModule(JhcnModule jhcnModule);

    /**
     * 修改模组条码
     *
     * @param jhcnModule 模组条码
     * @return 结果
     */
    public int updateJhcnModule(JhcnModule jhcnModule);

    /**
     * 删除模组条码
     *
     * @param id 模组条码主键
     * @return 结果
     */
    public int deleteJhcnModuleById(Long id);

    /**
     * 批量删除模组条码
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJhcnModuleByIds(Long[] ids);
}

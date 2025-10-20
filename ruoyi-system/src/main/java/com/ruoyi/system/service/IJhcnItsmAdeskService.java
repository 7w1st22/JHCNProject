package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.JhcnItsmAdesk;

/**
 * IT工单Service接口
 * 
 * @author Russel
 * @date 2025-10-17
 */
public interface IJhcnItsmAdeskService 
{
    /**
     * 查询IT工单
     * 
     * @param id IT工单主键
     * @return IT工单
     */
    public JhcnItsmAdesk selectJhcnItsmAdeskById(Long id);

    /**
     * 查询IT工单列表
     * 
     * @param jhcnItsmAdesk IT工单
     * @return IT工单集合
     */
    public List<JhcnItsmAdesk> selectJhcnItsmAdeskList(JhcnItsmAdesk jhcnItsmAdesk);

    /**
     * 新增IT工单
     * 
     * @param jhcnItsmAdesk IT工单
     * @return 结果
     */
    public int insertJhcnItsmAdesk(JhcnItsmAdesk jhcnItsmAdesk);

    /**
     * 修改IT工单
     * 
     * @param jhcnItsmAdesk IT工单
     * @return 结果
     */
    public int updateJhcnItsmAdesk(JhcnItsmAdesk jhcnItsmAdesk);

    /**
     * 批量删除IT工单
     * 
     * @param ids 需要删除的IT工单主键集合
     * @return 结果
     */
    public int deleteJhcnItsmAdeskByIds(Long[] ids);

    /**
     * 删除IT工单信息
     * 
     * @param id IT工单主键
     * @return 结果
     */
    public int deleteJhcnItsmAdeskById(Long id);
}

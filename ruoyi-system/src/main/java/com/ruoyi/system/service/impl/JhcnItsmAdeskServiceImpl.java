package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnItsmAdeskMapper;
import com.ruoyi.system.domain.JhcnItsmAdesk;
import com.ruoyi.system.service.IJhcnItsmAdeskService;

/**
 * IT工单Service业务层处理
 * 
 * @author Russel
 * @date 2025-10-17
 */
@Service
public class JhcnItsmAdeskServiceImpl implements IJhcnItsmAdeskService 
{
    @Autowired
    private JhcnItsmAdeskMapper jhcnItsmAdeskMapper;

    /**
     * 查询IT工单
     * 
     * @param id IT工单主键
     * @return IT工单
     */
    @Override
    public JhcnItsmAdesk selectJhcnItsmAdeskById(Long id)
    {
        return jhcnItsmAdeskMapper.selectJhcnItsmAdeskById(id);
    }

    /**
     * 查询IT工单列表
     * 
     * @param jhcnItsmAdesk IT工单
     * @return IT工单
     */
    @Override
    public List<JhcnItsmAdesk> selectJhcnItsmAdeskList(JhcnItsmAdesk jhcnItsmAdesk)
    {
        return jhcnItsmAdeskMapper.selectJhcnItsmAdeskList(jhcnItsmAdesk);
    }

    /**
     * 新增IT工单
     * 
     * @param jhcnItsmAdesk IT工单
     * @return 结果
     */
    @Override
    public int insertJhcnItsmAdesk(JhcnItsmAdesk jhcnItsmAdesk)
    {
        jhcnItsmAdesk.setCreateTime(DateUtils.getNowDate());
        return jhcnItsmAdeskMapper.insertJhcnItsmAdesk(jhcnItsmAdesk);
    }

    /**
     * 修改IT工单
     * 
     * @param jhcnItsmAdesk IT工单
     * @return 结果
     */
    @Override
    public int updateJhcnItsmAdesk(JhcnItsmAdesk jhcnItsmAdesk)
    {
        return jhcnItsmAdeskMapper.updateJhcnItsmAdesk(jhcnItsmAdesk);
    }

    /**
     * 批量删除IT工单
     * 
     * @param ids 需要删除的IT工单主键
     * @return 结果
     */
    @Override
    public int deleteJhcnItsmAdeskByIds(Long[] ids)
    {
        return jhcnItsmAdeskMapper.deleteJhcnItsmAdeskByIds(ids);
    }

    /**
     * 删除IT工单信息
     * 
     * @param id IT工单主键
     * @return 结果
     */
    @Override
    public int deleteJhcnItsmAdeskById(Long id)
    {
        return jhcnItsmAdeskMapper.deleteJhcnItsmAdeskById(id);
    }
}

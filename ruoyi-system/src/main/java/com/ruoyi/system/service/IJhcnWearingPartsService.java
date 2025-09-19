package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.JhcnWearingParts;

/**
 * 易损件Service接口
 *
 * @author russel
 * @date 2025-09-15
 */
public interface IJhcnWearingPartsService
{
    /**
     * 查询易损件
     *
     * @param id 易损件主键
     * @return 易损件
     */
    public JhcnWearingParts selectJhcnWearingPartsById(Long id);

    /**
     * 查询易损件列表
     *
     * @param jhcnWearingParts 易损件
     * @return 易损件集合
     */
    public List<JhcnWearingParts> selectJhcnWearingPartsList(JhcnWearingParts jhcnWearingParts);

    /**
     * 新增易损件
     *
     * @param jhcnWearingParts 易损件
     * @return 结果
     */
    public int insertJhcnWearingParts(JhcnWearingParts jhcnWearingParts);

    /**
     * 修改易损件
     *
     * @param jhcnWearingParts 易损件
     * @return 结果
     */
    public int updateJhcnWearingParts(JhcnWearingParts jhcnWearingParts);
    public int changedNum(Long id,Long existedNum);

    /**
     * 批量删除易损件
     *
     * @param ids 需要删除的易损件主键集合
     * @return 结果
     */
    public int deleteJhcnWearingPartsByIds(Long[] ids);

    /**
     * 删除易损件信息
     *
     * @param id 易损件主键
     * @return 结果
     */
    public int deleteJhcnWearingPartsById(Long id);

    String importParts(List<JhcnWearingParts> partList, Boolean updateSupport, String operName);


}

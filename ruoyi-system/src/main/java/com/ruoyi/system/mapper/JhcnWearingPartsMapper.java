package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JhcnWearingParts;
import org.apache.ibatis.annotations.Param;

/**
 * 易损件Mapper接口
 *
 * @author russel
 * @date 2025-09-15
 */
public interface JhcnWearingPartsMapper
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

    /**
     * 删除易损件
     *
     * @param id 易损件主键
     * @return 结果
     */
    public int deleteJhcnWearingPartsById(Long id);

    /**
     * 批量删除易损件
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJhcnWearingPartsByIds(Long[] ids);

    JhcnWearingParts selectJhcnWearingPartsByNameAndType(@Param(value = "partName") String partName,@Param(value = "partType")  String partType);
}

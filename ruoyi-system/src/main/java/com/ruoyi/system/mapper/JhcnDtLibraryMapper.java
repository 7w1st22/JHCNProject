package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JhcnDtLibrary;

/**
 * 单体库Mapper接口
 *
 * @author ruoyi
 * @date 2025-08-25
 */
public interface JhcnDtLibraryMapper
{
    /**
     * 查询单体库
     *
     * @param id 单体库主键
     * @return 单体库
     */
    public JhcnDtLibrary selectJhcnDtLibraryById(Long id);

    /**
     * 查询单体库列表
     *
     * @param jhcnDtLibrary 单体库
     * @return 单体库集合
     */
    public List<JhcnDtLibrary> selectJhcnDtLibraryList(JhcnDtLibrary jhcnDtLibrary);

    /**
     * 新增单体库
     *
     * @param jhcnDtLibrary 单体库
     * @return 结果
     */
    public int insertJhcnDtLibrary(JhcnDtLibrary jhcnDtLibrary);

    /**
     * 修改单体库
     *
     * @param jhcnDtLibrary 单体库
     * @return 结果
     */
    public int updateJhcnDtLibrary(JhcnDtLibrary jhcnDtLibrary);

    /**
     * 删除单体库
     *
     * @param id 单体库主键
     * @return 结果
     */
    public int deleteJhcnDtLibraryById(Long id);

    /**
     * 批量删除单体库
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJhcnDtLibraryByIds(Long[] ids);

    public JhcnDtLibrary selectJhcnDtLibraryByDtCode(String dtCode);

    int resetJhcnDtLibraryBindedTimeById(Long dtId);
}

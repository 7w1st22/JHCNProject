package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnDtLibraryMapper;
import com.ruoyi.system.domain.JhcnDtLibrary;
import com.ruoyi.system.service.IJhcnDtLibraryService;

import javax.validation.Validator;

/**
 * 单体库Service业务层处理
 *
 * @author ruoyi
 * @date 2025-08-25
 */
@Service
public class JhcnDtLibraryServiceImpl implements IJhcnDtLibraryService
{
    @Autowired
    private JhcnDtLibraryMapper jhcnDtLibraryMapper;

    @Autowired
    private Validator validator;

    /**
     * 查询单体库
     *
     * @param id 单体库主键
     * @return 单体库
     */
    @Override
    public JhcnDtLibrary selectJhcnDtLibraryById(Long id)
    {
        return jhcnDtLibraryMapper.selectJhcnDtLibraryById(id);
    }

    @Override
    public JhcnDtLibrary selectJhcnDtLibraryByDtCode(String dtCode) {
        return jhcnDtLibraryMapper.selectJhcnDtLibraryByDtCode(dtCode);
    }



    /**
     * 查询单体库列表
     *
     * @param jhcnDtLibrary 单体库
     * @return 单体库
     */
    @Override
    public List<JhcnDtLibrary> selectJhcnDtLibraryList(JhcnDtLibrary jhcnDtLibrary)
    {
        return jhcnDtLibraryMapper.selectJhcnDtLibraryList(jhcnDtLibrary);
    }

    /**
     * 新增单体库
     *
     * @param jhcnDtLibrary 单体库
     * @return 结果
     */
    @Override
    public int insertJhcnDtLibrary(JhcnDtLibrary jhcnDtLibrary)
    {
        return jhcnDtLibraryMapper.insertJhcnDtLibrary(jhcnDtLibrary);
    }

    /**
     * 修改单体库
     *
     * @param jhcnDtLibrary 单体库
     * @return 结果
     */
    @Override
    public int updateJhcnDtLibrary(JhcnDtLibrary jhcnDtLibrary)
    {
        return jhcnDtLibraryMapper.updateJhcnDtLibrary(jhcnDtLibrary);
    }

    /**
     * 批量删除单体库
     *
     * @param ids 需要删除的单体库主键
     * @return 结果
     */
    @Override
    public int deleteJhcnDtLibraryByIds(Long[] ids)
    {
        for(Long id:ids){
            jhcnDtLibraryMapper.deleteJhcnDtLibraryById(id);
        }
        return jhcnDtLibraryMapper.deleteJhcnDtLibraryByIds(ids);
    }

    /**
     * 删除单体库信息
     *
     * @param id 单体库主键
     * @return 结果
     */
    @Override
    public int deleteJhcnDtLibraryById(Long id)
    {
        JhcnDtLibrary dt = jhcnDtLibraryMapper.selectJhcnDtLibraryById(id);
        // 单体已绑定无法删除
        if(dt.getBindedTime()!=null){
            throw new ServiceException("单体 "+dt.getDtCode()+" 已绑定无法删除");
        }
        return jhcnDtLibraryMapper.deleteJhcnDtLibraryById(id);
    }

    @Override
    public String importDT(List<JhcnDtLibrary> dtLibraryList, Boolean isUpdateSupport, String operName) {
        Date nowdate = new Date();
        if (StringUtils.isNull(dtLibraryList) || dtLibraryList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (JhcnDtLibrary dtLibrary : dtLibraryList)
        {
            try
            {
                // 验证是否存在这个用户
                JhcnDtLibrary dt = jhcnDtLibraryMapper.selectJhcnDtLibraryByDtCode(dtLibrary.getDtCode());
                // 不存在则新增
                if (StringUtils.isNull(dt))
                {
                    BeanValidators.validateWithException(validator, dtLibrary);
                    dtLibrary.setImportTime(nowdate);
                    jhcnDtLibraryMapper.insertJhcnDtLibrary(dtLibrary);
                    System.out.println("新增一条");
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + dtLibrary.getDtCode() + " 导入成功");
                }
                // 存在则判断是否更新
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, dtLibrary);
                    dtLibrary.setImportTime(null);
                    jhcnDtLibraryMapper.updateJhcnDtLibrary(dtLibrary);
                    System.out.println("更新一条");
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、单体 " + dtLibrary.getDtCode() + " 更新成功");
                }
                else
                // 都不符合则记录失败的单体编号
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、单体 " + dtLibrary.getDtCode() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + dtLibrary.getDtCode() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}

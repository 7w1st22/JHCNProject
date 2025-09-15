package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.system.domain.JhcnDtLibrary;
import com.ruoyi.system.domain.JhcnModule;
import com.ruoyi.system.mapper.JhcnDtLibraryMapper;
import com.ruoyi.system.mapper.JhcnModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnModuleBindMapper;
import com.ruoyi.system.domain.JhcnModuleBind;
import com.ruoyi.system.service.IJhcnModuleBindService;

/**
 * 绑定管理Service业务层处理
 *
 * @author ruoyi
 * @date 2025-08-26
 */
@Service
public class JhcnModuleBindServiceImpl implements IJhcnModuleBindService
{
    @Autowired
    private JhcnModuleBindMapper jhcnModuleBindMapper;

    @Autowired
    private JhcnModuleMapper jhcnModuleMapper;

    @Autowired
    private JhcnDtLibraryMapper jhcnDtLibraryMapper;

    /**
     * 查询绑定管理
     *
     * @param id 绑定管理主键
     * @return 绑定管理
     */
    @Override
    public JhcnModuleBind selectJhcnModuleBindById(Long id)
    {
        return jhcnModuleBindMapper.selectJhcnModuleBindById(id);
    }

    /**
     * 查询绑定管理列表
     *
     * @param jhcnModuleBind 绑定管理
     * @return 绑定管理
     */
    @Override
    public List<JhcnModuleBind> selectJhcnModuleBindList(JhcnModuleBind jhcnModuleBind)
    {
        return jhcnModuleBindMapper.selectJhcnModuleBindList(jhcnModuleBind);
    }

    /**
     * 新增绑定管理
     *
     * @param jhcnModuleBind 绑定管理
     * @return 结果
     */
    @Override
    public int insertJhcnModuleBind(JhcnModuleBind jhcnModuleBind)
    {
        Date bindTime = new Date();
        if(jhcnModuleBind.getBindTime()==null){
            jhcnModuleBind.setBindTime(bindTime);
        }
        // 模组条码更新绑定数量
        JhcnModule jhcnModule = jhcnModuleMapper.selectJhcnModuleById(jhcnModuleBind.getModuleId());
        if(jhcnModule.getBindedNum() >= jhcnModule.getNum()){
            throw new RuntimeException("绑定条码数量已满");
        }
        jhcnModule.setBindedNum(jhcnModule.getBindedNum()+1);
        jhcnModuleMapper.updateJhcnModule(jhcnModule);
        // 模组单体更新绑定时间
        JhcnDtLibrary jhcnDtLibrary = jhcnDtLibraryMapper.selectJhcnDtLibraryById(jhcnModuleBind.getDtId());
        jhcnDtLibrary.setBindedTime(bindTime);
        jhcnDtLibraryMapper.updateJhcnDtLibrary(jhcnDtLibrary);
        return jhcnModuleBindMapper.insertJhcnModuleBind(jhcnModuleBind);
    }

    /**
     * 修改绑定管理
     *
     * @param jhcnModuleBind 绑定管理
     * @return 结果
     */
    @Override
    public int updateJhcnModuleBind(JhcnModuleBind jhcnModuleBind)
    {
        return jhcnModuleBindMapper.updateJhcnModuleBind(jhcnModuleBind);
    }

    /**
     * 批量删除绑定管理
     *
     * @param ids 需要删除的绑定管理主键
     * @return 结果
     */
    @Override
    public int deleteJhcnModuleBindByIds(Long[] ids)
    {
        int r = 1;
        for(Long id:ids){
            r = r * deleteJhcnModuleBindById(id);
        }
        return r;
//        return jhcnModuleBindMapper.deleteJhcnModuleBindByIds(ids);
    }

//    @Override
//    public int deleteJhcnModuleBindByDtCode(String dtCode){
//        return jhcnModuleBindMapper.deleteJhcnModuleBindByDtCode(dtCode);
//    }

    /**
     * 删除绑定管理信息
     *
     * @param id 绑定管理主键
     * @return 结果
     */
    @Override
    public int deleteJhcnModuleBindById(Long id)
    {
        // 模组条码更新绑定数量
        JhcnModuleBind bind = jhcnModuleBindMapper.selectJhcnModuleBindById(id);
        JhcnModule jhcnModule = jhcnModuleMapper.selectJhcnModuleById(bind.getModuleId());
        jhcnModule.setBindedNum(jhcnModule.getBindedNum()-1);
        jhcnModuleMapper.updateJhcnModule(jhcnModule);
        // 单体库清空绑定时间
        jhcnDtLibraryMapper.resetJhcnDtLibraryBindedTimeById(bind.getDtId());
        return jhcnModuleBindMapper.deleteJhcnModuleBindById(id);
    }

}

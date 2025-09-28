package com.ruoyi.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.EmailUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnWearingPartsMapper;
import com.ruoyi.system.domain.JhcnWearingParts;
import com.ruoyi.system.service.IJhcnWearingPartsService;

import javax.mail.MessagingException;
import javax.validation.Validator;

/**
 * 易损件Service业务层处理
 *
 * @author russel
 * @date 2025-09-15
 */
@Service
public class JhcnWearingPartsServiceImpl implements IJhcnWearingPartsService
{
    @Autowired
    private JhcnWearingPartsMapper jhcnWearingPartsMapper;
    @Autowired
    private Validator validator;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询易损件
     *
     * @param id 易损件主键
     * @return 易损件
     */
    @Override
    public JhcnWearingParts selectJhcnWearingPartsById(Long id)
    {
        return jhcnWearingPartsMapper.selectJhcnWearingPartsById(id);
    }

    /**
     * 查询易损件列表
     *
     * @param jhcnWearingParts 易损件
     * @return 易损件
     */
    @Override
    public List<JhcnWearingParts> selectJhcnWearingPartsList(JhcnWearingParts jhcnWearingParts)
    {
        return jhcnWearingPartsMapper.selectJhcnWearingPartsList(jhcnWearingParts);
    }

    /**
     * 新增易损件
     *
     * @param jhcnWearingParts 易损件
     * @return 结果
     */
    @Override
    public int insertJhcnWearingParts(JhcnWearingParts jhcnWearingParts)
    {
        if( (jhcnWearingParts.getPartName()==null|| jhcnWearingParts.getPartName().isEmpty()) &&
                (jhcnWearingParts.getPartType()==null|| jhcnWearingParts.getPartType().isEmpty()))
        {
            throw new RuntimeException("品名与型号不能同时为空");
        }
        if(jhcnWearingParts.getSafetyNum()== null){
            throw new RuntimeException("请输入安全库存数量");
        }
        return jhcnWearingPartsMapper.insertJhcnWearingParts(jhcnWearingParts);
    }

    /**
     * 修改易损件
     *
     * @param jhcnWearingParts 易损件
     * @return 结果
     */
    @Override
    public int updateJhcnWearingParts(JhcnWearingParts jhcnWearingParts)
    {
        return jhcnWearingPartsMapper.updateJhcnWearingParts(jhcnWearingParts);
    }

    @Override
    public int changedNum(Long id,Long existedNum) {
        JhcnWearingParts part = jhcnWearingPartsMapper.selectJhcnWearingPartsById(id);
        // 黄线阈值
        Long safetyNum = part.getSafetyDistance()+part.getSafetyNum();
        // 红线阈值
        Long dangerNum = part.getSafetyNum();
        part.setNum(existedNum);
        int result = jhcnWearingPartsMapper.updateJhcnWearingParts(part);
        if(result>0){

            if(existedNum<=safetyNum && existedNum>dangerNum){
                sendAlarmEmail(part,"safety");
            }
            if(existedNum<=dangerNum){
                sendAlarmEmail(part,"danger");
            }
        }else{
            throw new RuntimeException("变更库存总量失败");
        }
        return result;
    }

    public void sendAlarmEmail(JhcnWearingParts part,String level ){
        try {
            List<SysUser> list = userService.selectSCBZEmailByRoleKey();
            for(SysUser user: list){
                if(user.getEmail()!=null){
                    if(level.equals("safety")){
                        Map<String,Object> obj =constructEmailObj(part,"黄线");
                        if(user.getEmail()!=null&&!user.getEmail().equals("")){
                            emailUtil.sendTemplateEmail("库存数量阈值告警", "partAlarmTemplate", obj, user.getEmail());
                        }
                    }
                    if(level.equals("danger")){
                        Map<String,Object> obj =constructEmailObj(part,"红线");
                        if(user.getEmail()!=null&&!user.getEmail().equals("")){
                            emailUtil.sendTemplateEmail("库存数量阈值告警", "partAlarmTemplate", obj, user.getEmail());
                        }
                    }
                }else{
                    throw new ServiceException("用户邮箱为空");
                }
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    // 构建邮件发送模版参数
    public  Map<String,Object> constructEmailObj(JhcnWearingParts part,String level){
        Map<String,Object> obj = new HashMap<>();
        obj.put("level",level);
        obj.put("partName",part.getPartName());
        obj.put("partType",part.getPartType());
        obj.put("pos",part.getPos());
        obj.put("num",part.getNum());//库存已有数量
        obj.put("dangerNum",part.getSafetyNum());// 红线
        obj.put("safetyDistance",part.getSafetyDistance()); // 缓冲距离
        obj.put("safetyNum",part.getSafetyDistance()+part.getSafetyNum()); // 黄线距离
        obj.put("serviceLife",part.getServiceLife()); // 使用寿命
        obj.put("leadTime",part.getLeadTime()); // 采购周期
        obj.put("img",part.getImg());
        obj.put("description",part.getDescription());
        obj.put("importance",part.getImportance());// 1 一般配件 2 关键配件
        return obj;
    }
    /**
     * 批量删除易损件
     *
     * @param ids 需要删除的易损件主键
     * @return 结果
     */
    @Override
    public int deleteJhcnWearingPartsByIds(Long[] ids)
    {
        return jhcnWearingPartsMapper.deleteJhcnWearingPartsByIds(ids);
    }

    /**
     * 删除易损件信息
     *
     * @param id 易损件主键
     * @return 结果
     */
    @Override
    public int deleteJhcnWearingPartsById(Long id)
    {
        return jhcnWearingPartsMapper.deleteJhcnWearingPartsById(id);
    }

    @Override
    public String importParts(List<JhcnWearingParts> partList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(partList) || partList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (JhcnWearingParts part : partList)
        {
            try
            {
                JhcnWearingParts model = jhcnWearingPartsMapper.selectJhcnWearingPartsByNameAndType( part.getPartName(), part.getPartType());
                // 不存在则新增
                if (StringUtils.isNull(model))
                {
                    BeanValidators.validateWithException(validator, part);
                    jhcnWearingPartsMapper.insertJhcnWearingParts(part);
                    System.out.println("新增一条");
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、 易损件" +part.getPartName()+"——"+part.getPartType() + " 导入成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、易损件 " + part.getPartName()+"——"+part.getPartType() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、易损件 " + part.getPartName()+"——"+part.getPartType()+ " 导入失败：";
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

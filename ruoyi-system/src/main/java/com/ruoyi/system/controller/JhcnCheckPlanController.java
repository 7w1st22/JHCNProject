package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.JhcnCheckPlan;
import com.ruoyi.system.service.IJhcnCheckPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检查计划Controller
 * 
 * @author russel
 * @date 2025-09-12
 */
@RestController
@RequestMapping("/system/plan")
public class JhcnCheckPlanController extends BaseController
{
    @Autowired
    private IJhcnCheckPlanService jhcnCheckPlanService;

    /**
     * 查询检查计划列表
     */
    @PreAuthorize("@ss.hasPermi('system:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnCheckPlan jhcnCheckPlan)
    {
        startPage();
        List<JhcnCheckPlan> list = jhcnCheckPlanService.selectJhcnCheckPlanList(jhcnCheckPlan);
        return getDataTable(list);
    }

    /**
     * 导出检查计划列表
     */
    @PreAuthorize("@ss.hasPermi('system:plan:export')")
    @Log(title = "检查计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnCheckPlan jhcnCheckPlan)
    {
        List<JhcnCheckPlan> list = jhcnCheckPlanService.selectJhcnCheckPlanList(jhcnCheckPlan);
        ExcelUtil<JhcnCheckPlan> util = new ExcelUtil<JhcnCheckPlan>(JhcnCheckPlan.class);
        util.exportExcel(response, list, "检查计划数据");
    }

    /**
     * 获取检查计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:plan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnCheckPlanService.selectJhcnCheckPlanById(id));
    }

    /**
     * 新增检查计划
     */
    @PreAuthorize("@ss.hasPermi('system:plan:add')")
    @Log(title = "检查计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnCheckPlan jhcnCheckPlan)
    {
        return toAjax(jhcnCheckPlanService.insertJhcnCheckPlan(jhcnCheckPlan));
    }

    /**
     * 修改检查计划
     */
    @PreAuthorize("@ss.hasPermi('system:plan:edit')")
    @Log(title = "检查计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnCheckPlan jhcnCheckPlan)
    {
        return toAjax(jhcnCheckPlanService.updateJhcnCheckPlan(jhcnCheckPlan));
    }

    /**
     * 删除检查计划
     */
    @PreAuthorize("@ss.hasPermi('system:plan:remove')")
    @Log(title = "检查计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnCheckPlanService.deleteJhcnCheckPlanByIds(ids));
    }
}

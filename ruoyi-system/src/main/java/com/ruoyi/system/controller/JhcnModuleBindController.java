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
import com.ruoyi.system.domain.JhcnModuleBind;
import com.ruoyi.system.service.IJhcnModuleBindService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绑定管理Controller
 *
 * @author ruoyi
 * @date 2025-08-26
 */
@RestController
@RequestMapping("/system/bind")
public class JhcnModuleBindController extends BaseController
{
    @Autowired
    private IJhcnModuleBindService jhcnModuleBindService;

    /**
     * 查询绑定管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:bind:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnModuleBind jhcnModuleBind)
    {
        startPage();
        List<JhcnModuleBind> list = jhcnModuleBindService.selectJhcnModuleBindList(jhcnModuleBind);
        return getDataTable(list);
    }

    /**
     * 导出绑定管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:bind:export')")
    @Log(title = "绑定管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnModuleBind jhcnModuleBind)
    {
        List<JhcnModuleBind> list = jhcnModuleBindService.selectJhcnModuleBindList(jhcnModuleBind);
        ExcelUtil<JhcnModuleBind> util = new ExcelUtil<JhcnModuleBind>(JhcnModuleBind.class);
        util.exportExcel(response, list, "绑定管理数据");
    }

    /**
     * 获取绑定管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:bind:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnModuleBindService.selectJhcnModuleBindById(id));
    }

    /**
     * 新增单个绑定管理
     */
    @PreAuthorize("@ss.hasPermi('system:bind:add')")
    @Log(title = "绑定管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnModuleBind jhcnModuleBind)
    {
        return toAjax(jhcnModuleBindService.insertJhcnModuleBind(jhcnModuleBind));
    }


    /**
     * 修改绑定管理
     */
    @PreAuthorize("@ss.hasPermi('system:bind:edit')")
    @Log(title = "绑定管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnModuleBind jhcnModuleBind)
    {
        return toAjax(jhcnModuleBindService.updateJhcnModuleBind(jhcnModuleBind));
    }

    /**
     * 删除绑定管理
     */
    @PreAuthorize("@ss.hasPermi('system:bind:remove')")
    @Log(title = "绑定管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnModuleBindService.deleteJhcnModuleBindByIds(ids));
    }

}

package com.ruoyi.system.controller;

import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.util.EmailUtil;
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
import com.ruoyi.system.domain.JhcnModule;
import com.ruoyi.system.service.IJhcnModuleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模组条码Controller
 *
 * @author ruoyi
 * @date 2025-08-26
 */
@RestController
@RequestMapping("/system/module")
public class JhcnModuleController extends BaseController
{
    @Autowired
    private IJhcnModuleService jhcnModuleService;

    @Autowired
    private EmailUtil emailUtil;

    /**
     * 查询模组条码列表
     */
    @PreAuthorize("@ss.hasPermi('system:module:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnModule jhcnModule)
    {
        startPage();
        List<JhcnModule> list = jhcnModuleService.selectJhcnModuleList(jhcnModule);
        return getDataTable(list);
    }

    /**
     * 导出模组条码列表
     */
    @PreAuthorize("@ss.hasPermi('system:module:export')")
    @Log(title = "模组条码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnModule jhcnModule)
    {
        List<JhcnModule> list = jhcnModuleService.selectJhcnModuleList(jhcnModule);
        ExcelUtil<JhcnModule> util = new ExcelUtil<JhcnModule>(JhcnModule.class);
        util.exportExcel(response, list, "模组条码数据");
    }

    /**
     * 获取模组条码详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:module:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnModuleService.selectJhcnModuleById(id));
    }

    /**
     * 新增模组条码
     */
    @PreAuthorize("@ss.hasPermi('system:module:add')")
    @Log(title = "模组条码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnModule jhcnModule)
    {
        return toAjax(jhcnModuleService.insertJhcnModule(jhcnModule));
    }

    /**
     * 修改模组条码
     */
    @PreAuthorize("@ss.hasPermi('system:module:edit')")
    @Log(title = "模组条码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnModule jhcnModule)
    {
        return toAjax(jhcnModuleService.updateJhcnModule(jhcnModule));
    }

    /**
     * 删除模组条码
     */
    @PreAuthorize("@ss.hasPermi('system:module:remove')")
    @Log(title = "模组条码", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnModuleService.deleteJhcnModuleByIds(ids));
    }



}

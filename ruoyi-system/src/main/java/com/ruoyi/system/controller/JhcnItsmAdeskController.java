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
import com.ruoyi.system.domain.JhcnItsmAdesk;
import com.ruoyi.system.service.IJhcnItsmAdeskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * IT工单Controller
 * 
 * @author Russel
 * @date 2025-10-17
 */
@RestController
@RequestMapping("/system/adesk")
public class JhcnItsmAdeskController extends BaseController
{
    @Autowired
    private IJhcnItsmAdeskService jhcnItsmAdeskService;

    /**
     * 查询IT工单列表
     */
    @PreAuthorize("@ss.hasPermi('system:adesk:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnItsmAdesk jhcnItsmAdesk)
    {
        startPage();
        List<JhcnItsmAdesk> list = jhcnItsmAdeskService.selectJhcnItsmAdeskList(jhcnItsmAdesk);
        return getDataTable(list);
    }

    /**
     * 导出IT工单列表
     */
    @PreAuthorize("@ss.hasPermi('system:adesk:export')")
    @Log(title = "IT工单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnItsmAdesk jhcnItsmAdesk)
    {
        List<JhcnItsmAdesk> list = jhcnItsmAdeskService.selectJhcnItsmAdeskList(jhcnItsmAdesk);
        ExcelUtil<JhcnItsmAdesk> util = new ExcelUtil<JhcnItsmAdesk>(JhcnItsmAdesk.class);
        util.exportExcel(response, list, "IT工单数据");
    }

    /**
     * 获取IT工单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:adesk:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnItsmAdeskService.selectJhcnItsmAdeskById(id));
    }

    /**
     * 新增IT工单
     */
    @PreAuthorize("@ss.hasPermi('system:adesk:add')")
    @Log(title = "IT工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnItsmAdesk jhcnItsmAdesk)
    {
        return toAjax(jhcnItsmAdeskService.insertJhcnItsmAdesk(jhcnItsmAdesk));
    }

    /**
     * 修改IT工单
     */
    @PreAuthorize("@ss.hasPermi('system:adesk:edit')")
    @Log(title = "IT工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnItsmAdesk jhcnItsmAdesk)
    {
        return toAjax(jhcnItsmAdeskService.updateJhcnItsmAdesk(jhcnItsmAdesk));
    }

    /**
     * 删除IT工单
     */
    @PreAuthorize("@ss.hasPermi('system:adesk:remove')")
    @Log(title = "IT工单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnItsmAdeskService.deleteJhcnItsmAdeskByIds(ids));
    }
}

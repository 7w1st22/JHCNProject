package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.JhcnDtLibrary;
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
import com.ruoyi.system.domain.JhcnWearingParts;
import com.ruoyi.system.service.IJhcnWearingPartsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 易损件Controller
 *
 * @author russel
 * @date 2025-09-15
 */
@RestController
@RequestMapping("/system/parts")
public class JhcnWearingPartsController extends BaseController
{
    @Autowired
    private IJhcnWearingPartsService jhcnWearingPartsService;

    /**
     * 查询易损件列表
     */
    @PreAuthorize("@ss.hasPermi('system:parts:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnWearingParts jhcnWearingParts)
    {
        startPage();
        List<JhcnWearingParts> list = jhcnWearingPartsService.selectJhcnWearingPartsList(jhcnWearingParts);
        return getDataTable(list);
    }

    /**
     * 导出易损件列表
     */
    @PreAuthorize("@ss.hasPermi('system:parts:export')")
    @Log(title = "易损件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnWearingParts jhcnWearingParts)
    {
        List<JhcnWearingParts> list = jhcnWearingPartsService.selectJhcnWearingPartsList(jhcnWearingParts);
        ExcelUtil<JhcnWearingParts> util = new ExcelUtil<JhcnWearingParts>(JhcnWearingParts.class);
        util.exportExcel(response, list, "易损件数据");
    }

    /**
     * 获取易损件详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:parts:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnWearingPartsService.selectJhcnWearingPartsById(id));
    }

    /**
     * 新增易损件
     */
    @PreAuthorize("@ss.hasPermi('system:parts:add')")
    @Log(title = "易损件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnWearingParts jhcnWearingParts)
    {
        return toAjax(jhcnWearingPartsService.insertJhcnWearingParts(jhcnWearingParts));
    }

    /**
     * 修改易损件
     */
    @PreAuthorize("@ss.hasPermi('system:parts:edit')")
    @Log(title = "易损件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnWearingParts jhcnWearingParts)
    {
        return toAjax(jhcnWearingPartsService.updateJhcnWearingParts(jhcnWearingParts));
    }

    @PreAuthorize("@ss.hasPermi('system:parts:change')")
    @Log(title = "易损件", businessType = BusinessType.UPDATE)
    @PutMapping("/changedNum")
    public AjaxResult changedNum(@RequestBody JhcnWearingParts jhcnWearingParts)
    {
        return toAjax(jhcnWearingPartsService.changedNum(jhcnWearingParts.getId(), jhcnWearingParts.getNum()));
    }

    /**
     * 删除易损件
     */
    @PreAuthorize("@ss.hasPermi('system:parts:remove')")
    @Log(title = "易损件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnWearingPartsService.deleteJhcnWearingPartsByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('system:parts:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<JhcnWearingParts> util = new ExcelUtil<JhcnWearingParts>(JhcnWearingParts.class);
        util.importTemplateExcel(response, "易损件数据");
    }

    @PreAuthorize("@ss.hasPermi('system:parts:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<JhcnWearingParts> util = new ExcelUtil<JhcnWearingParts>(JhcnWearingParts.class);
        List<JhcnWearingParts> partList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = jhcnWearingPartsService.importParts(partList, updateSupport, operName);
        return success(message);
    }
}

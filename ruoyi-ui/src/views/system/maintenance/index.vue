<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备编号" prop="deviceNo">
        <el-input
          v-model="queryParams.deviceNo"
          placeholder="请输入设备编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机台号" prop="jtNo">
        <el-input
          v-model="queryParams.jtNo"
          placeholder="请输入机台号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <!-- 修改日期范围选择器 -->
      <el-form-item label="维护日期" prop="maintanceDateRange">
        <el-date-picker
          type="daterange"
          v-model="queryParams.maintanceDateRange"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          :style="{width: '100%'}"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          range-separator="至"
          clearable>
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:maintenance:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:maintenance:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:maintenance:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:maintenance:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:parts:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="maintenanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备编号" align="center" prop="deviceNo" />
      <el-table-column label="机台号" align="center" prop="jtNo" />
      <el-table-column label="设备名称" align="center" prop="deviceName" />
      <el-table-column label="型号" align="center" prop="deviceType" />
      <el-table-column label="厂家" align="center" prop="company" />
      <el-table-column label="计划维护日期" align="center" prop="maintanceDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.maintanceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工序" align="center" prop="process" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:maintenance:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:maintenance:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改维护计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备编号" prop="deviceNo">
          <el-input v-model="form.deviceNo" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="机台号" prop="jtNo">
          <el-input v-model="form.jtNo" placeholder="请输入机台号" />
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="厂家" prop="company">
          <el-input v-model="form.company" placeholder="请输入厂家" />
        </el-form-item>
        <el-form-item label="维护日期" prop="maintanceDate">
          <el-date-picker clearable
            v-model="form.maintanceDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择计划维护日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工序" prop="process">
          <el-input v-model="form.process" placeholder="请输入工序" />
        </el-form-item>
<!--        <el-form-item label="定时提醒" prop="reminded" v-if="title==='添加维护计划'" required>-->
<!--          <el-switch v-model="form.reminded" active-color="#33FF00" inactive-color="#EE1616"-->
<!--                     :active-value='1' :inactive-value='0'></el-switch>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 导入对话框保持不变 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size: 12px; vertical-align: baseline" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip text-center" slot="tip">
          <span style="font-weight: bold;">! 导入计划默认开启提醒功能 !</span>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMaintenance, getMaintenance, delMaintenance, addMaintenance, updateMaintenance } from "@/api/system/maintenance";
import {getToken} from "@/utils/auth";

export default {
  name: "Maintenance",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 维护计划表格数据
      maintenanceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceNo: null,
        jtNo: null,
        deviceName: null,
        deviceType: null,
        maintanceDateRange: []
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
        maintanceDate: [
          { required: true, message: "计划维护日期不能为空", trigger: "blur" }
        ],
        deviceNo: [
          { required: true, message: "设备编号不能为空", trigger: "blur" }
        ]
      },
      // 导入参数
      upload: {
        // 是否显示弹出层（导入）
        open: false,
        // 弹出层标题（导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的易损件数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/maintenance/importData"
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询维护计划列表 */
    getList() {
      this.loading = true;

      listMaintenance(this.queryParams).then(response => {
        this.maintenanceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        deviceNo: null,
        jtNo: null,
        deviceName: null,
        deviceType: null,
        company: null,
        maintanceDate: null,
        process: null,
        reminded: 1
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      // 清空日期范围
      this.queryParams.maintanceDateRange = [];
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加维护计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMaintenance(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改维护计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMaintenance(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMaintenance(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除维护计划编号为"' + ids + '"的数据项？').then(function() {
        return delMaintenance(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/maintenance/export', this.queryParams, `维护计划_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "维护计划导入";
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },

    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },

    /** 下载模板操作 */
    importTemplate() {
      this.download('system/maintenance/importTemplate', {}, `维护计划模版_${new Date().getTime()}.xlsx`)
    },

    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>

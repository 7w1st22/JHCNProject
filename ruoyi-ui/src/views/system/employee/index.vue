<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-row>
        <el-form-item label="姓名" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入姓名"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="证件类型" prop="credentialType">
          <el-select v-model="queryParams.credentialType" placeholder="请选择证件类型" clearable>
            <el-option
              v-for="dict in dict.type.credential_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="证件号码" prop="credentialNo">
          <el-input
            v-model="queryParams.credentialNo"
            placeholder="请输入证件号码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="姓名拼音" prop="namePy">
          <el-input
            v-model="queryParams.namePy"
            placeholder="请输入姓名拼音"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="用工形式" prop="employeeType">
          <el-select v-model="queryParams.employeeType" placeholder="请选择用工形式" clearable>
            <el-option
              v-for="dict in dict.type.employee_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="合同签署公司" prop="companyName">
          <el-input
            v-model="queryParams.companyName"
            placeholder="请输入合同签署公司全称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="入职来源" prop="jobSource">
          <el-input
            v-model="queryParams.jobSource"
            placeholder="请输入入职来源"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="工作地点" prop="workPlace">
          <el-input
            v-model="queryParams.workPlace"
            placeholder="请输入工作地点"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="社保缴纳地" prop="socialSecurityPaymentLocation">
          <el-input
            v-model="queryParams.socialSecurityPaymentLocation"
            placeholder="请输入社保缴纳地"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable>
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-input
            v-model="queryParams.birthday"
            placeholder="请输入出生日期"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="民族" prop="ethn">
          <el-select v-model="queryParams.ethn" placeholder="请选择民族" clearable>
            <el-option
              v-for="dict in dict.type.ethn"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="婚姻状况" prop="marriageStatus">
          <el-select v-model="queryParams.marriageStatus" placeholder="请选择婚姻状况" clearable>
            <el-option
              v-for="dict in dict.type.marriage_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="国籍" prop="nation">
          <el-select v-model="queryParams.nation" placeholder="请选择国籍" clearable>
            <el-option
              v-for="dict in dict.type.nation"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="户口性质" prop="registeredResidenceNature">
          <el-select v-model="queryParams.registeredResidenceNature" placeholder="请选择户口性质" clearable>
            <el-option
              v-for="dict in dict.type.registered_residence_nature"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="户口所在地" prop="permanentResidence">
          <el-input
            v-model="queryParams.permanentResidence"
            placeholder="请输入户口所在地"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="户口详细地址" prop="permanentResidenceAddress">
          <el-input
            v-model="queryParams.permanentResidenceAddress"
            placeholder="请输入户口所在地详细地址"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="籍贯" prop="nativePlace">
          <el-input
            v-model="queryParams.nativePlace"
            placeholder="请输入籍贯"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="出生地" prop="birthPlace">
          <el-input
            v-model="queryParams.birthPlace"
            placeholder="请输入出生地"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="家庭地址" prop="homeAddress">
          <el-input
            v-model="queryParams.homeAddress"
            placeholder="请输入家庭地址"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="家庭地址信息" prop="homeAddressInfo">
          <el-input
            v-model="queryParams.homeAddressInfo"
            placeholder="请输入家庭地址详细信息"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="工作时间" prop="workStart">
          <el-input
            v-model="queryParams.workStart"
            placeholder="请输入工作时间"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="入职时间" prop="jobStart">
          <el-input
            v-model="queryParams.jobStart"
            placeholder="请输入入职时间"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="个人邮箱" prop="email">
          <el-input
            v-model="queryParams.email"
            placeholder="请输入个人邮箱"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="常用手机" prop="phone">
          <el-input
            v-model="queryParams.phone"
            placeholder="请输入常用手机"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-row>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:employee:add']"
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
          v-hasPermi="['system:employee:edit']"
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
          v-hasPermi="['system:employee:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:employee:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="employeeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="证件类型" align="center" prop="credentialType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.credential_type" :value="scope.row.credentialType"/>
        </template>
      </el-table-column>
      <el-table-column label="证件号码" align="center" prop="credentialNo" />
      <el-table-column label="用工形式" align="center" prop="employeeType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.employee_type" :value="scope.row.employeeType"/>
        </template>
      </el-table-column>
      <el-table-column label="合同签署公司全称" align="center" prop="companyName" />
      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="出生日期" align="center" prop="birthday" />
      <el-table-column label="民族" align="center" prop="ethn">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ethn" :value="scope.row.ethn"/>
        </template>
      </el-table-column>
      <el-table-column label="婚姻状况" align="center" prop="marriageStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.marriage_status" :value="scope.row.marriageStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:employee:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:employee:remove']"
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

    <!-- 添加或修改employeeInfo对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input style="width: 100%" v-model="form.name" placeholder="请输入姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="姓名拼音" prop="namePy">
            <el-input v-model="form.namePy" placeholder="请输入姓名拼音" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="证件类型" prop="credentialType">
            <el-select v-model="form.credentialType" placeholder="请选择证件类型">
              <el-option
                v-for="dict in dict.type.credential_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="证件号码" prop="credentialNo">
            <el-input v-model="form.credentialNo" placeholder="请输入证件号码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用工形式" prop="employeeType">
            <el-select v-model="form.employeeType" placeholder="请选择用工形式">
              <el-option
                v-for="dict in dict.type.employee_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="合同签署公司" prop="companyName">
            <el-input v-model="form.companyName" placeholder="请输入合同签署公司全称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入职来源" prop="jobSource">
            <el-input v-model="form.jobSource" placeholder="请输入入职来源" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工作地点" prop="workPlace">
            <el-input v-model="form.workPlace" placeholder="请输入工作地点" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="社保缴纳地" prop="socialSecurityPaymentLocation">
            <el-input v-model="form.socialSecurityPaymentLocation" placeholder="请输入社保缴纳地" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="性别" prop="sex">
            <el-select v-model="form.sex" placeholder="请选择性别">
              <el-option
                v-for="dict in dict.type.sys_user_sex"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="出生日期" prop="birthday">
            <el-input v-model="form.birthday" placeholder="请输入出生日期" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="民族" prop="ethn">
            <el-select v-model="form.ethn" placeholder="请选择民族">
              <el-option
                v-for="dict in dict.type.ethn"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="国籍" prop="nation">
            <el-select v-model="form.nation" placeholder="请选择国籍">
              <el-option
                v-for="dict in dict.type.nation"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="婚姻状况" prop="marriageStatus">
            <el-radio-group v-model="form.marriageStatus">
              <el-radio
                v-for="dict in dict.type.marriage_status"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="户口性质" prop="registeredResidenceNature">
            <el-radio-group v-model="form.registeredResidenceNature">
              <el-radio
                v-for="dict in dict.type.registered_residence_nature"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="户口所在地" prop="permanentResidence">
            <el-input v-model="form.permanentResidence" placeholder="请输入户口所在地" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="籍贯" prop="nativePlace">
            <el-input v-model="form.nativePlace" placeholder="请输入籍贯" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="详细地址" prop="permanentResidenceAddress">
            <el-input v-model="form.permanentResidenceAddress" placeholder="请输入户口所在地详细地址" />
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="出生地" prop="birthPlace">
            <el-input v-model="form.birthPlace" placeholder="请输入出生地" />
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="家庭地址" prop="homeAddress">
            <el-input v-model="form.homeAddress" placeholder="请输入家庭地址" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="家庭地址详细信息" prop="homeAddressInfo">
            <el-input v-model="form.homeAddressInfo" placeholder="请输入家庭地址详细信息" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工作时间" prop="workStart">
            <el-input v-model="form.workStart" placeholder="请输入工作时间" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入职时间" prop="jobStart">
            <el-input v-model="form.jobStart" placeholder="请输入入职时间" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="个人邮箱" prop="email">
            <el-input v-model="form.email" placeholder="请输入个人邮箱" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="常用手机" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入常用手机" />
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size: 12px; vertical-align: baseline" @click="importTemplate">下载模板</el-link>
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
import { listEmployee, getEmployee, delEmployee, addEmployee, updateEmployee } from "@/api/system/employee";
import { getToken } from "@/utils/auth";

export default {
  name: "Employee",
  dicts: ['credential_type', 'ethn', 'sys_user_sex', 'registered_residence_nature', 'employee_type', 'nation', 'marriage_status'],
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
      // employeeInfo表格数据
      employeeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        credentialType: null,
        credentialNo: null,
        namePy: null,
        employeeType: null,
        companyName: null,
        jobSource: null,
        workPlace: null,
        socialSecurityPaymentLocation: null,
        sex: null,
        birthday: null,
        ethn: null,
        marriageStatus: null,
        nation: null,
        registeredResidenceNature: null,
        permanentResidence: null,
        permanentResidenceAddress: null,
        nativePlace: null,
        birthPlace: null,
        homeAddress: null,
        homeAddressInfo: null,
        workStart: null,
        jobStart: null,
        email: null,
        phone: null
      },
      // 表单参数
      form: {},
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "导入",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/employee/importData"
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询employeeInfo列表 */
    getList() {
      this.loading = true;
      listEmployee(this.queryParams).then(response => {
        this.employeeList = response.rows;
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
        name: null,
        credentialType: null,
        credentialNo: null,
        namePy: null,
        employeeType: null,
        companyName: null,
        jobSource: null,
        workPlace: null,
        socialSecurityPaymentLocation: null,
        sex: null,
        birthday: null,
        ethn: null,
        marriageStatus: null,
        nation: null,
        registeredResidenceNature: null,
        permanentResidence: null,
        permanentResidenceAddress: null,
        nativePlace: null,
        birthPlace: null,
        homeAddress: null,
        homeAddressInfo: null,
        workStart: null,
        jobStart: null,
        email: null,
        phone: null
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
      this.title = "添加employeeInfo";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEmployee(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改employeeInfo";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEmployee(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEmployee(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除employeeInfo编号为"' + ids + '"的数据项？').then(function() {
        return delEmployee(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/employee/export', {
        ...this.queryParams
      }, `employee_${new Date().getTime()}.xlsx`)
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
    importTemplate() {
      this.download('system/employee/importTemplate', {
      }, `employee_template_${new Date().getTime()}.xlsx`)
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    handleImport() {
      this.upload.title = "员工导入";
      this.upload.open = true;
    }
  }
};
</script>

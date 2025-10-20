<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="85px">
      <el-form-item label="工单" prop="itsmName">
        <el-input
          v-model="queryParams.itsmName"
          placeholder="请输入工单名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="填写人" prop="createUsername">
        <el-input
          v-model="queryParams.createUsername"
          placeholder="请输入填写人姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请人" prop="applyUsername">
        <el-input
          v-model="queryParams.applyUsername"
          placeholder="请输入申请人姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请人工号" prop="applyNo">
        <el-input
          v-model="queryParams.applyNo"
          placeholder="请输入申请人工号"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['system:adesk:add']"
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
          v-hasPermi="['system:adesk:edit']"
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
          v-hasPermi="['system:adesk:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:adesk:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="adeskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工单名" align="center" prop="itsmName" :show-overflow-tooltip="true"/>
      <el-table-column label="填写人姓名" align="center" prop="createUsername" />
      <el-table-column label="申请人姓名" align="center" prop="applyUsername" />
      <el-table-column label="申请人工号" align="center" prop="applyNo" />
      <el-table-column label="申请时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:adesk:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:adesk:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:adesk:remove']"
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
  </div>
</template>

<script>
import { listAdesk, delAdesk } from "@/api/system/adesk";

export default {
  name: "Adesk",
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
      // IT工单表格数据
      adeskList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itsmName: null,
        createUsername: null,
        applyUsername: null,
        applyNo: null,
        applyDeptId: null,
        demandAdesk: null,
        demandAdeskAnyshare: null,
        demandAdeskIpguard: null,
        demandOa: null,
        demandOaPhone: null,
        demandMail: null,
        demandMailJob: null,
        demandMailPre: null,
        demandPrinter: null,
        demandPrinterLarge: null,
        demandPrinterSmall: null,
        demandPrinterMz: null,
        demandPrinterOther: null,
        demandSap: null,
        demandSapNumber: null,
        demandSapNumberInfo: null,
        demandWechatUpdate: null,
        demandWpsSet: null,
        demandOther: null
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询IT工单列表 */
    getList() {
      this.loading = true;
      listAdesk(this.queryParams).then(response => {
        this.adeskList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({ path: '/system/adesk/add' });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids[0];
      this.$router.push({ path: '/system/adesk/edit/' + id });
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      const id = row.id || this.ids[0];
      this.$router.push({ path: '/system/adesk/detail/' + id });
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除IT工单编号为"' + ids + '"的数据项？').then(function() {
        return delAdesk(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/adesk/export', {
        ...this.queryParams
      }, `adesk_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped>
.link-type {
  color: #1890ff;
  cursor: pointer;
}
.link-type:hover {
  color: #40a9ff;
  text-decoration: underline;
}
</style>

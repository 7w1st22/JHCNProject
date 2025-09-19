<template>
  <div class="app-container">
    <!-- 搜索和操作区域保持不变 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="品名" prop="partName">
        <el-input
            v-model="queryParams.partName"
            placeholder="请输入品名"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="位置" prop="pos">
        <el-input
            v-model="queryParams.pos"
            placeholder="请输入位置"
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
            v-hasPermi="['system:parts:add']"
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
            v-hasPermi="['system:parts:edit']"
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
            v-hasPermi="['system:parts:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:parts:export']"
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
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 表格保持不变 -->
    <el-table v-loading="loading" :data="partsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="品名" align="center" prop="partName" />
      <el-table-column label="型号" align="center" prop="partType" />
      <el-table-column label="位置" align="center" prop="pos" />
      <el-table-column label="安全库存" align="center" prop="safetyNum" />
      <el-table-column label="缓冲距离" align="center" prop="safetyDistance" />
      <el-table-column label="库存数量" align="center" prop="num" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleDetail(scope.row)"
          >详情</el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:parts:edit']"
          >修改</el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="changeNum(scope.row)"
              v-hasPermi="['system:parts:change']"
          >库存变更</el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:parts:remove']"
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

    <!-- 添加或修改对话框保持不变 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="品名" prop="partName">
          <el-input v-model="form.partName" placeholder="请输入品名" />
        </el-form-item>
        <el-form-item label="位置" prop="pos">
          <el-input v-model="form.pos" placeholder="请输入位置" />
        </el-form-item>
        <el-form-item label="类型" prop="importance">
          <el-radio-group v-model="form.importance" size="medium">
            <el-radio v-for="(item, index) in field101Options" :key="index" :label="item.value"
                      :disabled="item.disabled">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="安全库存" prop="safetyNum">
          <el-input v-model="form.safetyNum" placeholder="请输入安全库存" />
        </el-form-item>
        <el-form-item label="缓冲距离" prop="safetyDistance">
          <el-input v-model="form.safetyDistance" placeholder="请输入缓冲距离" />
        </el-form-item>
        <el-form-item label="库存数量" prop="num">
          <el-input v-model="form.num" placeholder="请输入库存数量" />
        </el-form-item>
        <el-form-item label="使用寿命" prop="serviceLife">
          <el-input v-model="form.serviceLife" placeholder="请输入使用寿命" />
        </el-form-item>
        <el-form-item label="采购周期" prop="leadTime">
          <el-input v-model="form.leadTime" placeholder="请输入采购周期" />
        </el-form-item>
        <el-form-item label="图片链接" prop="img">
          <el-input v-model="form.img" placeholder="请输入图片链接" />
        </el-form-item>
        <el-form-item label="备注" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="openChangeNum" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="库存数量" prop="num">
          <el-input v-model="form.num" placeholder="请输入库存数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitNum">确 定</el-button>
        <el-button @click="cancelChangeNum">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 - 主要修改部分 -->
    <el-dialog :title="'易损件详情 - ' + detailForm.partName" :visible.sync="detailVisible" width="800px" append-to-body>
      <!-- 使用SVG作为背景 -->
      <div class="detail-content-container" :class="{'important-background': detailForm.importance === '2'}">
        <div class="detail-container">
          <!-- 左侧属性区域 -->
          <div class="detail-properties">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="品名">{{ detailForm.partName || '无' }}</el-descriptions-item>
              <el-descriptions-item label="型号">{{ detailForm.partType || '无' }}</el-descriptions-item>
              <el-descriptions-item label="位置">{{ detailForm.pos || '无' }}</el-descriptions-item>
              <el-descriptions-item label="安全库存">{{ detailForm.safetyNum || '无' }}</el-descriptions-item>
              <el-descriptions-item label="缓冲距离">{{ detailForm.safetyDistance || '无' }}</el-descriptions-item>
              <el-descriptions-item label="库存数量">{{ detailForm.num || '无' }}</el-descriptions-item>
              <el-descriptions-item label="使用寿命">{{ detailForm.serviceLife || '无' }}</el-descriptions-item>
              <el-descriptions-item label="采购周期">{{ detailForm.leadTime || '无' }}</el-descriptions-item>
              <el-descriptions-item label="重要性级别">
                <el-tag :type="detailForm.importance === '2' ? 'danger' : 'success'">
                  {{ detailForm.importance === '2' ? '重要备件' : '一般配件' }}
                </el-tag>
              </el-descriptions-item>
            </el-descriptions>
          </div>

<!--           右侧图片区域-->
          <div class="detail-image-section">
            <div v-if="detailForm.img">
              <img :src="detailForm.img" class="detail-image" @click="handlePreview(detailForm.img)" />
              <div style="margin-top: 10px;">
                <el-button type="text" @click="handlePreview(detailForm.img)">查看大图</el-button>
              </div>
            </div>
            <div v-else class="image-placeholder">暂无图片</div>
          </div>
        </div>

        <!-- 文本内容区域 -->
        <div class="text-content-section">
          <div class="text-content">
            <div style="font-weight: bold; margin-bottom: 8px;">备注:</div>
            <div>{{ detailForm.description || '无' }}</div>
          </div>
        </div>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 图片预览对话框保持不变 -->
    <el-dialog :visible.sync="previewVisible" title="图片预览" width="60%" top="5vh">
      <img :src="previewImage" style="width: 100%;" />
    </el-dialog>
  </div>
</template>

<script>
import { listParts, getParts, delParts, addParts, updateParts,changeNum } from "@/api/system/parts";
import { getToken } from "@/utils/auth";

export default {
  name: "Parts",
  data() {
    return {
      field101Options: [{
        "label": "一般配件",
        "value": '1'
      }, {
        "label": "重要备件",
        "value": '2'
      }],
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
      // 易损件表格数据
      partsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openChangeNum:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        partName: null,
        partType: null,
        pos: null,
      },
      // 表单参数
      form: {},
      // 详情对话框显示状态
      detailVisible: false,
      // 详情表单数据
      detailForm: {},
      // 图片预览对话框显示状态
      previewVisible: false,
      // 预览的图片URL
      previewImage: '',
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
        url: process.env.VUE_APP_BASE_API + "/system/parts/importData"
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
    /** 查询易损件列表 */
    getList() {
      this.loading = true;
      listParts(this.queryParams).then(response => {
        this.partsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancelChangeNum(){
      this.openChangeNum = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        partName: null,
        partType: null,
        pos: null,
        safetyNum: null,
        safetyDistance: null,
        num: null,
        serviceLife: null,
        leadTime: null,
        img: null,
        importance: null,
        description: null
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
      this.title = "添加易损件";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getParts(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改易损件";
      });
    },
    changeNum(row){
      this.reset();
      const id = row.id || this.ids
      getParts(id).then(response => {
        this.form = response.data;
        this.openChangeNum = true;
        this.title = row.partName+row.partType;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateParts(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addParts(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    submitNum(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            changeNum(this.form.id,this.form.num).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openChangeNum = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除易损件编号为"' + ids + '"的数据项？').then(function() {
        return delParts(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.detailForm = Object.assign({}, row);
      this.detailVisible = true;
    },

    /** 图片预览 */
    handlePreview(imageUrl) {
      this.previewImage = imageUrl;
      this.previewVisible = true;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/parts/export', {
        ...this.queryParams
      }, `parts_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "易损件导入";
      this.upload.open = true;
    },

    /** 下载模板操作 */
    importTemplate() {
      this.download('system/parts/importTemplate', {}, `parts_template_${new Date().getTime()}.xlsx`)
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

    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
<style scoped>
.detail-image {
  max-width: 100%;
  max-height: 200px;
  display: block;
  margin: 0 auto 10px;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  cursor: pointer;
}
.image-placeholder {
  padding: 30px 20px;
  background-color: #f5f7fa;
  border-radius: 4px;
  color: #909399;
  text-align: center;
}

/* 详情容器 */
.detail-container {
  display: flex;
  position: relative;
  z-index: 2; /* 确保内容在背景之上 */
}
.detail-properties {
  flex: 3;
  padding-right: 20px;
}
.detail-image-section {
  flex: 1;
  text-align: center;
  padding: 15px;
  border-left: 1px solid #e8e8e8;
}
.text-content-section {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #e8e8e8;
}
.text-content {
  background: #f5f7fa;
  padding: 12px;
  border-radius: 4px;
  margin-bottom: 15px;
  line-height: 1.6;
  white-space: pre-wrap;
}

/* 重要配件背景效果 - 使用伪元素确保在最上层 */
.detail-content-container.important-background {
  position: relative;
  min-height: 500px;
}

.detail-content-container.important-background::before {
  content: "";
  position: absolute;
  top: 0;
  left: 30px;
  width: 100px;
  height: 100px;
  background-image: url("../../../../public/importance.svg");
  background-repeat: no-repeat;
  background-size: contain;
  z-index: 10; /* 提高z-index确保在内容之上 */
  opacity: 0.7;
  pointer-events: none; /* 确保不会阻挡交互 */
}

/* 确保内容区域在背景之上 */
.detail-content-container {
  position: relative;
}

.detail-container {
  display: flex;
  position: relative;
  z-index: 1; /* 正常层级 */
}
</style>

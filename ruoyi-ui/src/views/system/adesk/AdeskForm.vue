<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="200px">
      <el-card>
        <div slot="header" class="clearfix">
          <span>{{ title }}</span>
          <el-button style="float: right; margin-left: 10px;" @click="cancel" :disabled="isDetail">取消</el-button>
          <el-button type="primary" style="float: right;" @click="submitForm" :disabled="isDetail">提交</el-button>
        </div>

        <!-- 基本信息 -->
        <el-divider content-position="left">基本信息</el-divider>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="工单名" prop="itsmName">
              <el-input v-model="form.itsmName" placeholder="自动生成" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="填写人姓名" prop="createUsername">
              <el-input v-model="form.createUsername" placeholder="请输入填写人姓名" :disabled="isDetail"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="申请人姓名" prop="applyUsername">
              <el-input v-model="form.applyUsername" placeholder="请输入申请人姓名" :disabled="isDetail"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请人工号" prop="applyNo">
              <el-input v-model="form.applyNo" placeholder="请输入申请人工号" :disabled="isDetail"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="9">
            <el-form-item label="申请人部门" prop="applyDeptId">
              <treeselect v-model="form.applyDeptId" :options="deptOptions" :show-count="true" placeholder="请选择申请人归属部门" :disabled="isDetail"/>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 云桌面相关 -->
        <el-divider content-position="left">云桌面需求</el-divider>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="云桌面" prop="demandAdesk">
              <el-checkbox v-model="form.demandAdeskChecked" @change="handleAdeskChange" :disabled="isDetail">需要云桌面</el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" v-if="form.demandAdeskChecked">
          <el-col :span="6">
            <el-form-item label="爱数账号" prop="demandAdeskAnyshare">
              <el-checkbox v-model="form.demandAdeskAnyshareChecked" :disabled="isDetail">需要爱数账号</el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="加密系统" prop="demandAdeskIpguard">
              <el-checkbox v-model="form.demandAdeskIpguardChecked" :disabled="isDetail">需要加密系统</el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- OA账号申请 -->
        <el-divider content-position="left">OA账号申请</el-divider>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="OA账号申请" prop="demandOa">
              <el-checkbox v-model="form.demandOaChecked" @change="handleOaChange" :disabled="isDetail">需要OA账号</el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" v-if="form.demandOaChecked">
          <el-col :span="12">
            <el-form-item label="11位手机号" prop="demandOaPhone">
              <el-input
                v-model="form.demandOaPhone"
                placeholder="请输入11位手机号"
                maxlength="11"
                show-word-limit
                :disabled="isDetail"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 邮箱申请 -->
        <el-divider content-position="left">邮箱申请</el-divider>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="邮箱申请" prop="demandMail">
              <el-checkbox v-model="form.demandMailChecked" @change="handleMailChange" :disabled="isDetail">需要邮箱申请</el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" v-if="form.demandMailChecked">
          <el-col :span="12">
            <el-form-item label="岗位" prop="demandMailJob">
              <el-input v-model="form.demandMailJob" placeholder="请输入岗位" :disabled="isDetail"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拟设邮箱" prop="demandMailPre">
              <el-input
                v-model="form.demandMailPre"
                placeholder="请输入拟设邮箱"
                :formatter="formatEmail"
                :disabled="isDetail"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 打印机驱动安装 -->
        <el-divider content-position="left">打印机驱动安装</el-divider>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="打印机安装" prop="demandPrinter">
              <el-checkbox v-model="form.demandPrinterChecked" @change="handlePrinterChange" :disabled="isDetail">需要打印机安装</el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" v-if="form.demandPrinterChecked" >
          <el-col :span="12">
            <el-form-item label="技术大办公室京瓷打印机" prop="demandPrinterLarge">
              <el-checkbox v-model="form.demandPrinterLargeChecked" :disabled="isDetail">需要安装</el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="人事小办公室京瓷打印机" prop="demandPrinterSmall">
              <el-checkbox v-model="form.demandPrinterSmallChecked" :disabled="isDetail">需要安装</el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="模组彩色打印机" prop="demandPrinterMz">
              <el-checkbox v-model="form.demandPrinterMzChecked" :disabled="isDetail">需要安装</el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="其他打印机" prop="demandPrinterOther">
              <el-checkbox v-model="form.demandPrinterOtherChecked" @change="handlePrinterOtherChange" :disabled="isDetail">其他打印机</el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="form.demandPrinterOtherChecked">
            <el-form-item label="其他打印机描述" prop="demandPrinterOtherInfo">
              <el-input
                v-model="form.demandPrinterOtherInfo"
                placeholder="请输入其他打印机描述"
                type="textarea"
                :rows="2"
                :disabled="isDetail"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- SAP安装 -->
        <el-divider content-position="left">SAP安装</el-divider>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="SAP安装" prop="demandSap">
              <el-checkbox v-model="form.demandSapChecked" @change="handleSapChange" :disabled="isDetail">需要SAP安装</el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" >
          <el-col :span="12">
            <el-form-item label="SAP账号新开" prop="demandSapNumber">
              <el-checkbox v-model="form.demandSapNumberChecked" :disabled="isDetail">需要SAP账号</el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系IT沟通新账号权限" prop="demandSapNumberInfo">
              <el-input
                v-model="form.demandSapNumberInfo"
                type="textarea"
                :rows="4"
                placeholder="请详细描述您的新账号权限需求"
                show-word-limit
                maxlength="500"
                :disabled="isDetail"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 其他软件需求 -->
        <el-divider content-position="left">其他软件需求</el-divider>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="微信更新" prop="demandWechatUpdate">
              <el-checkbox v-model="form.demandWechatUpdateChecked" :disabled="isDetail">需要微信更新</el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="WPS文件格式关联" prop="demandWpsSet">
              <el-checkbox v-model="form.demandWpsSetChecked" :disabled="isDetail">需要WPS文件格式关联</el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 其他需求 -->
        <el-divider content-position="left">其他需求</el-divider>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="其他需求" prop="demandOther">
              <el-input
                v-model="form.demandOther"
                type="textarea"
                :rows="4"
                placeholder="请输入其他需求"
                show-word-limit
                maxlength="500"
                :disabled="isDetail"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>
    </el-form>
  </div>
</template>

<script>
import { getAdesk, addAdesk, updateAdesk } from "@/api/system/adesk";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {deptTreeSelect} from "@/api/system/user";

export default {
  name: "AdeskForm",
  components: {Treeselect},
  data() {
    // 手机号验证规则
    const validatePhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('手机号不能为空'));
      }
      const phoneReg = /^1[3-9]\d{9}$/;
      if (!phoneReg.test(value)) {
        callback(new Error('请输入正确的11位手机号'));
      } else {
        callback();
      }
    };

    // 邮箱验证规则
    const validateEmail = (rule, value, callback) => {
      if (value && !value.endsWith('@jianghai.com')) {
        callback(new Error('邮箱必须以@jianghai.com结尾'));
      } else {
        callback();
      }
    };

    return {
      title: "",
      // 所有部门树选项
      deptOptions: undefined,
      isDetail: false, // 新增：详情模式标识
      form: {
        id: null,
        itsmName: null,
        createUsername: null,
        createTime: null,
        applyUsername: null,
        applyNo: null,
        applyDeptId: null,

        // 云桌面相关
        demandAdeskChecked: false,
        demandAdeskAnyshareChecked: false,
        demandAdeskIpguardChecked: false,

        // OA账号相关
        demandOaChecked: false,
        demandOaPhone: null,

        // 邮箱相关
        demandMailChecked: false,
        demandMailJob: null,
        demandMailPre: null,

        // 打印机相关
        demandPrinterChecked: false,
        demandPrinterLargeChecked: false,
        demandPrinterSmallChecked: false,
        demandPrinterMzChecked: false,
        demandPrinterOtherChecked: false,
        demandPrinterOtherInfo: null,

        // SAP相关
        demandSapChecked: false,
        demandSapNumberChecked: false,
        demandSapNumberInfoChecked: false,

        // 其他软件
        demandWechatUpdateChecked: false,
        demandWpsSetChecked: false,

        // 其他需求
        demandOther: null
      },
      rules: {
        createUsername: [
          { required: true, message: "填写人姓名不能为空", trigger: "blur" }
        ],
        applyUsername: [
          { required: true, message: "申请人姓名不能为空", trigger: "blur" }
        ],
        applyNo: [
          { required: true, message: "申请人工号不能为空", trigger: "blur" }
        ],
        applyDeptId: [
          { required: true, message: "申请人部门不能为空", trigger: "blur" }
        ],
        demandOaPhone: [
          { required: true, validator: validatePhone, trigger: 'blur' }
        ],
        demandMailJob: [
          { required: true, message: "岗位不能为空", trigger: "blur" }
        ],
        demandMailPre: [
          { validator: validateEmail, trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.getDeptTree();
    // 根据路由名称判断是新增、修改还是详情
    const routeName = this.$route.name;
    const id = this.$route.params.id;

    if (routeName === 'AdeskDetail') {
      this.title = "IT工单详情";
      this.isDetail = true;
      this.getData(id);
    } else if (id) {
      this.title = "修改IT工单";
      this.getData(id);
    } else {
      this.title = "新增IT工单";
      // 新增时自动生成工单名
      this.form.itsmName = `IT工单_${new Date().getTime()}`;
    }
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 获取详情 */
    getData(id) {
      getAdesk(id).then(response => {
        const data = response.data;
        // 将后端返回的字符类型转换为布尔值
        this.form = {
          ...data,
          demandAdeskChecked: data.demandAdesk === '1',
          demandAdeskAnyshareChecked: data.demandAdeskAnyshare === '1',
          demandAdeskIpguardChecked: data.demandAdeskIpguard === '1',
          demandOaChecked: data.demandOa === '1',
          demandMailChecked: data.demandMail === '1',
          demandPrinterChecked: data.demandPrinter === '1',
          demandPrinterLargeChecked: data.demandPrinterLarge === '1',
          demandPrinterSmallChecked: data.demandPrinterSmall === '1',
          demandPrinterMzChecked: data.demandPrinterMz === '1',
          demandPrinterOtherChecked: data.demandPrinterOther === '1',
          // demandPrinterOtherInfo 直接使用字符串值，不需要转换
          demandSapChecked: data.demandSap === '1',
          demandSapNumberChecked: data.demandSapNumber === '1',
          // demandSapNumberInfo 直接使用字符串值，不需要转换
          demandWechatUpdateChecked: data.demandWechatUpdate === '1',
          demandWpsSetChecked: data.demandWpsSet === '1'
        };
      });
    },
    /** 取消按钮 */
    cancel() {
      this.$router.back();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 准备提交的数据，将布尔值转换为字符类型
          const submitData = {
            ...this.form,
            demandAdesk: this.form.demandAdeskChecked ? '1' : '0',
            demandAdeskAnyshare: this.form.demandAdeskAnyshareChecked ? '1' : '0',
            demandAdeskIpguard: this.form.demandAdeskIpguardChecked ? '1' : '0',
            demandOa: this.form.demandOaChecked ? '1' : '0',
            demandMail: this.form.demandMailChecked ? '1' : '0',
            demandPrinter: this.form.demandPrinterChecked ? '1' : '0',
            demandPrinterLarge: this.form.demandPrinterLargeChecked ? '1' : '0',
            demandPrinterSmall: this.form.demandPrinterSmallChecked ? '1' : '0',
            demandPrinterMz: this.form.demandPrinterMzChecked ? '1' : '0',
            demandPrinterOther: this.form.demandPrinterOtherChecked ? '1' : '0',
            // demandPrinterOtherInfo 保持为字符串，不需要转换
            demandSap: this.form.demandSapChecked ? '1' : '0',
            demandSapNumber: this.form.demandSapNumberChecked ? '1' : '0',
            // demandSapNumberInfo 保持为字符串，不需要转换
            demandWechatUpdate: this.form.demandWechatUpdateChecked ? '1' : '0',
            demandWpsSet: this.form.demandWpsSetChecked ? '1' : '0'
          };

          if (this.form.id) {
            // 修改操作
            updateAdesk(submitData).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.$router.push({ path: "/system/adesk/index" });
            });
          } else {
            // 新增操作
            addAdesk(submitData).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.$router.push({ path: "/system/adesk/index" });
            });
          }
        }
      });
    },
    /** 处理云桌面复选框变化 */
    handleAdeskChange(val) {
      if (!val) {
        // 如果取消云桌面，同时取消爱数账号和加密系统
        this.form.demandAdeskAnyshareChecked = false;
        this.form.demandAdeskIpguardChecked = false;
      }
    },
    /** 处理OA账号复选框变化 */
    handleOaChange(val) {
      if (!val) {
        // 如果取消OA账号，清空手机号
        this.form.demandOaPhone = null;
      }
    },
    /** 处理邮箱复选框变化 */
    handleMailChange(val) {
      if (!val) {
        // 如果取消邮箱申请，清空相关字段
        this.form.demandMailJob = null;
        this.form.demandMailPre = null;
      }
    },
    /** 处理打印机复选框变化 */
    handlePrinterChange(val) {
      if (!val) {
        // 如果取消打印机安装，清空所有打印机相关选项
        this.form.demandPrinterLargeChecked = false;
        this.form.demandPrinterSmallChecked = false;
        this.form.demandPrinterMzChecked = false;
        this.form.demandPrinterOtherChecked = false;
        this.form.demandPrinterOtherInfo = null;
      }
    },
    /** 处理其他打印机复选框变化 */
    handlePrinterOtherChange(val) {
      if (!val) {
        // 如果取消其他打印机，清空描述
        this.form.demandPrinterOtherInfo = null;
      }
    },
    /** 处理SAP复选框变化 */
    handleSapChange(val) {
      if (!val) {
        // 如果取消SAP安装，清空相关选项
        this.form.demandSapNumberChecked = false;
        this.form.demandSapNumberInfoChecked = false;
      }
    },
    /** 邮箱格式化 */
    formatEmail(value) {
      if (value && !value.endsWith('@jianghai.com')) {
        return value + '@jianghai.com';
      }
      return value;
    }
  }
};
</script>

<style scoped>
.el-form-item {
  margin-bottom: 22px;
}
.el-divider {
  margin: 30px 0;
}
</style>

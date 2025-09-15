<template>
  <div class="tab-content">
    <div class="card shadow">
      <div class="card-body">
        <!-- 旧单体条码输入 -->
        <div class="input-group">
          <span class="input-group-text">单体条码</span>
          <input
            type="text"
            v-model="replacementData.oldDTCode"
            class="form-control"
            placeholder="扫描单体条码"
            @keypress.enter="handleSingleUnbindInput"
            :disabled="unbindLoading"
          >
          <span class="input-group-text" @click="clearReplacementInput('oldDTCode')" style="cursor: pointer;">
            <i class="fas fa-times"></i>
          </span>
        </div>

        <!-- 绑定信息展示 -->
        <div v-if="bindingInfo" class="binding-info">
          <h4>绑定信息</h4>
          <div class="info-grid">
            <div class="info-item">
              <span class="info-label">模组:</span>
              <span class="info-value">{{ bindingInfo.moduleCode }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">板子:</span>
              <span class="info-value">{{ bindingInfo.bzCode }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">档位:</span>
              <span class="info-value">{{ bindingInfo.level }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">绑定时间:</span>
              <span class="info-value">{{ bindingInfo.bindedTime }}</span>
            </div>
          </div>
        </div>

        <!-- 错误信息展示 -->
        <div v-if="errorMessage" class="alert alert-danger mt-3">
          {{ errorMessage }}
        </div>

        <!-- 提交和清空按钮 -->
        <div class="btn-group mt-3">
          <button
            class="btn-submit"
            @click="confirmUnbind"
            :disabled="!bindingInfo || !bindingInfo.bindId || unbindLoading"
          >
            {{ unbindLoading ? '处理中...' : '提交解绑' }}
          </button>
          <button class="btn-clear" @click="clearReplacementData">清空</button>
        </div>
      </div>
    </div>

    <!-- 确认解绑弹窗 -->
    <div v-if="showUnbindConfirm" class="modal-backdrop">
      <div class="confirm-modal">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">确认解绑</h5>
          </div>
          <div class="modal-body">
            <p>确定要解绑单体条码 <strong>{{ replacementData.oldDTCode }}</strong> 吗？</p>
            <div v-if="bindingInfo" class="info-grid">
              <div class="info-item">
                <span class="info-label">模组:</span>
                <span class="info-value">{{ bindingInfo.moduleCode }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">板子:</span>
                <span class="info-value">{{ bindingInfo.bzCode }}</span>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="showUnbindConfirm = false">取消</button>
            <button class="btn btn-primary" @click="submitUnbind">确定解绑</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getLibraryByDtCode } from "@/api/system/library";
import { delBind } from "@/api/system/bind";

export default {
  name: 'SingleReplacement',
  data() {
    return {
      replacementData: {
        oldDTCode: ''
      },
      bindingInfo: null,
      errorMessage: '',
      showUnbindConfirm: false,
      unbindLoading: false
    };
  },
  methods: {
    handleSingleUnbindInput(event) {
      const value = event.target.value.trim();
      if (!value) {
        alert('输入码不应为空');
        return;
      }

      this.replacementData.oldDTCode = value;
      this.fetchBindingInfo(value);
    },

    // 获取绑定信息
    async fetchBindingInfo(code) {
      this.unbindLoading = true;
      this.errorMessage = '';
      this.bindingInfo = null;

      try {
        // 调用实际接口获取绑定信息
        const response = await getLibraryByDtCode(code);

        if (response.code === 200) {
          // 检查是否有单体数据
          if (response.data) {
            // 创建基础绑定信息对象
            this.bindingInfo = {
              // 单体基本信息
              ...response.data,
              // 绑定信息（如果有的话）
              bzCode: response.bindedInfo ? response.bindedInfo.bzCode : '',
              moduleCode: response.bindedInfo ? response.bindedInfo.moduleCode : '',
              bindId: response.bindedInfo ? response.bindedInfo.id : null,
              bindedTime: response.bindedInfo ? response.bindedInfo.bindedTime : '',
              level: response.data.level || '' // 确保档位信息也显示
            };

            // 设置错误信息
            if (!response.bindedInfo) {
              this.errorMessage = '该单体未绑定';
            } else {
              this.errorMessage = ''; // 清除错误信息
            }
          } else {
            // 没有单体数据
            this.bindingInfo = null;
            this.errorMessage = '未查询到该单体';
          }
        } else {
          this.bindingInfo = null;
          this.errorMessage = response.msg || '查询绑定信息失败';
        }
      } catch (error) {
        this.bindingInfo = null;
        this.errorMessage = '网络错误，请稍后重试';
        console.error('获取绑定信息失败:', error);
      } finally {
        this.unbindLoading = false;
      }
    },

    // 确认解绑
    confirmUnbind() {
      this.showUnbindConfirm = true;
    },

    // 提交解绑
    async submitUnbind() {
      this.showUnbindConfirm = false;
      this.unbindLoading = true;

      try {
        const response = await delBind(this.bindingInfo.bindId);

        if (response.code === 200) {
          alert('解绑成功');
          this.clearReplacementData();
        } else {
          alert('解绑失败: ' + response.msg);
        }
      } catch (error) {
        alert('网络错误，解绑失败');
        console.error('解绑失败:', error);
      } finally {
        this.unbindLoading = false;
      }
    },

    clearReplacementInput(type) {
      this.replacementData[type] = '';
      this.bindingInfo = null;
      this.errorMessage = '';
    },

    clearReplacementData() {
      this.replacementData = {
        oldDTCode: ''
      };
      this.bindingInfo = null;
      this.errorMessage = '';
    }
  }
};
</script>

<style scoped>
.tab-content {
  opacity: 1;
  transition: opacity 0.5s ease;
}

.card {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.input-group {
  margin-bottom: 2rem;
}

.btn-group {
  display: flex;
  gap: 10px;
  justify-content: center;
  margin-top: 2rem;
}

.btn-group button {
  padding: 12px 24px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.btn-submit {
  background-color: #28a745;
  color: white;
}

.btn-clear {
  background-color: #dc3545;
  color: white;
}

.binding-info {
  margin-top: 20px;
  padding: 15px;
  border: 1px solid #dee2e6;
  border-radius: 5px;
  background-color: #f8f9fa;
}

.binding-info h4 {
  margin-bottom: 15px;
  color: #3A4F63;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.info-item {
  display: flex;
}

.info-label {
  font-weight: bold;
  margin-right: 8px;
  min-width: 70px;
}

.info-value {
  color: #495057;
}

.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1050;
}

.confirm-modal {
  width: 100%;
  max-width: 500px;
}

.modal-content {
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.modal-header {
  padding: 15px;
  border-bottom: 1px solid #dee2e6;
}

.modal-title {
  margin: 0;
}

.modal-body {
  padding: 15px;
}

.modal-footer {
  padding: 15px;
  border-top: 1px solid #dee2e6;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.btn {
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  border: none;
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}

.alert {
  padding: 10px 15px;
  border-radius: 4px;
}

.alert-danger {
  background-color: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}
</style>

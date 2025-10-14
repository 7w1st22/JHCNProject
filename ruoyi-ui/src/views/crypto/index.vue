<template>
  <div class="crypto-wrapper">
    <div class="container">
      <header>
        <h1>加密解密工具箱</h1>
        <p class="description">提供多种加密解密算法的在线工具，包括Base64、AES、DES、MD5、SHA等。所有操作在浏览器中完成，数据不会发送到服务器。</p>
      </header>

      <div class="tools-grid">
        <!-- Base64 编码/解码 -->
        <div class="tool-box">
          <h2><span class="icon">🔒</span> Base64 编码/解码</h2>
          <div class="input-group">
            <label for="base64-input">输入文本</label>
            <textarea
              v-model="base64Input"
              id="base64-input"
              placeholder="输入要编码或解码的文本"
              rows="4"
              @input="handleBase64Input"
            ></textarea>
          </div>
          <div class="button-group">
            <button @click="base64Encode" class="btn btn-primary">编码</button>
            <button @click="base64Decode" class="btn btn-secondary">解码</button>
          </div>
          <div class="result" :class="{ 'has-error': base64Error }">
            {{ base64Result || '结果将显示在这里' }}
          </div>
          <div v-if="base64Error" class="error">{{ base64Error }}</div>
        </div>

        <!-- MD5 哈希 -->
        <div class="tool-box">
          <h2><span class="icon">🔑</span> MD5 哈希</h2>
          <div class="input-group">
            <label for="md5-input">输入文本</label>
            <textarea
              v-model="md5Input"
              id="md5-input"
              placeholder="输入要计算MD5的文本"
              rows="4"
              @input="calculateMD5"
            ></textarea>
          </div>
          <div class="result">
            {{ md5Result || '结果将显示在这里' }}
          </div>
        </div>

        <!-- SHA 系列 -->
        <div class="tool-box">
          <h2><span class="icon">🔐</span> SHA 系列</h2>
          <div class="input-group">
            <label for="sha-input">输入文本</label>
            <textarea
              v-model="shaInput"
              id="sha-input"
              placeholder="输入要计算SHA的文本"
              rows="4"
              @input="calculateSHA"
            ></textarea>
          </div>
          <div class="sha-options">
            <label>
              <input type="radio" v-model="shaType" value="SHA-1"> SHA-1
            </label>
            <label>
              <input type="radio" v-model="shaType" value="SHA-256"> SHA-256
            </label>
            <label>
              <input type="radio" v-model="shaType" value="SHA-512"> SHA-512
            </label>
          </div>
          <div class="result">
            {{ shaResult || '结果将显示在这里' }}
          </div>
        </div>

        <!-- URL 编码/解码 -->
        <div class="tool-box">
          <h2><span class="icon">🌐</span> URL 编码/解码</h2>
          <div class="input-group">
            <label for="url-input">输入URL</label>
            <textarea
              v-model="urlInput"
              id="url-input"
              placeholder="输入要编码或解码的URL"
              rows="4"
              @input="handleURLInput"
            ></textarea>
          </div>
          <div class="button-group">
            <button @click="urlEncode" class="btn btn-primary">编码</button>
            <button @click="urlDecode" class="btn btn-secondary">解码</button>
          </div>
          <div class="result" :class="{ 'has-error': urlError }">
            {{ urlResult || '结果将显示在这里' }}
          </div>
          <div v-if="urlError" class="error">{{ urlError }}</div>
        </div>
      </div>

      <div class="examples">
        <h3>使用说明</h3>
        <p>• <strong>Base64</strong>: 用于文本和二进制数据的相互转换</p>
        <p>• <strong>MD5</strong>: 生成128位哈希值，常用于文件校验</p>
        <p>• <strong>SHA系列</strong>: 更安全的哈希算法，提供不同位数选择</p>
        <p>• <strong>URL编码</strong>: 对URL中的特殊字符进行编码和解码</p>
        <p>• 所有操作都在本地浏览器中完成，确保数据安全</p>
      </div>
    </div>

    <footer>
      <p>© 加密解密工具箱 | 完全在浏览器中运行</p>
    </footer>
  </div>
</template>

<script>
import CryptoJS from 'crypto-js'

export default {
  name: 'CryptoChef',
  data() {
    return {
      // Base64
      base64Input: '',
      base64Result: '',
      base64Error: '',
      
      // MD5
      md5Input: '',
      md5Result: '',
      
      // SHA
      shaInput: '',
      shaResult: '',
      shaType: 'SHA-256',
      
      // URL
      urlInput: '',
      urlResult: '',
      urlError: ''
    }
  },
  methods: {
    // Base64 方法
    handleBase64Input() {
      this.base64Error = ''
      this.base64Result = ''
    },
    
    base64Encode() {
      if (!this.base64Input.trim()) {
        this.base64Error = '请输入要编码的文本'
        return
      }
      try {
        this.base64Result = btoa(unescape(encodeURIComponent(this.base64Input)))
        this.base64Error = ''
      } catch (error) {
        this.base64Error = '编码错误: ' + error.message
      }
    },
    
    base64Decode() {
      if (!this.base64Input.trim()) {
        this.base64Error = '请输入要解码的文本'
        return
      }
      try {
        this.base64Result = decodeURIComponent(escape(atob(this.base64Input)))
        this.base64Error = ''
      } catch (error) {
        this.base64Error = '解码错误: 请输入有效的Base64编码'
      }
    },
    
    // MD5 方法
    calculateMD5() {
      if (!this.md5Input.trim()) {
        this.md5Result = ''
        return
      }
      this.md5Result = CryptoJS.MD5(this.md5Input).toString()
    },
    
    // SHA 方法
    calculateSHA() {
      if (!this.shaInput.trim()) {
        this.shaResult = ''
        return
      }
      
      let hash
      switch (this.shaType) {
        case 'SHA-1':
          hash = CryptoJS.SHA1(this.shaInput)
          break
        case 'SHA-256':
          hash = CryptoJS.SHA256(this.shaInput)
          break
        case 'SHA-512':
          hash = CryptoJS.SHA512(this.shaInput)
          break
        default:
          hash = CryptoJS.SHA256(this.shaInput)
      }
      
      this.shaResult = hash.toString()
    },
    
    // URL 方法
    handleURLInput() {
      this.urlError = ''
      this.urlResult = ''
    },
    
    urlEncode() {
      if (!this.urlInput.trim()) {
        this.urlError = '请输入要编码的URL'
        return
      }
      try {
        this.urlResult = encodeURIComponent(this.urlInput)
        this.urlError = ''
      } catch (error) {
        this.urlError = '编码错误: ' + error.message
      }
    },
    
    urlDecode() {
      if (!this.urlInput.trim()) {
        this.urlError = '请输入要解码的URL'
        return
      }
      try {
        this.urlResult = decodeURIComponent(this.urlInput)
        this.urlError = ''
      } catch (error) {
        this.urlError = '解码错误: 请输入有效的URL编码'
      }
    }
  },
  watch: {
    shaType() {
      this.calculateSHA()
    }
  }
}
</script>

<style scoped>
.crypto-wrapper {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 20px;
  color: #333;
}

.container {
  width: 100%;
  max-width: 1200px;
  background: white;
  border-radius: 15px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  margin-top: 20px;
}

header {
  background: #5a67d8;
  color: white;
  padding: 25px;
  text-align: center;
}

h1 {
  font-size: 2.2rem;
  margin-bottom: 10px;
}

.description {
  font-size: 1.1rem;
  opacity: 0.9;
  max-width: 800px;
  margin: 0 auto;
  line-height: 1.6;
}

.tools-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 30px;
  padding: 30px;
}

@media (max-width: 768px) {
  .tools-grid {
    grid-template-columns: 1fr;
  }
}

.tool-box {
  background: #f8f9fa;
  border-radius: 10px;
  padding: 25px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  color: #5a67d8;
  display: flex;
  align-items: center;
}

.icon {
  margin-right: 10px;
  font-size: 1.8rem;
}

.input-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
  color: #555;
}

textarea {
  width: 100%;
  padding: 14px;
  border: 2px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  font-family: 'Courier New', monospace;
  resize: vertical;
  transition: border-color 0.3s;
}

textarea:focus {
  outline: none;
  border-color: #5a67d8;
}

.button-group {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.btn {
  padding: 12px 24px;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
  flex: 1;
}

.btn-primary {
  background: #5a67d8;
  color: white;
}

.btn-primary:hover {
  background: #4c51bf;
}

.btn-secondary {
  background: #718096;
  color: white;
}

.btn-secondary:hover {
  background: #4a5568;
}

.sha-options {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}

.sha-options label {
  display: flex;
  align-items: center;
  gap: 5px;
  margin-bottom: 0;
  cursor: pointer;
}

.result {
  margin-top: 20px;
  padding: 20px;
  background: white;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-family: 'Courier New', monospace;
  word-break: break-all;
  min-height: 60px;
  color: #2d3748;
}

.result.has-error {
  border-color: #e53e3e;
  color: #e53e3e;
}

.error {
  color: #e53e3e;
  margin-top: 10px;
  font-size: 0.9rem;
}

.examples {
  background: #edf2f7;
  padding: 25px;
  border-top: 1px solid #e2e8f0;
}

.examples h3 {
  color: #2d3748;
  margin-bottom: 15px;
}

.examples p {
  margin-bottom: 8px;
  line-height: 1.5;
}

footer {
  text-align: center;
  padding: 20px;
  color: white;
  opacity: 0.8;
}
</style>
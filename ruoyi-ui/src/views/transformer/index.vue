<template>
  <div class="converter-wrapper">
    <div class="container">
      <header>
        <h1>十进制与34进制转换工具</h1>
        <p class="description">此工具可以将十进制数字转换为34进制（跳过字母i和o），也可以将34进制字符串转换回十进制数字。转换完全在浏览器中进行，您的数据不会发送到服务器。</p>
      </header>

      <div class="converter">
        <div class="conversion-box">
          <h2><span class="icon">➡️</span> 十进制转34进制</h2>
          <div class="input-group">
            <label for="decimal-input">输入十进制数字</label>
            <input
              v-model="decimalInput"
              type="text"
              id="decimal-input"
              placeholder="例如：2537001"
              @input="convertToBase34"
            >
            <div v-if="decimalError" class="error">{{ decimalError }}</div>
          </div>
          <div class="result" :class="{ 'has-error': decimalError }">
            {{ base34Result || '结果将显示在这里' }}
          </div>
        </div>

        <div class="conversion-box">
          <h2><span class="icon">⬅️</span> 34进制转十进制</h2>
          <div class="input-group">
            <label for="base34-input">输入34进制字符串</label>
            <input
              v-model="base34Input"
              type="text"
              id="base34-input"
              placeholder="例如：1wjmp"
              @input="convertToDecimal"
            >
            <div v-if="base34Error" class="error">{{ base34Error }}</div>
          </div>
          <div class="result" :class="{ 'has-error': base34Error }">
            {{ decimalResult || '结果将显示在这里' }}
          </div>
        </div>
      </div>

      <div class="examples">
        <h3>使用说明</h3>
        <p>• 34进制字符集：0-9, a-h, j-n, p-z（跳过了字母i和o）</p>
        <p>• 示例：十进制数字 <code>2537001</code> 转换为34进制是 <code>1wjmp</code></p>
        <p>• 最大支持转换的数字：<code>9,223,372,036,854,775,807</code> (JavaScript最大安全整数)</p>
      </div>
    </div>

    <footer>
      <p>© author russel 进制转换工具 | 完全在浏览器中运行</p>
      <p>联系电话 ： 19850705679</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'Base34Converter',
  data() {
    return {
      decimalInput: '2537001',
      base34Input: '1wjmp',
      base34Result: '',
      decimalResult: '',
      decimalError: '',
      base34Error: '',
      base34Chars: '0123456789abcdefghjklmnpqrstuvwxyz'
    };
  },
  mounted() {
    // 初始转换
    this.convertToBase34();
    this.convertToDecimal();
  },
  methods: {
    convertToBase34() {
      this.decimalError = '';
      this.base34Result = '';

      if (!this.decimalInput.trim()) {
        return;
      }

      // 验证输入
      if (!this.validateDecimal(this.decimalInput)) {
        return;
      }

      try {
        // 使用BigInt支持大数字
        let num = BigInt(this.decimalInput);

        if (num === 0n) {
          this.base34Result = '0';
          return;
        }

        let result = '';
        while (num > 0n) {
          const remainder = Number(num % 34n);
          result = this.base34Chars[remainder] + result;
          num = num / 34n;
        }

        this.base34Result = result;
      } catch (error) {
        this.decimalError = '转换错误: ' + error.message;
      }
    },

    convertToDecimal() {
      this.base34Error = '';
      this.decimalResult = '';

      if (!this.base34Input.trim()) {
        return;
      }

      // 验证输入
      if (!this.validateBase34(this.base34Input)) {
        return;
      }

      try {
        let result = 0n;
        const input = this.base34Input.toLowerCase();

        for (let i = 0; i < input.length; i++) {
          const char = input[i];
          const value = BigInt(this.base34Chars.indexOf(char));

          if (value === -1n) {
            throw new Error(`无效的34进制字符: ${char}`);
          }

          result = result * 34n + value;
        }

        this.decimalResult = result.toString();
      } catch (error) {
        this.base34Error = '转换错误: ' + error.message;
      }
    },

    validateDecimal(input) {
      if (input === '') {
        this.decimalError = '请输入一个数字';
        return false;
      }

      try {
        const num = BigInt(input);

        if (num < 0n) {
          this.decimalError = '请输入非负数字';
          return false;
        }

        return true;
      } catch (error) {
        this.decimalError = '请输入有效的整数';
        return false;
      }
    },

    validateBase34(input) {
      if (input === '') {
        this.base34Error = '请输入34进制字符串';
        return false;
      }

      // 检查是否包含无效字符
      const lowerInput = input.toLowerCase();
      for (let char of lowerInput) {
        if (this.base34Chars.indexOf(char) === -1) {
          this.base34Error = `无效字符: ${char}。34进制只能使用: 0-9, a-h, j-n, p-z`;
          return false;
        }
      }

      return true;
    }
  }
};
</script>

<style scoped>
.converter-wrapper {
  background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 20px;
  color: #333;
}

.container {
  width: 100%;
  max-width: 900px;
  background: white;
  border-radius: 15px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  margin-top: 20px;
}

header {
  background: #4a00e0;
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
  max-width: 700px;
  margin: 0 auto;
  line-height: 1.6;
}

.converter {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
  padding: 30px;
}

@media (max-width: 768px) {
  .converter {
    grid-template-columns: 1fr;
  }
}

.conversion-box {
  background: #f8f9fa;
  border-radius: 10px;
  padding: 25px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  color: #4a00e0;
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

input {
  width: 100%;
  padding: 14px;
  border: 2px solid #ddd;
  border-radius: 8px;
  font-size: 1.1rem;
  transition: border-color 0.3s;
}

input:focus {
  outline: none;
  border-color: #4a00e0;
}

.error {
  color: #e74c3c;
  margin-top: 10px;
  font-size: 0.9rem;
}

.result {
  margin-top: 25px;
  padding: 20px;
  background: white;
  border-radius: 8px;
  border-left: 5px solid #4a00e0;
  font-size: 1.2rem;
  min-height: 70px;
  display: flex;
  align-items: center;
  font-weight: 600;
  color: #2c3e50;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.08);
  word-break: break-all;
}

.result.has-error {
  border-left-color: #e74c3c;
}

.examples {
  margin-top: 30px;
  padding: 20px;
  background: #eef4ff;
  border-radius: 10px;
}

.examples h3 {
  margin-bottom: 15px;
  color: #4a00e0;
}

.examples p {
  line-height: 1.6;
  margin-bottom: 10px;
}

.examples code {
  background: #ddd;
  padding: 2px 6px;
  border-radius: 4px;
  font-family: monospace;
}

footer {
  text-align: center;
  margin-top: 40px;
  color: white;
  opacity: 0.8;
}
</style>

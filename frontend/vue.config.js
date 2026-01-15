const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 添加 devServer 配置
  devServer: {
    port: 8081
  }
})

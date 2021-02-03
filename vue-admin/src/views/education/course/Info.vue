<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="1" process-status="wait" align-center style="marginbottom:40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <el-form label-width="120px" class="save_item">
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">
          保存并下一步
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        saveBtnDisabled: false // 保存按钮是否禁用
      }
    },
    created() {
      console.log('info created')
    },
    methods: {
      next() {
        console.log('next')
        this.$router.push({path: '/chapter/2'})
      }
    }
  }
</script>


<style scoped>
  .save_item {
    margin-left: 39.5%;
    margin-top: 5%;
  }

</style>

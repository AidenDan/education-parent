<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right"
                         :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
          数据类型一定要和取出的json中的一致，否则没法回填
          因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>
      <!-- 讲师头像：TODO -->
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary"
                   @click="saveOrUpdate()">保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import teacherApi from "../../../api/teacher/teacher";

  export default {
    name: "Save",
    data() {
      return {
        teacher: {
          name: '',
          sort: 0,
          level: 1,
          career: '',
          intro: '',
          avatar: ''
        },
        saveBtnDisabled: false // 保存按钮是否禁用,
      }
    },
    created() {
      this.init();
    },
    watch:{ // 监听路由变化，路由变化时会调用监听方法
      $route(to, from){
        this.init();
      }
    },
    methods: {
      init() {
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id;
          this.feedBackTeacherInfo(id);
        } else {
          this.teacher = {}
        }
      },
      saveOrUpdate() {
        if (!this.teacher.id) {
          this.saveTeacher();
        } else {
          this.updateTeacherInfo(this.teacher);
        }
      },
      // 保存讲师
      saveTeacher() {
        teacherApi.saveTeacher(this.teacher)
          .then(() => {
            this.$message({
              type: 'success',
              message: '添加成功!'
            });
            this.$router.push({path: "/table"}); // 在同一个父路由下的子路由
          })
      },
      // 更新讲师信息
      updateTeacherInfo(teacher) {
        teacherApi.updateTeacherInfo(teacher)
          .then(response => {
            this.$message.success("更新讲师信息成功!");
            // 跳转到列表页面
            this.$router.push({path: "/table"});
          })
      },
      // 回显讲师信息详情
      feedBackTeacherInfo(id) {
        teacherApi.feedBackTeacherInfo(id)
          .then(response => {
            this.teacher = response.data.item;
          })
      }
    }
  }
</script>

<style scoped>

</style>

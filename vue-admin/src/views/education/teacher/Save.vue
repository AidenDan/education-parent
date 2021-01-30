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
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload"
                   @click="imagecropperShow=true">更换头像
        </el-button>
        <!--
        v-show：是否显示上传组件
        :key：类似于id，如果一个页面多个图片上传控件，可以做区分
        :url：后台上传的url地址,调用上传头像的服务接口
        @close：关闭上传组件
        @crop-upload-success：上传成功后的回调 -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/oss/uploadAvatar'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>
      </el-form-item>
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

  // 引入组件
  import ImageCropper from "../../../components/ImageCropper"
  import PanThumb from "../../../components/PanThumb"

  export default {
    components: {ImageCropper, PanThumb}, // 申明组件使用组件
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
        saveBtnDisabled: false, // 保存按钮是否禁用,
        imagecropperShow: false, // 默认设为false，不展示弹框
        imagecropperKey: 0,
        BASE_API: process.env.BASE_API
      }
    },
    created() {
      this.init();
    },
    watch: { // 监听路由变化，路由变化时会调用监听方法
      $route(to, from) {
        this.init();
      }
    },
    methods: {
      // 关闭上传头像的对话框
      close() {
        this.imagecropperShow = false;
      },
      // 上传图片成功后的回调方法
      // data = response.data 这里已被组件封装,直接写data
      cropSuccess(data) {
        this.imagecropperShow = false;
        this.teacher.avatar = data.url;
      },
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

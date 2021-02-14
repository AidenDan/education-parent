<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="banner名称">
        <el-input v-model="banner.title"/>
      </el-form-item>
      <el-form-item label="banner排序">
        <el-input-number v-model="banner.sort" controls-position="right"
                         :min="0"/>
      </el-form-item>

      <!-- 讲师头像 -->
      <el-form-item label="banner图像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="banner.imageUrl"/>
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
  import bannerApi from "../../../api/banner/banner";
  // 引入组件
  import ImageCropper from "../../../components/ImageCropper"
  import PanThumb from "../../../components/PanThumb"

  export default {
    components: {ImageCropper, PanThumb}, // 申明组件使用组件
    name: "Save",
    data() {
      return {
        banner: {
          title: '',
          sort: 0,
          imageUrl: '',
          linkUrl: ''
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
        this.imagecropperKey = this.imagecropperKey + 1;
      },
      // 上传图片成功后的回调方法
      // data = response.data 这里已被组件封装,直接写data
      cropSuccess(data) {
        this.imagecropperShow = false;
        this.banner.imageUrl = data.url;
        this.imagecropperKey = this.imagecropperKey + 1;
      },
      init() {
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id;
          this.feedBackbannerInfo(id);
        } else {
          this.banner = {}
        }
      },
      saveOrUpdate() {
        if (!this.banner.id) {
          this.savebanner();
        } else {
          this.updatebannerInfo(this.banner);
        }
      },
      // 保存banner
      savebanner() {
        this.banner.linkUrl = '/'
        bannerApi.saveBanner(this.banner)
          .then(() => {
            this.$message({
              type: 'success',
              message: '添加成功!'
            });
            this.$router.push({path: "/bannerTable"}); // 在同一个父路由下的子路由
          })
      },
      // 更新讲师信息
      updatebannerInfo(banner) {
        bannerApi.updateBanner(banner)
          .then(response => {
            this.$message.success("更新banner信息成功!");
            // 跳转到列表页面
            this.$router.push({path: "/bannerTable"});
          })
      },
      // 回显讲师信息详情
      feedBackbannerInfo(id) {
        bannerApi.feedBackBannerInfo(id)
          .then(response => {
            this.banner = response.data.crmBanner;
          })
      }
    }
  }
</script>

<style scoped>

</style>

<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="2" process-status="wait" align-center style="marginbottom:40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <h2 style="text-align: center;">
      <el-button type="text" @click="openDialog()">添加章节</el-button>
    </h2>

    <div class="chapter_container">
      <el-form label-width="120px">
        <el-form-item>
          <ul class="chapterList">
            <li v-for="chapter in chapterInfoList" :key="chapter.id">
              <p>{{chapter.title}}
                <span class="acts">
                <el-button type="text" @click="openAddXChapterDialog(chapter.id)">添加小节</el-button>
                <el-button type="text" @click="openEditChapterDialog(chapter.id)">编辑</el-button>
                <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
                </span>
              </p>
              <ul class="videoList">
                <li v-for="video  in chapter.children" :key="video.id">
                  <p>{{video.title}}
                    <span class="acts">
                    <el-button type="text" @click="openEditVideoDialog(video.id)">编辑</el-button>
                    <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
                    </span>
                  </p>
                </li>
              </ul>
            </li>
          </ul>
        </el-form-item>

        <h2 style="text-align: center;">
          <el-form-item style="margin-left: 5%">
            <el-button @click="previous">上一步</el-button>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步
            </el-button>
          </el-form-item>
        </h2>

      </el-form>
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controlsposition="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加小节">
      <el-form :model="video" label-width="120px">
        <el-form-item label="小节标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="小节排序">
          <el-input-number v-model="video.sort" :min="0" controlsposition="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduvod/uploadVideo'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary"
                   @click="saveOrUpdateVideo">确 定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import chapter from "../../../api/chapter/chapter";
  import video from "../../../api/chapter/video";

  export default {
    data() {
      return {
        chapter: {
          title: '',
          sort: 0
        },
        dialogChapterFormVisible: false,
        courseId: '',
        chapterInfoList: [],
        saveBtnDisabled: false, // 保存按钮是否禁用

        dialogVideoFormVisible: false,
        saveVideoBtnDisabled: false,
        video: {
          sort: 0,
          isFree: true,
          videoSourceId: '',
          videoOriginalName: ''
        },
        BASE_API: process.env.BASE_API,
        fileList: []

      }
    },
    created() {
      // 页面跳转的时候获取courseId
      this.courseId = this.$route.params.id;
      this.getChapterInfoByCourseId();
    },
    methods: {
      // 上传视屏成功的回调方法
      handleVodUploadSuccess(response, file, fileList) {
        // 把视屏id和视屏名称入库
        this.video.videoSourceId = response.data.videoId;
        // 获取文件名称
        this.video.videoOriginalName = file.name;
      },
      // 点击确定删除视屏后执行的方法
      handleVodRemove() {
        // 删除服务器中对应的视屏
        video.deleteVideoByVideoId(this.video.videoSourceId)
          .then(response => {
            this.$message.success("删除成功!");
            // 将显示的视频列表清空
            this.fileList = [];
            // 将之前上传初始化的videoSourceId videoOriginalName清空
            this.video.videoSourceId = '';
            this.video.videoOriginalName = '';
          })
      },
      // 删除之前执行的方法 点击X执行
      beforeVodRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      // 上传的视屏过多执行的方法
      handleUploadExceed(file, fileList) {
        this.$message.warning('想要重新上传视频，请先删除已上传的视频')
      },
      // 添加小节信息
      openAddXChapterDialog(chapterId) {
        // 先将form数据重置
        this.video.sort = 0;
        this.video.title = '';
        this.video.isFree = true;
        this.dialogVideoFormVisible = true;
        this.fileList = [];
        // 添加小节时需要这两个参数
        this.video.chapterId = chapterId;
        this.video.courseId = this.courseId;
      },
      // 保存或者修改小节信息
      saveOrUpdateVideo() {
        if (this.video.id) {
          // 编辑修改
          this.updateVideo();
        } else {
          this.saveVideo();
        }
      },
      // 保存小节信息
      saveVideo() {
        video.addXChapterInfo(this.video)
          .then(response => {
            this.dialogVideoFormVisible = false;
            // 给出提示
            this.$message.success("添加小节成功!");
            // 刷新章节列表
            this.getChapterInfoByCourseId();
          })
      },
      // 打开编辑小节对话框
      openEditVideoDialog(videoId) {
        // 打开对话框
        this.dialogVideoFormVisible = true;
        // 根据id查询小节信息进行回显
        video.getXChapterInfo(videoId)
          .then(response => {
            this.video = response.data.eduVideo;
          })
      },
      // 修改小节信息
      updateVideo() {
        video.updateXChapterInfo(this.video)
          .then(response => {
            // 关闭对话框
            this.dialogVideoFormVisible = false;
            // 给出提示
            this.$message.success("修改小节信息成功!");
            // 重新刷新列表
            this.getChapterInfoByCourseId();
          })
      },
      // 删除小节信息
      removeVideo(videoId) {
        video.deleteXChapterInfo(videoId)
          .then(response => {
            // 删除后需要刷新列表
            this.getChapterInfoByCourseId();
          })
      },


      // 删除章信息
      removeChapter(id) {
        this.$confirm('此操作将永久删除该章信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { // 点击确定执行
          chapter.deleteChapterInfo(id)
            .then((response) => {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getChapterInfoByCourseId();
            })
        })
      },
      // 打开添加章的对话框
      openDialog() {
        this.dialogChapterFormVisible = true;
        // 打开对话框时要重置数据
        this.chapter.title = '';
        this.chapter.sort = 0;
      },
      // 打开编辑章信息的对话框
      openEditChapterDialog(id) {
        // 打开对话框
        this.dialogChapterFormVisible = true;
        // 数据要回显
        chapter.getChapterInfo(id)
          .then(response => {
            this.chapter = response.data.chapterInfo;
          })
      },
      saveOrUpdate() {
        // 保存后才有章id
        if (this.chapter.id) {
          this.updateChapter();
        } else {
          this.saveChapter();
        }
      },
      // 添加章信息
      saveChapter() {
        this.chapter.courseId = this.courseId;
        chapter.addChapterInfo(this.chapter)
          .then(response => {
            // 关闭对话框
            this.dialogChapterFormVisible = false;
            // 给出提示信息
            this.$message.success("添加章信息成功!");
            // 重新加载章节列表
            this.getChapterInfoByCourseId();
          })
      },
      // 修改章信息
      updateChapter() {
        chapter.updateChapterInfo(this.chapter)
          .then(response => {
            // 关闭对话框
            this.dialogChapterFormVisible = false;
            // 给出提示信息
            this.$message.success("修改章信息成功!");
            // 重新加载章节列表
            this.getChapterInfoByCourseId();
          })
      },
      // 根据课程id获取对应的章节信息
      getChapterInfoByCourseId() {
        chapter.getChapterInfoByCourseId(this.courseId)
          .then(response => {
            console.log(response.data.chapterInfoList)
            this.chapterInfoList = response.data.chapterInfoList;
          })
      },
      previous() {
        // 返回上一步数据回显
        this.$router.push({path: '/info/' + this.$route.params.id})
      },
      next() {
        this.$router.push({path: '/publish/' + this.$route.params.id})
      }
    }
  }
</script>

<style scoped>
  .chapter_container {
    /*居中样式*/
    margin: 0 auto;
    width: 70%;
    margin-left: 10%;
    margin-top: 3%;
  }

  .save_item {
    margin-left: 38%;
    margin-top: 5%;
  }

  .chapterList {
    position: relative;
    list-style-type: none;
    margin: 0;
    padding: 0;
  }

  .chapterList li {
    position: relative;
  }

  .chapterList p {
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }

  .chapterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList {
    padding-left: 50px;
    list-style-type: none;
  }

  .videoList p {
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }
</style>

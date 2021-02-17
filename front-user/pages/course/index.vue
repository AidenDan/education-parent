<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#">全部</a>
                </li>
                <li v-for="oneSub in levelOneSubject" :key="oneSub.id">
                  <a :title="oneSub.title" href="#" :onchange="getLevelTwoSubject(oneSub.id)">{{oneSub.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="twoSub in levelTwoSubject" :key="twoSub.id">
                  <a :title="twoSub.title" href="#">{{twoSub.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
            <i class="c-master f-fM">1</i>/
            <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li>
                <a title="关注度" href="#">关注度</a>
              </li>
              <li>
                <a title="最新" href="#">最新</a>
              </li>
              <li class="current bg-orange">
                <a title="价格" href="#">价格&nbsp;
                  <span>↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="page.rows.length ===0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="page.rows.length>0">
            <ul class="of" id="bna">
              <li v-for="course in page.rows" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover"
                         class="img-responsive" :alt="course.title">
                    <div class="cc-mask">
                      <a :href="'/course/'+course.id" title="开始学习" class="comm-btn cbtn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green" v-if="course.price===0">
                    <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fr jgTag bg-green" v-else>
                    <i class="c-fff fsize12 f-fA">${{course.price}}</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                    <i class="c-999 f-fA">{{course.viewCount}}人学习</i>
                    |
                    <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !page.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)">首</a>

            <a
              :class="{undisable: !page.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(page.pageCurrent-1)">&lt;</a>

            <a
              v-for="p in page.pages"
              :key="p"
              :class="{current: page.current === p, undisable: page.current ===p}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(p)">{{ p }}</a>

            <a
              :class="{undisable: !page.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(page.pageCurrent+1)">&gt;</a>

            <a
              :class="{undisable: !page.hasNext}"
              href="#"
              title="末页"
              @click.prevent="gotoPage(page.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
  import courseApi from "../../api/course";
  import subjectApi from "../../api/subject";

  export default {
    data() {
      return {
        page: {
          total: 0,
          rows: [],
          pageCurrent: 1,
          pages: 0,
          size: 8,
          hasPrevious: false,
          hasNext: false,
        },
        levelOneSubject: [],
        levelTwoSubject: [],
        courseQuery: {}
      }
    },
    created() {
      this.getLevelOneSubject();
      this.getCourseList();
    },
    methods: {
      gotoPage(p) {
        // 如果是最后一页 return
        if (p === this.page.pages + 1) {
          return;
        }
        courseApi.getCourseList(p, this.page.size, this.courseQuery)
          .then(response => {
            if (response.data.code === 20000) {
              this.page = response.data.data;
            }
          })
      },
      // 获取所有课程列表
      getCourseList() {
        courseApi.getCourseList(this.page.pageCurrent, this.page.size, this.courseQuery)
          .then(response => {
            if (response.data.code === 20000) {
              this.page = response.data.data;
            } else {
              this.$message.error("加载课程列表失败");
            }
          }).catch(err => {
          this.$message.error("加载课程列表失败");
        })
      },
      // 获取一级课程
      getLevelOneSubject() {
        subjectApi.getTotalSubject()
          .then(response => {
            if (response.data.code === 20000) {
              this.levelOneSubject = response.data.data.totalSubject;
            } else {
              this.$message.error("获取课程失败");
            }
          }).catch(err => {
          this.$message.error("获取课程失败");
        })
      },
      // 获取二级课程
      getLevelTwoSubject(oneId) {
        for (let j = 0; j < this.levelOneSubject.length; j++) {
          if (this.levelOneSubject[j].id === oneId) {
            this.levelTwoSubject = this.levelOneSubject[j].children;
          }
        }
      }
    }
  };
</script>

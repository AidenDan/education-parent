<template>
  <div class="app_container">
    <!--查询表单-->
    <el-form :inline="true" class="el-breadcrumb query_form">
      <el-form-item>
        <el-input v-model="bannerQuery.title" placeholder="banner名称"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getBannerList()">查询
      </el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row class="el-table_class">
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (current - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="名称" width="280" align="center"/>

      <el-table-column prop="gmtCreate" label="添加时间" width="360" align="center"/>
      <el-table-column prop="gmtModified" label="修改时间" width="360" align="center"/>
      <el-table-column prop="sort" label="排序" width="160" align="center"/>
      <el-table-column label="操作" width="335" align="center">
        <template slot-scope="scope">
          <router-link :to="'/editBanner/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete"
                     @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="current"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getBannerList"
    />
  </div>

</template>

<script>
  import banner from "../../../api/banner/banner";

  export default {
    name: "List",
    data() {
      return {
        list: null,
        total: 0,
        current: 1,
        limit: 5,
        bannerQuery: {}
      }
    },
    created() {
      this.getBannerList()
    },
    methods: {
      getBannerList(current = 1) {  // current = 1为默认值
        this.current = current;
        banner.getBannerList(this.current, this.limit, this.bannerQuery)
          .then(response => {
            this.list = response.data.rows;
            this.total = response.data.total;
          })  // 请求成功处理逻辑
          .catch(error => {
          })  // 请求失败处理逻辑
      },

      // 重置查询参数
      resetData() {
        // 1.清空查询参数
        this.bannerQuery = {};
        // 2.查询所有数据
        this.getBannerList()
      },

      // 讲师删除功能
      removeDataById(id) {
        this.$confirm('此操作将永久删除该banner数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { // 点击确定执行
          banner.deleteBannerById(id)
            .then((response) => {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getBannerList();
            })
        })
      }
    }
  }
</script>

<style scoped>
  .app_container {
    margin-left: 5%;
    width: 90%;
    text-align: center;
  }

</style>

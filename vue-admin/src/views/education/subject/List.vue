<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;"/>
    <el-tree
      ref="tree2"
      :data="dataSubject"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
  import subject from "../../../api/subject/subject";

  export default {
    data() {
      return {
        filterText: '',
        dataSubject: [],
        defaultProps: {
          children: 'children',
          label: 'title'
        }
      }
    },
    watch: {
      filterText(val) {
        this.$refs.tree2.filter(val)
      }
    },

    created() {
      this.getTotalSubject();
    },

    methods: {
      getTotalSubject() {
        subject.getTotalSubject()
          .then(response => {
            this.dataSubject = response.data.totalSubject;
          })
      },
      // 过滤节点，不区分大小写
      filterNode(value, data) {
        if (!value) return true;
        return data.title.toLowerCase().indexOf(value) !== -1
      }
    }
  }
</script>

<style>

</style>


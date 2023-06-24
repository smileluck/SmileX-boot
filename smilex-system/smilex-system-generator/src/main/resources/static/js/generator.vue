<template>
  <div class="table-card">
    <div class="table-search">
      <el-form :inline="true" :model="searchFormModel" ref="searchForm">
        <el-row :gutter="10">
          <el-col :span="4">
            <el-form-item label="表名：">
              <el-input
                v-model.trim="searchFormModel.tableName"
                placeholder="请输入表名"
              /> </el-form-item
          ></el-col>
          <el-col :span="4">
            <el-button type="primary" @click="list()">
              <el-icon class="el-icon"><search /></el-icon>
              <span>搜索</span></el-button
            >
            <el-button type="primary" @click="resetSearch()">
              <el-icon class="el-icon"><refresh-right /></el-icon>
              <span>重置</span></el-button
            >
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="table-operate">
      <el-row>
        <el-col :span="2">
          <el-button type="primary" @click="configHandle()"
            >配置数据源</el-button
          >
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="genHandle()">生成数据表</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="table-selection" v-show="selectColumn.length > 0">
      <span class="selection-span"
        >已选择 <a>{{ selectColumn.length }}</a> 项
      </span>
      <span class="selection-operate">
        <a @click="handleSelectionClear()">清空</a>
      </span>
    </div>
    <el-table
      ref="table"
      v-loading="loading"
      element-loading-text="Loading..."
      :data="tableData"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="50" />
      <el-table-column prop="tableName" label="表名" width="200" />
      <el-table-column prop="engine" label="引擎" width="200" />
      <el-table-column prop="tableComment" label="表注释" />
      <el-table-column prop="createTime" label="创建时间" width="200" />
      <!-- <el-table-column fixed="right" label="操作" width="200">
        <template #default>
          <el-button type="text">查看列</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <!-- <column-model></column-model> -->
    <db-config-model ref="configModel" @refresh="list"></db-config-model>

    <code-gen-model ref="genModel" @refresh="list"></code-gen-model>
  </div>
</template>

<script setup>
import { Search, RefreshRight } from "@element-plus/icons-vue";
import { ref, reactive, toRaw, unref } from "vue";
import { getAction } from "@/api/manage";
// import ColumnModel from "./modules/ColumnModel.vue";
import DbConfigModel from "./modules/DbConfigModel.vue";
import CodeGenModel from "./modules/CodeGenModel.vue";
import { ElNotification } from "element-plus";

let tableData = ref([]);
const loading = ref(false);
const table = ref();
const configModel = ref();
const genModel = ref();
const searchForm = ref();

const selectColumn = ref([]);

const searchFormModel = reactive({
  tableName: "",
  tableCommene: "",
});

const handleSelectionChange = (val) => {
  console.log(val);
  selectColumn.value = val;
};

const handleSelectionClear = () => {
  table.value.clearSelection();
};

const list = () => {
  loading.value = true;
  getAction("/generator/list", toRaw(searchFormModel))
    .then((res) => {
      if (res.success) {
        tableData.value = res.data;
      } else {
        tableData.value = [];
      }
    })
    .finally(() => {
      loading.value = false;
    });
};
const configHandle = () => {
  unref(configModel).initModel();
};
const genHandle = () => {
  if (selectColumn.value.length == 0) {
    ElNotification({
      title: "系统提示",
      message: "请选择表进行生成",
      type: "error",
    });
    return;
  }
  unref(genModel).initModel(selectColumn.value);
};

const resetSearch = () => {
  unref(searchForm).resetFields();
  list();
};

list();
</script>

<template>
    <div class="table-card">
        <div class="table-search">
            <el-form :inline="true" :model="pageSearchFormModel" ref="pageSearchForm">
                <el-row :gutter="10">
                    <el-col :span="4">
                        <#if primaryColumn??>
                        <el-form-item label="ID：">
                            <el-input
                                    v-model.trim="pageSearchFormModel.${primaryColumn.humpColumnName}"
                                    placeholder="请输入ID"
                            /> </el-form-item
                        ></el-col>
                        </#if>
                        <#list columnModels as var>
                        <el-col :span="4">
                            <el-form-item label="${var.columnComment}：">
                                <el-input
                                        v-model.trim="pageSearchFormModel.${var.humpColumnName}"
                                        placeholder="请输入${var.columnComment}"
                                /> </el-form-item
                            ></el-col>
                        </#list>
                    <el-col :span="4">
                        <el-button type="primary" @click="pageList()">
                            <el-icon class="el-icon"><search /></el-icon>
                            <span>搜索</span></el-button
                        >
                        <el-button type="primary" @click="pageSearchReset()">
                            <el-icon class="el-icon"><refresh-right /></el-icon>
                            <span>重置</span></el-button
                        >
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <div class="table-operate">
            <el-row :gutter="20">
                <el-col :span="1">
                    <el-button type="primary" @click="pageOperaAdd()">新增</el-button>
                </el-col>
                <el-col :span="1" v-show="pageSelectColumn.length > 0">
                    <el-button type="danger" @click="pageOperaRemove()"
                    >批量删除</el-button
                    >
                </el-col>
            </el-row>
        </div>
        <div class="table-selection" v-show="pageSelectColumn.length > 0">
      <span class="selection-span"
      >已选择 <a>{{ pageSelectColumn.length }}</a> 项
      </span>
            <span class="selection-operate">
        <a @click="pageSelectClear()">清空</a>
      </span>
        </div>
        <el-table
                ref="pageTable"
                v-loading="pageLoading"
                element-loading-text="Loading..."
                :data="pageTableData"
                style="width: 100%"
                @selection-change="pageSelectChange"
        >
            <el-table-column type="selection" width="50" />
            <#if primaryColumn??>
            <el-table-column prop="${primaryColumn.humpColumnName}" label="ID" width="" />
            </#if>
            <#list columnModels as var>
            <el-table-column prop="${var.humpColumnName}" label="${var.columnComment}" width="200" />
            </#list>
            <el-table-column fixed="right" label="Operations" width="120">
                <template #default>
                    <el-button type="text" >删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination
            class="table-card-pagination"
            :page-sizes="[10, 20, 50, 100]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pagePaginationInfo.total"
            :currentPage="pagePaginationInfo.current"
            :page-size="pagePaginationInfo.size"
            @size-change="pagePaginationSizeChange"
            @current-change="pagePaginationCurrentChange"
        >
        </el-pagination>
        <${smallDashName}-model ref="pageOperaModel" @refresh="pageList"></${smallDashName}-model>
    </div>
</template>

<script setup>
    import { Search, RefreshRight } from "@element-plus/icons-vue";
    import { reactive } from "vue";
    import ${bigHumpClass}Model from "./modules/${bigHumpClass}Model.vue";
    import usePages from "@/composables/pages";

    const {
        // 组件引用
        pageLoading,
        pageOperaModel,
        pageTable,
        pageSelectColumn,

        pageList,
        pagePaginationInfo,
        pagePaginationSizeChange,
        pagePaginationCurrentChange,
        pageTableData,
        pageSelectChange,
        pageSelectClear,

        pageOperaAdd,
        pageOperaRemove,

        pageSearchForm,
        pageSearchReset,
    } = usePages(pageSearchFormModel);

    const pageSearchFormModel = reactive({
        <#if primaryColumn??>
        ${primaryColumn.humpColumnName}:"",
        </#if>
        <#list columnModels as var>
        ${var.humpColumnName}:"",
        </#list>
    });
</script>

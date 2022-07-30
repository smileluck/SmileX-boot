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
                        <#if var.humpColumnName=="enableFlag">
                         <el-col :span="4">
                            <el-form-item label="启用状态：">
                               <dict-select
                                    dictCode="enableFlag"
                                    v-model="pageSearchFormModel.enableFlag"
                                    :clearable="true" />
                            </el-form-item
                        ></el-col>
                        <#else>
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
            <el-table-column type="expand">
                <template #default="props">
                    <el-space wrap
                    ><el-card class="box-card" style="min-width: 500px; width: 50%">
                            <template #header>
                                <div class="card-header">
                                    <span>额外信息</span>
                                </div>
                            </template>
                            <div class="text item">创建时间: {{ props.row.createTime }}</div>
                            <div class="text item">创建人: {{ props.row.createBy }}</div>
                            <div class="text item">
                                最后更新时间: {{ props.row.updateTime }}
                            </div>
                            <div class="text item">最后更新人: {{ props.row.updateBy }}</div>
                        </el-card>
                    </el-space>
                </template>
            </el-table-column>
            <#if primaryColumn??>
            <el-table-column prop="${primaryColumn.humpColumnName}" label="ID" width="180" />
            </#if>
            <#list columnModels as var>
            <#if var.humpColumnName=="enableFlag">
            <el-table-column prop="enableFlag" label="启用状态" width="200">
                <template #default="scope">
                    <table-column-dict
                            dictCode="enableFlag"
                            :value="scope.row.enableFlag"
                    ></table-column-dict>
                </template>
            </el-table-column>
            <#else>
            <el-table-column prop="${var.humpColumnName}" label="${var.columnComment}" width="200" />
            </#if>
            </#list>
            <el-table-column fixed="right" label="Operations" width="120">
                <template v-slot:default="scope">
                    <el-button type="primary" link @click="pageOperaAdd(scope.row.id)"
                    >修改</el-button
                    >
                    <el-popconfirm
                            :title="'是否确认删除id=[' + scope.row.id + ']?'"
                            @confirm="pageOperaRemove(scope.row.id)"
                    >
                        <template #reference>
                            <el-button type="danger" link>删除</el-button>
                        </template>
                    </el-popconfirm>
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

    <#if hasDict==true>
    import DictSelect from "@/components/DictSelect";
    </#if>

    const pageSearchFormModel = reactive({
        <#if primaryColumn??>
        ${primaryColumn.humpColumnName}:"",
        </#if>
        <#list columnModels as var>
        ${var.humpColumnName}:"",
        </#list>
    });

    const reqPrefix = "/${reqMapping}"

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
    } = usePages(pageSearchFormModel,reqPrefix);
    pageList();
</script>

<template>
    <el-dialog
        v-model="dialogVisible"
        :title='`${r'${form.info.id!= null ? "修改" : "新增"}'}`'
        width="50%"
        :before-close="handleClose"
    >
        <el-form
                :model="form.info"
                label-width="120px"
                :rules="rules"
                ref="formRef"
        >
            <#list columnModels as var>
            <el-form-item label="${var.columnComment}">
            <el-input
                    v-model.trim="form.info.${var.humpColumnName}"
                    placeholder="请输入${var.columnComment}"
            /> </el-form-item>
            </#list>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submitForm()">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
    import { ref, unref, reactive, toRaw, defineExpose, defineEmits } from "vue";
    import { postAction, getAction } from "@/api/manage";

    const emit = defineEmits(["refresh"]);

    const dialogVisible = ref(false);
    const formRef = ref();
    const selectArr = [];

    const form = reactive({
        info: {
            id: null,
            <#list columnModels as var>
            ${var.humpColumnName}:"",
            </#list>
        },
    });

    const rules = reactive({
        <#list columnModels as var>
        ${var.humpColumnName}:[ { required: true, message: "请选择${var.columnComment}", trigger: "blur" }],
        </#list>
    });

    const getInfo = () => {
        getAction(`/${reqMapping}/info/${r'${form.id}'}`, {}).then((res) => {
            if (res.success) {
                form.info = { ...res.data };
            }
        });
    };

    const submitForm = () => {
        // if (!formEl) return;
        const formEl = unref(formRef);
        if (!formEl) {
            return;
        }
        formEl.validate((valid, fields) => {
            if (valid) {
                postAction(`/sys/menu/${r'${form.id != null ? "update" : "save"}'}`, {
                    ...toRaw(form),
                    tableName: selectArr,
                }).then((res) => {
                    if (res.success) {
                        emit("refresh");
                        dialogVisible.value = false;
                    }
                });
            } else {
                console.log("error submit!", fields);
            }
        });
    };

    const initModel = (id) => {
        dialogVisible.value = true;
        form.id = id;
        if (id != null) {
            getInfo();
        }
    };
    defineExpose({ initModel });
</script>

<template>
    <el-dialog
        v-model="dialogVisible"
        :title='`${r'${form.info.id!= null ? "修改" : "新增"}'}`'
        width="50%"
        :before-close="cancelForm"
    >
        <el-form
                :model="form.info"
                label-width="120px"
                :rules="from.rules"
                ref="formRef"
        >
            <#list columnModels as var>
            <#if var.humpColumnName=="enableFlag">
            <el-form-item label="状态" prop="${var.humpColumnName}">
                <el-switch
                        v-model="form.info.${var.humpColumnName}"
                        active-text="启用"
                        inactive-text="禁用"
                        active-value="1"
                        inactive-value="0"
                />
            </el-form-item>
            <#else>
            <el-form-item label="${var.columnComment}" prop="${var.humpColumnName}">
            <el-input
                    v-model.trim="form.info.${var.humpColumnName}"
                    placeholder="请输入${var.columnComment}"
            /> </el-form-item>
            </#if>
            </#list>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="cancelForm()">取消</el-button>
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

    const form = reactive({
        info: {
            id: null,
            <#list columnModels as var>
            ${var.humpColumnName}:"",
            </#list>
        },
        rules: {
            <#list columnModels as var>
            ${var.humpColumnName}:[ { required: true, message: "请输入${var.columnComment}", trigger: "blur" }],
            </#list>
        }
    });

    const getInfo = () => {
        getAction(`/${reqMapping}/info/${r'${form.info.id}'}`, {}).then((res) => {
            if (res.success) {
                form.info = { ...res.data };
            }
        });
    };

    const cancelForm = () => {
        formRef.value.resetFields();
        dialogVisible.value = false;
        form.info = {};
    };

    const submitForm = () => {
        // if (!formEl) return;
        const formEl = unref(formRef);
        if (!formEl) {
            return;
        }
        formEl.validate((valid, fields) => {
            if (valid) {
                postAction(`/${reqMapping}/${r'${form.info.id != null ? "update" : "save"}'}`, {
                    ...toRaw(form.info)
                }).then((res) => {
                    if (res.success) {
                        emit("refresh");
                        cancelForm();
                    }
                });
            } else {
                console.log("error submit!", fields);
            }
        });
    };

    const initModel = (id) => {
        dialogVisible.value = true;
        if (id != null) {
            form.info.id = id;
            getInfo();
        }
    };
    defineExpose({ initModel });
</script>

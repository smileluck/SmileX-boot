package top.zsmile.tool.gen;

import org.junit.jupiter.api.Test;
import top.zsmile.tool.gen.domain.model.ColumnModel;
import top.zsmile.tool.gen.domain.model.MenuModel;
import top.zsmile.tool.gen.domain.model.TableModel;
import top.zsmile.tool.gen.utils.GeneratorUtils;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 模板渲染回归测试（无 DB 依赖）：全部 9 个模板可渲染且关键内容正确
 */
class TemplateRenderTest {

    private TableModel buildModel() {
        ColumnModel primary = new ColumnModel();
        primary.setColumnName("id");
        primary.setHumpColumnName("id");
        primary.setBigHumpColumnName("Id");
        primary.setDataType("bigint");
        primary.setColumnType("bigint");
        primary.setConvertDataType("Long");
        primary.setColumnKey("PRI");
        primary.setColumnComment("主键");

        ColumnModel name = new ColumnModel();
        name.setColumnName("user_name");
        name.setHumpColumnName("userName");
        name.setBigHumpColumnName("UserName");
        name.setDataType("varchar");
        name.setColumnType("varchar(64)");
        name.setConvertDataType("String");
        name.setColumnKey("");
        name.setColumnComment("用户名");

        ColumnModel tenant = new ColumnModel();
        tenant.setColumnName("tenant_id");
        tenant.setHumpColumnName("tenantId");
        tenant.setBigHumpColumnName("TenantId");
        tenant.setDataType("bigint");
        tenant.setColumnType("bigint");
        tenant.setConvertDataType("Long");
        tenant.setColumnKey("");
        tenant.setColumnComment("租户");

        ColumnModel createTime = new ColumnModel();
        createTime.setColumnName("birthday");
        createTime.setHumpColumnName("birthday");
        createTime.setBigHumpColumnName("Birthday");
        createTime.setDataType("date");
        createTime.setColumnType("date");
        createTime.setConvertDataType("LocalDate");
        createTime.setColumnKey("");
        createTime.setColumnComment("生日");

        TableModel model = new TableModel();
        model.setPackages("top.zsmile.modules");
        model.setModuleName("sys");
        model.setTableName("sys_user");
        model.setBigHumpClass("SysUser");
        model.setSmallHumpClass("sysUser");
        model.setSmallDashName("sys-user");
        model.setSmallColonName("sys:user");
        model.setReqMapping("sys/user");
        model.setTableComment("用户表");
        model.setPrimaryColumn(primary);
        model.setColumnModels(Arrays.asList(name, tenant, createTime));
        model.setFilterColumn(new String[]{"password", "salt"});
        model.setImportTypes(new HashSet<>(Arrays.asList("LocalDate")));
        MenuModel menuModel = new MenuModel();
        menuModel.setParentId(1L);
        menuModel.setMenuIds(Arrays.asList(11L, 12L, 13L, 14L));
        model.setMenuModel(menuModel);
        return model;
    }

    @Test
    void entityTemplateShouldContainTableIdAndTenantId() {
        String code = GeneratorUtils.renderToString("entity.ftl", buildModel());
        assertTrue(code.contains("@TableName(\"sys_user\")"), "应生成 @TableName");
        assertTrue(code.contains("@TableId"), "主键应生成 @TableId（任意主键名场景必需）");
        assertTrue(code.contains("@TenantId"), "tenant_id 字段应生成 @TenantId");
        assertTrue(code.contains("import java.time.LocalDate;"), "LocalDate 类型应生成 import");
        assertTrue(code.contains("private Long id;"), "主键类型应正确");
    }

    @Test
    void mapperServiceTemplatesShouldExtendBaseClasses() {
        TableModel model = buildModel();
        assertTrue(GeneratorUtils.renderToString("mapper.ftl", model).contains("extends BaseMapper<SysUserEntity>"));
        assertTrue(GeneratorUtils.renderToString("service.ftl", model).contains("extends BaseService<SysUserEntity>"));
        assertTrue(GeneratorUtils.renderToString("serviceimpl.ftl", model).contains("extends BaseServiceImpl<SysUserMapper,SysUserEntity>"));
    }

    @Test
    void controllerTemplateShouldUsePrimaryType() {
        String code = GeneratorUtils.renderToString("controller.ftl", buildModel());
        assertTrue(code.contains("public R<SysUserEntity> info(@PathVariable(\"id\") Long id)"), "主键类型应来自元数据而非硬编码");
        assertTrue(code.contains("Long[] ids"), "批量删除参数应使用主键类型");
    }

    @Test
    void vueTemplatesShouldHaveCorrectImportPath() {
        TableModel model = buildModel();
        String page = GeneratorUtils.renderToString("vuePage.ftl", model);
        assertTrue(page.contains("import SysUserModel from \"./modules/SysUserModel.vue\";"),
                "生成前端的 import 路径应为 ./modules/{X}Model.vue");
        String pageModel = GeneratorUtils.renderToString("vuePageModel.ftl", model);
        assertTrue(pageModel.contains("required: false"), "字段不应强制必填");
    }

    @Test
    void menuSqlShouldRenderWithEscape() {
        TableModel model = buildModel();
        model.setTableComment("含'单引号'的表");
        String sql = GeneratorUtils.renderToString("menuSql.ftl", model);
        assertTrue(sql.contains("sys_menu"));
        assertTrue(sql.contains("'含''单引号''的表'"), "单引号应转义为两个单引号");
    }

    @Test
    void mapperXmlShouldContainNamespace() {
        String xml = GeneratorUtils.renderToString("mapperXml.ftl", buildModel());
        assertTrue(xml.contains("namespace=\"top.zsmile.modules.sys.dao.SysUserMapper\""));
    }
}

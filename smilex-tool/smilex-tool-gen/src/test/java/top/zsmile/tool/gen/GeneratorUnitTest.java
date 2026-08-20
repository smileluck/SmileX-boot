package top.zsmile.tool.gen;

import org.junit.jupiter.api.Test;
import top.zsmile.tool.gen.convert.MysqlTypeConvert;
import top.zsmile.tool.gen.utils.NameStyleUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 类型映射与命名转换单测
 */
class GeneratorUnitTest {

    private final MysqlTypeConvert converter = new MysqlTypeConvert();

    @Test
    void shouldConvertKnownTypes() {
        assertEquals("Long", converter.convert("bigint"));
        assertEquals("BigDecimal", converter.convert("decimal"));
        assertEquals("BigDecimal", converter.convert("numeric"));
        assertEquals("String", converter.convert("json"));
        assertEquals("String", converter.convert("enum"));
        assertEquals("String", converter.convert("varchar"));
        assertEquals("LocalDateTime", converter.convert("datetime"));
        assertEquals("LocalDate", converter.convert("date"));
        assertEquals("LocalTime", converter.convert("time"));
        assertEquals("byte[]", converter.convert("blob"));
    }

    @Test
    void tinyintOneShouldBeBoolean() {
        assertEquals("Boolean", converter.convert("tinyint", "tinyint(1)"));
        assertEquals("Integer", converter.convert("tinyint", "tinyint(4)"));
        assertEquals("Integer", converter.convert("tinyint"));
    }

    @Test
    void unknownTypeShouldFallbackToString() {
        assertEquals("String", converter.convert("geometry"));
        assertEquals("String", converter.convert(null));
    }

    @Test
    void lineToHumpShouldHandleConsecutiveUnderscores() {
        assertEquals("userName", NameStyleUtils.lineToHump("user_name", false));
        assertEquals("userName", NameStyleUtils.lineToHump("user__name", false));
        assertEquals("UserName", NameStyleUtils.lineToHump("user_name", true));
        assertEquals("sys:user", NameStyleUtils.lineToCustomStr("sys_user", ":"));
        assertEquals("sys-user", NameStyleUtils.lineToDash("sys_user"));
        assertEquals("sys/user", NameStyleUtils.lineToSlash("sys_user"));
    }

    @Test
    void javaKeywordColumnShouldBeEscaped() {
        assertEquals("class_", NameStyleUtils.lineToHump("class", false));
        assertEquals("int_", NameStyleUtils.lineToHump("int", false));
        assertEquals("normal", NameStyleUtils.lineToHump("normal", false));
    }
}

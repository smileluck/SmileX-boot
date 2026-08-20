package top.zsmile.common.mybatis;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.core.utils.uuid.SnowFlake;
import top.zsmile.common.mybatis.annotation.FieldEncrypt;
import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.annotation.TenantIgnore;
import top.zsmile.common.mybatis.cache.TableInfoCache;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.common.mybatis.entity.BaseEntity;
import top.zsmile.common.mybatis.interceptor.FieldDecryptInterceptor;
import top.zsmile.common.mybatis.interceptor.PaginationInnerInterceptor;
import top.zsmile.common.mybatis.interceptor.TenantLineInnerInterceptor;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.common.mybatis.meta.Page;
import top.zsmile.common.mybatis.meta.conditions.query.LambdaQueryWrapper;
import top.zsmile.common.mybatis.meta.conditions.query.QueryWrapper;
import top.zsmile.common.mybatis.spi.TenantIdProvider;
import top.zsmile.common.mybatis.utils.EntityAutoFill;
import top.zsmile.common.mybatis.utils.FieldEncryptor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * H2（MySQL 模式）集成测试：覆盖 CRUD/逻辑删除/白名单 fail-fast/Wrapper/分页/租户/字段加密
 */
class MybatisEnhanceTest {

    private static SqlSessionFactory sqlSessionFactory;
    private static JdbcDataSource dataSource;
    private static final AtomicReference<Long> CURRENT_TENANT = new AtomicReference<>();

    @Data
    @TableName("t_user")
    public static class UserEntity extends BaseEntity {
        private Long id;
        private String userName;
        private Long tenantId;
    }

    public interface UserMapper extends BaseMapper<UserEntity> {
        @TenantIgnore
        @org.apache.ibatis.annotations.Select("SELECT * FROM t_user")
        List<UserEntity> selectAllIgnoreTenant();
    }

    @Data
    @TableName("t_plain")
    public static class PlainEntity extends BaseEntity {
        private Long id;
        private String name;
    }

    public interface PlainMapper extends BaseMapper<PlainEntity> {
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @TableName("t_secret")
    public static class SecretEntity extends BaseEntity {
        private Long id;
        @FieldEncrypt
        private String secretText;
    }

    public interface SecretMapper extends BaseMapper<SecretEntity> {
    }

    @BeforeAll
    static void initAll() throws Exception {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:smilex-mybatis-test;MODE=MySQL;DB_CLOSE_DELAY=-1");
        try (Connection conn = dataSource.getConnection(); Statement st = conn.createStatement()) {
            st.execute("CREATE TABLE t_user (id BIGINT PRIMARY KEY, user_name VARCHAR(64), tenant_id BIGINT, " +
                    "del_flag INT, create_time TIMESTAMP, create_by VARCHAR(32), update_time TIMESTAMP, update_by VARCHAR(32))");
            st.execute("CREATE TABLE t_plain (id BIGINT PRIMARY KEY, name VARCHAR(64), " +
                    "del_flag INT, create_time TIMESTAMP, create_by VARCHAR(32), update_time TIMESTAMP, update_by VARCHAR(32))");
            st.execute("CREATE TABLE t_secret (id BIGINT PRIMARY KEY, secret_text VARCHAR(512), " +
                    "del_flag INT, create_time TIMESTAMP, create_by VARCHAR(32), update_time TIMESTAMP, update_by VARCHAR(32))");
        }

        TenantIdProvider tenantIdProvider = CURRENT_TENANT::get;
        EntityAutoFill.initGenerator(new SnowFlake(1, 1));
        EntityAutoFill.initTenantProvider(tenantIdProvider);
        // 16 字节 AES 密钥
        FieldEncryptor.init(java.util.Base64.getEncoder().encodeToString(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}));

        Configuration configuration = new Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setEnvironment(new Environment("test", new JdbcTransactionFactory(), dataSource));
        // 注册顺序即拦截链：租户（内层）-> 分页（外层）
        configuration.addInterceptor(new TenantLineInnerInterceptor(tenantIdProvider, new HashSet<>()));
        configuration.addInterceptor(new PaginationInnerInterceptor(500));
        configuration.addInterceptor(new FieldDecryptInterceptor());
        configuration.addMapper(UserMapper.class);
        configuration.addMapper(PlainMapper.class);
        configuration.addMapper(SecretMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        // 预热元数据
        TableInfoCache.getTableInfo(UserMapper.class);
        TableInfoCache.getTableInfo(PlainMapper.class);
        TableInfoCache.getTableInfo(SecretMapper.class);
    }

    @AfterAll
    static void tearDownAll() throws Exception {
        try (Connection conn = dataSource.getConnection(); Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS t_user");
            st.execute("DROP TABLE IF EXISTS t_plain");
            st.execute("DROP TABLE IF EXISTS t_secret");
        }
        CURRENT_TENANT.set(null);
    }

    @BeforeEach
    void setUp() {
        CURRENT_TENANT.set(null);
    }

    @AfterEach
    void cleanData() throws Exception {
        try (Connection conn = dataSource.getConnection(); Statement st = conn.createStatement()) {
            st.execute("DELETE FROM t_user");
            st.execute("DELETE FROM t_plain");
            st.execute("DELETE FROM t_secret");
        }
    }

    private static boolean hasCause(Throwable ex, Class<? extends Throwable> type) {
        while (ex != null) {
            if (type.isInstance(ex)) {
                return true;
            }
            ex = ex.getCause();
        }
        return false;
    }

    private UserEntity newUser(String name, Long tenantId) {
        UserEntity user = new UserEntity();
        user.setUserName(name);
        user.setTenantId(tenantId);
        return user;
    }

    // ==================== 插入与自动填充 ====================

    @Test
    void insertShouldFillSnowflakeIdAndDefaults() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            UserEntity user = newUser("alice", 100L);
            mapper.insert(user);
            assertNotNull(user.getId());
            assertNotNull(user.getCreateTime());
            assertNotNull(user.getCreateBy());
            assertEquals(0, user.getDelFlag());

            UserEntity loaded = mapper.selectById(user.getId());
            assertEquals("alice", loaded.getUserName());
            assertEquals(0, loaded.getDelFlag());
        }
    }

    @Test
    void batchInsertShouldPreFillDefaultsSoRowsVisible() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<UserEntity> list = Arrays.asList(newUser("a", 100L), newUser("b", 100L));
            mapper.batchInsert(list);
            // saveBatch 语义：全字段插入但 delFlag 等已预填，逻辑删除过滤后仍可见
            Map<String, Object> cm = new HashMap<>();
            cm.put("tenantId", 100L);
            List<UserEntity> loaded = mapper.selectListByMap(cm);
            assertEquals(2, loaded.size());
            for (UserEntity user : loaded) {
                assertEquals(0, user.getDelFlag());
            }
        }
    }

    // ==================== 逻辑删除 ====================

    @Test
    void deleteByIdShouldBeLogicalDeleteAndRecordTime() throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            UserEntity user = newUser("bob", 100L);
            mapper.insert(user);
            mapper.deleteById(user.getId());
            assertNull(mapper.selectById(user.getId()));
            // 数据仍在表中，且 update_time 被记录
            try (Connection conn = dataSource.getConnection(); Statement st = conn.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT del_flag, update_time FROM t_user WHERE id = " + user.getId());
                assertTrue(rs.next());
                assertEquals(1, rs.getInt(1));
                assertNotNull(rs.getTimestamp(2));
            }
        }
    }

    // ==================== fail-fast 与注入面 ====================

    @Test
    void mapConditionWithUnknownKeyShouldFailFast() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            Map<String, Object> cm = new HashMap<>();
            cm.put("out_trade_no", "SN123");
            Exception ex = assertThrows(Exception.class, () -> mapper.selectListByMap(cm));
            assertTrue(hasCause(ex, SXException.class));
        }
    }

    @Test
    void illegalColumnNameShouldBeRejected() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            Exception ex1 = assertThrows(Exception.class, () -> mapper.selectById(1L, "id; DROP TABLE t_user"));
            assertTrue(hasCause(ex1, SXException.class));
            QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("id = 1 OR 1=1; --", 1);
            assertThrows(SXException.class, wrapper::getWhereSqlFragment);
        }
    }

    // ==================== Wrapper ====================

    @Test
    void wrapperEqInLikeOrderByShouldWork() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.batchInsert(Arrays.asList(
                    newUser("carl", 100L), newUser("cathy", 100L), newUser("david", 200L)));

            QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("tenantId", 100L)
                    .likeRight("userName", "ca")
                    .in("userName", Arrays.asList("carl", "cathy", "nore"))
                    .orderByDesc("id");
            List<UserEntity> list = mapper.selectList(wrapper);
            assertEquals(2, list.size());
        }
    }

    @Test
    void wrapperSqlFragmentShouldContainDirectionalLike() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.likeLeft("userName", "abc");
        // % 在 Java 层拼入参数值，SQL 片段只含参数占位符
        assertTrue(wrapper.getWhereSqlFragment().contains("user_name LIKE"));
    }

    @Test
    void nestedAndOrShouldGenerateBracket() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("tenantId", 100L)
                .and(w -> w.eq("userName", "a").or().eq("userName", "b"));
        String sql = wrapper.getWhereSqlFragment();
        assertTrue(sql.contains("tenant_id ="));
        assertTrue(sql.contains("AND"));
        assertTrue(sql.contains("user_name ="));
        // 嵌套括号存在
        int open = sql.length() - sql.replace("(", "").length();
        int close = sql.length() - sql.replace(")", "").length();
        assertTrue(open >= 2 && close >= 2);
    }

    @Test
    void emptyWrapperWithOnlyAndShouldNotCrash() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.and();
        assertEquals("", wrapper.getWhereSqlFragment());
    }

    @Test
    void groupByHavingOrderByFragmentShouldNotDuplicateKeyword() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.groupBy("tenantId")
                .groupBy("userName")
                .having("tenant_id", 100L)
                .orderByAsc("id")
                .orderByDesc("userName");
        String group = wrapper.getGroupSqlFragment().trim();
        String having = wrapper.getHavingSqlFragment().trim();
        String order = wrapper.getOrderSqlFragment().trim();
        assertEquals("GROUP BY tenant_id,user_name", group.replace("  ", " ").trim());
        assertEquals(1, group.split("GROUP BY", -1).length - 1);
        assertTrue(having.startsWith("HAVING"));
        assertEquals(1, order.split("ORDER BY", -1).length - 1);
        assertTrue(order.contains("id ASC"));
        assertTrue(order.contains("user_name DESC"));
    }

    @Test
    void lastSqlShouldAppendAndRejectIllegal() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("tenantId", 100L).last("LIMIT 1");
        assertEquals(" LIMIT 1", wrapper.getLastSql());
        assertThrows(SXException.class, () -> wrapper.last("LIMIT 1; DROP TABLE t_user"));
    }

    @Test
    void lambdaWrapperShouldWork() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.batchInsert(Arrays.asList(newUser("lambda1", 100L), newUser("lambda2", 200L)));

            LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserEntity::getTenantId, 100L).select(UserEntity::getId, UserEntity::getUserName);
            List<UserEntity> list = mapper.selectListByLambda(wrapper);
            assertEquals(1, list.size());
            assertEquals("lambda1", list.get(0).getUserName());
        }
    }

    // ==================== 分页 ====================

    @Test
    void paginationShouldCountAndLimit() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            for (int i = 0; i < 25; i++) {
                mapper.insert(newUser("user" + i, 100L));
            }
            Map<String, Object> cm = new HashMap<>();
            cm.put("tenantId", 100L);
            cm.put("page", "2");
            cm.put("size", "10");
            IPage<UserEntity> page = new Page<>(new PageQueryOf().current(cm), new PageQueryOf().size(cm));
            List<UserEntity> records = mapper.selectListPageByMap(page, cm);
            page.setRecords(records);
            assertEquals(25, page.getTotal());
            assertEquals(10, page.getRecords().size());
        }
    }

    /** 简单解析 page/size 的辅助（模拟 PageQuery 行为，含键移除） */
    static class PageQueryOf {
        int current(Map<String, Object> cm) {
            return Integer.parseInt(String.valueOf(cm.remove("page")));
        }

        int size(Map<String, Object> cm) {
            return Integer.parseInt(String.valueOf(cm.remove("size")));
        }
    }

    @Test
    void paginationAllShouldReturnAllWithoutLimit() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            for (int i = 0; i < 15; i++) {
                mapper.insert(newUser("u" + i, 100L));
            }
            IPage<UserEntity> page = new Page<>(1, -1);
            Map<String, Object> cm = new HashMap<>();
            cm.put("tenantId", 100L);
            List<UserEntity> records = mapper.selectListPageByMap(page, cm);
            page.setRecords(records);
            assertEquals(15, page.getTotal());
            assertEquals(15, page.getRecords().size());
        }
    }

    // ==================== 多租户 ====================

    @Test
    void tenantShouldFilterSelectUpdateDelete() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.batchInsert(Arrays.asList(
                    newUser("t1-a", 1L), newUser("t1-b", 1L), newUser("t2-a", 2L)));

            // 租户1 只能看到自己的数据
            CURRENT_TENANT.set(1L);
            assertEquals(2, mapper.selectListByMap(null).size());
            assertEquals(2L, mapper.selectCountByMap(null));

            // 无租户上下文：全部可见
            CURRENT_TENANT.set(null);
            assertEquals(3, mapper.selectListByMap(null).size());

            // @TenantIgnore 逃生舱：全部可见
            CURRENT_TENANT.set(1L);
            assertEquals(3, mapper.selectAllIgnoreTenant().size());

            // 更新受租户限制：租户1 更新 t2 的行不生效
            int updated = mapper.update(new top.zsmile.common.mybatis.meta.conditions.update.UpdateWrapper<UserEntity>()
                    .set(true, "userName", "changed").eq("userName", "t2-a"));
            assertEquals(0, updated);

            // 逻辑删除同样受租户限制
            Map<String, Object> cm = new HashMap<>();
            cm.put("userName", "t2-a");
            mapper.deleteByMap(cm);
            CURRENT_TENANT.set(null);
            assertEquals(3, mapper.selectCountByMap(null));
        }
    }

    @Test
    void insertShouldFillTenantIdWhenProviderPresent() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            CURRENT_TENANT.set(9L);
            UserEntity user = newUser("tenant-fill", null);
            mapper.insert(user);
            assertEquals(9L, user.getTenantId());
        }
    }

    // ==================== 字段加密 ====================

    @Test
    void fieldEncryptShouldCipherOnWriteAndDecryptOnRead() throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            SecretMapper mapper = session.getMapper(SecretMapper.class);
            SecretEntity secret = new SecretEntity();
            secret.setSecretText("s3cret-plain-value");
            mapper.insert(secret);

            // 库中为密文
            try (Connection conn = dataSource.getConnection(); Statement st = conn.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT secret_text FROM t_secret WHERE id = " + secret.getId());
                assertTrue(rs.next());
                assertNotEquals("s3cret-plain-value", rs.getString(1));
            }
            // 读取自动解密
            SecretEntity loaded = mapper.selectById(secret.getId());
            assertEquals("s3cret-plain-value", loaded.getSecretText());
        }
    }

    // ==================== 无租户表不受影响 ====================

    @Test
    void plainTableShouldNotBeAffectedByTenant() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            PlainMapper mapper = session.getMapper(PlainMapper.class);
            CURRENT_TENANT.set(1L);
            PlainEntity entity = new PlainEntity();
            entity.setName("no-tenant");
            mapper.insert(entity);
            assertEquals(1, mapper.selectListByMap(Collections.singletonMap("name", "no-tenant")).size());
        }
    }
}

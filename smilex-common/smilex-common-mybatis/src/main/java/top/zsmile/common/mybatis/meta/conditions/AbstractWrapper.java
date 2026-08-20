package top.zsmile.common.mybatis.meta.conditions;

import org.apache.commons.lang3.StringUtils;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.fragment.ISqlFragment;
import top.zsmile.common.mybatis.meta.conditions.fragment.MergeSqlFragment;
import top.zsmile.common.mybatis.meta.conditions.interfaces.Compare;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static top.zsmile.common.mybatis.meta.conditions.SqlKeyword.*;

/**
 * @param <E>        注入的Entity实体
 * @param <R>        使用的COLUMN字段类型，主要为String和SFunction
 * @param <Children> 当前实体，this
 */
public abstract class AbstractWrapper<E, R, Children extends AbstractWrapper<E, R, Children>> extends Wrapper<E> implements Compare<Children, R> {

    /**
     * last SQL 合法字符白名单（LIMIT/参数占位等），拦截 SQL 注入
     */
    private static final java.util.regex.Pattern LAST_SQL_PATTERN =
            java.util.regex.Pattern.compile("^[A-Za-z0-9_,.\\s()%*?]+$");

    /**
     * 单独返回this时，需要强制转换
     * 直接用全局的引用来持有该this就可以了。
     */
    protected final Children _this = (Children) this;

    /**
     * 数据库表映射的实体类
     */
    private E entity;

    /**
     * 实体类型的class类，方便查找TableInfo缓存
     */
    private Class<E> entityClass;

    /**
     * 参数计算索引
     */
    AtomicInteger paramInt;
    /**
     * 参数存储Map
     */
    Map<String, Object> paramPairs;

    /**
     * 处理各类条件使用
     */
    MergeSqlFragment mergeSqlFragment;

    /**
     * 尾部原生 SQL（last）
     */
    SharedString lastSql = SharedString.of();

    /**
     * 初始化数据
     */
    protected void init() {
        paramInt = new AtomicInteger(0);
        paramPairs = new HashMap<>();
        mergeSqlFragment = new MergeSqlFragment();
    }

    /**
     * 创建同类型子 Wrapper（供 and(Consumer)/or(Consumer) 嵌套使用）
     */
    protected abstract Children newChildren();

    /**
     * 获取当前实体
     */
    public E getEntity() {
        return entity;
    }

    /**
     * 设置当前实体
     */
    public Children setEntity(E entity) {
        this.entity = entity;
        this.entityClass = (Class<E>) entity.getClass();
        return _this;
    }

    /**
     * 获取当前实体类型
     */
    public Class<E> getEntityClass() {
        return entityClass;
    }

    @Override
    public Children eq(R column, Object obj) {
        return addCondition(column, EQ, obj);
    }

    @Override
    public Children ne(R column, Object obj) {
        return addCondition(column, NE, obj);
    }

    @Override
    public Children gt(R column, Object obj) {
        return addCondition(column, GT, obj);
    }

    @Override
    public Children lt(R column, Object obj) {
        return addCondition(column, LT, obj);
    }

    @Override
    public Children ge(R column, Object obj) {
        return addCondition(column, GE, obj);
    }

    @Override
    public Children le(R column, Object obj) {
        return addCondition(column, LE, obj);
    }

    @Override
    public Children between(R column, Object left, Object right) {
        return doThing(BETWEEN, () -> columnToString(column), () -> formatValue(StringPool.ZERO_INDEX, left),
                AND, () -> formatValue(StringPool.ZERO_INDEX, right));
    }

    @Override
    public Children notBetween(R column, Object left, Object right) {
        return doThing(NOT_BETWEEN, () -> columnToString(column), () -> formatValue(StringPool.ZERO_INDEX, left),
                AND, () -> formatValue(StringPool.ZERO_INDEX, right));
    }

    @Override
    public Children like(R column, Object obj) {
        return likeCondition(LIKE, column, obj, true, true);
    }

    @Override
    public Children likeLeft(R column, Object obj) {
        return likeCondition(LIKE, column, obj, true, false);
    }

    @Override
    public Children likeRight(R column, Object obj) {
        return likeCondition(LIKE, column, obj, false, true);
    }

    @Override
    public Children notLike(R column, Object obj) {
        return likeCondition(NOT_LIKE, column, obj, true, true);
    }

    @Override
    public Children notLikeLeft(R column, Object obj) {
        return likeCondition(NOT_LIKE, column, obj, true, false);
    }

    @Override
    public Children notLikeRight(R column, Object obj) {
        return likeCondition(NOT_LIKE, column, obj, false, true);
    }

    @Override
    public Children isNull(R column) {
        return doThing(IS_NULL, () -> columnToString(column));
    }

    @Override
    public Children isNotNull(R column) {
        return doThing(IS_NOT_NULL, () -> columnToString(column));
    }

    @Override
    public Children in(R column, Collection<?> values) {
        if (values == null || values.isEmpty()) {
            // 空集合 IN 恒假，避免生成 IN () 语法错误
            return doThing(() -> "1 = 2");
        }
        return doThing(() -> columnToString(column), IN, inExpression(values));
    }

    @Override
    public Children in(R column, Object... values) {
        return in(column, Arrays.asList(values));
    }

    @Override
    public Children notIn(R column, Collection<?> values) {
        if (values == null || values.isEmpty()) {
            // NOT IN 空集合恒真，等价于无该条件
            return _this;
        }
        return doThing(() -> columnToString(column), NOT_IN, inExpression(values));
    }

    @Override
    public Children notIn(R column, Object... values) {
        return notIn(column, Arrays.asList(values));
    }

    @Override
    public Children groupBy(R... column) {
        return doThing(GROUP_BY, () -> columnToString(column));
    }

    @Override
    public Children orderBy(boolean isAsc, R... column) {
        return doThing(ORDER_BY, () -> columnToString(column) + StringPool.SPACE + (isAsc ? ASC : DESC));
    }

    @Override
    public Children orderByAsc(R... column) {
        return orderBy(true, column);
    }

    @Override
    public Children orderByDesc(R... column) {
        return orderBy(false, column);
    }

    @Override
    public Children having(R column, Object obj) {
        return doThing(HAVING, () -> columnToString(column) + StringPool.SPACE
                + EQ + StringPool.SPACE + formatValue(StringPool.ZERO_INDEX, obj));
    }

    @Override
    public Children and() {
        return doThing(AND);
    }

    @Override
    public Children or() {
        return doThing(OR);
    }

    @Override
    public Children and(Consumer<Children> consumer) {
        return nested(true, consumer);
    }

    @Override
    public Children or(Consumer<Children> consumer) {
        return nested(false, consumer);
    }

    @Override
    public Children last(String lastSql) {
        if (StringUtils.isBlank(lastSql)) {
            return _this;
        }
        String trimmed = lastSql.trim();
        if (!LAST_SQL_PATTERN.matcher(trimmed).matches()) {
            throw new SXException("last() 仅允许字母数字及 _ , . ( ) % * ? 等安全字符，防止 SQL 注入");
        }
        this.lastSql.setValue(StringPool.SPACE + trimmed);
        return _this;
    }

    /**
     * 嵌套条件：子 wrapper 的 WHERE 片段以 "( ... )" 加入，
     * 首个条件不带连接词，其后自带 " AND ("/" OR (" 前缀（与 NormalSqlFragment 尾部连接词语义配合）
     */
    protected Children nested(boolean isAnd, Consumer<Children> consumer) {
        Children child = newChildren();
        consumer.accept(child);
        String nestedSql = child.getWhereSqlFragment();
        if (StringUtils.isBlank(nestedSql)) {
            return _this;
        }
        boolean hasCondition = !mergeSqlFragment.isNormalEmpty();
        String prefix = hasCondition ? (StringPool.SPACE + (isAnd ? AND : OR) + StringPool.SPACE) : StringPool.EMPTY;
        return doThing((ISqlFragment) () -> prefix + nestedSql);
    }

    /**
     * like 条件：百分号在 Java 层拼入参数值（方言无关、参数化安全）
     */
    private Children likeCondition(SqlKeyword keyword, R column, Object obj, boolean leftPercent, boolean rightPercent) {
        Object wrapped = (leftPercent ? "%" : "") + obj + (rightPercent ? "%" : "");
        return doThing(() -> columnToString(column), keyword, () -> formatValue(StringPool.ZERO_INDEX, wrapped));
    }

    protected Children addCondition(R column, SqlKeyword sqlKeyword, Object obj) {
        return doThing(() -> columnToString(column), sqlKeyword, () -> formatValue(StringPool.ZERO_INDEX, obj));
    }

    protected Children doThing(ISqlFragment... iSqlFragments) {
        mergeSqlFragment.add(iSqlFragments);
        return _this;
    }

    protected String formatValue(String paramStr, Object... objects) {
        if (StringUtils.isBlank(paramStr)) {
            return null;
        }
        for (int i = 0; i < objects.length; i++) {
            String paramIndex = StringPool.WRAPPER_PARAM + paramInt.getAndIncrement();
            paramStr = paramStr.replace(String.format("{%s}", i), String.format(StringPool.WRAPPER_PARAM_FORMAT, StringPool.WRAPPER, paramIndex));
            paramPairs.put(paramIndex, objects[i]);
        }
        return paramStr;
    }

    /**
     * 字段转化String类型：接受驼峰属性名或下划线列名，统一转为下划线列名（格式白名单校验防注入）
     */
    protected String columnToString(R column) {
        return TableQueryUtils.humpToLineName(TableQueryUtils.checkSqlName((String) column));
    }

    /**
     * 字段列表转化为String，并使用逗号分割
     */
    protected String columnToString(R... column) {
        return Arrays.asList(column).stream().map(this::columnToString).collect(Collectors.joining(StringPool.COMMA));
    }

    /**
     * 获取in表达式 包含括号
     *
     * @param value 集合
     */
    private ISqlFragment inExpression(Collection<?> value) {
        return () -> value.stream().map(i -> formatValue("{0}", i))
                .collect(Collectors.joining(StringPool.COMMA, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
    }

    @Override
    public String getSqlFragment() {
        return mergeSqlFragment.getSqlFragment();
    }

    @Override
    public String getWhereSqlFragment() {
        return mergeSqlFragment.getNormalSql();
    }

    @Override
    public String getGroupSqlFragment() {
        return mergeSqlFragment.getGroupSql();
    }

    @Override
    public String getOrderSqlFragment() {
        return mergeSqlFragment.getOrderSql();
    }

    @Override
    public String getHavingSqlFragment() {
        return mergeSqlFragment.getHavingSql();
    }

    public String getLastSql() {
        return lastSql.getValue();
    }
}

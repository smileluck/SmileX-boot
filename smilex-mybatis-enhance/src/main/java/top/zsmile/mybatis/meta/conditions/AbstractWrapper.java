package top.zsmile.mybatis.meta.conditions;

import org.apache.commons.lang3.StringUtils;
import top.zsmile.mybatis.meta.StringPool;
import top.zsmile.mybatis.meta.conditions.fragment.ISqlFragment;
import top.zsmile.mybatis.meta.conditions.fragment.MergeSqlFragment;
import top.zsmile.mybatis.meta.conditions.interfaces.Compare;
import top.zsmile.mybatis.meta.conditions.query.Query;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static top.zsmile.mybatis.meta.conditions.SqlKeyword.*;

/**
 * @param <E>        注入的Entity实体
 * @param <R>        使用的COLUMN字段类型，主要为String和SFunciton
 * @param <Children> 当前实体，this
 */
public abstract class AbstractWrapper<E, R, Children extends AbstractWrapper<E, R, Children>> extends Wrapper<E> implements Compare<Children, R> {


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
     * 初始化数据
     */
    protected void init() {
        paramInt = new AtomicInteger(0);
        paramPairs = new HashMap<>();
        mergeSqlFragment = new MergeSqlFragment();
    }

    /**
     * 获取当前实体
     *
     * @return
     */
    public E getEntity() {
        return entity;
    }

    /**
     * 设置当前实体
     *
     * @param entity 实体
     * @return
     */
    public Children setEntity(E entity) {
        this.entity = entity;
        this.entityClass = (Class<E>) entity.getClass();
        return _this;
    }

    /**
     * 获取当前实体类型
     *
     * @return 实体类
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
        return doThing(LIKE, () -> columnToString(column), () -> formatValue(StringPool.ZERO_INDEX, obj));
    }

    @Override
    public Children likeLeft(R column, Object obj) {
        return doThing(LIKE, () -> columnToString(column), () -> formatValue(StringPool.ZERO_INDEX, obj));
    }

    @Override
    public Children likeRight(R column, Object obj) {
        return doThing(LIKE, () -> columnToString(column), () -> formatValue(StringPool.ZERO_INDEX, obj));
    }

    @Override
    public Children notLike(R column, Object obj) {
        return doThing(NOT_LIKE, () -> columnToString(column), () -> formatValue(StringPool.ZERO_INDEX, obj));
    }

    @Override
    public Children notLikeLeft(R column, Object obj) {
        return doThing(NOT_LIKE, () -> columnToString(column), () -> formatValue(StringPool.ZERO_INDEX, obj));
    }

    @Override
    public Children notLikeRight(R column, Object obj) {
        return doThing(NOT_LIKE, () -> columnToString(column), () -> formatValue(StringPool.ZERO_INDEX, obj));
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
        return doThing(IN, () -> columnToString(column), inExpression(values));
    }

    @Override
    public Children in(R column, Object... values) {
        return doThing(IN, () -> columnToString(column), inExpression(Arrays.asList(values)));
    }

    @Override
    public Children notIn(R column, Collection<?> values) {
        return doThing(NOT_IN, () -> columnToString(column), inExpression(values));
    }

    @Override
    public Children notIn(R column, Object... values) {
        return doThing(NOT_IN, () -> columnToString(column), inExpression(Arrays.asList(values)));
    }

    @Override
    public Children groupBy(R... column) {
        return doThing(GROUP_BY, () -> columnToString(column));
    }

    @Override
    public Children orderBy(boolean isAsc, R... column) {
        return doThing(ORDER_BY, () -> columnToString(column), isAsc ? ASC : DESC);
    }

    @Override
    public Children orderByAsc(R... column) {
        return doThing(ORDER_BY, () -> columnToString(column), ASC);
    }

    @Override
    public Children orderByDesc(R... column) {
        return doThing(ORDER_BY, () -> columnToString(column), DESC);
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
     * 字段转化String类型
     *
     * @param column
     * @return
     */
    protected String columnToString(R column) {
        return (String) column;
    }

    /**
     * 字段列表转化为String，并使用逗号分割
     *
     * @param column
     * @return
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
                .collect(joining(StringPool.COMMA, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
    }

    @Override
    public String getSqlFragment() {
        return mergeSqlFragment.getSqlFragment();
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
}

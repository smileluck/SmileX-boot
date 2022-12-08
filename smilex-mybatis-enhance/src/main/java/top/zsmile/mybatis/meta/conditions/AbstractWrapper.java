package top.zsmile.mybatis.meta.conditions;

import top.zsmile.mybatis.meta.StringPool;

/**
 * @param <E>        注入的Entity实体
 * @param <R>        使用的COLUMN字段类型，主要为String和SFunciton
 * @param <Children> 当前实体，this
 */
public abstract class AbstractWrapper<E, R, Children extends AbstractWrapper<E, R, Children>> implements StringPool {


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

    public E getEntity() {
        return entity;
    }

    public Children setEntity(E entity) {
        this.entity = entity;
        this.entityClass = (Class<E>) entity.getClass();
        return _this;
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }
}

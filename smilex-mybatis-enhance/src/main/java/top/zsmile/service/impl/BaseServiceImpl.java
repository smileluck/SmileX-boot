package top.zsmile.service.impl;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.common.utils.SnowFlake;
import top.zsmile.dao.BaseMapper;
import top.zsmile.service.BaseService;
import top.zsmile.utils.SqlHelper;

import java.lang.reflect.Field;
import java.util.Collection;

public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    protected Logger log = LoggerFactory.getLogger(getClass());

    private SnowFlake snowFlake = new SnowFlake(0, 0);

    @Autowired
    protected M baseMapper;

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    public SnowFlake getSnowFlake() {
        return snowFlake;
    }

    /**
     * TODO 批量新增
     *
     * @param collection
     * @param size
     * @return
     */
    @Transactional
    @Override
    public boolean saveBatch(Collection<? extends T> collection, int size) {
        return false;
    }


    @Override
    public boolean save(T entity) {
        setDefaultIdValue(entity);
        return SqlHelper.retBool(getBaseMapper().insert(entity));
    }

    /**
     * 设置雪花ID
     *
     * @param t
     */
    private void setDefaultIdValue(T t) {
        try {
            Field field = FieldUtils.getField(t.getClass(), "id", true);
            if (null != field) {
                Object id = FieldUtils.readField(t, "id", true);
                if (null == id || "0".equals(String.valueOf(id))) {
                    FieldUtils.writeField(t, "id", this.getSnowFlake().nextId(), true);
                }
            }

        } catch (IllegalAccessException var4) {
            throw new IllegalArgumentException(var4.getMessage());
        }
    }
}

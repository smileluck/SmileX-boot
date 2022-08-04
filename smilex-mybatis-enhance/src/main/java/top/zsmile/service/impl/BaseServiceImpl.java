package top.zsmile.service.impl;

import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    protected Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    protected M baseMapper;

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }


    /**
     * 批量新增
     *
     * @param collection
     * @param size
     * @return
     */
    @Transactional
    @Override
    public boolean saveBatch(Collection<? extends T> collection, int size) {
        int skip = 0;
        int updateCount = 0;
        while (skip < collection.size()) {
            List<? extends T> collect = collection.stream().skip(skip).limit(size).collect(Collectors.toList());
            updateCount += getBaseMapper().batchInsert(collect);
            skip += size;
        }
        return SqlHelper.retBool(updateCount);
    }


    @Override
    public boolean save(T entity) {
//        setDefaultIdValue(entity);
        return SqlHelper.retBool(getBaseMapper().insert(entity));
    }

}

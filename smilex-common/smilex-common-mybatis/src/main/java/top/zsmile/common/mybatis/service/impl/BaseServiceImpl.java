package top.zsmile.common.mybatis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.utils.SqlHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected M baseMapper;

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    /**
     * 批量新增（subList 分批，避免 stream skip/limit 的重复遍历）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<? extends T> collection, int size) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        List<? extends T> list = collection instanceof List ? (List<? extends T>) collection : new ArrayList<>(collection);
        int batchSize = Math.max(size, 1);
        int updateCount = 0;
        for (int start = 0; start < list.size(); start += batchSize) {
            int end = Math.min(start + batchSize, list.size());
            updateCount += getBaseMapper().batchInsert(list.subList(start, end));
        }
        return SqlHelper.retBool(updateCount);
    }

    @Override
    public boolean save(T entity) {
        return SqlHelper.retBool(getBaseMapper().insert(entity));
    }
}

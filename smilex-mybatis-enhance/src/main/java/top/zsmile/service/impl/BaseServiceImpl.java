package top.zsmile.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.zsmile.dao.BaseMapper;
import top.zsmile.service.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected M baseDao;

    @Override
    public M getBaseMapper() {
        return baseDao;
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

}

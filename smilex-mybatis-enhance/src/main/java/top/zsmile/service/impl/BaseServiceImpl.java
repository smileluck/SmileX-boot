package top.zsmile.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import top.zsmile.dao.BaseMapper;
import top.zsmile.service.BaseService;

import java.io.Serializable;
import java.util.Collection;

public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected M baseDao;

    @Override
    public M getBaseMapper() {
        return baseDao;
    }

    @Override
    public boolean saveBatch(Collection<? extends T> collection, int size) {
        return false;
    }

}

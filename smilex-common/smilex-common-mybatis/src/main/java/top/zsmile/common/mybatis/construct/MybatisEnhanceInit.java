package top.zsmile.common.mybatis.construct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.zsmile.common.mybatis.cache.TableInfoCache;

import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.common.web.utils.SpringFileUtils;

import java.util.Map;

@Slf4j
@Component
public class MybatisEnhanceInit implements InitializingBean {

    // TODO 后续做成start时，可以优化成配置项。
    private String packageStr = "top/zsmile/modules/**/*.class";

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("===========start mybatis-enhance init===============");
        Map<String, Class> classByAnnotation = SpringFileUtils.getClassBySuperClass(packageStr, BaseMapper.class);

        int size = classByAnnotation.size();
        log.debug("mybatis-enhance size ==> " + size);

        TableInfoCache.initTableInfo(classByAnnotation);

        log.debug("===========over mybatis-enhance init===============");
    }
}

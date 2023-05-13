package top.zsmile.common.mybatis.construct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.cache.TableInfoCache;
import top.zsmile.common.core.utils.file.SpringFileUtils;
import top.zsmile.common.mybatis.dao.BaseMapper;

import java.util.Map;

@Slf4j
@Component
public class MybatisEnhanceInit implements InitializingBean {

    // TODO 后续做成start时，可以优化成配置项。
    private String packageStr = "top/zsmile/modules/**/*.class";

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("===========start mybatis-enhance init===============");
        Map<String, Class> classByAnnotation = SpringFileUtils.getClassBySuperClass(packageStr, BaseMapper.class);

        int size = classByAnnotation.size();
        log.info("mybatis-enhance size ==> " + size);

        TableInfoCache.initTableInfo(classByAnnotation);

        log.info("===========over mybatis-enhance init===============");
    }
}

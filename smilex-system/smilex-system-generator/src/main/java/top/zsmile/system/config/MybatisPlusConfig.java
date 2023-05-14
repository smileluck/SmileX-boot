package top.zsmile.system.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = {"top.zsmile.system.modules.**.dao"})
public class MybatisPlusConfig {

}

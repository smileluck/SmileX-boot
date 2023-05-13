package top.zsmile.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = {"top.zsmile.modules.**.dao"})
public class MybatisPlusConfig {

}

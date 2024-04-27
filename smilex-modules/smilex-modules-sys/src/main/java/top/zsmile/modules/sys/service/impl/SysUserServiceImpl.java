package top.zsmile.modules.sys.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.common.redis.utils.RedisUtils;
import top.zsmile.common.web.utils.JwtUtils;
import top.zsmile.common.core.utils.PasswordUtils;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.modules.sys.dao.SysUserMapper;
import top.zsmile.modules.sys.entity.SysUserEntity;
import top.zsmile.modules.sys.model.SysLoginModel;
import top.zsmile.modules.sys.service.SysUserService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    @Transactional
    public boolean saveUser(SysUserEntity sysUserEntity) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUserEntity.setSalt(salt);
        sysUserEntity.setPassword(PasswordUtils.sha256Hash(sysUserEntity.getPassword() + sysUserEntity.getSalt()));
        return save(sysUserEntity);
    }

    @Override
    @Transactional
    public String login(SysLoginModel sysLoginModel) {
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();

        Map<String, Object> map = Collections.singletonMap("username", username);
        SysUserEntity sysUserEntity = getObjByMap(map, "password", "salt", "enableFlag");
        if (sysUserEntity == null) {
            throw new SXException("请输入正确的账号密码");
        }
        if (sysUserEntity.getEnableFlag().equals(0)) {
            throw new SXException("账号已被锁定，请联系管理员");
        }
        String encryptPwd = PasswordUtils.sha256Hash(password + sysUserEntity.getSalt());

        if (!encryptPwd.equals(sysUserEntity.getPassword())) {
            throw new SXException("请输入正确的账号密码");
        }

        log.info(sysUserEntity.getUsername() + "-用户登陆成功");
        String token = JwtUtils.generatorToken(sysUserEntity.getId(), sysUserEntity.getPassword());

        // TODO 添加缓存

        return token;
    }
}
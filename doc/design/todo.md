[toc]

---



# todo-list

> 从2022年7月12日开始，用来记录具体的开发事项。


- [ ] 代码生成器

  - [x] 添加API注解
  - [x] 添加下载zip包功能
  - [x] 增加字段过滤器，生成时忽略某些字段(password,salt)等，并不让他显示在Knife文档,默认(password，salt)
  - [ ] 面板管理
    - [x] 已有面板UI
    - [x] 配置数据源
    - [ ] 字段过滤
    - [ ] 选择生成方式。字典、文本框、日期选择器、下拉选择等。是否必填。
    - [ ] 选择校验规则。

- [ ] mybatis-enhance。
  - [ ] 增加分页拦截器
  - [ ] 增加多表查询支持。
  - [ ] 数据脱敏拦截器
- [ ] 微信公众号接入
- [ ] 多租户插件
    - 技术选型
        - [ ] 基于 ShardingSphere实现
        - [ ] 自实现
    - 三种实现方式兼容
        - [ ] 共享库共享表，字段区分
        - [ ] 共享库，独立表区分
        - [ ] 独立库区分
- [ ] 多语言管理
- [ ] 可视化报表
- [ ] 搜索引擎
  - ElasticSearch
- [ ] 动态编译
    - [ ] 动态添加接口和数据库查询等
    - [ ] 模块化
    - 参考
        - https://www.jianshu.com/p/32a1e01070d7
        - https://blog.csdn.net/weixin_39996256/article/details/90750318
        - https://blog.csdn.net/qq_39149275/article/details/118968520
        - https://www.jianshu.com/p/32a1e01070d7
- [x] 支付开发库
    - [x] 微信支付
        - [x] v2版本
            - H5支付
            - 小程序支付
            - APP支付
            - 微信扫码支付
        - [x] v3版本
            - 官方有提供包
    - [x] 支付宝支付
        - 官方有提供包
   
--- 
> 桌面端应用 SmileX-Boot
- [ ] markdown
- [ ] 浏览器功能
    - [ ] 书签
        - 功能
            - [ ] 书签分类
            - [ ] 分类管理
            - [ ] 书签管理
        - 存储
            - [ ] 本地存储
            - [ ] 云端存储
    - [ ] tab 分栏目
- [ ] WebAssembly

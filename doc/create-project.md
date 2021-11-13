[toc]

---

# 创建项目（多模块）

![image-20210906155249488](\image-20210906155249488.png)

创建项目后，删除项目下的src。再一次创建maven模块

![image-20210906155353320](image-20210906155353320.png)

我这里将项目分成了4个模块创建：

- api，用于存放service、entity和dao
- auth，用于存放身份验证和授权代码，方便后面独立扩展网关
- common，用于存放一下通用库和配置
- boot，用于存放单机springboot的启动类及controller层




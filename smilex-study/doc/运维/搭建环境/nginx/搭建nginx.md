[toc]

----

# ubuntu 18.04

## 安装步骤
### 安装并解压nginx

1. 通过wget下载nginx，通过[下载地址](http://nginx.org/download )可以查看对应的版本

```shell
wget http://nginx.org/download/nginx-1.21.6.tar.gz
```

2.  解压源码压缩包到 **/usr/local/** 目录下 

```shell
tar -xvf nginx-1.21.6.tar.gz -C /usr/local/
```

### 安装nginx依赖

1. nginx依赖**PCRE**（[Perl Compatible Regular Expressions](http://www.pcre.org/)）库，所以需要先安装PCRE库。

```shell
apt-get install libpcre3 libpcre3-dev
```

2. 检查pcre是否安装成功。如果打印版本，则安装完成。

```shell
pcre-config --version
```

### 编译安装nginx

1. 进入nginx的解压目录

```shell
cd /usr/local/nginx-1.21.6
```

2. 配置编译和安装选项

```shell
./configure
```

3. 编译和安装。**如果没有在上一步配置nginx的目录的话，默认安装在 /usr/local/nginx 下**。

```shell
make && make install
```

## 启动nginx

1. 查看文件目录信息

```shell
ls -lh /usr/local/nginx/sbin/
```

### 添加环境变量（可选）
1. 进入 /etc/profile.d/ 目录，新建export_user.sh

```
cd /etc/profile.d
vim export_user.sh
```

2. export_user.sh输入如下内容。

```sh
#!/bin/bash

export PATH=$PATH:/usr/local/nginx/sbin/
```

3. 保存export_user.sh并添加可执行权限

```shell
chmod +x export_user.sh
```

4. 加载文件，令变量生效。

```shell
source /etc/profile
# 或者，二者等效
/etc/profile.d/export_path.sh
```

5. 检查nginx的环境变量是否生效。输出版本即成功

```shell
nginx -v
```


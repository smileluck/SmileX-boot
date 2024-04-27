package top.zsmile.modules.rpc.remote;

public interface Server<T> {

    /**
     * 启动服务
     *
     * @param port 端口
     * @return T
     */
    T start(int port);


    /**
     * 获取服务实例
     *
     * @return T
     */
    T get();

}

package top.zsmile.modules.rpc.remote.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zsmile.modules.rpc.remote.Server;

import java.net.ServerSocket;

public class SocketServer implements Server<ServerSocket> {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private static ServerSocket serverSocket = null;

    @Override
    public ServerSocket start(int port) {
//        try (serverSocket =new ServerSocket(port)){
//
//        }
        return null;
    }

    @Override
    public ServerSocket get() {
        return null;
    }
}

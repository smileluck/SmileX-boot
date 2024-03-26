package top.zsmile.tool.wechat.mp.handler;

public interface IMessageRule<T>{
    boolean matches(T object);
}

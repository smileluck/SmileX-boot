package top.zsmile.tool.wechat.mp.utils.xml;

import top.zsmile.tool.wechat.mp.bean.message.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDomDriver;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.*;

public class XStreamTransformer {
    private static final Map<Class<?>, XStream> CLASS_X_STREAM_MAP = new HashMap<>();

    static {
        registerClass(WechatMpInMessage.class);
        registerClass(WechatMpOutTextMessage.class);
        registerClass(WechatMpOutImageMessage.class);
        registerClass(WechatMpOutMusicMessage.class);
        registerClass(WechatMpOutNewsMessage.class);
        registerClass(WechatMpOutVideoMessage.class);
        registerClass(WechatMpOutVoiceMessage.class);
    }

    /**
     * xml -> pojo.
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXML(Class<T> clazz, String xml) {
        return (T) CLASS_X_STREAM_MAP.get(clazz).fromXML(xml);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromXML(Class<T> clazz, InputStream is) {
        return (T) CLASS_X_STREAM_MAP.get(clazz).fromXML(is);
    }

    /**
     * pojo -> xml
     *
     * @param clazz  Class类型
     * @param object 对象
     * @return XML字符串
     */
    public static <T> String toXML(Class<T> clazz, T object) {
        String xml = CLASS_X_STREAM_MAP.get(clazz).toXML(object);
        return StringUtils.replaceEach(xml, new String[]{"&lt;", "&quot;", "&apos;", "&gt;"}, new String[]{"<", "\"", "'", ">"});
    }

    public static <T extends WechatMpOutMessage> String toXML(Class<? extends WechatMpOutMessage> clazz, T obj) {
        String xml = CLASS_X_STREAM_MAP.get(clazz).toXML(obj);
        return StringUtils.replaceEach(xml, new String[]{"&lt;", "&quot;", "&apos;", "&gt;"}, new String[]{"<", "\"", "'", ">"});
    }

    /**
     * 注册扩展消息的解析器.
     *
     * @param clazz   类型
     * @param xStream xml解析器
     */
    public static void register(Class<?> clazz, XStream xStream) {
        CLASS_X_STREAM_MAP.put(clazz, xStream);
    }

    /**
     * 会自动注册该类及其子类.
     *
     * @param clz 要注册的类
     */
    private static void registerClass(Class<?> clz) {
        XppDomDriver driver = new XppDomDriver();

        XStream xStream = new XStream(driver);
        xStream.allowTypes(new Class[]{WechatMpInMessage.class});
        xStream.ignoreUnknownElements(); // 允许字段不存在
        xStream.processAnnotations(clz);
        xStream.processAnnotations(getInnerClasses(clz));
        xStream.setClassLoader(clz.getClassLoader());

        if (clz.getClass().isAssignableFrom(WechatMpInMessage.class)) {
            xStream.aliasField("MsgID", WechatMpInMessage.class, "msgId");
        }

        register(clz, xStream);
    }

    private static Class<?>[] getInnerClasses(Class<?> clz) {
        Class<?>[] innerClasses = clz.getClasses();
        if (innerClasses == null) {
            return null;
        }

        List<Class<?>> result = new ArrayList<>();
        result.addAll(Arrays.asList(innerClasses));
        for (Class<?> inner : innerClasses) {
            Class<?>[] innerClz = getInnerClasses(inner);
            if (innerClz == null) {
                continue;
            }

            result.addAll(Arrays.asList(innerClz));
        }

        return result.toArray(new Class<?>[0]);
    }

}

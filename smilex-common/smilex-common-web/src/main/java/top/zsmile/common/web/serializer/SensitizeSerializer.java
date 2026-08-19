package top.zsmile.common.web.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import top.zsmile.common.web.enums.SensitizeType;
import top.zsmile.common.web.utils.SensitizeUtils;

import java.io.IOException;

/**
 * 脱敏序列化
 */
public class SensitizeSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏类型，默认为DEFAULT
     */
    private SensitizeType type;
    /**
     * 脱敏起始位置
     */
    private int startInclude;
    /**
     * 脱敏结束位置
     */
    private int endExclude;

    public SensitizeSerializer() {
        this.type = SensitizeType.DEFAULT;
    }

    public SensitizeSerializer(SensitizeType type) {
        this.type = type;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        switch (type) {
            case PHONE:
                jsonGenerator.writeString(SensitizeUtils.desensitizePhone(value));
                break;
            default:
                jsonGenerator.writeString(value);
                break;
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        return null;
    }
}

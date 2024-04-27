package top.zsmile.tool.wechat.mp.converter;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


/**
 * MediaID转换
 * Image  转换   <Image><MediaId><![CDATA[media_id]]></MediaId></Image>
 */
public class XStreamMediaIdConverter extends XStreamCDataConverter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        String newStr = String.format("<MediaId><![CDATA[%s]]></MediaId>", o.toString());
        hierarchicalStreamWriter.setValue(newStr);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        String value = hierarchicalStreamReader.getValue();
        return value;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return String.class.equals(aClass);
    }

}

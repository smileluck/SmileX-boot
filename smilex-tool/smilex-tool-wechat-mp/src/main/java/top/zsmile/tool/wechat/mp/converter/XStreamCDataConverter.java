package top.zsmile.tool.wechat.mp.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.io.IOException;
import java.io.StringReader;

/**
 * XStreamCData转换
 * String  转换   <![CDATA[String]]>
 */
public class XStreamCDataConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        String newStr = String.format("<![CDATA[%s]]>", o.toString());
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

    private String unmarshalCDATA(String value) {
        XmlFactory xmlFactory = null;
        JsonParser jsonParser = null;
        try {
            StringBuilder sb = new StringBuilder();
            xmlFactory = new XmlFactory();
            jsonParser = xmlFactory.createParser(new StringReader(value));
            while (jsonParser.nextToken() != null) {
                if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
                    sb.append(jsonParser.getText());
                }
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (jsonParser != null) {
                    jsonParser.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

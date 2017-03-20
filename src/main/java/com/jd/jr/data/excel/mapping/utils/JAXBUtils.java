package com.jd.jr.data.excel.mapping.utils;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.bind.*;
import javax.xml.transform.sax.SAXSource;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/9 20:13
 * *****************************************
 */
public class JAXBUtils {
    private static Map<Class,JAXBContext> contexts = new HashMap<Class,JAXBContext>();

    public static String toXML(Object obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息

            StringWriter out = new StringWriter();
            OutputFormat format = new OutputFormat();
            format.setIndent(true);
            format.setNewlines(true);
            format.setNewLineAfterDeclaration(false);
            XMLWriter writer = new XMLWriter(out, format);
            XMLFilterImpl nsfFilter = new JaxbMarshalFilter();
            nsfFilter.setContentHandler(writer);
            marshaller.marshal(obj, nsfFilter);
            return out.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromXML(InputStream inputStream,Class<T> clazz) throws SAXException, JAXBException {

            JAXBContext context = getContext(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            XMLReader reader = XMLReaderFactory.createXMLReader();

            XMLFilterImpl nsfFilter = new JaxbUnmarshalFilter();

            nsfFilter.setParent(reader);
            InputSource input = new InputSource(inputStream);
            SAXSource source = new SAXSource(nsfFilter, input);
            JAXBElement<T> jaxbElement = unmarshaller.unmarshal(source,clazz);
            return jaxbElement.getValue() ;

    }
    public static <T> T fromXML(String xml, Class<T> clazz) throws SAXException, JAXBException {

        JAXBContext context = getContext(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        XMLReader reader = XMLReaderFactory.createXMLReader();
        XMLFilterImpl nsfFilter = new JaxbUnmarshalFilter();
        nsfFilter.setParent(reader);
        InputSource input = new InputSource(new StringReader(xml));
        SAXSource source = new SAXSource(nsfFilter, input);
        return (T) unmarshaller.unmarshal(source,clazz);

    }
    private static JAXBContext getContext(Class clazz){
        JAXBContext context = contexts.get(clazz);
        if (context != null){
            return context;
        }else {
            try {
                context = JAXBContext.newInstance(clazz);
                contexts.put(clazz,context);
                return context;
            } catch (JAXBException e) {
                e.printStackTrace();
                return null;
            }

        }
    }
    static class JaxbUnmarshalFilter extends XMLFilterImpl{
        private boolean ignoreNamespace = false;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            if (this.ignoreNamespace) uri = "";
            super.startElement(uri, localName, qName, atts);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (this.ignoreNamespace) uri = "";
            super.endElement(uri, localName, localName);
        }

        @Override
        public void startPrefixMapping(String prefix, String url) throws SAXException {
            if (!this.ignoreNamespace) super.startPrefixMapping("", url);
        }
    }
    static class JaxbMarshalFilter extends XMLFilterImpl {
        private boolean ignoreNamespace = false;
        private String rootNamespace = null;
        private boolean isRootElement = true;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            if (this.ignoreNamespace) uri = "";
            if (this.isRootElement) this.isRootElement = false;
            else if (!uri.equals("") && !localName.contains("xmlns")) localName = localName + " xmlns=\"" + uri + "\"";

            super.startElement(uri, localName, localName, atts);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (this.ignoreNamespace) uri = "";
            super.endElement(uri, localName, localName);
        }

        @Override
        public void startPrefixMapping(String prefix, String url) throws SAXException {
            if (this.rootNamespace != null) url = this.rootNamespace;
            if (!this.ignoreNamespace) super.startPrefixMapping("", url);

        }
    }

}

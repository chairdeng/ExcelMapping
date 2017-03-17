package com.jd.jr.data.excel.mapping.example;

import com.jd.jr.data.excel.mapping.SheetMapping;
import com.jd.jr.data.excel.mapping.SheetMappingHandler;
import com.jd.jr.data.excel.mapping.definition.ExcelMappingDefinition;
import com.jd.jr.data.excel.mapping.definition.SheetDefinition;
import com.jd.loan.dist.erp.domain.OrderSkuDetail;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.text.MessageFormat;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/9 18:44
 * *****************************************
 */
public class JaxbReadXml {

    @SuppressWarnings("unchecked")
    public static <T> T readString(Class<T> clazz, String context) throws JAXBException {
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller u = jc.createUnmarshaller();
            return (T) u.unmarshal(new File(context));
        } catch (JAXBException e) {
            // logger.trace(e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readConfig(Class<T> clazz, String config, Object... arguments) throws IOException,
            JAXBException {
        InputStream is = null;
        try {
            if (arguments.length > 0) {
                config = MessageFormat.format(config, arguments);
            }
            // logger.trace("read configFileName=" + config);
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller u = jc.createUnmarshaller();
            is = new FileInputStream(config);
            return (T) u.unmarshal(is);
        } catch (IOException e) {
            // logger.trace(config, e);
            throw e;
        } catch (JAXBException e) {
            // logger.trace(config, e);
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readConfigFromStream(Class<T> clazz, InputStream dataStream) throws JAXBException {
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller u = jc.createUnmarshaller();
            return (T) u.unmarshal(dataStream);
        } catch (JAXBException e) {
            // logger.trace(e);
            throw e;
        }
    }

    public static void main(String[] args) throws JAXBException, IOException {
        SheetDefinition sheetDefinition = JaxbReadXml.readString(SheetDefinition.class, "D:\\Work Directory\\workspase\\ExcelMapping\\src\\test\\resources\\sheetconfig.xml");
        System.out.println(sheetDefinition);
        InputStream inputStream = new FileInputStream("D:\\Work Directory\\workspase\\ExcelMapping\\src\\test\\resources\\sheetconfig.xml");
        SheetMapping<OrderSkuDetail> sheetMapping = SheetMappingHandler.newInstance(inputStream);
        System.out.println(sheetMapping);
//        JAXBContext jaxbContext = JAXBContext.newInstance(ExcelMappingDefinition.class);
//        Marshaller marshaller = jaxbContext.createMarshaller();
//        ExcelMappingDefinition excelMappingDefinition = new ExcelMappingDefinition();
//        SheetDefinition sheetDefinition = new SheetDefinition();
//
//
//        FieldDefinition fieldDefinition;
//        List<FieldDefinition> fieldDefinitions = new ArrayList<FieldDefinition>();
//        for(int i=0;i<10;i++){
//            fieldDefinition = new FieldDefinition();
//            fieldDefinition.setName("name_"+i);
//            fieldDefinition.setTitle("title_"+i);
//            fieldDefinitions.add(fieldDefinition);
//        }
//        sheetDefinition.setFieldDefinitions(fieldDefinitions);
//        List<SheetDefinition> sheetDefinitions = new ArrayList<SheetDefinition>();
//        sheetDefinitions.add(sheetDefinition);
//        excelMappingDefinition.setSheetDefinitions(sheetDefinitions);
//        marshaller.marshal(excelMappingDefinition,new File("D:\\a.xml"));
    }
}

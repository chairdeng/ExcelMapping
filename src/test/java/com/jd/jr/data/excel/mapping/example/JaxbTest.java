package com.jd.jr.data.excel.mapping.example;

import com.jd.jr.data.excel.mapping.definition.SheetDefinition;
import com.jd.jr.data.excel.mapping.utils.JAXBUtils;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/9 18:42
 * *****************************************
 */
public class JaxbTest {


    public static void main(String[] args){
        InputStream file = null;
        try {
            file = new FileInputStream("D:\\Work Directory\\workspase\\loan-dist\\SheetMapping\\src\\test\\resources\\sheetconfig.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SheetDefinition emd = null;
        try {
            emd = JAXBUtils.fromXML(file,SheetDefinition.class);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        System.out.println(emd);
    }
}

package com.jd.jr.data.excel.mapping.example;

import groovy.lang.GroovyShell;

import java.util.Map;

/**
 * Created by dengc on 2017/3/12.
 */
public class GroovyExpressionTest {
    public static void main(String[] args){
        GroovyShell shell = new GroovyShell();
        Map map = (Map)shell.evaluate("[true:'1',false:'2']");
        System.out.println(map);
    }
}

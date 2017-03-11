package com.jd.jr.data.excel.mapping.exceptions;

/**
 * *****************************************
 * 由xml或注解定义错误引发的异常
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/10 11:59
 * *****************************************
 */
public class DefinitionException extends RuntimeException {
    public DefinitionException(){
        super();
    }
    public DefinitionException(String message){
        super(message);
    }
    public DefinitionException(String message,Throwable cause){
        super(message,cause);
    }
    public DefinitionException(Throwable cause){
        super(cause);
    }
}

package com.jd.jr.data.excel.mapping.exceptions;

/**
 * Created by dengc on 2017/3/18.
 */
public class FormatterException extends Exception {
    public FormatterException(){
        super();
    }
    public FormatterException(String message){
        super(message);
    }
    public FormatterException(String message,Throwable cause){
        super(message,cause);
    }
    public FormatterException(Throwable cause){
        super(cause);
    }
}

package jd.jr.data.excel.mapping.exceptions;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/10 18:16
 * *****************************************
 */
public class MappingException extends RuntimeException{
    public MappingException(){
        super();
    }
    public MappingException(String message){
        super(message);
    }
    public MappingException(String message,Throwable cause){
        super(message,cause);
    }
    public MappingException(Throwable cause){
        super(cause);
    }
}

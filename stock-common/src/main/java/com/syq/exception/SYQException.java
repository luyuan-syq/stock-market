package com.syq.exception;


public class SYQException extends RuntimeException{

  /**
   * 
   * created by Shaoyuqi on 2016年10月4日 上午9:17:28
   */
  private static final long serialVersionUID = 2391748055726295777L;

  public SYQException() {
    super();
  }

  public SYQException(String message, Throwable cause) {
    super(message, cause);
  }

  public SYQException(String message) {
    super(message);
  }

  public SYQException(Throwable cause) {
    super(cause);
  }
    
   
    
}

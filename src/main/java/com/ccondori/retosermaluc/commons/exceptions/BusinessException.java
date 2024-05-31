package com.ccondori.retosermaluc.commons.exceptions;

public class BusinessException extends RuntimeException{
    public BusinessException(String s){ super("Error BE - ".concat(s));}
    public BusinessException(){ super("Error BE!");}
}

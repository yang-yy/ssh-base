package com.microsoft.msdn.util.service;


import java.util.Map;


public interface BaseService {

    public int checkValue(int value, Class eClass) throws Exception;

    public String checkValue(String value, Class eClass) throws Exception;

    public Map enumToMap(Class eClass) throws Exception;
}

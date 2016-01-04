package com.microsoft.msdn.util.service;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class BaseServiceImpl implements BaseService {

    protected ThreadPoolExecutor threadPool;

    private String gwIp;

    private String userId;

    private String tableId;

    private String channelTable;

    private String tableAccessKey;

    private String isMonth;
 
    private String status;

    private String productNum;
    
    private String adivseCount;
    private String betweenTime ;
    @Override
    public int checkValue(int value, Class eClass) throws Exception {
        Integer v = null;
        boolean find = false;
        if ((eClass == null) || (eClass.isEnum() == false))
            throw new Exception();
        for (Object o : eClass.getEnumConstants()) {
            v = (Integer) o.getClass().getMethod("getValue").invoke(o);
            if (v.intValue() != value)
                continue;
            find = true;
            break;
        }
        if (find == false)
            throw new Exception();
        return value;
    }

    @Override
    public String checkValue(String value, Class eClass) throws Exception {
        String v = null;
        boolean find = false;
        if ((eClass == null) || (eClass.isEnum() == false))
            throw new Exception();
        for (Object o : eClass.getEnumConstants()) {
            v = (String) o.getClass().getMethod("getValue").invoke(o);
            if (v.equals(value) == false)
                continue;
            find = true;
            break;
        }
        if (find == false)
            throw new Exception();
        return value;
    }

    @Override
    public Map enumToMap(Class eClass) throws Exception {
        Map<Object, String> rtn = new LinkedHashMap<Object, String>();
        if ((eClass == null) || (eClass.isEnum() == false))
            throw new Exception();
        Object value = null;
        String desc = null;
        for (Object o : eClass.getEnumConstants()) {
            value = o.getClass().getMethod("getValue").invoke(o);
            desc = (String) o.getClass().getMethod("getDesc").invoke(o);
            if (value == null) continue;
            if (StringUtils.isBlank(desc)) continue;
            rtn.put(value, desc);
        }

        return rtn;
    }
    
    

    public String getBetweenTime() {
		return betweenTime;
	}

	public void setBetweenTime(String betweenTime) {
		this.betweenTime = betweenTime;
	}

	public String getAdivseCount() {
		return adivseCount;
	}

	public void setAdivseCount(String adivseCount) {
		this.adivseCount = adivseCount;
	}

	public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public String getGwIp() {
        return gwIp;
    }

    public void setGwIp(String gwIp) {
        this.gwIp = gwIp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getChannelTable() {
        return channelTable;
    }

    public void setChannelTable(String channelTable) {
        this.channelTable = channelTable;
    }

    public String getTableAccessKey() {
        return tableAccessKey;
    }

    public void setTableAccessKey(String tableAccessKey) {
        this.tableAccessKey = tableAccessKey;
    }

    public String getMonth() {
        return isMonth;
    }

    public void setMonth(String month) {
        isMonth = month;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }
}

package com.microsoft.msdn.util.service.bean;

import java.io.Serializable;
import java.util.List;

public class TCompanyInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String Com_ID;
	
	private String Com_Name;

	private List<TDeptInfo> dpt;

	public String getCom_ID() {
		return Com_ID;
	}

	public void setCom_ID(String com_ID) {
		Com_ID = com_ID;
	}

	public String getCom_Name() {
		return Com_Name;
	}

	public void setCom_Name(String com_Name) {
		Com_Name = com_Name;
	}

	public List<TDeptInfo> getDpt() {
		return dpt;
	}

	public void setDpt(List<TDeptInfo> dpt) {
		this.dpt = dpt;
	}
}

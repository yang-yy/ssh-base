package com.microsoft.msdn.util.service.bean;


import java.io.Serializable;
import java.util.List;

public class TDeptInfo implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private String Dpt_ID;

    private String Dpt_Name;

    private List<TUserInfo> user;

	public String getDpt_ID() {
		return Dpt_ID;
	}

	public void setDpt_ID(String dpt_ID) {
		Dpt_ID = dpt_ID;
	}

	public String getDpt_Name() {
		return Dpt_Name;
	}

	public void setDpt_Name(String dpt_Name) {
		Dpt_Name = dpt_Name;
	}

	public List<TUserInfo> getUser() {
		return user;
	}

	public void setUser(List<TUserInfo> user) {
		this.user = user;
	}

}
